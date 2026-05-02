package com.ada.resource;

import com.ada.model.entity.ParcelaEntity;
import com.ada.service.ParcelaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/parcelas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ParcelaResource {

    @Inject
    ParcelaService parcelaService;

    @GET
    @Path("/{id}")
    @RolesAllowed({"user","gestor"})
    public Response buscarParcelaPorId(@PathParam("id") UUID id) {

        ParcelaEntity parcela = parcelaService.buscarPorId(id);

        return Response.ok(parcela).build();
    }

    @PATCH
    @Path("/{id}/pagamento")
    @RolesAllowed({"user","gestor"})
    public Response baixarParcela(@PathParam("id") UUID id) {

        parcelaService.baixarParcela(id);

        return Response.status(204).build();
    }
}
