package com.gabriel.jwtexample.Jwt.example.api.controller;

import com.gabriel.jwtexample.Jwt.example.api.assembler.UserAssembler;
import com.gabriel.jwtexample.Jwt.example.api.model.UserEditRequest;
import com.gabriel.jwtexample.Jwt.example.api.security.authentication.AuthService;
import com.gabriel.jwtexample.Jwt.example.api.security.authorization.CheckSecurity;
import com.gabriel.jwtexample.Jwt.example.domain.model.User;
import com.gabriel.jwtexample.Jwt.example.domain.repository.UserRepository;
import com.gabriel.jwtexample.Jwt.example.domain.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {

    private final UserService service;
    private final UserAssembler assembler;
    private final AuthService authService;
    private final UserRepository repository;

    @GetMapping
    @CheckSecurity.Users.canListAll
    public List<User> listAll() {
        return service.listAll();
    }

    //permitAll()
    @GetMapping("/count")
    public Long count() {
        return repository.count();
    }

    @GetMapping("/{userId}")
    @CheckSecurity.Users.canReadUser
    public User findById(@PathVariable Long userId) {
        return service.findById(userId);
    }

    @PutMapping("/{userId}")
    @CheckSecurity.Users.canEditUser
    public User edit(@PathVariable Long userId, @RequestBody UserEditRequest request) {
        var user = service.findById(userId);
        assembler.copyToEntity(request, user);
        return service.save(user);
    }

    @DeleteMapping("/{userId}")
    @CheckSecurity.Users.canRemoveUser
    public void remove(@PathVariable Long userId) {
        var user = service.findById(userId);
        service.remove(user);
    }
}
