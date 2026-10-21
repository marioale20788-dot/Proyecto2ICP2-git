/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import Entidades.AsignacionDocente;
import EntidadesDeServicio.CrudAsignacionDocente;
import EntidadesDto.AsignacionMaestro.NuevaAsginacionGetRequest;

import EntidadesDto.AsignacionMaestro.NuevaAsignacionRequest;
import EntidadesDto.AsignacionMaestro.NuevaAsingacionRespuesta;
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
import java.util.List;

/**
 *
 * @author mario
 */
@Path("/AsignacionDocente")
public class AsignacionDocenteResource {

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearSeccion(NuevaAsignacionRequest nuevaAsginacion) {
        CrudAsignacionDocente crud = new CrudAsignacionDocente();
        try {
            crud.asingarDocente(nuevaAsginacion);
            return Response.status(Response.Status.CREATED).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @GET

    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSeccionesPorGrado(NuevaAsginacionGetRequest nuevaAsignacion) {
        CrudAsignacionDocente crud = new CrudAsignacionDocente();
        try {
            AsignacionDocente asingacion = crud.obtenerAsignacionsPorSeccionCurriculo(nuevaAsignacion.getIdSeccion(), nuevaAsignacion.getIdCurriculo());
            return Response.ok(new NuevaAsingacionRespuesta(asingacion)).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
      @DELETE
    @Path("{idAsignacion}")
    public Response borrarSeccion(@PathParam("idAsignacion") String idAsginacion) {
     CrudAsignacionDocente crud = new CrudAsignacionDocente();
        try {
            crud.eliminarAsignacion(idAsginacion);
            return Response.ok().build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (ElemtoAElminarEnUsoExcepcion ex) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }


}
