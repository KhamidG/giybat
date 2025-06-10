package com.khamidgaipov.api.giybat.uz.dto;

import com.khamidgaipov.api.giybat.uz.enums.GeneralStatus;
import com.khamidgaipov.api.giybat.uz.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProfileDto {
    private String name;
    private String username;
    private List<ProfileRole> roleList;
    private String token;
}
