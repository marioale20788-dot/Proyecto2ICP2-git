/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.AsignacionDocente;
import Entidades.Curriculo;
import Entidades.Curso;
import Entidades.Estudiante;
import Entidades.Grado;
import Entidades.Seccion;
import Entidades.Usuario;
import Entidades.inscripcion;
import EntidadesDAO.AsignacionDocenteDB;
import EntidadesDAO.CurriculoDB;
import EntidadesDAO.CursoDB;
import EntidadesDAO.EstudianteDB;
import EntidadesDAO.GradoDB;
import EntidadesDAO.SeccionDB;
import EntidadesDAO.UsuarioDB;
import EntidadesDAO.inscripcionDB;
import EntidadesDto.Maestro.getAsignacionesRequest;
import EntidadesDto.Maestro.getInscripcionesSeccionRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class SistemaMaestros {

    public ArrayList<getAsignacionesRequest> obtenerAsignaciones(String idMaestro, int ciclo) throws ElementoNoEncotradoExcepcion {
        AsignacionDocenteDB asignacionDb = new AsignacionDocenteDB();
        ArrayList<AsignacionDocente> asignaciones = asignacionDb.obtenerAsignacionPorDocente(idMaestro);
        if (asignaciones.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO HAY ASINGACIONES REGISTRADAS PARA ESTE MAESTRO");
        }
        CurriculoDB curriculoDb = new CurriculoDB();
        SeccionDB seccionDb = new SeccionDB();
        CursoDB cursoDb = new CursoDB();
        GradoDB gradoDb = new GradoDB();

        ArrayList<getAsignacionesRequest> respuesta = new ArrayList<>();
        for (AsignacionDocente filtrarAsignaciones : asignaciones) {
            Curriculo curriculoF = curriculoDb.obtenreCurriculoPorIdCiclo(filtrarAsignaciones.getIdCurricuolo(), ciclo);
            if (curriculoF != null) {
                ArrayList<Seccion> seccion = seccionDb.obtenerSeccionPorId(filtrarAsignaciones.getIdSeccion());
                ArrayList<Curso> curso = cursoDb.bucarCursoPorNombreCodigo(curriculoF.getCodigoCurso());
                ArrayList<Grado> grado = gradoDb.obtenerGradoPorId(curriculoF.getIdGrado());
                getAsignacionesRequest asignacion = new getAsignacionesRequest(seccion.getFirst().getIdSeccion(),
                        seccion.getFirst().getNombreSeccion(),
                        curriculoF.getIdCurriculo(),
                        idMaestro,
                        ciclo,
                        curso.getFirst().getCodigoCurso(),
                        curso.getFirst().getNombreCurso(),
                        grado.getFirst().getIdGrado(),
                        grado.getFirst().getNombreGrado(),
                        grado.getFirst().getIdNivel(),
                       filtrarAsignaciones.getIdAsginacion());
                respuesta.add(asignacion);

            }

        }
        if (respuesta.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO HAY ASINGACIONES REGISTRADAS PARA ESTE MAESTRO EN ESTE CICLO");
        }

        return respuesta;

    }

    public ArrayList<getInscripcionesSeccionRequest> obtenerEstuidantesInscritos(String idSeccion, int ciclo,String idAsignacion) throws ElementoNoEncotradoExcepcion, DatosNoValidosExcepcion {

        inscripcionDB inscripcionDb = new inscripcionDB();

        ArrayList<inscripcion> inscripciones = inscripcionDb.ListaDeEstudiantesInscritos(ciclo, idSeccion);
        if (inscripciones.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO EXISTEN INSCRIPCIONES CON LOS DATOS INGRESADOS");
        }
        ArrayList<getInscripcionesSeccionRequest> inscripcioneSeccion = new ArrayList<>();
        EstudianteDB esDb = new EstudianteDB();
        for (inscripcion inscripcione : inscripciones) {
            Estudiante es = esDb.obtenerEstudiantePorId(inscripcione.getIdEstudiante());
            getInscripcionesSeccionRequest inscripcion = new getInscripcionesSeccionRequest(inscripcione.getFechaInscripcion(),
                    inscripcione.getIdEstudiante(), es.getNombreEstudiante(),idAsignacion);
            inscripcioneSeccion.add(inscripcion);

        }
        return inscripcioneSeccion;

    }

}
