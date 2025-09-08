package com.khamidgaipov.api.giybat.uz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AppResponse <T>{
    private T data;
}
