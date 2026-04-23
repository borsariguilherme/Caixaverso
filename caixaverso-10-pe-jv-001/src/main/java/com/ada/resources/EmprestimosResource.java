package com.ada.resources;

import com.ada.dto.EmprestimoRequest;
import com.ada.dto.EmprestimoResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/emprestimos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmprestimosResource {

    @GET
    public EmprestimoResponse consultaEmprestimo(@QueryParam("clientId") String clientId){

        //service consultar


        return Response.ok().entity(listaEmprestimo);};

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
