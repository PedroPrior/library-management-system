package com.pedroprior.projetointegrador.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;


import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true)
    private Long isbn;
    @Column(length = 200, nullable = false)
    private String description;

    @Column
    private Float rating;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDate;
    @ManyToOne
    @JsonIgnore
    private Author author;

    @ManyToOne
    @JsonIgnore
    private Category category;


    public Book() {

    }

    public Book(UUID id, String name, Long isbn, Author author, Category category) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.author = author;
        this.category = category;
    }


    public Book(UUID id, String name, Long isbn, String description, Author author, Float rating, Category category) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.description = description;
        this.author = author;
        this.rating = rating;
        this.category = category;
    }

    public Book(UUID id, String name, Long isbn) {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }


    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id) && Objects.equals(name, book.name) && Objects.equals(isbn, book.isbn) && Objects.equals(author, book.author) && Objects.equals(category, book.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isbn, author, category);
    }

    @Override
    public String toString() {
        return "name='" + name;
    }




}
