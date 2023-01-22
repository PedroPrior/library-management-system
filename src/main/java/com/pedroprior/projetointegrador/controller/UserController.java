package com.pedroprior.projetointegrador.controller;


import com.pedroprior.projetointegrador.entities.RoleModel;
import com.pedroprior.projetointegrador.entities.UserModel;

import com.pedroprior.projetointegrador.entities.enums.RoleName;
import com.pedroprior.projetointegrador.repository.RoleRepository;
import com.pedroprior.projetointegrador.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/view/{id}")
    public String getById(@PathVariable("id") UUID id, Model model) {
        Optional<UserModel> user = userRepository.findById(id);

        if (user.isPresent()) {;
            model.addAttribute("user", user.get());
            return "/view-user";
        } else {
            return "/public-index";
        }

    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_USER')")
    @RequestMapping("/list")
    public String listUser(Model model) {
        model.addAttribute("users", userRepository.findAll());


        return "auth/admin/admin-list-user";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping("/new")
    public String addUserModel(Model model) {
        model.addAttribute("user", new UserModel());
        return "register";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @PostMapping("/save")
    public String saveUser(@Validated @ModelAttribute("user") UserModel user, BindingResult result,
                           RedirectAttributes attributes, Model model) {


        if (result.hasErrors()) {
            model.addAttribute("user", user);
            model.addAttribute("errors", result.getAllErrors());
            attributes.addFlashAttribute("message", "Error");
            return "/register";
        }

        // check if password and confirm password match
        if(!user.getPassword().equals(user.getCpassword())){
            model.addAttribute("passwordError", "Password and Confirm Password must match.");
            return "/register";
        }


        // Encode the user's password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //Retrive roles from the role repository
        RoleModel r1 = roleRepository.findByRoleName(RoleName.ROLE_ADMIN);


        // Add roles to the user
        user.setRoles(List.of(r1));

        // Save the user
        userRepository.save(user);


        return "public-index";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable(value = "id") UUID id, @Valid UserModel user,
                             BindingResult bindingResult, Model model
    ) {

        if (bindingResult.hasErrors()) {
            user.setId(id);
            return "/update-user";
        }

        userRepository.save(user);

        return "redirect:/user/list";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") UUID id, Model model) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("user", user);
        return "/update-user";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") UUID id, Model model) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        return "redirect:/user/list";

    }
}
