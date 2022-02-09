package com.example.spring.security.userdetailsservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/users")
    public String users() {
        System.out.println("UsuarioController users");
        return "user data and token";
    }
}
