/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import Entidades.Curso;
import EntidadesDeServicio.CrudCurso;
import EntidadesDto.Curso.UpdateCurso;
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
@Path("/Curso")
public class CursoResource {
     @Context
    UriInfo uriInfo;
     
     @POST
     @Consumes(MediaType.APPLICATION_JSON)
     public Response crearCurso(Curso nuevoCurso){
         CrudCurso crud = new CrudCurso();
         try {
             crud.crearCurso(nuevoCurso);
             return Response.status(Response.Status.CREATED).build();
         } catch (DatosNoValidosExcepcion ex) {
           return Response.status(Response.Status.BAD_REQUEST).build();
         }
         
     }
     
     @GET
      @Produces(MediaType.APPLICATION_JSON)
    public Response buscarTodosLosCursos() {
         CrudCurso crud = new CrudCurso();
         ArrayList<Curso>cursos = crud.obtenerTodosLosCursos();
         return Response.ok(cursos).build();
        
    }
    
    @GET
    @Path("{identificador}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCursoPorCodigoNombre(@PathParam("identificador") String identificador) {
     CrudCurso crud = new CrudCurso();
        try {

            Curso curso = crud.buscarCarreraPorCodigoNombre(identificador);
            return Response.ok(curso).build();
        } catch (ElementoNoEncotradoExcepcion e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
    @PUT
    @Path("{identificador}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response UpdateCurso(@PathParam("identificador") String codigoCurso,
            UpdateCurso cursoUpdate) {
        CrudCurso crud = new CrudCurso();
        try {
          Curso curso = crud.updateCurso(codigoCurso, cursoUpdate);
            return Response.ok(curso).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
         @DELETE
    @Path("{codigoCurso}")
    public Response borrarCurso(@PathParam("codigoCurso") String codigoCurso) {
             CrudCurso crud = new CrudCurso();
        try {
            crud.elminarCurso(codigoCurso);
            return Response.ok().build();
        } catch (ElementoNoEncotradoExcepcion ex) {
           return Response.status(Response.Status.NOT_FOUND).build();
        } catch (ElemtoAElminarEnUsoExcepcion ex) {
            return Response.status(Response.Status.CONFLICT).build();        }
    }
    

    
}
