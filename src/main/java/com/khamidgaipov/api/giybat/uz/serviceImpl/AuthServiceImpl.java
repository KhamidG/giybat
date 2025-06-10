package com.khamidgaipov.api.giybat.uz.serviceImpl;

import com.khamidgaipov.api.giybat.uz.dto.AuthDto;
import com.khamidgaipov.api.giybat.uz.dto.ProfileDto;
import com.khamidgaipov.api.giybat.uz.dto.RegistrationDto;
import com.khamidgaipov.api.giybat.uz.entity.ProfileEntity;
import com.khamidgaipov.api.giybat.uz.entity.ProfileRoleEntity;
import com.khamidgaipov.api.giybat.uz.enums.GeneralStatus;
import com.khamidgaipov.api.giybat.uz.enums.ProfileRole;
import com.khamidgaipov.api.giybat.uz.exception.AppBadException;
import com.khamidgaipov.api.giybat.uz.repository.ProfileRepository;
import com.khamidgaipov.api.giybat.uz.repository.ProfileRoleRepository;
import com.khamidgaipov.api.giybat.uz.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    BCryptPasswordEncoder bc;
    @Autowired
    ProfileRoleServiceImpl profileRoleService;
    @Autowired
    EmailSenderServiceImpl senderService;
    @Autowired
    ProfileServiceImpl profileService;
    @Autowired
    ProfileRoleRepository profileRoleRepository;

    public String registration(RegistrationDto dto) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isPresent()) {
            ProfileEntity entity = optional.get();
            if (entity.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                profileRoleService.deleteRoles(entity.getId());
                profileRepository.delete(entity);
                // sms todo
            } else {
                throw new AppBadException("Username already exists");
            }
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setPassword(bc.encode(dto.getPassword()));
        entity.setStatus(GeneralStatus.IN_REGISTRATION);
        entity.setVisible(true);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);

        profileRoleService.create(entity.getId(), ProfileRole.ROLE_USER);
        senderService.sendRegistrationEmail(dto.getUsername(), entity.getId());
        return "Successfully registered";
    }

    public String regVerification(String token) {
        Long profileId = JwtUtil.decodeRegVerToken(token);
        try {
            ProfileEntity profile = profileService.getById(profileId);
            if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                profileRepository.changeStatus(profileId, GeneralStatus.ACTIVE);
                return "Successfully activated";
            }
        } catch (JwtException e) {
        }

        throw new AppBadException("Registration failed");
    }

    public ProfileDto login(AuthDto dto) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isEmpty()) {
            throw new AppBadException("Username or password is wrong");
        }
        ProfileEntity entity = optional.get();
        if (bc.matches(dto.getPassword(), entity.getPassword())){
            throw new AppBadException("Username or password is wrong");
        }
        if (!entity.getStatus().equals(GeneralStatus.ACTIVE)){
            throw new AppBadException("Wrong status");
        }

        ProfileDto response = new ProfileDto();
        response.setName(entity.getName());
        response.setUsername(entity.getUsername());
        response.setRoleList(profileRoleRepository.getAllRolesListNyProfileId(entity.getId()));

        response.setToken();

        return null;
    }
}
