package com.example.taskmanager.controller;

import com.example.taskmanager.dto.error.ErrorDto;
import com.example.taskmanager.model.Phase;
import com.example.taskmanager.service.PhaseService;
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
@RequestMapping("/api/phases")
@Tag(name = "Phase", description = "Phase APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class PhaseController {

    private final PhaseService phaseService;

    @Operation(
            summary = "Get all phases",
            description = "Get all phases")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Phase.class),
                    mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "401", content = {@Content(schema = @Schema(implementation = ErrorDto.class))}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @GetMapping("/getAll")
    public ResponseEntity<List<Phase>> getAllPhases() {
        List<Phase> phases = phaseService.getAllPhases();
        return new ResponseEntity<>(phases, phases.size() > 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }
}
