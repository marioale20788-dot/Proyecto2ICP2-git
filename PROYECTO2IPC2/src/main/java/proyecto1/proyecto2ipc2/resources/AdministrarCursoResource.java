/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;


import EntidadesDeServicio.AdministrarCurso;
import EntidadesDto.AdministrarCurso.AsistenciaRepuesta;
import EntidadesDto.AdministrarCurso.NuevaAsistenciaRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import jakarta.ws.rs.Consumes;
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

@Path("/Administrarcurso")
public class AdministrarCursoResource {
    
     @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response nuevaAsistencia(ArrayList<NuevaAsistenciaRequest> nuevaAsistencia) {
        AdministrarCurso administrarCurso = new AdministrarCurso();
        try {
           administrarCurso.TomarAsistencia(nuevaAsistencia);
            return Response.status(Response.Status.CREATED).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
    
     @GET
    @Path("{idResgistro}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerEstadoAsistencia(@PathParam("idResgistro") String idEstudiante) {
       AdministrarCurso administrarCurso = new AdministrarCurso();
        try {
            ArrayList<AsistenciaRepuesta> respuesta = administrarCurso.obtenerAsistencia(idEstudiante);
            return Response.ok(respuesta).build();
        } catch (ElementoNoEncotradoExcepcion e) {
                  return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DatosNoValidosExcepcion ex) {
               return Response.status(Response.Status.BAD_REQUEST).build();
         }
        
         
    }
    
  
}
