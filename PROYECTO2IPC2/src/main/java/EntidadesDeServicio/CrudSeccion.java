/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.Curriculo;
import Entidades.Seccion;
import EntidadesDAO.CurriculoDB;
import EntidadesDAO.SeccionDB;
import EntidadesDto.Seccion.NuevaSeccionRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class CrudSeccion {
    
    
    public void crearSeccion(NuevaSeccionRequest nuevaSeccion) throws DatosNoValidosExcepcion{
        SeccionDB seccionDB = new SeccionDB();
        Seccion seccion  = new Seccion(seccionDB.generarIdAleatorio(), nuevaSeccion.getNombreSeccion(), nuevaSeccion.getIdGrado());
        if(seccion.esValido()==false){
            throw  new DatosNoValidosExcepcion("DATOS NO VALIDOS");
        }
        CurriculoDB curriculoDb = new CurriculoDB();
        ArrayList<Curriculo> curriculos = curriculoDb.obtenerCurriculoCicloGrado(nuevaSeccion.getIdGrado(), nuevaSeccion.getAnio());
        if(curriculos.isEmpty()){
            throw  new DatosNoValidosExcepcion("DEBE DE CREAR UN CURRICULO PARA ESTA EN EL CICLO SELECCIONADO");
        }
        
        
        
        if (seccionDB.buscarSeccionPorNombreId(seccion.getNombreSeccion(), seccion.getIdGrado())) {
             throw  new DatosNoValidosExcepcion("YA EXISTE UNA SECCION CON ESE NOMBRE ASOCIADA A ESE GRADO");
        }
        seccionDB.crearSeccion(seccion);
        
    }
    
    public ArrayList<Seccion> obtenerTodasLasSecciones(boolean buscarSeccionPorNombre, String nombre, int pagina){
        SeccionDB seccionDB = new SeccionDB();
        ArrayList<Seccion> secciones = seccionDB.obtenerTodasLasSeccion(buscarSeccionPorNombre, nombre,pagina);
        return secciones;
        
    }
     public ArrayList<Seccion> obtenerTodasLasSeccionesSelet(){
        SeccionDB seccionDB = new SeccionDB();
        ArrayList<Seccion> secciones = seccionDB.obtenerTodasLasSeccionSelect();
        return secciones;
        
    }
     public ArrayList<Seccion> obtenerTodasLasSeccionesPorGrado(String idGrado){
        SeccionDB seccionDB = new SeccionDB();
        ArrayList<Seccion> secciones = seccionDB.obtenerTodasLasSeccionPorGrado(idGrado);
        return secciones;
        
    }
     public Seccion obtenerSeccionPorId(String idSeccion) throws ElementoNoEncotradoExcepcion{
          SeccionDB seccionDB = new SeccionDB();
        ArrayList<Seccion> secciones = seccionDB.obtenerSeccionPorId(idSeccion);
        if(secciones.isEmpty()){
             throw  new ElementoNoEncotradoExcepcion("NO SE ENCONTRO UNA SECCION CON ESE ID");
        }
        return secciones.getFirst();
     }
   
    
    public Seccion updateSeccion(String idSeccion, NuevaSeccionRequest updateSeccion) throws ElementoNoEncotradoExcepcion, DatosNoValidosExcepcion{
           SeccionDB seccionDB = new SeccionDB();
           ArrayList<Seccion> seccion = seccionDB.obtenerSeccionPorId(idSeccion);
           if(seccion.isEmpty()){
               throw  new ElementoNoEncotradoExcepcion("NO SE ENCONTRO UNA SECCION CON ESE ID");
           }
           Seccion seccionUpdate = seccion.getFirst();
           seccionUpdate.editarSeccion(updateSeccion);
           if(seccionUpdate.esValido()==false){
                 throw  new DatosNoValidosExcepcion("DATO NO VALIDOS");
           }
           return seccionDB.updateSeccion(seccionUpdate);
           
           
        
    }
    public void eliminarSeccion(String idSeccion) throws ElementoNoEncotradoExcepcion, ElemtoAElminarEnUsoExcepcion{
            SeccionDB seccionDB = new SeccionDB();
         ArrayList<Seccion> seccion = seccionDB.obtenerSeccionPorId(idSeccion);
           if(seccion.isEmpty()){
               throw  new ElementoNoEncotradoExcepcion("NO SE ENCONTRO UNA SECCION CON ESE ID");
           }
           seccionDB.eliminarSeccion(idSeccion);
    }
 
    
}
