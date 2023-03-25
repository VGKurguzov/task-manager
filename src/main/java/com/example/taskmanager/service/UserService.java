package com.example.taskmanager.service;

import com.example.taskmanager.exception.RecordNotFoundException;
import com.example.taskmanager.model.User;
import com.example.taskmanager.model.UserDetailsImpl;
import com.example.taskmanager.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.example.taskmanager.util.Constants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @SneakyThrows
    @Transactional
    public User getByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new RecordNotFoundException(String.format(USER_NOT_FOUND, username)));
    }

    @SneakyThrows
    @Transactional
    public User getAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            final String username = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
            return userRepository
                    .findByUsername(username)
                    .orElseThrow(() -> new RecordNotFoundException(String.format(USER_NOT_FOUND, username)));
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

}
