/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

/**
 *
 * @author mario
 */
import EntidadesDeServicio.CrudGrado;
import EntidadesDto.Grado.GradoRespuesta;
import EntidadesDto.Grado.NewGradoRequest;
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

@Path("/Grado")
public class GradoResource {

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearGrado(NewGradoRequest nuevoGrado) {
        CrudGrado crud = new CrudGrado();

        try {
            crud.crearGrado(nuevoGrado);

            return Response.status(Response.Status.CREATED).build();
        } catch (DatosNoValidosExcepcion e) {
            return Response.status(Response.Status.BAD_REQUEST).build();

        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTodosLosGrados() {
        CrudGrado crud = new CrudGrado();;
        List<GradoRespuesta> grados = crud.obtenerTodosLosGrados()
                .stream()
                .map(GradoRespuesta::new)
                .toList();

        return Response.ok(grados).build();

    }

    @GET
    @Path("{idNivel}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerGradosPorNivel(@PathParam("idNivel") String idNivel) {
        CrudGrado crud = new CrudGrado();;
        try {
            List<GradoRespuesta> grados = crud.obtenerGradoPorNivel(idNivel)
                    .stream()
                    .map(GradoRespuesta::new)
                    .toList();

            return Response.ok(grados).build();

        } catch (ElementoNoEncotradoExcepcion e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
    @DELETE
    @Path("{idGrado}")
    public Response borrarGrado(@PathParam("idGrado") String idGrado) {
        CrudGrado crud = new CrudGrado();

        try {
            crud.EliminarGrado(idGrado);
            return Response.ok().build();
        } catch (ElementoNoEncotradoExcepcion ex) {
           return Response.status(Response.Status.NOT_FOUND).build();
        } catch (ElemtoAElminarEnUsoExcepcion ex) {
            return Response.status(Response.Status.CONFLICT).build();        }
    }
}
