package com.pedroprior.projetointegrador.repository;

import com.pedroprior.projetointegrador.entities.RoleModel;
import com.pedroprior.projetointegrador.entities.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, UUID> {
    RoleModel findByRoleName(RoleName roleName);
}
