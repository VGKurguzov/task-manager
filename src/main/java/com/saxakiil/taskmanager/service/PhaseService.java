package com.saxakiil.taskmanager.service;

import com.saxakiil.taskmanager.exception.RecordNotFoundException;
import com.saxakiil.taskmanager.model.Phase;
import com.saxakiil.taskmanager.repository.PhaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.saxakiil.taskmanager.util.Constants.PHASE_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class PhaseService {
    private final PhaseRepository phaseRepository;

    @Transactional
    public List<Phase> getAllPhases() {
        return phaseRepository.findAll();
    }

    @Transactional
    @SneakyThrows
    public Phase getById(Long id) {
        return phaseRepository
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException(String.format(PHASE_NOT_EXIST, id)));
    }
}
