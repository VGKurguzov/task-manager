package com.saxakiil.taskmanager.service;

import com.saxakiil.taskmanager.exception.RecordNotFoundException;
import com.saxakiil.taskmanager.model.User;
import com.saxakiil.taskmanager.model.UserDetailsImpl;
import com.saxakiil.taskmanager.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.saxakiil.taskmanager.util.Constants.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User create(User user) {
        return userRepository.save(user);
    }

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
