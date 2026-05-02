package com.ada.resource;

import com.ada.dto.request.EmprestimoRequest;
import com.ada.dto.response.EmprestimoResponse;
import com.ada.model.entity.ParcelaEntity;
import com.ada.service.EmprestimoService;
import com.ada.service.ParcelaService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/emprestimos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmprestimosResource {

    @Inject
    EmprestimoService emprestimoService;

    @Inject
    ParcelaService parcelaService;

    @GET
    @RolesAllowed({"user","gestor"})
    public Response consultaEmprestimo(@Valid @QueryParam("clientId") UUID clientId){

        List<EmprestimoResponse> listaEmprestimo = emprestimoService.consultaById(clientId);


        return Response.ok().entity(listaEmprestimo).build();};

    @POST
    @RolesAllowed({"user","gestor"})
    public Response incluiEmprestimo(@Valid EmprestimoRequest novoEmprestimo){

        EmprestimoResponse emprestimoCalculado =
                emprestimoService.incluiEmprestimo(novoEmprestimo);

        return Response.status(Response.Status.CREATED)
                .entity(emprestimoCalculado)
                .build();
    }

    @DELETE
    @RolesAllowed({"gestor"})
    @Path("/{id}")
    public Response deletaEmprestimo(@PathParam("id") UUID id){

        emprestimoService.deletaEmprestimo(id);

        return Response.status(204).build();};

    @GET
    @Path("/{id}/parcelas")
    @RolesAllowed({"user","gestor"})
    public Response listarParcelasEmprestimo(@PathParam("id") UUID id) {

        List<ParcelaEntity> parcelas = parcelaService.listarPorEmprestimo(id);

        return Response.ok(parcelas).build();
    }

}
