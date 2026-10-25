/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.ActividadZona;
import Entidades.NotaActividad;
import EntidadesDAO.ActividadZonaDB;
import EntidadesDAO.AsignacionDocenteDB;
import EntidadesDAO.EstudianteDB;
import EntidadesDAO.NotasDb;
import EntidadesDto.AdministrarCurso.GetNotas;
import EntidadesDto.AdministrarCurso.NuevaNotaRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class AdministrarNotasActividades {
       public ArrayList<GetNotas> obtenerNotas(String idUsuario, String idAsignacion) throws ElementoNoEncotradoExcepcion{
        ActividadZonaDB actividadDb = new ActividadZonaDB();
        ArrayList<GetNotas> Resultado = new ArrayList<>();
        ArrayList<ActividadZona> actividades = actividadDb.obtenerActividadesAsignacion(idAsignacion);
        if(actividades.isEmpty()){
            throw new ElementoNoEncotradoExcepcion("NO EXISTEN ACTIVIDADES CREADAS PARA ESTA ASIGNACION");
        }
           AsignacionDocenteDB asignacionDb = new AsignacionDocenteDB();
           
        
        
        NotasDb notaDb = new NotasDb();
        
        for (ActividadZona actividade : actividades) {
            NotaActividad nota = notaDb.obtenerNotasPorActividad(idUsuario, actividade.getIdActividad());
            String punteo;
            if(nota==null){
                punteo = "SIN PUNTEO DEFINIDO";
                
            }else{
                punteo = String.valueOf(nota.getPunteoObtenido());
                
            }
            GetNotas notaEstado = new GetNotas(actividade.getIdActividad(), actividade.getNombreActividad(),
                    actividade.getPonderacion(),punteo, idUsuario);
            Resultado.add(notaEstado);
            
        }
        return Resultado;
        
        
        
        
    }
       public NotaActividad actualizarNota(NuevaNotaRequest nuevaNota) throws DatosNoValidosExcepcion{
           
           
           NotaActividad nota = new NotaActividad(nuevaNota.getPunteo(),nuevaNota.getIdActividad(), nuevaNota.getIdEstudiante());
           if(nota.esValido()==false){
               throw new DatosNoValidosExcepcion("DATOS INVALIDOS");
           }
           nota.generarId();
           NotasDb notasDb = new NotasDb();
           if(notasDb.obtenerNotasPorId(nota.getIdNota())==null){
               notasDb.crearNota(nota);
           }else{
               notasDb.updateNota(nota, nota.getIdNota());
           }
           return nota;
       
       }
      
       
       
       
       
       
      
}
