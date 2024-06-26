package com.agrotech.usersecurity.dto;

import java.util.Set;

public record dtoUsuario(Long id, String username,String email, String password,boolean isAccountNonExpired,boolean isAccountNonLocked,boolean isCredentialsNonExpired,boolean isEnabled,String activationKey,Set<dtoGrupo> grupo) {

}
