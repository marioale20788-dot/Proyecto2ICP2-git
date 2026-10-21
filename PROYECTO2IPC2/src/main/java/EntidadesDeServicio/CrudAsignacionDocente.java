/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.AsignacionDocente;
import EntidadesDAO.AsignacionDocenteDB;
import EntidadesDto.AsignacionMaestro.NuevaAsignacionRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class CrudAsignacionDocente {

    public void asingarDocente(NuevaAsignacionRequest nuevaAsignacion) throws DatosNoValidosExcepcion {
        AsignacionDocente asignacion = new AsignacionDocente(nuevaAsignacion.getIdUsuario(), nuevaAsignacion.getIdSeccion(), nuevaAsignacion.getIdCurriculo());
        if (asignacion.esValido() == false) {
            throw new DatosNoValidosExcepcion("DATOS INVALIDOS");
        }
        asignacion.crearId();
        AsignacionDocenteDB asignacionDb = new AsignacionDocenteDB();
        asignacionDb.asignarDocente(asignacion);
    }

    public AsignacionDocente obtenerAsignacionsPorSeccionCurriculo(String idSeccion, String idCurriculo) throws ElementoNoEncotradoExcepcion {
        AsignacionDocenteDB asignacionDb = new AsignacionDocenteDB();
        ArrayList<AsignacionDocente> asignacion = asignacionDb.obtenerAsignacionPorSeccionCurriculo(idSeccion, idCurriculo);
        if (asignacion.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENONTRO NINGUNA ASINGACION");
        }
        return asignacion.getFirst();

    }

    public void eliminarAsignacion(String idAsginacion) throws ElementoNoEncotradoExcepcion, ElemtoAElminarEnUsoExcepcion {
        AsignacionDocenteDB asignacionDb = new AsignacionDocenteDB();
        ArrayList<AsignacionDocente> asignacion = asignacionDb.obtenerAsignacionPorId(idAsginacion);
        if (asignacion.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENONTRO NINGUNA ASINGACION");

        }
        asignacionDb.eliminarAsignacion(idAsginacion);

    }

}
