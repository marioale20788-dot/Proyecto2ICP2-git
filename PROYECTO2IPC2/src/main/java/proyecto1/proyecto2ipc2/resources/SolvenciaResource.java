/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import EntidadesDeServicio.VerificarPagosSolvencia;
import EntidadesDto.Solvencia.GetCostosParaGenerarSolvencia;
import EntidadesDto.Solvencia.GetPagosPorFechaRequest;
import EntidadesDto.Solvencia.PagosPorFechaRespuesta;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import jakarta.ws.rs.GET;
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
@Path("/Solvencia")
public class SolvenciaResource {

    @Context
    UriInfo uriInfo;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCostosPendientes(GetPagosPorFechaRequest pagosPorFecha) {
        VerificarPagosSolvencia verificarPago = new VerificarPagosSolvencia();
        try {
            List<PagosPorFechaRespuesta> pagos = verificarPago.obtenerPagosDeUsuarioAnio(pagosPorFecha)
                    .stream()
                    .map(PagosPorFechaRespuesta::new)
                    .toList();
            return Response.ok(pagos).build();
        } catch (ElementoNoEncotradoExcepcion e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (DatosNoValidosExcepcion ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{idEstudiante}/{Ciclo}")
    public Response obtenerCostosParaGenerarSolvencia(@PathParam("idEstudiante") String idEstudiante, @PathParam("Ciclo") int ciclo) {
        VerificarPagosSolvencia verificarPago = new VerificarPagosSolvencia();
        try {
            ArrayList<GetCostosParaGenerarSolvencia> costos = verificarPago.obtenerCostosyEstados(idEstudiante, ciclo);
            return Response.ok(costos).build();
        } catch (ElementoNoEncotradoExcepcion e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

    }
    
    
}
