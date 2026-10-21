/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.Empleado;
import Entidades.TipoUsuarioEnum;
import Entidades.Usuario;
import Excepciones.DatosNoValidosExcepcion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mario
 */
public class EmpleadoDB {

    public void CrearEmpleado(String idUsuario, Empleado empleado) throws DatosNoValidosExcepcion {
        String sql = "INSERT INTO empleado(id_empleado, salario,nombre,fecha_de_contratacion) values(?,?,?,?)";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement insertStatement = conn.prepareStatement(sql);) {
            insertStatement.setString(1, idUsuario);
            insertStatement.setDouble(2, empleado.getSalario());
            insertStatement.setString(3, empleado.getNombre());
            insertStatement.setDate(4, Date.valueOf(empleado.getFechaDeContratacion()));
            insertStatement.execute();
        } catch (SQLException ex) {

            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }
    public void updateEmpleado(String dpi, Empleado empleado) throws DatosNoValidosExcepcion{
            String sql = "UPDATE empleado SET salario = ?,nombre = ?,fecha_de_contratacion=? WHERE id_empleado = ? ";
             try (Connection conn = DBConnection.getInstance().getConnection(); 
                     PreparedStatement updateStatement = conn.prepareStatement(sql);) {
                 updateStatement.setDouble(1,empleado.getSalario());
                 updateStatement.setString(2, empleado.getNombre());
                 updateStatement.setDate(3, Date.valueOf(empleado.getFechaDeContratacion()));
                 updateStatement.setString(4, dpi);
                 updateStatement.execute();
        
    }   catch (SQLException ex) {
          throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }
    }

    public Empleado obtenerEmpleadoPorId(String dpi) {
        String sql = "SELECT * FROM empleado WHERE id_empleado = ?";
       Empleado empleadoBuscado = null;

        try (Connection conn = DBConnection.getInstance().getConnection(); 
                PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, dpi);
            ResultSet empleado = queryStatement.executeQuery();
            while(empleado.next()){
               empleadoBuscado = new Empleado(dpi, 
                       empleado.getDouble("salario"), 
                       empleado.getString("nombre"),
                       empleado.getDate("fecha_de_contratacion").toLocalDate());
               
            }
              return empleadoBuscado;
           
        } catch (SQLException ex) {
     return empleadoBuscado;
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
