package com.agrotech.usersecurity.controller;

import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agrotech.usersecurity.dto.dtoGrupo;
import com.agrotech.usersecurity.dto.dtoUsuario;
import com.agrotech.usersecurity.entities.Usuario;
import com.agrotech.usersecurity.services.GrupoService;
import com.agrotech.usersecurity.services.UsuarioService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class ManagerUsersController {

    @Autowired
    HttpServletResponse httpServletResponse;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private GrupoService grupoService;


    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ManagerUsersController() {
    }

   /**
     * Registers a new admin user.
     * 
     * @param usuario the user to be registered
     * @return a response indicating whether the user was registered successfully
     */

    @PostMapping("/admin/user/register")
    public ResponseEntity registerAdminUser(@RequestBody dtoUsuario usuario) {

        ResponseEntity response = null;

        try {

             if (usuario != null) {
                usuarioService.novoUsuario(usuario);
                response = ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");

            } else {                
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("Json body is empity, fill up the content of json and try again !");
                
                
            }

        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("<h1>Error to save the user ! </h1></br>" + e.getMessage());
        }

        return response;

    }


       /**
     * Retrieves a list of all admin users.
     * 
     * @return a list of admin users
     */
    @GetMapping("/admin/user/listall")
    public ResponseEntity listAllAdminUser() {

        try {
            List<Usuario> users = usuarioService.buscaTodosusuarios();
            if (users != null)
                return ResponseEntity.ok().cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)).body(users);
            else {
                return ResponseEntity.status(HttpStatus.OK)
                        .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                        .body("Users not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Something went wrong. Error message: " + e.getMessage());
        }

    }


        /**
     * Retrieves an admin user by email.
     * 
     * @param email the email of the user to retrieve
     * @return the user with the specified email
     */
    @GetMapping("/admin/user/list")
    public ResponseEntity listAdminUserByEmail(@RequestParam("email") String email) {

        try {
            Usuario user = usuarioService.buscaUsuarioEmail(email);
            if (user != null) {
                return ResponseEntity.ok().body(user);
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Something went wrong. Error message: " + e.getMessage());
        }
    }

    /**
     * Deletes an admin user by email.
     * 
     * @param email the email of the user to delete
     * @return a response indicating whether the user was deleted successfully
     */
    @DeleteMapping("/admin/user")
    public ResponseEntity deleteAdminUser(@RequestParam("email") String email) {

        try {
            
            if (email != null) {
                usuarioService.deletaUsuario(email);
                return ResponseEntity.ok().body("User was disabled.");
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("User not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Something went wrong. Error message: " + e.getMessage());
        }

    }

        /**
     * Updates an admin user.
     * 
     * @param usuario the updated user information
     * @param email the email of the user to update
     * @return a response indicating whether the user was updated successfully    */

    @PutMapping("/admin/user")
    public ResponseEntity updateAdminUser(@RequestBody dtoUsuario usuario, @RequestParam("email") String email) {

        ResponseEntity response = null;

        try {

            if (usuario != null && email != null) {
                usuarioService.atualizaUsuario(usuario, email);
                response = ResponseEntity.status(HttpStatus.OK).body("User Updated.");
            } else {
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error to update user." + e.getMessage());
        }

        return response;

    }

        /**
     * Updates the groups of an admin user.
     * 
     * @param grupo the updated group information
     * @param email the email of the user to update
     * @return a response indicating whether the groups were updated successfully
     */
    @PutMapping("/admin/user/grupo")
    public ResponseEntity updateAdminUser(@RequestBody Set<dtoGrupo> grupo, @RequestParam("email") String email) {
    
        ResponseEntity response = null;

        try {

            if (grupo != null && email != null) {
                usuarioService.alterGrupoUsuario(grupo, email);
                response = ResponseEntity.status(HttpStatus.OK).body("Group of user Updated.");
            } else {
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Group of User not found.");
            }

        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error to update group of user." + e.getMessage());
        }

        return response;

    }


    @PutMapping("/admin/user/changepassword")
    public ResponseEntity changePasswordUser(@RequestBody String password, @RequestParam("email") String email) {

        ResponseEntity response = null;

        try {
            
            if (password != null && email != null) {
                usuarioService.changePasswordUser(email, password);
                response = ResponseEntity.status(HttpStatus.OK).body("Password changed successfully");
                
            } else {
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

        } catch (Exception e) {
            response = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error to update user:<br>" + e.getMessage());
        }

    return response;

    }



}
