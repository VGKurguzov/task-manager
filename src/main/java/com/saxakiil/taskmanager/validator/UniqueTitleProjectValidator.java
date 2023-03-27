package com.saxakiil.taskmanager.validator;

import com.saxakiil.taskmanager.service.ProjectService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueTitleProjectValidator implements ConstraintValidator<UniqueTitleProjectConstraint, String> {

    private final ProjectService projectService;

    @Override
    public void initialize(UniqueTitleProjectConstraint uniqueTitleProjectConstraint) {
    }

    @Override
    public boolean isValid(String contactField,
                           ConstraintValidatorContext cxt) {
        return !projectService.isExistTitle(contactField);
    }
}
