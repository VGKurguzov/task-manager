package com.saxakiil.taskmanager.converter;

import com.saxakiil.taskmanager.dto.LoginResponse;
import com.saxakiil.taskmanager.model.User;
import com.saxakiil.taskmanager.model.UserDetailsImpl;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthConverter {

    @SneakyThrows
    public static LoginResponse toDto(String jwt, UserDetailsImpl userDetails) {
        return new LoginResponse(jwt, userDetails.getUsername(),
                userDetails.getAuthorities().stream()
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)).toString());
    }

    public static User toModel(String username, String password) {
        return new User(null, username, password, "ROLE_USER");
    }
}
