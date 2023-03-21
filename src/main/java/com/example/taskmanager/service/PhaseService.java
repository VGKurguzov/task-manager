package com.example.taskmanager.service;

import com.example.taskmanager.model.Phase;
import com.example.taskmanager.repository.PhaseRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.taskmanager.util.Constants.PHASE_NOT_EXIST;

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
                .orElseThrow(() -> new Exception(String.format(PHASE_NOT_EXIST, id)));
    }
}
