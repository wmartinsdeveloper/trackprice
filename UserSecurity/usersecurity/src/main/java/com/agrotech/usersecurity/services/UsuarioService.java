package com.agrotech.usersecurity.services;

import java.util.Set;
import java.util.UUID;
import java.util.List;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.agrotech.usersecurity.dto.dtoGrupo;
import com.agrotech.usersecurity.dto.dtoUsuario;
import com.agrotech.usersecurity.entities.Grupo;
import com.agrotech.usersecurity.entities.Usuario;
import com.agrotech.usersecurity.repositories.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private GrupoService grupoService;

    @Autowired
    MailService emailSender;

    public Usuario buscaUsuarioEmail(String email) throws UsernameNotFoundException, Exception {

        try {
            Usuario savedUsuario = userRepository.findByEmail(email);
            if (savedUsuario != null) {
                return savedUsuario;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Something went wrong, detail about that: " + e.getMessage());
        }

    }

    public List<Usuario> buscaTodosusuarios() throws UsernameNotFoundException, Exception {

        try {
            List<Usuario> user = userRepository.findAll();
            if (user != null) {
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Something went wrong, detail about that: " + e.getMessage());
        }

    }

    public void novoUsuario(dtoUsuario usuario) throws Exception{

        Usuario savedUsuario = userRepository.findByEmail(usuario.email());
        if (savedUsuario == null){
            savedUsuario = new Usuario();            
            savedUsuario.setEmail(usuario.email());
            savedUsuario.setUsername(usuario.username());
            savedUsuario.setPassword(passwordEncoder.encode(usuario.password()));
            savedUsuario.setAccountNonExpired(usuario.isAccountNonExpired());
            savedUsuario.setAccountNonLocked(usuario.isAccountNonLocked());
            savedUsuario.setCredentialsNonExpired(usuario.isCredentialsNonExpired());
            savedUsuario.setEnabled(false);
            savedUsuario.setActivationKey(UUID.randomUUID().toString());
            savedUsuario.setGrupo(Set.of(grupoService.findByNome("USERS")));
            userRepository.save(savedUsuario);
            emailSender.sendEmailRegister(savedUsuario);
        }else{
            throw new Exception("User already exists, Please, choose another one and try again ! ");

        }

    }

    public void deletaUsuario(String email) throws UsernameNotFoundException, Exception  {
        Usuario savedUsuario = buscaUsuarioEmail(email);
        if (savedUsuario != null) {
            savedUsuario.setEnabled(false);
            userRepository.save(savedUsuario);        
        
            }else{
                throw new UsernameNotFoundException("User not found: "+email);
            }
    }

    public void atualizaUsuario(dtoUsuario usuario, String email) throws UsernameNotFoundException, Exception {

        Usuario savedUsuario = buscaUsuarioEmail(email);
        if (savedUsuario != null){            
            savedUsuario.setUsername(usuario.username());
            savedUsuario.setEmail(usuario.email());
            savedUsuario.setPassword(passwordEncoder.encode(usuario.password()));
            savedUsuario.setAccountNonExpired(usuario.isAccountNonExpired());
            savedUsuario.setAccountNonLocked(usuario.isAccountNonLocked());
            savedUsuario.setCredentialsNonExpired(usuario.isCredentialsNonExpired());
            savedUsuario.setEnabled(usuario.isEnabled());
            userRepository.save(savedUsuario);
        }else{
            throw new UsernameNotFoundException("User not found: "+usuario.email());
        }
    }

    public void activeUser(String email, String uuid) throws UsernameNotFoundException, Exception {
        Usuario savedUsuario = buscaUsuarioEmail(email.trim());
        
        if ( savedUsuario != null){
            if (savedUsuario.isEnabled() == false){
                String aux = savedUsuario.getActivationKey().trim();
                if (aux.equalsIgnoreCase(uuid)) {
                        savedUsuario.setEnabled(true);
                        savedUsuario.setAccountNonExpired(true);
                        savedUsuario.setAccountNonLocked(true);
                        savedUsuario.setCredentialsNonExpired(true);
                        userRepository.save(savedUsuario);
                }
                else{
                    throw new Exception("Invalid activation key ord user already actived !");
                    }
                }    
            else{
                    throw new UsernameNotFoundException("User already actived: "+email);
                }      
            } 
        else{
             throw new UsernameNotFoundException("User not found: "+email);
            }    
    }

    public void changePasswordUser(String email, String password) throws UsernameNotFoundException, Exception {

        Usuario usuario = buscaUsuarioEmail(email);
        if (usuario != null){
           usuario.setPassword(passwordEncoder.encode(password));
           userRepository.save(usuario);
        }else{
            throw new UsernameNotFoundException("User not found: "+email);
        }

    }

    public void alterGrupoUsuario(Set<dtoGrupo> grupo, String email) throws UsernameNotFoundException, Exception {
       Usuario usuario = buscaUsuarioEmail(email);

        if (usuario != null){
            Set<Grupo> grupoUser = new HashSet<>();

            grupo.forEach(a -> grupoUser.add(grupoService.findByNome(a.nome())));
            usuario.setGrupo(grupoUser) ;
            userRepository.save(usuario);
        }
        else{
            throw new UsernameNotFoundException("User not found: "+email);

       
        }
    }    

}
