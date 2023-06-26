package com.gabriel.jwtexample.Jwt.example.api.exception;

import lombok.Getter;

@Getter
public enum ErrorType {

    BAD_CREDENTIALS("/bad-credentials", "Bad Credentials");

    private final String url;
    private final String title;

    ErrorType(String path, String title) {
        url = "https://api.example/errors" + path;
        this.title = title;
    }
}
