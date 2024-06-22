package com.agrotech.usersecurity.services;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtEncodingException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import com.agrotech.usersecurity.Security.SecurityConstants;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class JwtTokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final Long getTokenExpireIn;

    /*
     * Constructor for JwtTokenService.
     * 
     * @param jwtEncoder JwtEncoder instance for encoding JWT tokens.
     * 
     * @param jwtDecoder JwtDecoder instance for decoding JWT tokens.
     * 
     * @param getTokenExpireIn Long value representing the token expiration time in
     * seconds.
     */

    public JwtTokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, Long getTokenExpireIn) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.getTokenExpireIn = getTokenExpireIn;
    }

    /*
     * Creates a new JWT token based on the provided authentication object.
     * 
     * @param authentication Authentication object containing user credentials and
     * authorities.
     * 
     * @return A string representing the generated JWT token.
     * 
     * @throws JwtEncodingException If an error occurs during token creation.
     * 
     * Example:
     * <pre>
     * Authentication authentication = new
     * UsernamePasswordAuthenticationToken("username", "password",
     * AuthorityUtils.createAuthorityList("ROLE_USER"));
     * String token = jwtTokenService.TokenCreation(authentication);
     * </pre>
     */

    public String TokenCreation(Authentication authentication) {

        try {
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

        } catch (JwtEncodingException exception) {
            throw new JwtEncodingException("Error to create token: " + exception.getMessage());
        }
    }

    /*
     * Validates a JWT token and returns an Authentication object if the token is
     * valid.
     * 
     * @param request HttpServletRequest object containing the JWT token in the
     * header.
     * 
     * @return An Authentication object containing the user credentials and
     * authorities if the token is valid.
     * 
     * @throws JwtException If the token is invalid or cannot be decoded.
     * 
     * Example:
     * <pre>
     * HttpServletRequest request =...;
     * Authentication authentication = jwtTokenService.TokenValidadtion(request);
     * </pre>
     */

    public Authentication TokenValidadtion(HttpServletRequest request) {

        if (request.getHeader(SecurityConstants.JWT_HEADER).split(" ")[1].trim() != null) {
            try {

                String token = request.getHeader(SecurityConstants.JWT_HEADER).split(" ")[1].trim();

                String username = extractUserName(token);
                List<GrantedAuthority> authorities = extractAuthoroties(token);

                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
                return auth;

            } catch (JwtException exception) {
                throw new JwtException("Error to validate Jwt Token. Invalid Token received.");
            }
        } else {
            return null;
        }
    }

    /*
     * Extracts the username from a JWT token.
     * 
     * @param token The JWT token to extract the username from.
     * 
     * @return The extracted username.
     */

    public String extractUserName(String token) {
        return (String) jwtDecoder.decode((String) token).getClaims().get("username");
    }

    /*
     * Extracts the authorities from a JWT token.
     * 
     * @param token The JWT token to extract the authorities from.
     * 
     * @return A list of authorities extracted from the token.
     */

    public List<GrantedAuthority> extractAuthoroties(String token) {
        String authorityString = (String) jwtDecoder.decode(token).getClaims().get("authorities");
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString);

    }

    /*
     * Populates a string with the authorities from the provided collection.
     * 
     * @param collection Collection of GrantedAuthority objects.
     * 
     * @return A string representing the authorities, separated by commas.
     */

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }

}
