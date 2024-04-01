package com.agrotech.usersecurity.controller.dto;

public record LoginResponse(String accessToken, Long expiresIn) {

}
