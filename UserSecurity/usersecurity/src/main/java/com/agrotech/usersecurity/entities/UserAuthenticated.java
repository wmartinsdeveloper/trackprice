package com.agrotech.usersecurity.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserAuthenticated implements UserDetails {

    private final Users user;

    public UserAuthenticated(Users user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return List.of(() -> "read");
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return user.getNome();
    }

    @Override
    public boolean isAccountNonExpired() {
        if (user.isAccountNonExpired() == true) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean isAccountNonLocked() {
        if (user.isAccountNonLocked() == true) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isCredentialsNonExpired() {
        if (user.isCredentialsNonExpired() == true) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isEnabled() {
        if (user.isEnabled() == true) {
            return true;
        } else {
            return false;
        }
    }

}
