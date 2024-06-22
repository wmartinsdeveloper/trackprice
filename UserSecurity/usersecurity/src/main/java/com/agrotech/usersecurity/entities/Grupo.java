package com.agrotech.usersecurity.entities;

import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "grupo")
public class Grupo implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "E-mail é uma informação obrigatória.")
    @Column(name = "nome", unique = true)
    private String nome;

    @JsonIgnore
    @ManyToMany(mappedBy = "grupo")
    private Set<Usuario> usuario;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
        Grupo other = (Grupo) obj;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "role [id=" + id + ", nome=" + nome + "]";
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(Set<Usuario> user) {
        this.usuario = user;
    }

    public Grupo(String nome, Set<Usuario> user) {
        this.nome = nome;
        this.usuario = user;
    }

    public Grupo(String nome) {
        this.nome = nome;
    }

    public Grupo() {
    }

    @Override
    public String getAuthority() {
        return this.nome;
    }

}