package com.saxakiil.taskmanager.service;

import com.saxakiil.taskmanager.exception.RecordNotFoundException;
import com.saxakiil.taskmanager.model.Duty;
import com.saxakiil.taskmanager.repository.DutyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.saxakiil.taskmanager.util.Constants.DUTY_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class DutyService {

    private final DutyRepository dutyRepository;

    @Transactional
    public List<Duty> getAll() {
        return dutyRepository.findAll();
    }

    @SneakyThrows
    @Transactional
    public Duty getById(Long id) {
        return dutyRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format(DUTY_NOT_EXIST, id)));
    }
}
