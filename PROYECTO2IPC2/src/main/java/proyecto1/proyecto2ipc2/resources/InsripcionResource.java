/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import EntidadesDeServicio.CrudInscrpcion;
import EntidadesDto.Inscripcion.IncripcionRespuesta;

import EntidadesDto.Inscripcion.NewIncricpionRequest;
import EntidadesDto.Inscripcion.UpdateIncripcionRequest;

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
import java.util.List;

/**
 *
 * @author mario
 */
@Path("/Incripcion")
public class InsripcionResource {

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearSeccion(NewIncricpionRequest nuevaIncripcion) {
        CrudInscrpcion crud = new CrudInscrpcion();
        try {
            crud.inscribirEstudiante(nuevaIncripcion);
            return Response.status(Response.Status.CREATED).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @GET
    @Path("/Estudiante/{idEstudiante}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSeccionesPorNombre(@PathParam("idEstudiante") String idEstudiante) {
        CrudInscrpcion crud = new CrudInscrpcion();

        List<IncripcionRespuesta> resupesta;
        try {
            resupesta = crud.obtenerInscripcionesPorEstuidante(idEstudiante);

            return Response.ok(resupesta).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }

    @GET
    @Path("/Ciclo/{ciclo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerSeccionesPorCiclo(@PathParam("ciclo") int ciclo) {
        CrudInscrpcion crud = new CrudInscrpcion();

        ArrayList<IncripcionRespuesta> resupesta;

        try {
            resupesta = crud.obtenerSeccionesPorCiclo(ciclo);
              return Response.ok(resupesta).build();

        } catch (ElementoNoEncotradoExcepcion ex) {
          return Response.status(Response.Status.NOT_FOUND).build();
        }

      
    }
    
       @PUT
    @Path("{idInscripcion}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response UpdateSeccion(@PathParam("idInscripcion") String idInscripcion,
            UpdateIncripcionRequest update) {
         CrudInscrpcion crud = new CrudInscrpcion();

        try {
            IncripcionRespuesta respuestaInscripcion = crud.updateInscripcion(update, idInscripcion);
            return Response.ok(respuestaInscripcion).build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
    
    @DELETE

    public Response borrarSeccion(NewIncricpionRequest inscripcionAEliminar) {
      CrudInscrpcion crud = new CrudInscrpcion();
        try {
            crud.elminiarInscipcion(inscripcionAEliminar);
            return Response.ok().build();
        } catch (ElementoNoEncotradoExcepcion ex) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (ElemtoAElminarEnUsoExcepcion ex) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

}
