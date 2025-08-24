package com.khamidgaipov.api.giybat.uz.service;

import com.khamidgaipov.api.giybat.uz.dto.RegistrationDto;
import com.khamidgaipov.api.giybat.uz.entity.ProfileEntity;
import com.khamidgaipov.api.giybat.uz.enums.GeneralStatus;
import com.khamidgaipov.api.giybat.uz.exps.AppBadException;
import com.khamidgaipov.api.giybat.uz.repository.ProfileRepository;
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

    public String registration(RegistrationDto dto) {
        // 1. validation
        // 2. check email
        profileRepository.findByUsernameAndVisibleTrue(dto.getUsername())
                .ifPresent(profileEntity -> {
                    if (GeneralStatus.IN_REGISTRATION.equals(profileEntity.getStatus())) {
                        throw new AppBadException("User registration is not finished yet");
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

        return "Successfully registered.";
    }
}
