package com.agrotech.usersecurity.entities;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @ManyToMany
    @JoinTable(name = "Role_Privilege", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private Set<Privilege> privilege;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "Role_Objects", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "objects_id"))
    private Set<Objects> objects;

    @JsonIgnore
    @ManyToMany(mappedBy = "role")
    private Set<User> user;

    public Role() {
    }

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
        Role other = (Role) obj;
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Privilege> getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Set<Privilege> privilege) {
        this.privilege = privilege;
    }

    public Set<Objects> getObjects() {
        return objects;
    }

    public void setObjects(Set<Objects> objects) {
        this.objects = objects;
    }

    public Set<User> getUser() {
        return user;
    }

    public void setUser(Set<User> user) {
        this.user = user;
    }

    public Role(String nome, Set<Privilege> privilege, Set<Objects> objects, Set<User> user) {
        this.nome = nome;
        this.privilege = privilege;
        this.objects = objects;
        this.user = user;
    }

    public Role(String nome, Set<Privilege> privilege, Set<Objects> objects) {
        this.nome = nome;
        this.privilege = privilege;
        this.objects = objects;
    }

}