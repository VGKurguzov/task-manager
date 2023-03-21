package com.example.taskmanager.validator;

import com.example.taskmanager.service.TaskService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueTitleTaskValidator implements ConstraintValidator<UniqueTitleTaskConstraint, String> {

    private final TaskService taskService;

    @Override
    public void initialize(UniqueTitleTaskConstraint uniqueTitleTaskConstraint) {
    }

    @Override
    public boolean isValid(String contactField,
                           ConstraintValidatorContext cxt) {
        return !taskService.isExistTitle(contactField);
    }
}
