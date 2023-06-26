package com.gabriel.jwtexample.Jwt.example.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {

    private String name;
    private String email;
    private String password;
}
