/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import EntidadesDeServicio.CrudPago;
import EntidadesDto.Pagos.GetCostosPorAnioEstudianteRequest;
import EntidadesDto.Pagos.GetPrestamosConMoraEstudiante;
import EntidadesDto.Pagos.NewPagoRequest;
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
import java.util.List;
/**
 *
 * @author mario
 */
@Path("/Pagos")
public class PagosResource {

    @Context
    UriInfo uriInfo;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registrarPago(NewPagoRequest nuevoPago) {
        CrudPago crud = new CrudPago();
        try {
            try {
                String idPago =crud.registrarPago(nuevoPago);
                return Response.ok(idPago).build();
            } catch (ElementoNoEncotradoExcepcion ex) {
                return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
            }

        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }

    }
    
 @GET
    @Path("{idEstudinate}/{idCiclo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCostosPendientes(@PathParam("idEstudinate") String idEstudiante,@PathParam("idCiclo") int idCiclo) {
      CrudPago crud = new CrudPago();
        try {
            List<GetCostosPorAnioEstudianteRequest> estudianteCostos = crud.costosPorEsudianteCiclo(idEstudiante, idCiclo)
                    .stream()
                    .map(GetCostosPorAnioEstudianteRequest::new)
                    .toList();
            return Response.ok(estudianteCostos).build();
        } catch (ElementoNoEncotradoExcepcion e) {
                  return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        
         
    }
     @GET
    @Path("{idEstudinate}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerPrestamosConMora(@PathParam("idEstudinate") String idEstudiante) {
      CrudPago crud = new CrudPago();
      
        try {
            List<GetPrestamosConMoraEstudiante> estudianteCostos = crud.prestamosConMoraEstudiante(idEstudiante)
                    .stream()
                    .map(GetPrestamosConMoraEstudiante::new)
                    .toList();
            return Response.ok(estudianteCostos).build();
        } catch (ElementoNoEncotradoExcepcion e) {
                  return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        
         
    }
    
    
 
}
