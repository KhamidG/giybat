package com.khamidgaipov.api.giybat.uz.entity;

import com.khamidgaipov.api.giybat.uz.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "profile")
@Entity
@Data
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    private GeneralStatus status;

    private Boolean visible
            = Boolean.TRUE;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "profile")
    private List<ProfileRoleEntity> roleList;
}
