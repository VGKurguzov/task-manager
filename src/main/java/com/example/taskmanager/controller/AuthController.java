package com.example.taskmanager.controller;

import com.example.taskmanager.converter.AuthConverter;
import com.example.taskmanager.dto.LoginRequest;
import com.example.taskmanager.dto.LoginResponse;
import com.example.taskmanager.dto.MessageResponse;
import com.example.taskmanager.model.UserDetailsImpl;
import com.example.taskmanager.util.JwtUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        final LoginResponse loginResponse = AuthConverter.toDto(jwt, userDetails);
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @SneakyThrows
    @PostMapping(value = "/logout")
    public ResponseEntity<MessageResponse> logout(HttpServletRequest request) {
        if (request.getUserPrincipal() == null) {
            return new ResponseEntity<>(new MessageResponse("User is not authorized"),
                    HttpStatus.UNAUTHORIZED);
        }
        try {
            request.logout();
        } catch (ServletException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity
                .ok(new MessageResponse("User logged out"));
    }
}
