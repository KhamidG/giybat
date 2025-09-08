package com.khamidgaipov.api.giybat.uz.service;

import com.khamidgaipov.api.giybat.uz.dto.AppResponse;
import com.khamidgaipov.api.giybat.uz.dto.AuthDto;
import com.khamidgaipov.api.giybat.uz.dto.ProfileDto;
import com.khamidgaipov.api.giybat.uz.dto.RegistrationDto;
import com.khamidgaipov.api.giybat.uz.entity.ProfileEntity;
import com.khamidgaipov.api.giybat.uz.enums.GeneralStatus;
import com.khamidgaipov.api.giybat.uz.enums.ProfileRole;
import com.khamidgaipov.api.giybat.uz.exps.AppBadException;
import com.khamidgaipov.api.giybat.uz.repository.ProfileRepository;
import com.khamidgaipov.api.giybat.uz.repository.ProfileRoleRepository;
import com.khamidgaipov.api.giybat.uz.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class AuthService {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    BCryptPasswordEncoder bc;

    @Autowired
    ProfileRoleService profileRoleService;

    @Autowired
    ProfileRoleRepository profileRoleRepository;

    @Autowired
    EmailSendService sendService;

    @Autowired
    ProfileService profileService;

    public AppResponse<String> registration(RegistrationDto dto) {
        // 1. validation
        // 2. check email
        profileRepository.findByUsernameAndVisibleTrue(dto.getUsername())
                .ifPresent(profileEntity -> {
                    if (!GeneralStatus.IN_REGISTRATION.equals(profileEntity.getStatus())) {
                        profileRoleService.deleteRoles(profileEntity.getId());
                        profileRepository.delete(profileEntity);
                    } else {
                        throw new AppBadException("Username already exists");
                    }
                });

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setPassword(bc.encode(dto.getPassword()));
        entity.setStatus(GeneralStatus.IN_REGISTRATION);
        entity.setVisible(true);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);
        profileRoleService.create(entity.getId(), ProfileRole.ROLE_USER);

        sendService.sendRegEmail(dto.getUsername(), entity.getId());
        return new AppResponse<>("Successfully registered.");
    }

    public String verification(String token) {
        try {
            Long profileId = JwtUtil.decodeRegVerfToken(token);
            ProfileEntity entity = profileService.getById(profileId);
            if (!entity.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                throw new AppBadException("Verification failed");
            }
            profileRepository.changeStatus(profileId, GeneralStatus.ACTIVE);
        } catch (JwtException e) {
        }
        return "Successfully activated!";
    }

    public ProfileDto login(AuthDto dto) {
        //dto check
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isEmpty()) {
            throw new AppBadException("Username or password is wrong");
        }
        ProfileEntity profile = optional.get();
        if (!bc.matches(dto.getPassword(), profile.getPassword())) {
            throw new AppBadException("Username or password is wrong");
        }
        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadException("Wrong status");
        }
        //response

        ProfileDto response = new ProfileDto();
        response.setName(profile.getName());
        response.setUsername(profile.getUsername());
        response.setRoleList(profileRoleRepository.getAllRolesListByProfileId(profile.getId()));
        response.setJwt(JwtUtil.encode(profile.getUsername(), profile.getId(), response.getRoleList()));

        return response;
    }
}
