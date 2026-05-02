package com.ada.client;

import com.ada.dto.RateResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.UUID;

@RegisterRestClient(configKey = "taxa-api")
public interface TaxaClient {

    @GET
    @Path("/{clienteId}/taxas/{tipo}/elegibilidade")
    RateResponse consultarTaxa(
            @PathParam("clienteId") UUID clienteId,
            @PathParam("tipo") String tipo
    );
}
