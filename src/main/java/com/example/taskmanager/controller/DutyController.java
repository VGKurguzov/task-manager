package com.example.taskmanager.controller;

import com.example.taskmanager.model.Duty;
import com.example.taskmanager.service.DutyService;
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
public class DutyController {

    private final DutyService dutyService;

    @PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
    @GetMapping("/getAll")
    public ResponseEntity<List<Duty>> getAllDuties() {
        List<Duty> duties = dutyService.getAll();
        return new ResponseEntity<>(duties, duties.size() > 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }
}
