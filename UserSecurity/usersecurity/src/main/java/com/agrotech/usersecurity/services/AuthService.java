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
public class AuthService implements UserDetailsService {
    private final UsuarioService userService;

    public AuthService(UsuarioService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {
            UserDetails user = userService.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException(email);
            } else {
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }

    }

}