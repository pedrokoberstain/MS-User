package com.compassuol.challenge3.User.web.dto;

public record AuthenticationDto(String email, String password) {


    public String getEmail() {
        return email;
    }
}

