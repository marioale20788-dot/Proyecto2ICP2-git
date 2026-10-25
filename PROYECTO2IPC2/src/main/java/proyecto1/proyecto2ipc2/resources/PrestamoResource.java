/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;


import EntidadesDeServicio.CrudPrestamo;
import EntidadesDto.Prestamos.FinalizarPrestamo;

import EntidadesDto.Prestamos.NewPrestamoRequest;
import EntidadesDto.Prestamos.PrestamoRespuesta;
import EntidadesDto.Prestamos.PrestamoUpdateRequest;

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
@Path("/Prestamo")
public class PrestamoResource {
    
    
    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response RegistrarPrestamo(NewPrestamoRequest nuevoPrestamo) {
        CrudPrestamo crud = new CrudPrestamo();
        try {
            crud.registrarPrestamo(nuevoPrestamo);
            return Response.status(Response.Status.CREATED).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
               return Response.status(Response.Status.NOT_FOUND).build();
        }   

    }
     @GET
    @Path("/Prestamo/{idUsuario}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPrestamosActivos(@PathParam("idUsuario") String idUsuario) {
             CrudPrestamo crud = new CrudPrestamo();

        List<PrestamoRespuesta> resupesta;
        try {
            resupesta = crud.obtenerPrestamosActivos(idUsuario)
                    .stream()
                    .map(PrestamoRespuesta::new)
                    .toList();
              return Response.ok(resupesta).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
             return Response.status(Response.Status.NOT_FOUND).build();
        }
      
    }
     @GET
       @Path("/{idPrestamo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPrestamoPorId(@PathParam("idPrestamo") String idPrestamo) {
             CrudPrestamo crud = new CrudPrestamo();

       
        try {
          PrestamoRespuesta respuesta = new PrestamoRespuesta(crud.obtenerPrestamoPorId(idPrestamo));
              return Response.ok(respuesta).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
             return Response.status(Response.Status.NOT_FOUND).build();
        }
      
    }
    
    
     @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response devolverLibro(FinalizarPrestamo finalizarPrestamo) {
        CrudPrestamo crud = new CrudPrestamo();

        try {
            crud.terminarPrestamo(finalizarPrestamo);
            return Response.ok().build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
    
     @PUT
    @Path("/Prestamo/{idPrestamo}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePretamo(@PathParam("idPrestamo") String idPrestamo,
          PrestamoUpdateRequest updatePrestamo) {
          CrudPrestamo crud = new CrudPrestamo();

        try {
          PrestamoRespuesta respuesta = new PrestamoRespuesta(crud.updatePrestamo(idPrestamo, updatePrestamo));
            return Response.ok(respuesta).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"error\": \"" + ex.getMessage() + "\"}")
               .type(MediaType.APPLICATION_JSON)
               .build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"" + ex.getMessage() + "\"}")
               .type(MediaType.APPLICATION_JSON)
               .build();
        }

    }
    
    
   
    @DELETE
    @Path("{idPrestamo}")
    public Response borrarPrestamo(@PathParam("idPrestamo") String idPrestamo) {
    CrudPrestamo crud = new CrudPrestamo();;
        try {
            crud.eliminarPretamo(idPrestamo);
            return Response.ok().build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (ElemtoAElminarEnUsoExcepcion ex) {
            return Response.status(Response.Status.CONFLICT).build();
        } catch (DatosNoValidosExcepcion ex) {
           return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    


    
    
    
}
