package com.pedroprior.projetointegrador.controller;



import com.pedroprior.projetointegrador.entities.Author;
import com.pedroprior.projetointegrador.entities.Category;
import com.pedroprior.projetointegrador.repository.CategoryRepository;
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
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/view/{id}")
    public String getById(@PathVariable("id") UUID id, Model model) {
        Optional<Category> category = categoryRepository.findById(id);

        if (category.isPresent()) {;
            model.addAttribute("category", category.get());
            return "/view-category";
        } else {
            return "/public-index";
        }

    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    @RequestMapping("/list")
    public String listCategory(Model model) {
        model.addAttribute("categories", categoryRepository.findAll());


        return "auth/admin/admin-list-category";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping("/new")
    public String addCategoryModel(Model model) {
        model.addAttribute("category", new Category());
        return "/create-category";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @PostMapping("/save")
    public String saveCategory(@Validated Category category, BindingResult result,
                             RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "/create-category";
        }
        categoryRepository.save(category);
        attributes.addFlashAttribute("message", "Category is saved!");
        return "redirect:/category/new";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @PostMapping("/update/{id}")
    public String updateCategory(@PathVariable(value = "id") UUID id, @Valid Category category,
                             BindingResult bindingResult, Model model
    ) {

        if (bindingResult.hasErrors()) {
            category.setId(id);
            return "update-category";
        }

        categoryRepository.save(category);

        return "redirect:/category/list";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") UUID id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("category", category);
        return "/update-category";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") UUID id, Model model) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        categoryRepository.delete(category);
        return "redirect:/category/list";

    }
}
