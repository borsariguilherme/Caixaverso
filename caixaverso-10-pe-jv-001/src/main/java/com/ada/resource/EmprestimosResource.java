package com.ada.resource;

import com.ada.dto.EmprestimoRequest;
import com.ada.dto.EmprestimoResponse;
import com.ada.service.EmprestimoService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

@Path("/emprestimos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmprestimosResource {

    @Inject
    EmprestimoService emprestimoService;

    @GET
    public Response consultaEmprestimo(@QueryParam("clientId") String clientId){

        List<EmprestimoResponse> listaEmprestimo = emprestimoService.consultaById(clientId);


        return Response.ok().entity(listaEmprestimo).build();};

    @POST
    public EmprestimoResponse incluiEmprestimo(EmprestimoRequest emprestimo){

        //service incluir

        return Response.ok().entity(novoEmprestimo);};

    @DELETE
    @Path("/{id}")
    public Response deletaEmprestimo(@PathParam("id") String id){

        //service deletar

        return Response.status(204).build();};

}
