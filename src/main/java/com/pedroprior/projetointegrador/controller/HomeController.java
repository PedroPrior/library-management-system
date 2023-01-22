package com.pedroprior.projetointegrador.controller;

import com.pedroprior.projetointegrador.entities.Book;
import com.pedroprior.projetointegrador.entities.UserModel;
import com.pedroprior.projetointegrador.repository.BookRepository;
import com.pedroprior.projetointegrador.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user/login")
    public String login() {



        return "login";
    }

    @RequestMapping("/")
    public String index(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
       return "public-index";
    }


}
