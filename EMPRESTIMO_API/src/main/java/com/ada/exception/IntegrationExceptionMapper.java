package com.ada.exception;

import com.ada.dto.error.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IntegrationExceptionMapper implements ExceptionMapper<IntegrationException> {

    @Override
    public Response toResponse(IntegrationException e) {
        return Response.status(Response.Status.BAD_GATEWAY)
                .entity(new ErrorResponse(
                        "ERRO_INTEGRACAO",
                        e.getMessage()
                ))
                .build();
    }
}
