package com.gabriel.jwtexample.Jwt.example.api.security.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabriel.jwtexample.Jwt.example.api.security.jwt.JwtService;
import com.gabriel.jwtexample.Jwt.example.domain.model.User;
import com.gabriel.jwtexample.Jwt.example.domain.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
        var refreshToken = service.generateRefreshToken(user);

        return AuthModel.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
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
        var refreshToken = service.generateRefreshToken(user);

        return AuthModel.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        userEmail = service.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = userService.findByEmail(userEmail);
            var authorities = new HashMap<String, Object>();
            authorities.put("authorities", List.of(user.getRole()));
            var token = service.generateToken(authorities, user);

            if (service.isTokenValid(refreshToken, user.getEmail())) {
                var accessToken = service.generateToken(authorities, user);
                var authModel = AuthModel.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();

                new ObjectMapper().writeValue(response.getOutputStream(), authModel);
            }
        }


    }
}
