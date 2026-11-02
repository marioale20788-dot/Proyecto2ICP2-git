/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import Entidades.Pagos;
import EntidadesDeServicio.AdministrarNotaFinal;
import EntidadesDeServicio.AdministrarNotasActividades;
import EntidadesDeServicio.CrudEstudiante;
import EntidadesDeServicio.CrudLibro;
import EntidadesDeServicio.CrudPago;
import EntidadesDeServicio.CrudPrestamo;
import EntidadesDto.AdministrarCurso.GetNotas;
import EntidadesDto.AdministrarCurso.NotaFinalResponse;
import EntidadesDto.Estudiante.EstudianteRespuesta;
import EntidadesDto.Libro.LibroRespuesta;
import EntidadesDto.Pagos.GetCostosPorAnioEstudianteRequest;
import EntidadesDto.Pagos.GetPrestamosConMoraEstudiante;
import EntidadesDto.Prestamos.PrestamoRespuesta;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mario
 */
@Path("/PerfilEstudiante")
public class perfilEstudianteResource {
    
    
     @GET
    @Path("dpi/{dpi}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarEstudiantePorDpi(@PathParam("dpi") String dpi) {
        CrudEstudiante crud = new CrudEstudiante();

        try {
            EstudianteRespuesta respuesta = crud.buscarEstudiantePorId(dpi);
                  return Response.ok(respuesta).build();
        } catch (DatosNoValidosExcepcion | ElementoNoEncotradoExcepcion ex) {
                 return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
    }
         @GET
    @Path("Notas/{idUsuario}/{idCiclo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerNotas(@PathParam("idUsuario") String idUsuario,@PathParam("idCiclo") int ciclo) {
         
        AdministrarNotaFinal notas = new AdministrarNotaFinal();
        try {
            ArrayList<NotaFinalResponse> respuesta = notas.obtenerNotaFinal(idUsuario, ciclo);
            return Response.ok(respuesta).build();
        } catch (ElementoNoEncotradoExcepcion e) {
                  return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        
         
    }
    
           @GET
    @Path("Pagos/{idUsuario}/{idCiclo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pagosRealizados(@PathParam("idUsuario") String idUsuario,@PathParam("idCiclo") int ciclo) {
         
        CrudPago pagos =  new CrudPago();
        try {
            ArrayList<Pagos> respuesta = pagos.pagosRealizados(idUsuario, ciclo);
            return Response.ok(respuesta).build();
        } catch (ElementoNoEncotradoExcepcion e) {
                  return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
        
         
    }
    @GET
    @Path("Costos/{idEstudinate}/{idCiclo}")
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
        @Path("Libros/pagina/{numero}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTodosLosLibros(@PathParam("numero")int pagina) {
       CrudLibro crud = new CrudLibro();
            try {
                       List<LibroRespuesta> libros = crud.buscarLibros(pagina)
                 .stream()
                .map(LibroRespuesta::new)
                .toList();

        return Response.ok(libros).build();
            } catch (Exception e) {
                return Response.status(Response.Status.BAD_REQUEST)
                       .entity(e.getMessage())
                       .build();
            }
  

            

    }
      @GET
    @Path("Prestamo/Prestamo/{idUsuario}")
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
             return Response.status(Response.Status.NOT_FOUND).entity(ex.getMessage()).build();
        }
      
    }
       @GET
    @Path("Mora/{idEstudinate}")
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
