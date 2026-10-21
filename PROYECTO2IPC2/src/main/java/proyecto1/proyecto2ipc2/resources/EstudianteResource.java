/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import EntidadesDeServicio.CrudEstudiante;
import EntidadesDeServicio.CrudUsuario;
import EntidadesDto.Empleado.EmpleadoUsuarioRespuesta;
import EntidadesDto.Empleado.NuevoUsuarioRequest;
import EntidadesDto.Estudiante.EstudianteRespuesta;
import EntidadesDto.Estudiante.NuevoEstudianteDatosMedicosRequest;
import EntidadesDto.Estudiante.NuevoEstudianteEncargadoRequest;
import EntidadesDto.Estudiante.NuevoEstudianteRequest;
import EntidadesDto.Estudiante.RegistroEstudianteCompleto;
import EntidadesDto.Estudiante.UpdateEstudiante;
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
@Path("/Estudiante")
public class EstudianteResource {
      @Context
    UriInfo uriInfo;

        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        public Response crearEstudiante(RegistroEstudianteCompleto registros) {
            CrudEstudiante crud = new CrudEstudiante();
            try {
                crud.crearEstudiante(registros.getEstudiante(), registros.getEncargado(), registros.getDatosMedicos());
                return Response.status(Response.Status.CREATED).build();
            } catch (DatosNoValidosExcepcion ex) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

        }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
     @Path("/{estado}")
    public Response buscarTodosLosEstudiantes(@PathParam("estado") boolean estado) {
       CrudEstudiante crud = new CrudEstudiante();

        try {
            ArrayList<EstudianteRespuesta> respuesta = crud.obtnerTodosLosEstudiantes(estado);
                  return Response.ok(respuesta).build();
        } catch (DatosNoValidosExcepcion ex) {
                 return Response.status(Response.Status.NOT_FOUND).build();
        }
       

    }

 @GET
    @Path("/dpi/{dpi}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarEstudiantePorDpi(@PathParam("dpi") String dpi) {
        CrudEstudiante crud = new CrudEstudiante();

        try {
            EstudianteRespuesta respuesta = crud.buscarEstudiantePorId(dpi);
                  return Response.ok(respuesta).build();
        } catch (DatosNoValidosExcepcion | ElementoNoEncotradoExcepcion ex) {
                 return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
          @PUT
    @Path("{dpi}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response UpdateEmpleado(@PathParam("dpi") String dpi,
            UpdateEstudiante update) {
          CrudEstudiante crud = new CrudEstudiante();

        try {
             EstudianteRespuesta respuesta   = crud.updateEstudiante(dpi, update);
              return Response.ok(respuesta).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
    
    
    
    
    }
