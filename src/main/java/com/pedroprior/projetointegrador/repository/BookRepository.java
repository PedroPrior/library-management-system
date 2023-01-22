package com.pedroprior.projetointegrador.repository;

import com.pedroprior.projetointegrador.entities.Author;
import com.pedroprior.projetointegrador.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {


}
