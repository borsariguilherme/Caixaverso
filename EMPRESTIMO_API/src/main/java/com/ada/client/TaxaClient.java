package com.ada.client;

import com.ada.exception.TaxaClientExceptionMapper;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

@RegisterRestClient(configKey = "taxa-api")
@RegisterProvider(TaxaClientExceptionMapper.class)
public interface TaxaClient {

    @GET
    @Path("/{clienteId}/taxas/{tipo}/elegibilidade")
    Response consultarTaxa(
            @PathParam("clienteId") UUID clienteId,
            @PathParam("tipo") String tipo
    );
}
