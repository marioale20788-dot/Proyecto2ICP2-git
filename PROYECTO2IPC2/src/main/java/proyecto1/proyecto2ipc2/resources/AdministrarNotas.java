/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import Entidades.NotaActividad;
import EntidadesDeServicio.AdministrarNotaFinal;
import EntidadesDeServicio.AdministrarNotasActividades;
import EntidadesDto.AdministrarCurso.GetNotas;
import EntidadesDto.AdministrarCurso.NuevaNotaRequest;
import EntidadesDto.AdministrarCurso.NuevanotaFinalRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
@Path("/Notas")
public class AdministrarNotas {
    
    
    
    
     @GET
    @Path("/{idResgistro}/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerNotas(@PathParam("idResgistro") String idResgistro,@PathParam("idUsuario") String idUsuario) {
       AdministrarNotasActividades notas = new AdministrarNotasActividades();
        try {
            ArrayList<GetNotas> respuesta = notas.obtenerNotas(idUsuario, idResgistro);
            return Response.ok(respuesta).build();
        } catch (ElementoNoEncotradoExcepcion e) {
                  return Response.status(Response.Status.NOT_FOUND).build();
        }
        
         
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertarNota(NuevaNotaRequest nuevaNota) {
      AdministrarNotasActividades notas = new AdministrarNotasActividades();
        try {
           NotaActividad nota = notas.actualizarNota(nuevaNota);
            return Response.ok(nota).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
    
      @POST
        @Path("/NotaFinal")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertarNotaFinal(NuevanotaFinalRequest notaFinal) {
      AdministrarNotaFinal admNotaFinal = new AdministrarNotaFinal();
        try {
          admNotaFinal.insertarNotaFinal(notaFinal);
            return Response.status(Response.Status.CREATED).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
    
    
    
    
}
