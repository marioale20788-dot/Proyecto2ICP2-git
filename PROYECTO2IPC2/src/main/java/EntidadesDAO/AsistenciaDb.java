/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.Asistencia;
import EntidadesDto.AdministrarCurso.AsistenciaRepuesta;
import Excepciones.DatosNoValidosExcepcion;
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
public class AsistenciaDb {
    
    public Asistencia obtenerAsistenciaPorId(String idAsistencia) throws DatosNoValidosExcepcion {
        String sql = "SELECT * FROM registro_asistencia WHERE id_asistencia = ?";
        Asistencia asistencia = null;
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, idAsistencia);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                asistencia = new Asistencia(resultado.getString("id_asistencia"),
                        resultado.getDate("fecha").toLocalDate(),
                        resultado.getBoolean("estado"),
                        resultado.getString("id_estudiante"),
                        resultado.getString("id_asignacion"));
            }
            return asistencia;
            
        } catch (SQLException ex) {
               throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
            
        }
        
    }
    
    public ArrayList<Asistencia> obtenerAsistenciaPorAsignacion(String idAsignacion) throws DatosNoValidosExcepcion {
        String sql = "SELECT * FROM registro_asistencia WHERE id_asignacion = ?";
           ArrayList<Asistencia> asistencias = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, idAsignacion);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Asistencia asistencia = new Asistencia(resultado.getString("id_asistencia"),
                        resultado.getDate("fecha").toLocalDate(),
                        resultado.getBoolean("estado"),
                        resultado.getString("id_estudiante"),
                        resultado.getString("id_asignacion"));
                asistencias.add(asistencia);
            }
            return asistencias;
            
            
            
        } catch (SQLException ex) {
               throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
            
        }
        
    }
    
    public AsistenciaRepuesta obtenerEstadoAsistencia(String idAsignacion, String idEstudiante) throws DatosNoValidosExcepcion{
          String sql = "SELECT * FROM registro_asistencia WHERE id_asignacion = ? AND id_estudiante = ?";
     
          try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, idAsignacion);
            queryStatement.setString(2, idEstudiante);
            ResultSet resultado = queryStatement.executeQuery();
            int faltas=0;
            int llegadas=0;
            while (resultado.next()) {
                if(resultado.getBoolean("estado")==true){
                    llegadas++;
                }else{
                    faltas++;
                }
              
            }
            AsistenciaRepuesta respuesta = new AsistenciaRepuesta(idEstudiante, faltas, llegadas);
            return respuesta;
    
            
            
            
        } catch (SQLException ex) {
               throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
            
        }
    }
    
    
    
    public void insertarAsistencia(Asistencia asistencia){
        String sql = "INSERT INTO registro_asistencia(id_asistencia,fecha,estado,id_estudiante,id_asignacion) VALUES(?,?,?,?,?)";
             try (Connection conn = DBConnection.getInstance().getConnection(); 
                     PreparedStatement insertStatement = conn.prepareStatement(sql);) {
            insertStatement.setString(1, asistencia.getIdAsistencia());
            insertStatement.setDate(2, Date.valueOf(asistencia.getFecchaAsistencia()));
            insertStatement.setBoolean(3, asistencia.getEstado());
            insertStatement.setString(4, asistencia.getIdEstudiante());
            insertStatement.setString(5, asistencia.getIdAsignacion());
            insertStatement.execute();
            
          
            
        } catch (SQLException ex) {
              
            
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
                error = "El dato ingersado no existe en el sistema.";
                break;

            default:
                error = "Ocurrió un inconveniente al procesar los datos.";
                break;
        }

        return error;
    }
    
}
