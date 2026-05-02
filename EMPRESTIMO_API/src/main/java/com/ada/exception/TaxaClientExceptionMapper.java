package com.ada.exception;

import com.ada.dto.error.ErrorResponse;
import com.ada.dto.response.RateResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

@Provider
public class TaxaClientExceptionMapper implements ResponseExceptionMapper<RuntimeException> {

    @Override
    public RuntimeException toThrowable(Response response) {

        if (response.getStatus() == 400) {
            ErrorResponse errorResponse = response.readEntity(ErrorResponse.class);
            return new BusinessException(errorResponse.codigo(), errorResponse.mensagem());
        }

        return new IntegrationException("Erro na API de taxas");

    }
}

