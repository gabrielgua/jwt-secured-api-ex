package com.gabriel.jwtexample.Jwt.example.domain.service;

import com.gabriel.jwtexample.Jwt.example.api.security.auth.AuthService;
import com.gabriel.jwtexample.Jwt.example.domain.model.User;
import com.gabriel.jwtexample.Jwt.example.domain.model.UserRole;
import com.gabriel.jwtexample.Jwt.example.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<User> listAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long userId) {
        return repository.findById(userId).orElseThrow();
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow();
    }

    @Transactional
    public User save(User user) {
        user.setRole(UserRole.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Transactional
    public void remove(User user) {
        repository.delete(user);
    }


}
