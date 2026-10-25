/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.ActividadZona;
import EntidadesDAO.ActividadZonaDB;
import EntidadesDto.AdministrarCurso.NuevaActividadZona;
import EntidadesDto.AdministrarCurso.UpdateActividadRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class CrudZona {
    
    
    
    public void crearActividad(NuevaActividadZona nuevaActividad) throws DatosNoValidosExcepcion{
        ActividadZona actividad = new ActividadZona(nuevaActividad.getNombreActividad(),
                nuevaActividad.getPonderacion(), nuevaActividad.getFechaEntrega(),nuevaActividad.getIdAsignacion());
        
        if(actividad.esValido()==false){
            throw new DatosNoValidosExcepcion("DATOS INVALIDOS");
        }
        ActividadZonaDB actividadDb = new ActividadZonaDB();
        actividad.setIdActividad(actividadDb.generarIdAleatorio());
        actividadDb.crearActividad(actividad);
        
     
        
    }
    public ArrayList<ActividadZona> obtenerActiviadesDeAsignacion(String idAsignacion) throws ElementoNoEncotradoExcepcion{
        ActividadZonaDB actividadDb = new ActividadZonaDB();
        ArrayList<ActividadZona> actividades = actividadDb.obtenerActividadesAsignacion(idAsignacion);
        if(actividades.isEmpty()){
              throw new ElementoNoEncotradoExcepcion("NO EXISTEN ACTIVIDADES CREADAS");
        }
        
        return actividades;
        
    }
    public ActividadZona updateActividad(String idActividad, UpdateActividadRequest update) throws ElementoNoEncotradoExcepcion, DatosNoValidosExcepcion{
          ActividadZonaDB actividadDb = new ActividadZonaDB();
          ArrayList<ActividadZona> actividad = actividadDb.obtenerActividadePorId(idActividad);
          if(actividad.isEmpty()){
              throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUNA ACTIVIDAD CON ESTE ID");
          }
          ActividadZona actividadUpdate = actividad.getFirst();
          actividadUpdate.updateActividad(update);
          if(actividadUpdate.esValido()==false){
              throw new DatosNoValidosExcepcion("DATOS INVALIDOS");
          }
          return actividadDb.updateActividad(actividadUpdate, idActividad);
          
        
    }
    public void eliminarActividad(String idActividad) throws ElementoNoEncotradoExcepcion, ElemtoAElminarEnUsoExcepcion{
        ActividadZonaDB actividadDb = new ActividadZonaDB();
          ArrayList<ActividadZona> actividad = actividadDb.obtenerActividadePorId(idActividad);
          if(actividad.isEmpty()){
              throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUNA ACTIVIDAD CON ESTE ID");
          }
          actividadDb.EliminarActividad(idActividad);
    }
    
    
    
    
    
}
