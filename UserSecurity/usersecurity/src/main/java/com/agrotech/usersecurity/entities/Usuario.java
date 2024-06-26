package com.agrotech.usersecurity.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "E-mail é uma informação obrigatória.")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "Usuário é uma informação obrigatória.")
    private String username;

    @NotBlank(message = "Password é uma informação obrigatória.")
    @Column(name = "password")
    private String password;

    @Column(name = "isAccountNonExpired")
    private boolean isAccountNonExpired;

    @Column(name = "isAccountNonLocked")
    private boolean isAccountNonLocked;

    @Column(name = "isCredentialsNonExpired")
    private boolean isCredentialsNonExpired;

    @Column(name = "isEnabled")
    private boolean isEnabled;

    @Column(name = "activationKey")
    private String activationKey;    

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Usario_Grupo", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "grupo_id"))
    private Set<Grupo> grupo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> role = new ArrayList<>();
        grupo.forEach((lista) -> role.add(new SimpleGrantedAuthority(lista.getNome())));
        return role;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccountNonExpired(boolean isAccountNonExpired) {
        this.isAccountNonExpired = isAccountNonExpired;
    }

    public void setAccountNonLocked(Boolean isAccountNonLocked) {
        this.isAccountNonLocked = isAccountNonLocked;
    }

    public void setCredentialsNonExpired(boolean isCredentialsNonExpired) {
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Set<Grupo> getGrupo() {
        return this.grupo;
    }

    public void setGrupo(Set<Grupo> grupo) {
        this.grupo = grupo;
    }

    public Usuario(@NotBlank(message = "E-mail é uma informação obrigatória.") String email,
            @NotBlank(message = "Login é uma informação obrigatória.") String username,
            @NotBlank(message = "Password é uma informação obrigatória.") String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public Usuario() {
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", email=" + email + ", username=" + username + ", password="
                + password + ", isAccountNonExpired=" + isAccountNonExpired + ", isAccountNonLocked="
                + isAccountNonLocked + ", isCredentialsNonExpired=" + isCredentialsNonExpired + ", isEnabled="
                + isEnabled + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + (isAccountNonExpired ? 1231 : 1237);
        result = prime * result + (isAccountNonLocked ? 1231 : 1237);
        result = prime * result + (isCredentialsNonExpired ? 1231 : 1237);
        result = prime * result + (isEnabled ? 1231 : 1237);
        result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Usuario other = (Usuario) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (isAccountNonExpired != other.isAccountNonExpired)
            return false;
        if (isAccountNonLocked != other.isAccountNonLocked)
            return false;
        if (isCredentialsNonExpired != other.isCredentialsNonExpired)
            return false;
        if (isEnabled != other.isEnabled)
            return false;
        if (grupo == null) {
            if (other.grupo != null)
                return false;
        } else if (!grupo.equals(other.grupo))
            return false;
        return true;
    }

    public void setAccountNonLocked(boolean isAccountNonLocked) {
        this.isAccountNonLocked = isAccountNonLocked;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

}
