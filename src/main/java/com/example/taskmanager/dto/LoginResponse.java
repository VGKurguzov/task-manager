package com.example.taskmanager.dto;

public record LoginResponse(String token,Long id, String username, String role) {
}
