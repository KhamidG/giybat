package com.khamidgaipov.api.giybat.uz.repository;

import com.khamidgaipov.api.giybat.uz.entity.ProfileRoleEntity;
import com.khamidgaipov.api.giybat.uz.enums.ProfileRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity, Long> {
    @Transactional
    @Modifying
    void deleteByProfileId(Long profileId);

    @Query("select p.roles From ProfileEntity p where p.profileId = ?1")
    List<ProfileRole> getAllRolesListNyProfileId(Long profileId);
}
