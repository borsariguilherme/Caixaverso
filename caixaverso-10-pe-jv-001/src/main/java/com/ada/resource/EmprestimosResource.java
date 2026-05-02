package com.ada.resource;

import com.ada.dto.EmprestimoRequest;
import com.ada.dto.EmprestimoResponse;
import com.ada.service.EmprestimoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/emprestimos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmprestimosResource {

    @Inject
    EmprestimoService emprestimoService;

    @GET
    public Response consultaEmprestimo(@Valid @QueryParam("clientId") UUID clientId){

        List<EmprestimoResponse> listaEmprestimo = emprestimoService.consultaById(clientId);


        return Response.ok().entity(listaEmprestimo).build();};

    @POST
    public Response incluiEmprestimo(EmprestimoRequest novoEmprestimo){

        EmprestimoResponse emprestimoCalculado = emprestimoService.incluiEmprestimo(novoEmprestimo);

        return Response.ok().entity(emprestimoCalculado).build();};

    @DELETE
    @Path("/{id}")
    public Response deletaEmprestimo(@PathParam("id") UUID id){

        emprestimoService.deletaEmprestimo(id);

        return Response.status(204).build();};

}
