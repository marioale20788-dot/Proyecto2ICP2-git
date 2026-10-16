/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import Entidades.Carrera;
import EntidadesDeServicio.CrudCarrera;
import EntidadesDeServicio.CrudGrado;
import EntidadesDto.Carrera.UpdateCarrera;
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

/**
 *
 * @author mario
 */
@Path("/Carreras")
public class CarreraResource {

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response CrearCarrera(Carrera nuevaCarrera) {
        CrudCarrera crud = new CrudCarrera();
        try {
            crud.crearCarrera(nuevaCarrera);
            return Response.status(Response.Status.CREATED).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarTodasLasCarreras() {
        CrudCarrera crud = new CrudCarrera();
        ArrayList<Carrera> carreras = crud.buscarTodasCarreras();
        return Response.ok(carreras).build();
    }

    @GET
    @Path("{identificador}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCarreraPorIdNombre(@PathParam("identificador") String identificador) {
        CrudCarrera crud = new CrudCarrera();
        try {

            Carrera carrera = crud.buscarCarreraPorNombreId(identificador);
            return Response.ok(carrera).build();
        } catch (ElementoNoEncotradoExcepcion e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{identificador}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response UpdateCarrera(@PathParam("identificador") String id,
            UpdateCarrera updateCarrera) {
        CrudCarrera crud = new CrudCarrera();
        try {
            Carrera carrera = crud.updateCarrera(id, updateCarrera);
            return Response.ok(carrera).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
        @DELETE
    @Path("{idCarrera}")
    public Response borrarCarrera(@PathParam("idCarrera") String idCarrera) {
           CrudCarrera crud = new CrudCarrera();
        try {
            crud.elminiarCarrera(idCarrera);
            return Response.ok().build();
        } catch (ElementoNoEncotradoExcepcion ex) {
           return Response.status(Response.Status.NOT_FOUND).build();
        } catch (ElemtoAElminarEnUsoExcepcion ex) {
            return Response.status(Response.Status.CONFLICT).build();        }
    }

}
