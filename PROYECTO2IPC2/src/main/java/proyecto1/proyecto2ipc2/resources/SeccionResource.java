/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import Entidades.Seccion;
import EntidadesDeServicio.CrudSeccion;
import EntidadesDto.Seccion.NuevaSeccionRequest;
import EntidadesDto.Seccion.SeccionRespuesta;

import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mario
 */
@Path("/Seccion")
public class SeccionResource {

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearSeccion(NuevaSeccionRequest nuevaSeccion) {
        CrudSeccion crud = new CrudSeccion();
        try {
            crud.crearSeccion(nuevaSeccion);
            return Response.status(Response.Status.CREATED).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarTodasLasSecciones() {
        CrudSeccion crud = new CrudSeccion();
        List<SeccionRespuesta> resupesta = crud.obtenerTodasLasSecciones(false, "")
                .stream()
                .map(SeccionRespuesta::new)
                .toList();
        return Response.ok(resupesta).build();

    }

    @GET
    @Path("{NombreSeccion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSeccionesPorNombre(@PathParam("NombreSeccion") String nombreSeccion) {
        CrudSeccion crud = new CrudSeccion();

        List<SeccionRespuesta> resupesta = crud.obtenerTodasLasSecciones(true, nombreSeccion)
                .stream()
                .map(SeccionRespuesta::new)
                .toList();
        return Response.ok(resupesta).build();
    }
      @GET
    @Path("/Grado/{idGrado}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSeccionesPorGrado(@PathParam("idGrado") String idGrado) {
        CrudSeccion crud = new CrudSeccion();

        List<SeccionRespuesta> resupesta = crud.obtenerTodasLasSeccionesPorGrado(idGrado)
                .stream()
                .map(SeccionRespuesta::new)
                .toList();
        return Response.ok(resupesta).build();
    }


    @PUT
    @Path("{idSeccion}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response UpdateSeccion(@PathParam("idSeccion") String idSeccion,
            NuevaSeccionRequest updatesSeccion) {
        CrudSeccion crud = new CrudSeccion();

        try {
            Seccion seccionEditado = crud.updateSeccion(idSeccion, updatesSeccion);
            return Response.ok(new SeccionRespuesta(seccionEditado)).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @DELETE
    @Path("{idLibro}")
    public Response borrarSeccion(@PathParam("idLibro") String idLibro) {
    CrudSeccion crud = new CrudSeccion();
        try {
            crud.eliminarSeccion(idLibro);
            return Response.ok().build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (ElemtoAElminarEnUsoExcepcion ex) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

}
