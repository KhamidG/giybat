package com.khamidgaipov.api.giybat.uz.dto;

import com.khamidgaipov.api.giybat.uz.enums.ProfileRole;
import lombok.Data;

import java.util.List;

@Data
public class ProfileDto {
    private String name;
    private String username;
    private List<ProfileRole> roleList;
    private String jwt;
}
