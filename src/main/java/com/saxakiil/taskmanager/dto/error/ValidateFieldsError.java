package com.saxakiil.taskmanager.dto.error;

import java.util.List;

public record ValidateFieldsError(List<FieldError> fieldsError) {
}
