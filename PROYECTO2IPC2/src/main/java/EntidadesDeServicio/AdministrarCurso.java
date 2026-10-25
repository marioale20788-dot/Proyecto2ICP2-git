/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.AsignacionDocente;
import Entidades.Asistencia;
import Entidades.Estudiante;
import EntidadesDAO.AsignacionDocenteDB;
import EntidadesDAO.AsistenciaDb;
import EntidadesDAO.EstudianteDB;
import EntidadesDto.AdministrarCurso.AsistenciaRepuesta;
import EntidadesDto.AdministrarCurso.NuevaAsistenciaRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class AdministrarCurso {

    public void TomarAsistencia(ArrayList<NuevaAsistenciaRequest> asistencia) throws DatosNoValidosExcepcion {
        ArrayList<NuevaAsistenciaRequest> nuevaAsistencia = asistencia;
        if (nuevaAsistencia.isEmpty()) {
            throw new DatosNoValidosExcepcion("DATOS INVALIDOS LISTA DE ASISTENCIA VACIA");
        }
        if (verificarAsistencia(nuevaAsistencia) == false) {
            throw new DatosNoValidosExcepcion("DATOS INVALIDOS");
        }
        AsistenciaDb asistenciaDb = new AsistenciaDb();
        for (NuevaAsistenciaRequest nuevaAsistenciaRequest : nuevaAsistencia) {
            Asistencia asistenciaEstudiante = new Asistencia(null, nuevaAsistenciaRequest.getFecchaAsistencia(),
                    nuevaAsistenciaRequest.getEstado(),
                    nuevaAsistenciaRequest.getIdEstudiante(),
                    nuevaAsistenciaRequest.getIdAsignacion());
            asistenciaEstudiante.crearid();
            asistenciaDb.insertarAsistencia(asistenciaEstudiante);

        }

    }

    public ArrayList<AsistenciaRepuesta> obtenerAsistencia(String idAsigncacion) throws DatosNoValidosExcepcion, ElementoNoEncotradoExcepcion {
        AsistenciaDb asistenciaDb = new AsistenciaDb();
        ArrayList<Asistencia> asistencaisAsignacion = asistenciaDb.obtenerAsistenciaPorAsignacion(idAsigncacion);
        if (asistencaisAsignacion.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO EXISTE NINGUNA ASISTENCIA REGISTRADA A ESTA ASIGNACION");
        }
        ArrayList<AsistenciaRepuesta> respuesta = new ArrayList<>();
        for (Asistencia asistencia : asistencaisAsignacion) {
            if (!respuesta.isEmpty()) {
                boolean repetido = false;
                for (AsistenciaRepuesta asistenciaRepuesta : respuesta) {
                    if (asistencia.getIdEstudiante().equalsIgnoreCase(asistenciaRepuesta.getIdEstudiante())) {
                        repetido = true;
                        break;
                    }

                }
                if (repetido == true) {
                    continue;
                }

            }

            AsistenciaRepuesta estadoAsistencia = asistenciaDb.obtenerEstadoAsistencia(idAsigncacion, asistencia.getIdEstudiante());
            respuesta.add(estadoAsistencia);

        }
        return respuesta;

    }

    public boolean verificarAsistencia(ArrayList<NuevaAsistenciaRequest> asistencia) throws DatosNoValidosExcepcion {
        EstudianteDB estudianteDb = new EstudianteDB();
        AsignacionDocenteDB asginacionDb = new AsignacionDocenteDB();
        AsistenciaDb asistenciaDb = new AsistenciaDb();

        for (NuevaAsistenciaRequest nuevaAsistenciaRequest : asistencia) {
            Asistencia asistenciaEstudiante = new Asistencia(null, nuevaAsistenciaRequest.getFecchaAsistencia(),
                    nuevaAsistenciaRequest.getEstado(),
                    nuevaAsistenciaRequest.getIdEstudiante(),
                    nuevaAsistenciaRequest.getIdAsignacion());
            if (asistenciaEstudiante.esValido() == false) {
                return false;
            }
            asistenciaEstudiante.crearid();

            Asistencia asistenciaExiste = asistenciaDb.obtenerAsistenciaPorId(asistenciaEstudiante.getIdAsistencia());
            if (asistenciaExiste != null) {
                return false;
            }

            ArrayList<AsignacionDocente> asingacion = asginacionDb.obtenerAsignacionPorId(nuevaAsistenciaRequest.getIdAsignacion());
            if (asingacion.isEmpty()) {
                return false;
            }

            Estudiante estudiante = estudianteDb.obtenerEstudiantePorId(nuevaAsistenciaRequest.getIdEstudiante());
            if (estudiante == null) {
                return false;
            }

        }
        return true;

    }

}
