package com.khamidgaipov.api.giybat.uz.repository;

import com.khamidgaipov.api.giybat.uz.entity.ProfileRoleEntity;
import com.khamidgaipov.api.giybat.uz.enums.ProfileRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfileRoleRepository extends JpaRepository<ProfileRoleEntity, Long> {
    @Transactional
    @Modifying
    void deleteByProfileId(Long id);

    @Query("select p.roles From ProfileRoleEntity p where p.profileId = ?1")
    List<ProfileRole> getAllRolesListByProfileId(Long profileId);
}
