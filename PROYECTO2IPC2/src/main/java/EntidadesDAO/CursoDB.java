/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.Curso;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class CursoDB {

    public Curso crearCurso(Curso curso) throws DatosNoValidosExcepcion {
        String sql = "INSERT INTO curso(codigo_curso,nombre_curso,descripcion,estado) values(?,?,?,?)";
          if (bucarCursoPorNombreCodigo(curso.getNombreCurso()).isEmpty() == false) {
                throw new DatosNoValidosExcepcion("nombre de curso repetido");
            }
        try ( Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement insertStatement = conn.prepareStatement(sql);){
           
            insertStatement.setString(1, curso.getCodigoCurso());
          
            insertStatement.setString(2, curso.getNombreCurso());
            insertStatement.setString(3, curso.getDescripcionCurso());
            insertStatement.setBoolean(4, curso.getEstado());
            insertStatement.execute();
            return curso;
        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }

    public ArrayList<Curso> obtenerTodosLosCursos() {
        String sql = "SELECT * FROM curso";
        ArrayList<Curso> cursos = new ArrayList<>();

        try (     Connection   conn = DBConnection.getInstance().getConnection();
            PreparedStatement queryStatement = conn.prepareStatement(sql);){
    
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Curso curso = new Curso(resultado.getString("codigo_curso"),
                        resultado.getString("nombre_curso"),
                        resultado.getString("descripcion"),
                        resultado.getBoolean("estado"));
                cursos.add(curso);

            }
            return cursos;
        } catch (SQLException ex) {
           return cursos;
        }

    }
    public Curso updateCurso(String codigoCurso , Curso curso) throws DatosNoValidosExcepcion{
            String sql = "UPDATE curso SET nombre_curso = ?,descripcion = ?,estado = ? WHERE codigo_curso = ? ";
        try (    Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement updateStatement = conn.prepareStatement(sql);){
          
        
            updateStatement.setString(1, curso.getNombreCurso());
            updateStatement.setString(2,curso.getDescripcionCurso());
            updateStatement.setBoolean(3, curso.getEstado());
            updateStatement.setString(4, codigoCurso);
            updateStatement.executeUpdate();
            return curso;
        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }
    }
    public void eliminarCurso(String codigoCurso) throws ElemtoAElminarEnUsoExcepcion{
            String sql =  "DELETE FROM curso WHERE codigo_curso = ?";
        try(    Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement updateStatement = conn.prepareStatement(sql);) {
        
            updateStatement.setString(1, codigoCurso);
            updateStatement.execute();
            
        } catch (SQLException ex) {
            throw  new ElemtoAElminarEnUsoExcepcion(sqlExcepcion(ex));
        }
    }

    public ArrayList<Curso> bucarCursoPorNombreCodigo(String dato)  {
        String sql = "SELECT * FROM curso WHERE(nombre_curso = ? OR codigo_curso = ?)";
        ArrayList<Curso> cursos = new ArrayList<>();
        try (   Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement queryStatement = conn.prepareStatement(sql);){
         
            queryStatement.setString(1, dato);
            queryStatement.setString(2, dato);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Curso curso = new Curso(resultado.getString("codigo_curso"),
                        resultado.getString("nombre_curso"),
                        resultado.getString("descripcion"),
                        resultado.getBoolean("estado"));
                cursos.add(curso);
            }
            return cursos;
        } catch (SQLException ex) {
          return cursos;
        }

    }

    public String sqlExcepcion(SQLException excepcion) {
        String error;

        switch (excepcion.getErrorCode()) {

            case 1062:
                error = "El codigo de curso  ingresado ya se encuentra registrado.";
                break;
            case 1451:
                error = "No se puede eliminar este registro porque está relacionado con otros datos en el sistema.";
                break;
            case 1292:
                error = "Formato no válido.";
                break;

            case 1406:
                error = "Dato ingresado muy largo.";
                break;

            default:
                error = "Ocurrió un inconveniente al procesar los datos.";
                break;
        }

        return error;
    }

}
