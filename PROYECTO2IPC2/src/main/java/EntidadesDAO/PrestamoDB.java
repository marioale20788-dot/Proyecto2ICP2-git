/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mario
 */
public class PrestamoDB {
     
    
    public boolean buscarLibroEnPrestamo(String idLibro) throws ElementoNoEncotradoExcepcion{
        String sql = "SELECT * FROM prestamo WHERE id_libro = ? AND estado = 'ACTIVO'";
         try (    Connection conn = DBConnection.getInstance().getConnection();
             PreparedStatement queryStatement = conn.prepareStatement(sql);){
         
             queryStatement.setString(1, idLibro);
             ResultSet resultado =  queryStatement.executeQuery();
             return resultado.next();
         } catch (SQLException ex) {
              throw new ElementoNoEncotradoExcepcion(sqlExcepcion(ex));
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
