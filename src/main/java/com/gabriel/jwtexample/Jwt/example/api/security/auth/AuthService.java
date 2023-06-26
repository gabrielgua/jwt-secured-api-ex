package com.gabriel.jwtexample.Jwt.example.api.security.auth;

import com.gabriel.jwtexample.Jwt.example.api.security.jwt.JwtService;
import com.gabriel.jwtexample.Jwt.example.domain.model.User;
import com.gabriel.jwtexample.Jwt.example.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtService service;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    public AuthModel register(User user) {
        var authorities = new HashMap<String, Object>();
        authorities.put("authorities", List.of(user.getRole()));
        var token = service.generateToken(authorities, user);

        return AuthModel.builder()
                .accessToken(token)
                .build();
    }

    public AuthModel login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );


        var user = userService.findByEmail(request.getEmail());
        var authorities = new HashMap<String, Object>();
        authorities.put("authorities", List.of(user.getRole()));
        var token = service.generateToken(authorities, user);

        return AuthModel.builder()
                .accessToken(token)
                .build();
    }
}
