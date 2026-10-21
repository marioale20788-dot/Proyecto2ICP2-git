/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import Entidades.Carrera;
import EntidadesDeServicio.CrudCarrera;
import EntidadesDeServicio.CrudUsuario;
import EntidadesDto.Empleado.EmpleadoUsuarioRespuesta;
import EntidadesDto.Empleado.NuevoUsuarioRequest;
import EntidadesDto.Empleado.UpdateEmpleadoRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import jakarta.ws.rs.Consumes;
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
@Path("/Administradores")
public class AdministradoresResource {

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearAdmin(NuevoUsuarioRequest nuevoAdmin) {

        CrudUsuario crud = new CrudUsuario();
        try {
            crud.crearUsuario(nuevoAdmin);
            return Response.status(Response.Status.CREATED).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
     @Path("/{estado}")
    public Response buscarTodosLosUsuarios(@PathParam("estado") boolean estado) {
        CrudUsuario crud = new CrudUsuario();

        try {
            ArrayList<EmpleadoUsuarioRespuesta> respuesta = crud.buscarTodosUsuariosRespuesta(false, null,estado);
                  return Response.ok(respuesta).build();
        } catch (DatosNoValidosExcepcion ex) {
                 return Response.status(Response.Status.NOT_FOUND).build();
        }
       

    }

    @GET
    @Path("/rol/{rol}/{estado}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarUsuarioPorRol(@PathParam("rol") String rol,@PathParam("estado") boolean estado) {
       CrudUsuario crud = new CrudUsuario();

        try {
            ArrayList<EmpleadoUsuarioRespuesta> respuesta = crud.buscarTodosUsuariosRespuesta(true, rol,estado);
                  return Response.ok(respuesta).build();
        } catch (DatosNoValidosExcepcion ex) {
                 return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
 @GET
    @Path("/dpi/{dpi}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarUsuarioPorDpi(@PathParam("dpi") String dpi) {
       CrudUsuario crud = new CrudUsuario();

        try {
            EmpleadoUsuarioRespuesta respuesta = crud.buscarEmpleadoUsuarioPorId(dpi);
                  return Response.ok(respuesta).build();
        } catch (DatosNoValidosExcepcion | ElementoNoEncotradoExcepcion ex) {
                 return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    
      @PUT
    @Path("{dpi}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response UpdateAdmin(@PathParam("dpi") String dpi,
            UpdateEmpleadoRequest update) {
       CrudUsuario crud = new CrudUsuario();
        try {
             EmpleadoUsuarioRespuesta updateUsuario  = crud.actualizarUsuario(dpi, update);
            return Response.ok(updateUsuario).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
    
    
}
