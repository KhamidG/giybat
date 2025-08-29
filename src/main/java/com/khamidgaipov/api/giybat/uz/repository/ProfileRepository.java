package com.khamidgaipov.api.giybat.uz.repository;

import com.khamidgaipov.api.giybat.uz.entity.ProfileEntity;
import com.khamidgaipov.api.giybat.uz.enums.GeneralStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Long> {
    // select * from profile where username =? and visible = true;
    Optional<ProfileEntity> findByUsernameAndVisibleTrue(String username);

    Optional<ProfileEntity> findByIdAndVisibleTrue(Long id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status =?2 where id = ?1")
    void changeStatus(Long id, GeneralStatus status);
}
