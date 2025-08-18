package com.khamidgaipov.api.giybat.uz.service;

import com.khamidgaipov.api.giybat.uz.dto.RegistrationDto;
import com.khamidgaipov.api.giybat.uz.entity.ProfileEntity;
import com.khamidgaipov.api.giybat.uz.enums.GeneralStatus;
import com.khamidgaipov.api.giybat.uz.exps.AppBadException;
import com.khamidgaipov.api.giybat.uz.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final ProfileRepository profileRepository;

    public String registration(RegistrationDto dto) {
        // 1. validation
        // 2. check email
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isPresent()) {
            ProfileEntity profile = optional.get();
            if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                profileRepository.delete(profile);
                // sms/email send
            } else {
                throw new AppBadException("Username already exception.");
            }
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword()); // TODO ""bcrypt""
        ///  check profile status. LAST VIDEO


        return "Successfully registered.";
    }
}
