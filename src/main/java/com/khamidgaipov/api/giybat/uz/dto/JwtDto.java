package com.khamidgaipov.api.giybat.uz.dto;

import com.khamidgaipov.api.giybat.uz.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtDto {
    private Long id;
    private List<ProfileRole> roleList;
}
