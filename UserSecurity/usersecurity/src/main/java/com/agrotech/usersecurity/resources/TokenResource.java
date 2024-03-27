package com.agrotech.usersecurity.resources;

import java.time.Instant;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.RestController;

import com.agrotech.usersecurity.resources.dto.LoginRequest;
import com.agrotech.usersecurity.resources.dto.LoginResponse;
import com.agrotech.usersecurity.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class TokenResource {

    private final JwtEncoder jwtEncoder;

    private final UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public TokenResource(JwtEncoder jwtEncoder, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var user = userService.Login(loginRequest.username());
        if (user.isEmpty() || user.get().isLoginMatch(loginRequest, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("User o password is invalid!");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var claims = JwtClaimsSet.builder()
                .issuer("UserSecurity")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));

    }

}
