/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import Entidades.EnumPago;
import Entidades.Pagos;
import EntidadesDeServicio.CrudPago;
import EntidadesDeServicio.CrudPrestamo;
import EntidadesDeServicio.Reportes;
import EntidadesDto.Pagos.GetCostosPorAnioEstudianteRequest;
import EntidadesDto.Pagos.GetPrestamosConMoraEstudiante;
import EntidadesDto.Prestamos.PrestamoRespuesta;
import EntidadesDto.Reportes.GetAlumnosSolventesMorosos;
import EntidadesDto.Reportes.GetReporteAcademico;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mario
 */
@Path("/Reportes")


public class ReportesResource {
    
    
      @GET
    @Path("solvente/{idSeccion}/{ciclo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReporteSolventes(@PathParam("idSeccion") String idSeccion,@PathParam("ciclo") int ciclo) {
          Reportes reportes = new Reportes();
          try {
              ArrayList<GetAlumnosSolventesMorosos> alumnos = reportes.reporteAlumosMorosos(idSeccion, ciclo);
              return Response.ok(alumnos).build();
          } catch (ElementoNoEncotradoExcepcion ex) {
             return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
          } catch (DatosNoValidosExcepcion ex) {
                   return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
          }
       
      
    }
    
        
      @GET
    @Path("RendimientoAcademico/{idSeccion}/{ciclo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReporteAcademico(@PathParam("idSeccion") String idSeccion,@PathParam("ciclo") int ciclo) {
          Reportes reportes = new Reportes();
          try {
              ArrayList<GetReporteAcademico> reporte = reportes.promedioCursos(idSeccion, ciclo);
              return Response.ok(reporte).build();
          } catch (ElementoNoEncotradoExcepcion ex) {
             return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
          }
       
      
    }
    
           @GET
        @Path("Pagos/{idUsuario}/{fechaInicio}/{fechaFinal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pagosRealizados(@PathParam("idUsuario") String idUsuario,@PathParam("fechaInicio") String fechaInicio,@PathParam("fechaFinal") String fechaFinal) {
         
        CrudPago pagos =  new CrudPago();
        try {
            ArrayList<Pagos> respuesta = pagos.pagosRealizadosPorFecha(idUsuario, fechaInicio,fechaFinal);
            return Response.ok(respuesta).build();
        } catch (ElementoNoEncotradoExcepcion e) {
                  return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatosNoValidosExcepcion ex) {
             return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
          }
        
         
    }
    @GET
    @Path("Costos/{idEstudinate}/{fechaInicio}/{fechaFinal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerCostosPendientes(@PathParam("idEstudinate") String idEstudiante,@PathParam("fechaInicio") String fechaInicio,@PathParam("fechaFinal") String fechaFinal) {
      CrudPago crud = new CrudPago();
        try {
            List<GetCostosPorAnioEstudianteRequest> estudianteCostos = crud.costosPorEsudianteFecha(idEstudiante, fechaInicio,fechaFinal)
                    .stream()
                    .map(GetCostosPorAnioEstudianteRequest::new)
                    .toList();
            return Response.ok(estudianteCostos).build();
        } catch (ElementoNoEncotradoExcepcion e) {
                  return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        
         
    }
   
      @GET
    @Path("Ingresos/{tipo}/{fechaInicio}/{fechaFinal}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reporteIngresos(@PathParam("fechaInicio") String fechaInicio,@PathParam("fechaFinal") String fechaFinal,@PathParam("tipo") EnumPago tipo) {
      Reportes reportes = new Reportes();
      
        try {
          ArrayList<Pagos> respuesta = reportes.reportePagos(tipo, fechaInicio, fechaFinal);
            return Response.ok(respuesta).build();
        } catch (ElementoNoEncotradoExcepcion e) {
                  return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DatosNoValidosExcepcion ex) {
             return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
          }
        
         
    }
    
      @GET
    @Path("Biblioteca")
    @Produces(MediaType.APPLICATION_JSON)
    public Response reporteBiblioteca() {
      Reportes reportes = new Reportes();
         try {
            List<GetPrestamosConMoraEstudiante> estudianteCostos = reportes.prestamosConMora()
                    .stream()
                    .map(GetPrestamosConMoraEstudiante::new)
                    .toList();
            return Response.ok(estudianteCostos).build();
        } catch (ElementoNoEncotradoExcepcion e) {
                  return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        
         
    
         
    }
    
    
    
    
}
