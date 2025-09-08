package com.khamidgaipov.api.giybat.uz.controller;

import com.khamidgaipov.api.giybat.uz.dto.AppResponse;
import com.khamidgaipov.api.giybat.uz.dto.AuthDto;
import com.khamidgaipov.api.giybat.uz.dto.ProfileDto;
import com.khamidgaipov.api.giybat.uz.dto.RegistrationDto;
import com.khamidgaipov.api.giybat.uz.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<AppResponse<String>> registration(@Valid @RequestBody RegistrationDto dto) {
        return ResponseEntity.ok().body(authService.registration(dto));
    }

    @GetMapping("/registration/verification/{token}")
    public ResponseEntity<String> verification(@PathVariable("token") String token) {
        return ResponseEntity.ok().body(authService.verification(token));
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDto> login(@Valid @RequestBody AuthDto dto) {
        return ResponseEntity.ok().body(authService.login(dto));
    }
}
