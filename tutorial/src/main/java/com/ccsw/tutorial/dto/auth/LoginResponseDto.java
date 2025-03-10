package com.ccsw.tutorial.dto.auth;

public class LoginResponseDto {

    private String token;

    public LoginResponseDto() {
    }

    public LoginResponseDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
