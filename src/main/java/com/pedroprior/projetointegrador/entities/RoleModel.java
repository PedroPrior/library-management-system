package com.pedroprior.projetointegrador.entities;

import com.pedroprior.projetointegrador.entities.enums.RoleName;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table
public class RoleModel implements Serializable, GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private RoleName roleName;


    public RoleModel(UUID id, RoleName roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public RoleModel() {

    }

    @Override
    public String getAuthority() {
        return this.roleName.toString();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Roles{" +
                 roleName +
                '}';
    }
}
