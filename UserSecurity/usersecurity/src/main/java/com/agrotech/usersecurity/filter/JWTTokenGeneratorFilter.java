package com.agrotech.usersecurity.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.agrotech.usersecurity.Security.SecurityConstants;
import com.agrotech.usersecurity.services.JwtTokenService;
import java.io.IOException;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final Long getTokenExpireIn;

    public JWTTokenGeneratorFilter(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, Long getTokenExpireIn) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.getTokenExpireIn = getTokenExpireIn;
        jwtTokenService = new JwtTokenService(this.jwtEncoder, this.jwtDecoder, this.getTokenExpireIn);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (null != SecurityContextHolder.getContext().getAuthentication()) {
            response.setHeader(SecurityConstants.JWT_HEADER,
                    jwtTokenService.TokenCreation(SecurityContextHolder.getContext().getAuthentication()));
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getServletPath().equals("/login");
    }

    // @Override
    // protected void doFilterInternal(HttpServletRequest request,
    // HttpServletResponse response, FilterChain filterChain)
    // throws ServletException, IOException {
    // Authentication authentication =
    // SecurityContextHolder.getContext().getAuthentication();

    // if(null != authentication){
    // SecretKey key =
    // Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

    // String jwt = Jwts.builder()
    // .setIssuer("agrotech")
    // .setSubject("JWT Token")
    // .claim("username",authentication.getName())
    // .claim("authorities", populateAuthorities(authentication.getAuthorities()))
    // .setIssuedAt(new Date())
    // .setExpiration(new Date((new Date()).getTime() + 30000000))
    // .signWith(key).compact();
    // response.setHeader(SecurityConstants.JWT_HEADER, jwt);

    // }
    // filterChain.doFilter(request, response);

    // }

    // private String populateAuthorities(Collection<? extends GrantedAuthority>
    // collection) {
    // Set<String> authoritiesSet = new HashSet<>();
    // for (GrantedAuthority authority : collection) {
    // authoritiesSet.add(authority.getAuthority());
    // }
    // return String.join(",", authoritiesSet);
    // }

}
