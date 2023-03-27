package com.example.taskmanager.dto.error;

public record ErrorDto(Integer status, String error, String path, String message, String timestamp) {
}