package com.gabriel.jwtexample.Jwt.example.api.controller;

import com.gabriel.jwtexample.Jwt.example.api.assembler.UserAssembler;
import com.gabriel.jwtexample.Jwt.example.api.model.UserEditRequest;
import com.gabriel.jwtexample.Jwt.example.api.model.UserRegisterRequest;
import com.gabriel.jwtexample.Jwt.example.api.security.auth.AuthService;
import com.gabriel.jwtexample.Jwt.example.domain.model.User;
import com.gabriel.jwtexample.Jwt.example.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("users")
public class UserController {

    private UserService service;
    private UserAssembler assembler;
    private AuthService authService;

    @GetMapping
    public List<User> listAll() {
        return service.listAll();
    }

    @GetMapping(params = "id")
    public User findById(@RequestParam("id") Long userId) {
        return service.findById(userId);
    }

    @GetMapping(params = "email")
    public User findByEmail(@RequestParam String email) {
        return service.findByEmail(email);
    }

    @PutMapping("/{userId}")
    public User edit(@PathVariable Long userId, @RequestBody UserEditRequest request) {
        var user = service.findById(userId);
        assembler.copyToEntity(request, user);
        return service.save(user);
    }

    @DeleteMapping("/{userId}")
    public void remove(@PathVariable Long userId) {
        var user = service.findById(userId);
        service.remove(user);
    }

}
