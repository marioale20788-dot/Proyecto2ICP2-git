/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.Curso;
import EntidadesDAO.CursoDB;
import EntidadesDto.Curso.UpdateCurso;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class CrudCurso {
    
    public void crearCurso(Curso curso) throws DatosNoValidosExcepcion {
        CursoDB cursoDB = new CursoDB();
        Curso cursoNuevo = curso;
        if (cursoNuevo.cursoValido() == false) {
            throw new DatosNoValidosExcepcion("DATOS INGRESADOS NO VALIDOS");
            
        }
        cursoDB.crearCurso(curso);
        
    }
    
    public ArrayList<Curso> obtenerTodosLosCursos() {
        CursoDB cursoDB = new CursoDB();
        ArrayList<Curso> cursos = cursoDB.obtenerTodosLosCursos();
        return cursos;
    }

    public Curso buscarCarreraPorCodigoNombre(String dato) throws ElementoNoEncotradoExcepcion {
        CursoDB cursoDB = new CursoDB();
        ArrayList<Curso> curso = cursoDB.bucarCursoPorNombreCodigo(dato);
        if (curso.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN CURSO CON EL IDENTIFICADOR");
        }
        return curso.getFirst();
        
    }

    public Curso updateCurso(String idCurso, UpdateCurso updateCurso) throws ElementoNoEncotradoExcepcion, DatosNoValidosExcepcion {
        CursoDB cursoDB = new CursoDB();
        Curso curso = buscarCarreraPorCodigoNombre(idCurso);
        if (updateCurso.getNombrecurso() != null) {
            curso.setNombreCurso(updateCurso.getNombrecurso());
        } else if (updateCurso.getDescripcionCurso() != null) {
            curso.setDescripcionCurso(updateCurso.getDescripcionCurso());
        } else if (updateCurso.getEstado() != null) {
            curso.setEstado(updateCurso.getEstado());
        }
        if (curso.cursoValido() == false) {
            throw new DatosNoValidosExcepcion("DATO NO VALIDO");
        }
        return cursoDB.updateCurso(idCurso, curso);
    }
    
    public void elminarCurso(String codigoCurso) throws ElementoNoEncotradoExcepcion, ElemtoAElminarEnUsoExcepcion {
        CursoDB cursoDB = new CursoDB();
        Curso curso = buscarCarreraPorCodigoNombre(codigoCurso);
        cursoDB.eliminarCurso(codigoCurso);
    }
}
