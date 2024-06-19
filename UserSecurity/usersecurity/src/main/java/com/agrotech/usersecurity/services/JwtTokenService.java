package com.agrotech.usersecurity.services;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;

import com.agrotech.usersecurity.Security.SecurityConstants;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.proc.JWTClaimsSetVerifier;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SigningKeyResolver;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtTokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final Long getTokenExpireIn;

    public JwtTokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, Long getTokenExpireIn) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.getTokenExpireIn = getTokenExpireIn;
    }

    public String TokenCreation(Authentication authentication) {

        var now = Instant.now();
        var claims = JwtClaimsSet.builder()
                .issuer("agrotech")
                .subject("JWT Token")
                .claim("username", authentication.getName())
                .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                .issuedAt(now)
                .expiresAt(now.plusSeconds(getTokenExpireIn))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Authentication TokenValidadtion(HttpServletRequest request) {
        try {

            String token = request.getHeader(SecurityConstants.JWT_HEADER).split(" ")[1].trim();
            String aux = jwtDecoder.decode(token).getClaims().toString();
            Collection<Object> aux1 = jwtDecoder.decode(token).getClaims().values();
            String aux2 = (String) jwtDecoder.decode(token).getClaims().get("iss");

            // "{iss=agrotech, sub=JWT Token, exp=2024-06-19T19:21:17Z,
            // iat=2024-06-19T19:16:17Z, authorities=ADMIN,USERS, username=admin}"

            String username = (String) jwtDecoder.decode(token).getClaims().get("username");
            String authorities = (String) jwtDecoder.decode(token).getClaims().get("authorities");

            Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
                    AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
            return auth;
        } catch (Exception e) {
            // throw new BadCredentialsException("Invalid Token received!");
            throw new BadCredentialsException(e.getMessage());
        }

    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }

}
