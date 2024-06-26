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

    public Usuario findByEmail(String email) throws UsernameNotFoundException, Exception {

        try {
            Usuario user = userRepository.findByEmail(email);
            if (user != null) {
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new Exception("Something went wrong, detail about that: " + e.getMessage());
        }

    }

    public List<Usuario> findAll() throws UsernameNotFoundException, Exception {

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

    public Usuario save(Usuario usuario, String grupo, boolean isEnabled) {

        Grupo grupoUsr = grupoService.findByNome(grupo);

        usuario.setAccountNonExpired(true);
        usuario.setAccountNonLocked(true);
        usuario.setCredentialsNonExpired(true);
        usuario.setEnabled(isEnabled);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setActivationKey(UUID.randomUUID().toString());
        usuario.setGrupo(Set.of(grupoUsr));

        return userRepository.save(usuario);

    }

    public void delete(Usuario usuario) {
        usuario.setEnabled(false);
        userRepository.save(usuario);
    }

    // public void update(Usuario savedUsuario) {
    //     userRepository.save(savedUsuario);
    // }

    public void activeUser(Usuario usuario, String uuid) {

        if (usuario.getActivationKey() == uuid){   
            usuario.setEnabled(true);
            userRepository.save(usuario);
        }    
    
    }

    public void changePasswordUser(String email, String password) throws UsernameNotFoundException, Exception {

        Usuario usuario = findByEmail(email);
        if (usuario != null){
           usuario.setPassword(passwordEncoder.encode(password));
           userRepository.save(usuario);
        }else{
            throw new UsernameNotFoundException("User not found: "+email);
        }

    }

    public void changegrupo(Set<dtoGrupo> grupo, String email) throws UsernameNotFoundException, Exception {
       Usuario usuario = findByEmail(email);

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
