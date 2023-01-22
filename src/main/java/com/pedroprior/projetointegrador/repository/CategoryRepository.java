package com.pedroprior.projetointegrador.repository;

import com.pedroprior.projetointegrador.entities.Author;
import com.pedroprior.projetointegrador.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
