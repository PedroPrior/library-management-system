package com.pedroprior.projetointegrador.controller;

import com.pedroprior.projetointegrador.entities.Author;
import com.pedroprior.projetointegrador.entities.Book;

import com.pedroprior.projetointegrador.entities.Category;


import com.pedroprior.projetointegrador.repository.AuthorRepository;
import com.pedroprior.projetointegrador.repository.BookRepository;
import com.pedroprior.projetointegrador.repository.CategoryRepository;
import jakarta.validation.Valid;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Controller
@RequestMapping("/book")
public class BookController {


    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    CategoryRepository categoryRepository;





    @GetMapping("/view/{id}")
    public String getById(@PathVariable("id") UUID id, Model model) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {

            model.addAttribute("book", book.get());
            return "/view-book";
        } else {
            return "/public-index";
        }



    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    @RequestMapping("/list")
    public String listAuthor(Model model) {
        model.addAttribute("books", bookRepository.findAll());

        return "auth/admin/admin-list-book";
    }

    @GetMapping("/new")
    public String addBookModel(Model model) {

        model.addAttribute("book", new Book());
        model.addAttribute("author", authorRepository.findAll());
        model.addAttribute("category", categoryRepository.findAll());
        return "/create-book";
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    @PostMapping("/save")
    public String saveBook(@Validated Book book,  BindingResult result, RedirectAttributes attributes ) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "Please check your form, there are errors");
            attributes.addFlashAttribute("author", authorRepository.findAll());
            attributes.addFlashAttribute("category", categoryRepository.findAll());

            return "/create-book";
        }



        bookRepository.save(book);
        attributes.addFlashAttribute("message", "Book is saved!");

        return "redirect:/book/new";
    }



    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable(value = "id") UUID id, @Valid Book book,
                               BindingResult bindingResult, Model model
    ) {

        if (bindingResult.hasErrors()) {
            book.setId(id);
            return "update-book";
        }

        bookRepository.save(book);

        return "redirect:/book/list";
    }



    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") UUID id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("author", authorRepository.findAll());
        model.addAttribute("category", categoryRepository.findAll());
        model.addAttribute("book", book);
        return "/update-book";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") UUID id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        bookRepository.delete(book);
        return "redirect:/book/list";

    }




}
