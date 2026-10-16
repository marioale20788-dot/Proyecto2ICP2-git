/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import Entidades.CicloLectivo;
import Entidades.Libro;
import EntidadesDeServicio.CrudCicloLectivo;
import EntidadesDeServicio.CrudLibro;
import EntidadesDto.CicloLectivo.CicloLectivoRespuesta;
import EntidadesDto.CicloLectivo.NewCicloRequest;
import EntidadesDto.CicloLectivo.UpdateCicloRequest;
import EntidadesDto.Libro.LibroRespuesta;
import EntidadesDto.Libro.NewLibroRequest;
import EntidadesDto.Libro.UpdateLibroRequest;
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
@Path("/CicloLectivo")
public class CicloLectivoResource {
    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearCiclo(NewCicloRequest nuevoCiclo) {
        CrudCicloLectivo crud = new CrudCicloLectivo();

        try {
            crud.crearCicloLectivo(nuevoCiclo);
            
            return Response.status(Response.Status.CREATED).build();
        } catch (DatosNoValidosExcepcion e) {
            return Response.status(Response.Status.BAD_REQUEST).build();

        }

    }
        @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCiclosLectivos() {
      CrudCicloLectivo crud = new CrudCicloLectivo();
           List<CicloLectivoRespuesta> ciclos = crud.obtenerTodosLosCiclos()
                 .stream()
                .map(CicloLectivoRespuesta::new)
                .toList();

        return Response.ok(ciclos).build();
            

    }
    @GET
    @Path("{anio}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCicloPorAnio(@PathParam("anio") int anio) {
   CrudCicloLectivo crud = new CrudCicloLectivo();
        try {
            CicloLectivo ciclo = crud.obtenerCicloPorAnio(anio);
            return Response.ok(new CicloLectivoRespuesta(ciclo)).build();
        } catch (ElementoNoEncotradoExcepcion e) {
                  return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
        

    @PUT
    @Path("{anio}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response UpdateCiclo(@PathParam("anio") int anio,
            UpdateCicloRequest updatesCiclo) {
        
        CrudCicloLectivo crud = new CrudCicloLectivo();
        try {
            CicloLectivo ciclo = crud.updateCiclo(anio, updatesCiclo);
             return Response.ok(new CicloLectivoRespuesta(ciclo)).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
           return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        
    }
    @DELETE
    @Path("{anio}")
    public Response borrarCiclo(@PathParam("anio") int anio) {
       CrudCicloLectivo crud = new CrudCicloLectivo();

        try {
            crud.ElminiarCiclo(anio);
            return Response.ok().build();
        } catch (ElementoNoEncotradoExcepcion ex) {
           return Response.status(Response.Status.NOT_FOUND).build();
        } catch (ElemtoAElminarEnUsoExcepcion ex) {
            return Response.status(Response.Status.CONFLICT).build();        }
    }
 


}
