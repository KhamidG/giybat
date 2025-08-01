package com.khamidgaipov.api.giybat.uz.entity;

import com.khamidgaipov.api.giybat.uz.enums.ProfileRole;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "profile_role")
@Data
public class ProfileRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @Column(name = "profile_id",insertable = false, updatable = false)
    private Long profileId;

    @Enumerated(EnumType.STRING)
    private ProfileRole roles;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
