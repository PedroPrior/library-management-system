package com.pedroprior.projetointegrador.controller;


import com.pedroprior.projetointegrador.entities.Author;



import com.pedroprior.projetointegrador.repository.AuthorRepository;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/author")
public class AuthorController {


    @Autowired
    AuthorRepository authorRepository;


    @GetMapping("/view/{id}")
    public String getById(@PathVariable("id") UUID id, Model model) {
        Optional<Author> author = authorRepository.findById(id);

        if (author.isPresent()) {

            model.addAttribute("author", author.get());
            return "/view-author";
        } else {
            return "/public-index";
        }

    }

    // Thymeleaf <--
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    @GetMapping("/new")
    public String addAuthorModel(Model model) {
        model.addAttribute("author", new Author());
        return "/create-author";
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER', 'ROLE_MODERATOR')")
    @RequestMapping("/list")
    public String listAuthor(Model model) {
        model.addAttribute("authors", authorRepository.findAll());


        return "auth/admin/admin-list-author";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @PostMapping("/save")
    public String saveAuthor(@Validated Author author, BindingResult result,
                             RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "/create-author";
        }


        authorRepository.save(author);
        attributes.addFlashAttribute("message", "Author is saved!");
        return "redirect:/author/new";
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") UUID id, Model model) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        authorRepository.delete(author);
        return "redirect:/author/list";

    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable(value = "id") UUID id, @Valid Author author,
                             BindingResult bindingResult, Model model
    ) {

        if (bindingResult.hasErrors()) {
            author.setId(id);
            return "update-author";
        }

        authorRepository.save(author);

        return "redirect:/user/list";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") UUID id, Model model) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("author", author);
        return "/update-author";
    }


}
