package com.khamidgaipov.api.giybat.uz.service;

import com.khamidgaipov.api.giybat.uz.entity.ProfileEntity;
import com.khamidgaipov.api.giybat.uz.entity.ProfileRoleEntity;
import com.khamidgaipov.api.giybat.uz.enums.ProfileRole;
import com.khamidgaipov.api.giybat.uz.repository.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProfileRoleService {
    @Autowired
    private ProfileRoleRepository repository;

    public void create(Long profileId, ProfileRole role) {
        ProfileEntity profile = new ProfileEntity();
        profile.setId(profileId);

        ProfileRoleEntity entity = new ProfileRoleEntity();
        entity.setProfile(profile);  // связываем
        entity.setRoles(role);
        entity.setCreatedDate(LocalDateTime.now());
        repository.save(entity);
    }

    public void deleteRoles(Long profileId) {
        repository.deleteByProfileId(profileId);
    }
}
