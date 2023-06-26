package com.gabriel.jwtexample.Jwt.example.domain.repository;

import com.gabriel.jwtexample.Jwt.example.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}