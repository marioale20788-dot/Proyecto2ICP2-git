/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.Grado;
import EntidadesDAO.GradoDB;
import EntidadesDto.Grado.NewGradoRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class CrudGrado {
    
    public void crearGrado(NewGradoRequest nuevoGrado) throws DatosNoValidosExcepcion{
        GradoDB gradoDB = new GradoDB();
        Grado grado = convertirGrado(nuevoGrado);
        gradoDB.crearGrado(grado);
        
        
        
        
    }
       public void EliminarGrado(String id) throws ElementoNoEncotradoExcepcion, ElemtoAElminarEnUsoExcepcion {
          GradoDB gradoDB = new GradoDB();
        ArrayList<Grado> grado = gradoDB.obtenerGradoPorId(id);
        if (grado.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN CICLO EN EL ANIO INDICADO");
        }
        gradoDB.eliminarGrado(id);
    }

    
    public ArrayList<Grado> obtenerTodosLosGrados(){
       GradoDB gradoDB = new GradoDB();
        ArrayList<Grado> grados = gradoDB.obtenerTodosLosGrados();
        return grados;
    }
    public  ArrayList<Grado>   obtenerGradoPorId(String id) throws ElementoNoEncotradoExcepcion{
            GradoDB gradoDB = new GradoDB();
         ArrayList<Grado> grados = gradoDB.obtenerGradoPorId(id);
         if(grados.isEmpty()){
             throw  new ElementoNoEncotradoExcepcion("NO SE ECONTRO");
         }
         return  grados;
        
        
    }
    
    public ArrayList<Grado>  obtenerGradoPorNivel(String idNivel) throws ElementoNoEncotradoExcepcion{
        
         GradoDB gradoDB = new GradoDB();
         ArrayList<Grado> grados = gradoDB.obtenerGradoPorNivel(idNivel);
         if(grados.isEmpty()){
             throw  new ElementoNoEncotradoExcepcion("NO SE ECONTRO");
         }
         return  grados;
        
    }
    
    public Grado convertirGrado(NewGradoRequest nuevoGrado) throws DatosNoValidosExcepcion{
         try {
            Grado grado = new Grado(nuevoGrado.getNombreGrado(), null, nuevoGrado.getIdNivel(), nuevoGrado.getIdCarrera());

            if (grado.gradoValido()== false) {
                throw new DatosNoValidosExcepcion("Datos enviados incorrectos");
            }

            return grado;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DatosNoValidosExcepcion("Datos enviados incorrectos");
        }
    }
    
}
