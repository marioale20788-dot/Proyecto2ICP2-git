/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import Entidades.ActividadZona;
import EntidadesDeServicio.CrudZona;
import EntidadesDto.AdministrarCurso.NuevaActividadZona;
import EntidadesDto.AdministrarCurso.UpdateActividadRequest;
import EntidadesDto.AdministrarCurso.ZonaActividadRespuesta;

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
import java.util.List;

/**
 *
 * @author mario
 */
@Path("/Zona")
public class ZonaCursoResource {

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearActividad(NuevaActividadZona nuevaActividad) {
        CrudZona crud = new CrudZona();
        try {
            crud.crearActividad(nuevaActividad);
            return Response.status(Response.Status.CREATED).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @GET
    @Path("{idAsignacion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerActividades(@PathParam("idAsignacion") String idAsignacion) {
        try {
            CrudZona crud = new CrudZona();

            List<ZonaActividadRespuesta> resupesta = crud.obtenerActiviadesDeAsignacion(idAsignacion)
                    .stream()
                    .map(ZonaActividadRespuesta::new)
                    .toList();
            return Response.ok(resupesta).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("{idActividad}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response UpdateActividad(@PathParam("idActividad") String idActividad,
            UpdateActividadRequest update) {
        CrudZona crud = new CrudZona();

        try {
            ActividadZona actividadZona = crud.updateActividad(idActividad, update);
            return Response.ok(new ZonaActividadRespuesta(actividadZona)).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
     @DELETE
    @Path("{idActividad}")
    public Response borrarActividad(@PathParam("idActividad") String idActividad) {
      CrudZona crud = new CrudZona();
        try {
            crud.eliminarActividad(idActividad);
            return Response.ok().build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (ElemtoAElminarEnUsoExcepcion ex) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }


}
