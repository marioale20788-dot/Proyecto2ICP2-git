/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.TipoUsuarioEnum;
import Entidades.Usuario;
import Entidades.inscripcion;
import EntidadesDto.Inscripcion.NewIncricpionRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class inscripcionDB {

    public void inscribirEstudiante(inscripcion incripcionEstudiante) throws DatosNoValidosExcepcion {
        UsuarioDB usuarioDB = new UsuarioDB();
        ArrayList<Usuario> usuario = usuarioDB.obtenerUsuarioPorDpi(incripcionEstudiante.getIdEstudiante());
        if (!usuario.isEmpty()) {
            if (usuario.getFirst().getTipo() != TipoUsuarioEnum.Estudiante) {
                throw new DatosNoValidosExcepcion("EL USUARIO INGRESADO NO ES UN ESTUDIANTE");
            }
        }
        String sql = "INSERT INTO inscripcion(id_inscripcion,fecha_inscripcion,id_estudiante,id_seccion,id_ciclo_lectivo) VALUES(?,?,?,?,?) ";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement insertStatement = conn.prepareStatement(sql);) {
            insertStatement.setString(1, incripcionEstudiante.getIdInscripcion());
            insertStatement.setDate(2, Date.valueOf(incripcionEstudiante.getFechaInscripcion()));
            insertStatement.setString(3, incripcionEstudiante.getIdEstudiante());
            insertStatement.setString(4, incripcionEstudiante.getIdSeccion());
            insertStatement.setInt(5, incripcionEstudiante.getIdCicloLectivo());
            insertStatement.execute();

        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }

    public inscripcion updateInscripcion(inscripcion inscripcion, String idInscripcion) throws DatosNoValidosExcepcion {
        String sql = "UPDATE inscripcion SET fecha_inscripcion = ?, id_estudiante = ?,id_seccion = ?,id_ciclo_lectivo = ?  WHERE id_inscripcion = ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement updateStatement = conn.prepareStatement(sql);) {

            updateStatement.setDate(1, Date.valueOf(inscripcion.getFechaInscripcion()));
            updateStatement.setString(2, inscripcion.getIdEstudiante());
            updateStatement.setString(3, inscripcion.getIdSeccion());
            updateStatement.setInt(4, inscripcion.getIdCicloLectivo());
            updateStatement.setString(5, idInscripcion);

            updateStatement.executeUpdate();
            return inscripcion;
        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }

    public void eliminarInscipcion(NewIncricpionRequest inscripcionEliminar) throws ElemtoAElminarEnUsoExcepcion {
        String sql = "DELETE FROM inscripcion WHERE id_estudiante = ? AND id_ciclo_lectivo = ?";

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement updateStatement = conn.prepareStatement(sql);) {

            updateStatement.setString(1, inscripcionEliminar.getIdEstudiante());
            updateStatement.setInt(2, inscripcionEliminar.getIdCicloLectivo());
            updateStatement.execute();
        } catch (SQLException ex) {
            throw new ElemtoAElminarEnUsoExcepcion(sqlExcepcion(ex));
        }
    }

    public ArrayList<inscripcion> incripcionesEstudiante(String idEstudiante) {
        String sql = "SELECT * FROM inscripcion WHERE id_estudiante = ?";
        ArrayList<inscripcion> inscripcionesEstudiante = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, idEstudiante);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                inscripcion inscripcion = new inscripcion(resultado.getDate("fecha_inscripcion").toLocalDate(),
                        resultado.getString("id_estudiante"),
                        resultado.getString("id_seccion"),
                        resultado.getInt("id_ciclo_lectivo"));
                inscripcion.setIdInscripcion(resultado.getString("id_inscripcion"));
                inscripcionesEstudiante.add(inscripcion);
            }
            return inscripcionesEstudiante;

        } catch (SQLException ex) {
            return inscripcionesEstudiante;
        }
    }

    public ArrayList<inscripcion> incripcionesPorId(String idInscripcion) {
        String sql = "SELECT * FROM inscripcion WHERE id_inscripcion = ?";
        ArrayList<inscripcion> inscripcionesEstudiante = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, idInscripcion);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                inscripcion inscripcion = new inscripcion(resultado.getDate("fecha_inscripcion").toLocalDate(),
                        resultado.getString("id_estudiante"),
                        resultado.getString("id_seccion"),
                        resultado.getInt("id_ciclo_lectivo"));
                inscripcion.setIdInscripcion(resultado.getString("id_inscripcion"));
                inscripcionesEstudiante.add(inscripcion);
            }
            return inscripcionesEstudiante;

        } catch (SQLException ex) {
            return inscripcionesEstudiante;
        }
    }
    public boolean existeIncripcionAElminar(String idAlumno, int idCicloLectivo){
         String sql = "SELECT * FROM inscripcion WHERE id_ciclo_lectivo = ? AND id_estudiante = ?";
               try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setInt(1, idCicloLectivo);
              queryStatement.setString(2, idAlumno);
            ResultSet resultado = queryStatement.executeQuery();
            return resultado.next();
    }   catch (SQLException ex) {
        return false;
        
        }
    }

    public ArrayList<inscripcion> incripcionesCiclo(int idCiclo) {
        String sql = "SELECT * FROM inscripcion WHERE id_ciclo_lectivo = ?";
        ArrayList<inscripcion> inscripcionesEstudiante = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setInt(1, idCiclo);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                inscripcion inscripcion = new inscripcion(resultado.getDate("fecha_inscripcion").toLocalDate(),
                        resultado.getString("id_estudiante"),
                        resultado.getString("id_seccion"),
                        resultado.getInt("id_ciclo_lectivo"));
                inscripcion.setIdInscripcion(resultado.getString("id_inscripcion"));
                inscripcionesEstudiante.add(inscripcion);
            }
            return inscripcionesEstudiante;

        } catch (SQLException ex) {
            return inscripcionesEstudiante;
        }
    }

    public String sqlExcepcion(SQLException excepcion) {
        String error;

        switch (excepcion.getErrorCode()) {

            case 1062:
                error = "El id  ingresado ya se encuentra registrado.";
                break;

            case 1292:
                error = "Formato no válido.";
                break;

            case 1406:
                error = "Dato ingresado muy largo.";
                break;

            case 1452:
                error = "El dato seleccionado no existe en el sistema.";
                break;

            default:
                error = "Ocurrió un inconveniente al procesar los datos.";
                break;
        }

        return error;
    }
}
