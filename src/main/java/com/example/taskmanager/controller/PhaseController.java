package com.example.taskmanager.controller;

import com.example.taskmanager.model.Phase;
import com.example.taskmanager.service.PhaseService;
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
public class PhaseController {

    private final PhaseService phaseService;

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @GetMapping("/getAll")
    public ResponseEntity<List<Phase>> getAllPhases() {
        List<Phase> phases = phaseService.getAllPhases();
        return new ResponseEntity<>(phases, phases.size() > 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }
}
