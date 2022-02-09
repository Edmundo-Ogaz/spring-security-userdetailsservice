package com.example.spring.security.userdetailsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String root() {
        System.out.println("UsuarioController root");
        return "UsuarioController users";
    }

    @GetMapping("/users")
    public List<CustomerModel> users() {
        System.out.println("UsuarioController users");
        return customerRepository.findAll();
    }

    @PostMapping("/users")
    public long save(@RequestBody UserDto userDto) {
        System.out.println("UsuarioController save");
        System.out.println("UsuarioController save"+userDto.toString());

        userDto.setPassword(passwordEncoder
                .encode(userDto.getPassword()));
        CustomerModel user = new CustomerModel(userDto.getId(), userDto.getFirstName(), userDto.getLastName(), userDto.getEmail(), userDto.getPassword());
        return customerRepository.save(user).getId();
    }
}
