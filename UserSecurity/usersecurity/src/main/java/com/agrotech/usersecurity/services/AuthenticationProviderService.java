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

    /*
     * Authenticates a user based on the provided email and password.
     *
     * @param authentication the authentication object containing the email and
     * password
     * 
     * @return an authenticated user object if the credentials are valid, null
     * otherwise
     * 
     * @throws AuthenticationException if the authentication fails
     */

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            UserDetails user = usuarioService.findByEmail(authentication.getName());
            if (user != null) {
                if (passwordEncoder.matches((String) authentication.getCredentials(), user.getPassword())) {
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

    /**
     * Checks if this authentication provider supports the given authentication
     * class.
     *
     * @param authentication the authentication class to check
     * @return true if this provider supports the given class, false otherwise
     */

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
