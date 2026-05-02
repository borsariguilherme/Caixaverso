package com.ada.exception;

import com.ada.dto.error.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.List;

@Provider
public class BusinessExceptionMapper implements ExceptionMapper<BusinessException> {

    @Override
    public Response toResponse(BusinessException e) {

        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(
                        "ERRO_NEGOCIAL",
                        e.getMessage()
                ))
                .build();
    }
}
