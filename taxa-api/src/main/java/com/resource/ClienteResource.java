package com.resource;

import com.dto.ErrorResponse;
import com.dto.RateResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @GET
    @Path("/{clienteId}/taxas/{tipo}/elegibilidade")
    public Response getTaxa(
            @PathParam("clienteId") UUID clienteId,
            @PathParam("tipo") String tipo) {

        //regra simulada de reprovação
        if (clienteId.toString().startsWith("0")) {

            ErrorResponse error = new ErrorResponse(
                    "CREDITO_REPROVADO",
                    "O cliente informado não atendeu aos critérios técnicos de elegibilidade."
            );

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(error)
                    .build();
        }

        //taxa aleatória entre 1% e 5%
        int taxa = ThreadLocalRandom.current().nextInt(1, 6);

        RateResponse response = new RateResponse(
                clienteId,
                taxa
        );

        return Response.ok(response).build();
    }
}
