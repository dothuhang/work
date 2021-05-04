package com.example.demo.controllers;

import com.example.demo.model.User;
import com.example.demo.repository.EcomRepository;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
/*
    @GetMapping("/")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model)
    {
        model.addAttribute("name", name);
        return "greeting";
    }

 */
    @Autowired
    private UserRepository userrepo;

    @GetMapping("/")
    public String hello()  {
        return "hello";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("users", new User());
        return "register";
    }

    @PostMapping("/process_register")
    public String processRegistration(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPwd = encoder.encode(user.getPassword());
        user.setPassword(encodedPwd);
        userrepo.save(user);
        return "register_success";
    }

}
