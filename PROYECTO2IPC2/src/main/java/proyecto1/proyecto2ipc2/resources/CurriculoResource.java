/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import EntidadesDeServicio.CrudCurriculo;

import EntidadesDto.Curriculo.CurriculoRespuesta;
import EntidadesDto.Curriculo.NuevoCurriculoRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
@Path("/Curriculo")
public class CurriculoResource {

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response InsertarCurriculo(NuevoCurriculoRequest nuevoCurriculo) {
        CrudCurriculo crud = new CrudCurriculo();
        try {
            crud.AgregarCurriculo(nuevoCurriculo);
            return Response.status(Response.Status.CREATED).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @GET
    @Path("{idGrado}/{ciclo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSeccionesPorNombre(@PathParam("idGrado") String idGrado,
            @PathParam("ciclo") int ciclo) {
        CrudCurriculo crud = new CrudCurriculo();

        try {
            ArrayList<CurriculoRespuesta> respuesta = crud.obtenerCurriculoPorGradoCiclo(idGrado, ciclo);
            return Response.ok(respuesta).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
  @DELETE
    @Path("{idCurriculo}")
    public Response borrarSeccion(@PathParam("idCurriculo") String idCurriculo) {
    CrudCurriculo crud = new CrudCurriculo();

        try {
            crud.quitarCursoDelCurriculo(idCurriculo);
            return Response.ok().build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (ElemtoAElminarEnUsoExcepcion ex) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }


}
