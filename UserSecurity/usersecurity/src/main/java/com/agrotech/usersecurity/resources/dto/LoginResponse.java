package com.agrotech.usersecurity.resources.dto;

public record LoginResponse(String accessToken, long expiresIn) {

}
