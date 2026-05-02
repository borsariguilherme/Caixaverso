package com.ada.exception;

import com.ada.dto.error.ErrorResponse;
import com.ada.dto.error.FieldError;
import com.ada.dto.error.ValidationErrorResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {

        List<FieldError> errors = exception.getConstraintViolations()
                .stream()
                .map(v -> new FieldError(
                        v.getPropertyPath().toString(),
                        v.getMessage()
                ))
                .toList();

        ValidationErrorResponse response = new ValidationErrorResponse(
                "Erro de validação nos campos enviados",
                errors
        );

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(response)
                .build();
    }
}
