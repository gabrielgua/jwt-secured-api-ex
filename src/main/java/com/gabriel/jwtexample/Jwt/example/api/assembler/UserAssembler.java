package com.gabriel.jwtexample.Jwt.example.api.assembler;

import com.gabriel.jwtexample.Jwt.example.api.model.UserEditRequest;
import com.gabriel.jwtexample.Jwt.example.api.model.UserRegisterRequest;
import com.gabriel.jwtexample.Jwt.example.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public User toEntity(UserRegisterRequest request) {
        return modelMapper.map(request, User.class);
    }

    public void copyToEntity(UserEditRequest request, User user) {
        modelMapper.map(request, user);
    }
}
