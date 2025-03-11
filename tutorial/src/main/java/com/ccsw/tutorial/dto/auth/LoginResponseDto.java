package com.ccsw.tutorial.dto.auth;

public class LoginResponseDto {

    private String token;
    private String username;

    public LoginResponseDto() {
    }

    public LoginResponseDto(String token, String username) {
        this.token = token;
        this.username = username;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
