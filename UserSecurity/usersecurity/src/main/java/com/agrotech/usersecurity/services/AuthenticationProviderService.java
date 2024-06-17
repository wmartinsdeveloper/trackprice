package com.agrotech.usersecurity.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProviderService implements AuthenticationProvider {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            UserDetails user = usuarioService.findByEmail(authentication.getName());
            if (user != null) {
                if (passwordEncoder.matches((String) authentication.getCredentials(), user.getPassword())) {
                    // List<GrantedAuthority> authorities = new ArrayList<>();
                    // authorities.add(new SimpleGrantedAuthority(user.getAuthorities()));
                    return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
                } else {
                    throw new BadCredentialsException("Invalid password ! Check it and try again.");
                }
            } else {
                throw new UsernameNotFoundException("User not found ! Check your email and try again.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }



}
