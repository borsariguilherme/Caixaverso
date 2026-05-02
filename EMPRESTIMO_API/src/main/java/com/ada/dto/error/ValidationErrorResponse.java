package com.ada.dto.error;

import java.util.List;

public record ValidationErrorResponse (

        String codigo,
        List<FieldError> campos

) {}
