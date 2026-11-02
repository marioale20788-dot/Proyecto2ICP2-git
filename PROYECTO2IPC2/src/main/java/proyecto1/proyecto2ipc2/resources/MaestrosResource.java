/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import EntidadesDeServicio.SistemaMaestros;
import EntidadesDto.Maestro.getAsignacionesRequest;
import EntidadesDto.Maestro.getEstudiantesInscritos;
import EntidadesDto.Maestro.getInscripcionesSeccionRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mario
 */
@Path("/Maestros")
public class MaestrosResource {

    @GET
    @Path("/{idMaestro}/{ciclo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSeccionesPorGrado(@PathParam("idMaestro") String idMaestro, @PathParam("ciclo") int ciclo) {
        SistemaMaestros maestros = new SistemaMaestros();

        try {
            ArrayList<getAsignacionesRequest> asignaciones = maestros.obtenerAsignaciones(idMaestro, ciclo);
            return Response.ok(asignaciones).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
  @GET
    @Path("/{idSeccion}/{ciclo}/{idAsignacion}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEstudiantesInscritosAseccion(
            @PathParam("idSeccion") String idSeccion,
            @PathParam("ciclo") int ciclo,
            @PathParam("idAsignacion") String idAsignacion) {

        SistemaMaestros maestros = new SistemaMaestros();

        try {
            ArrayList<getInscripcionesSeccionRequest> inscripciones = maestros.obtenerEstuidantesInscritos(
                    idSeccion, 
                    ciclo, 
                    idAsignacion
            );
            return Response.ok(inscripciones).build();

        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    

}
