package com.khamidgaipov.api.giybat.uz.util;

import com.khamidgaipov.api.giybat.uz.dto.JwtDto;
import com.khamidgaipov.api.giybat.uz.enums.ProfileRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

public class JwtUtil {
    private static final String secretKey = "veryLongSecretmazgillattayevlasharaaxmojonjinnijonsurbetbekkiydirhonuxlatdibekloxovdangasabekochkozjonduxovmashaynikmaydagapchishularnioqiganbolsangizgapyoqaniqsizmazgi";

    public static String encode(Long id, List<ProfileRole> role) {
        String roleList = role.stream()
                .map(item -> item.name())
                .collect(Collectors.joining(","));
        String.join(",", roleList);

        Map<String, String> claims = new HashMap<>();
        claims.put("role", roleList);
        return Jwts
                .builder()
                .subject(String.valueOf(id))
                .setClaims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)))
                .signWith(getSignInKey())
                .compact();
    }

    public static JwtDto decode(String token) {
        Claims claims = Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long id = Long.valueOf(claims.getSubject());
        String strRoleList = (String) claims.get("role");

        String[] roleArr = strRoleList.split(",");

        List<ProfileRole> roleLis = new ArrayList<>();
        for (String role : roleArr) {
            roleLis.add(ProfileRole.valueOf(role));
        }
        return new JwtDto(id, roleLis);
    }


    public static String encode(Long id) {
        return Jwts
                .builder()
                .subject(String.valueOf(id))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)))
                .signWith(getSignInKey())
                .compact();
    }

    public static Long decodeRegVerfToken(String token) {
        Claims claims = Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claims.getSubject());
    }

    private static SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
