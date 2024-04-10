package com.agrotech.usersecurity.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/* Classe responsavél em receber a requisição de autenticação do usuário e iniciar o processo de validação
 * da credenciais
 *  
 * Por conta da implentação da interface UserDetailsService e a anotação service
 */

@Service
public class OuthService implements UserDetailsService {
    private final UsuarioService userService;

    public OuthService(UsuarioService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userService.findByEmail(email);
    }

}