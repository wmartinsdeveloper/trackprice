package com.agrotech.usersecurity.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.agrotech.usersecurity.Security.SecurityConstants;
import com.agrotech.usersecurity.services.JwtTokenService;

import java.io.IOException;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final Long getTokenExpireIn;

    public JWTTokenValidatorFilter(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, Long getTokenExpireIn) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.getTokenExpireIn = getTokenExpireIn;
        jwtTokenService = new JwtTokenService(this.jwtEncoder, this.jwtDecoder, this.getTokenExpireIn);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        if (null != request.getHeader(SecurityConstants.JWT_HEADER)) {
            try {

                SecurityContextHolder.getContext().setAuthentication(jwtTokenService.TokenValidadtion(request));
            } catch (Exception e) {
                // throw new BadCredentialsException("Invalid Token received!");
                throw new BadCredentialsException(e.getMessage());
            }

        }
        filterChain.doFilter(request, response);

    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().equals("/login");
    }

    // @Override
    // protected void doFilterInternal(HttpServletRequest request,
    // HttpServletResponse response,
    // FilterChain filterChain) throws ServletException, IOException {

    // if (null != request.getHeader(SecurityConstants.JWT_HEADER)) {
    // try {

    // String jwt = request.getHeader(SecurityConstants.JWT_HEADER).split("
    // ")[1].trim();

    // SecretKey key =
    // Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

    // Claims claims = Jwts.parserBuilder()
    // .setSigningKey(key)
    // .build()
    // .parseClaimsJws(jwt)
    // .getBody();

    // String username = String.valueOf(claims.get("username"));
    // String authorities = (String) claims.get("authorities");

    // Authentication auth = new UsernamePasswordAuthenticationToken(username,
    // null,AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
    // SecurityContextHolder.getContext().setAuthentication(auth);
    // } catch (Exception e) {
    // //throw new BadCredentialsException("Invalid Token received!");
    // throw new BadCredentialsException(e.getMessage());
    // }

    // }
    // filterChain.doFilter(request, response);

    // }

}
