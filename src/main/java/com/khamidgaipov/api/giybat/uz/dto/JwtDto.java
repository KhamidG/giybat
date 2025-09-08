package com.khamidgaipov.api.giybat.uz.dto;

import com.khamidgaipov.api.giybat.uz.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class JwtDto {
    private Long id;
    private String username;
    private List<ProfileRole> roleList;

    public JwtDto(Long id, String username, List<ProfileRole> roleList) {
        this.id = id;
        this.username = username;
        this.roleList = roleList;
    }
}
