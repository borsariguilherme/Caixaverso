package com.ada.exception;

import com.ada.dto.error.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ProcessingExceptionMapper implements ExceptionMapper<jakarta.ws.rs.ProcessingException> {

    @Override
    public Response toResponse(jakarta.ws.rs.ProcessingException e) {

        return Response.status(Response.Status.BAD_GATEWAY)
                .entity(new ErrorResponse(
                        "ERRO_INTEGRACAO",
                        "Serviço de taxas indisponível"
                ))
                .build();
    }
}
