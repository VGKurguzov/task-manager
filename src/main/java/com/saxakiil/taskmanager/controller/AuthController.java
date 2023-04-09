package com.saxakiil.taskmanager.controller;

import com.saxakiil.taskmanager.converter.AuthConverter;
import com.saxakiil.taskmanager.dto.LoginRequest;
import com.saxakiil.taskmanager.dto.LoginResponse;
import com.saxakiil.taskmanager.dto.MessageResponse;
import com.saxakiil.taskmanager.dto.error.ErrorDto;
import com.saxakiil.taskmanager.model.UserDetailsImpl;
import com.saxakiil.taskmanager.util.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "Auth", description = "Auth APIs")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Operation(
            summary = "Login",
            description = "Login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content
                    (schema = @Schema(implementation = LoginResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema(implementation = ErrorDto.class))}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
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

    @Operation(
            summary = "Logout",
            description = "Logout")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content
                    (schema = @Schema(implementation = MessageResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema(implementation = ErrorDto.class))}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @SneakyThrows
    @PostMapping(value = "/logout")
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    public ResponseEntity<MessageResponse> logout(HttpServletRequest request) {
        try {
            request.logout();
        } catch (ServletException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity
                .ok(new MessageResponse("User logged out"));
    }
}
