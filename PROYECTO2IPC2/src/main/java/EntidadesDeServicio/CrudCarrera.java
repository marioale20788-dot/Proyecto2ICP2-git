/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.Carrera;
import EntidadesDAO.CarreraDB;
import EntidadesDto.Carrera.UpdateCarrera;

import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mario
 */
public class CrudCarrera {

    public Carrera crearCarrera(Carrera nuevaCarrera) throws DatosNoValidosExcepcion {
        Carrera carrera = nuevaCarrera;
        CarreraDB carreraDb = new CarreraDB();
        if (!StringUtils.isNotBlank(carrera.getIdCarrera()) || !StringUtils.isNotBlank(carrera.getNombre())) {
            throw new DatosNoValidosExcepcion("VALOR VACIO");
        }

        carreraDb.crearCarrera(carrera);
        return carrera;

    }

    public ArrayList<Carrera> buscarTodasCarreras() {

        CarreraDB carreraDb = new CarreraDB();
        ArrayList<Carrera> carrera = carreraDb.buscarTodasLasCarreras();
        return carrera;

    }

    public Carrera buscarCarreraPorNombreId(String nombreId) throws ElementoNoEncotradoExcepcion {
        CarreraDB carreraDb = new CarreraDB();
        ArrayList<Carrera> carrera = carreraDb.buscarCarreraPorNombreOId(nombreId);
        if (carrera.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO UNA CARRERA CON ESE IDENTIFICADOR");

        }
        return carrera.getFirst();

    }

    public Carrera updateCarrera(String id, UpdateCarrera updatesCarrera) throws ElementoNoEncotradoExcepcion, DatosNoValidosExcepcion {

        CarreraDB carreraDb = new CarreraDB();
        Carrera CarreraAEditar = buscarCarreraPorNombreId(id);
        if (updatesCarrera.getNombre() != null) {
            if (!StringUtils.isNotBlank(updatesCarrera.getNombre())) {
                throw new DatosNoValidosExcepcion("VALOR VACIO");
            }
            CarreraAEditar.setNombre(updatesCarrera.getNombre());
        } else {
            CarreraAEditar.setEstado(updatesCarrera.getEstado());
        }
        return carreraDb.updateCarrera(CarreraAEditar, id);

    }
    
    public void elminiarCarrera(String idCarrera) throws ElementoNoEncotradoExcepcion, ElemtoAElminarEnUsoExcepcion{
        
         CarreraDB carreraDb = new CarreraDB();
         Carrera carreraAEliminar = buscarCarreraPorNombreId(idCarrera);
         carreraDb.EliminarCarrera(idCarrera);
    }
    

}
