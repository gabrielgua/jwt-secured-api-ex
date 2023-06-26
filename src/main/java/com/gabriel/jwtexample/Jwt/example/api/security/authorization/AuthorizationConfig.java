package com.gabriel.jwtexample.Jwt.example.api.security.authorization;

import com.gabriel.jwtexample.Jwt.example.api.security.jwt.JwtService;
import com.gabriel.jwtexample.Jwt.example.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationConfig {

    private final UserService userService;
    private final JwtService jwtService;

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private boolean isAuthenticated() {
        return getAuthentication() != null;
    }

    public boolean isAdmin() {
        return hasAuthority("ADMIN");
    }

    public boolean isUser() {
        return hasAuthority("USER");
    }

    public Long getUserId() {
        var userEmail = getAuthentication().getName();
        return userService.findByEmail(userEmail).getId();
    }

    public boolean isOwner(Long userId) {
        return getUserId() != null && userId != null && getUserId().equals(userId);
    }

    public boolean hasAuthority(String authority) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(authority));
    }

    public boolean canListAll() {
        return isAdmin();
    }

    public boolean canReadUser(Long userId) {
        return isAuthenticated() && (isAdmin() || isOwner(userId));
    }

    public boolean canEditUser(Long userId) {
        return isAuthenticated() && (isAdmin() || isOwner(userId));
    }

    public boolean canRemoveUser() {
        return isAuthenticated() && isAdmin();
    }
}
