package com.sertifikasi.learn.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private int id;
    private String token;
    private String type = "Bearer";
    private String username;
    private String role;

    public JwtResponse(String token, int id, String username, String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.role = role;
    }
}
