package com.agrotech.usersecurity.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.agrotech.usersecurity.entities.UserAuthenticated;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserDetails usr = userService.findByEmail(email).map(user -> new UserAuthenticated(user))
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + email));

        if (usr.isAccountNonExpired() == true && usr.isAccountNonLocked() && usr.isCredentialsNonExpired()
                && usr.isEnabled()) {
            return usr;
        } else {
            return (UserDetails) new UsernameNotFoundException("User Not Found with username: " + email);
        }

    }

}