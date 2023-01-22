package com.pedroprior.projetointegrador.repository;

import com.pedroprior.projetointegrador.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    Optional<UserModel> findByUsername(String username);

    UserModel findByUsernameAndPassword(String username, String password);
}
