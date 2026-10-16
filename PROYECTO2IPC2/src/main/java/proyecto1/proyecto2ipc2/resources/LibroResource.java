/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import Entidades.Libro;
import EntidadesDeServicio.CrudLibro;
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
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mario
 */
@Path("/Libro")
public class LibroResource {

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearLibro(NewLibroRequest libroACrear) {
        CrudLibro crud = new CrudLibro();

        try {
            crud.crearLibro(libroACrear);
            return Response.status(Response.Status.CREATED).build();
        } catch (DatosNoValidosExcepcion e) {
            return Response.status(Response.Status.BAD_REQUEST).build();

        }

    }
        @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTodosLosLibros() {
       CrudLibro crud = new CrudLibro();
           List<LibroRespuesta> libros = crud.buscarLibros()
                 .stream()
                .map(LibroRespuesta::new)
                .toList();

        return Response.ok(libros).build();
            

    }
    @GET
    @Path("{idLibro}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerLibrosPorIdNombre(@PathParam("idLibro") String idLibro) {
        CrudLibro crud = new CrudLibro();
        try {
            Libro libro = crud.obtenerLibroPorNombreId(idLibro);
            return Response.ok(new LibroRespuesta(libro)).build();
        } catch (ElementoNoEncotradoExcepcion e) {
                  return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
        

    @PUT
    @Path("{idLibro}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response UpdateLibro(@PathParam("idLibro") String idLibro,
            UpdateLibroRequest updatesLibro) {
        
         CrudLibro crud = new CrudLibro();
        try {
            Libro libroEditado = crud.updateLibro(idLibro, updatesLibro);
             return Response.ok(new LibroRespuesta(libroEditado)).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
           return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        
    }
    @DELETE
    @Path("{idLibro}")
    public Response borrarLibro(@PathParam("idLibro") String idLibro) {
       CrudLibro crud = new CrudLibro();

        try {
            crud.eliminarLibro(idLibro);
            return Response.ok().build();
        } catch (ElementoNoEncotradoExcepcion ex) {
           return Response.status(Response.Status.NOT_FOUND).build();
        } catch (ElemtoAElminarEnUsoExcepcion ex) {
            return Response.status(Response.Status.CONFLICT).build();        }
    }
 


}
