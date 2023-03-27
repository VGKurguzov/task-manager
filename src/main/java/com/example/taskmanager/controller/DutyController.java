package com.example.taskmanager.controller;

import com.example.taskmanager.dto.error.ErrorDto;
import com.example.taskmanager.model.Duty;
import com.example.taskmanager.service.DutyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/duties")
@Tag(name = "Duty", description = "Duty APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class DutyController {

    private final DutyService dutyService;

    @Operation(
            summary = "Get all duties",
            description = "Get all duties")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content
                    (schema = @Schema(implementation = Duty.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema(implementation = ErrorDto.class))}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @GetMapping("/getAll")
    public ResponseEntity<List<Duty>> getAllDuties() {
        List<Duty> duties = dutyService.getAll();
        return new ResponseEntity<>(duties, duties.size() > 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }
}
