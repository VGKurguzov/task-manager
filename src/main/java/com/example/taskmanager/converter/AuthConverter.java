package com.example.taskmanager.converter;

import com.example.taskmanager.dto.LoginResponse;
import com.example.taskmanager.model.UserDetailsImpl;
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
}
