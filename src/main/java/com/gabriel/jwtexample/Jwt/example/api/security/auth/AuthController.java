package com.gabriel.jwtexample.Jwt.example.api.security.auth;

import com.gabriel.jwtexample.Jwt.example.api.assembler.UserAssembler;
import com.gabriel.jwtexample.Jwt.example.api.model.UserRegisterRequest;
import com.gabriel.jwtexample.Jwt.example.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;
    private final UserAssembler userAssembler;

    @PostMapping("/register")
    public ResponseEntity<AuthModel> register(@RequestBody UserRegisterRequest request) {
        var user = userAssembler.toEntity(request);
        return ResponseEntity.ok(authService.register(userService.save(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthModel> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
