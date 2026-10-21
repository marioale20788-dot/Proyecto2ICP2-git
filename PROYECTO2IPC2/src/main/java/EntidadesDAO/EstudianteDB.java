/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.Estudiante;
import Excepciones.DatosNoValidosExcepcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mario
 */
public class EstudianteDB {
    
    
    
    
    
    public void crearEstudiante(Estudiante estudiante) throws DatosNoValidosExcepcion{
        String sql = "INSERT INTO estudiante(id_estudiante,nombre) values(?,?)";
        try(     Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement insertStatement = conn.prepareStatement(sql);) {
       
            insertStatement.setString(1, estudiante.getDpiUsuario());
            insertStatement.setString(2, estudiante.getNombreEstudiante());
            insertStatement.execute();
            
        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }
    }
    public void updateEstudiante(Estudiante estudiante, String idEstudiante) throws DatosNoValidosExcepcion{
          String sql = "UPDATE estudiante SET nombre = ? WHERE id_estudiante = ? ";
          try(     Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement updateStatement = conn.prepareStatement(sql);) {
       
            updateStatement.setString(1, estudiante.getNombreEstudiante());
            updateStatement.setString(2, idEstudiante);

            updateStatement.executeUpdate();
            
        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }
    }
    public Estudiante obtenerEstudiantePorId(String dpi){
        String sql = "SELECT * FROM estudiante WHERE id_estudiante = ?";
        Estudiante estudiante = null;
        try(     Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement queryStatement = conn.prepareStatement(sql);) {
       
            queryStatement.setString(1, dpi);
    
            ResultSet resultado = queryStatement.executeQuery();
            while(resultado.next()){
                estudiante = new Estudiante(resultado.getString("id_estudiante"),resultado.getString("nombre") );
            }
            return estudiante;
            
        } catch (SQLException ex) {
              return estudiante;
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

            default:
                error = "Ocurrió un inconveniente al procesar los datos.";
                break;
        }

        return error;
    }
    
    
}
