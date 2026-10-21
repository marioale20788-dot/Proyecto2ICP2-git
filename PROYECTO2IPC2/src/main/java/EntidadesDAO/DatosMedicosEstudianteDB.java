/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.DatosMedicosEstudiante;
import Excepciones.DatosNoValidosExcepcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mario
 */
public class DatosMedicosEstudianteDB {
    
    
    public void crearDatosMedicos(DatosMedicosEstudiante datosMedicos) throws DatosNoValidosExcepcion{
        String sql = "INSERT INTO datos_medicos(tipo_sangre,alergias,enfermedades,medicamentos,contacto_de_emergencia,id_estudiante) values(?,?,?,?,?,?)";
        try ( Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement insertStatement = conn.prepareStatement(sql);){
            insertStatement.setString(1, datosMedicos.getTipoDeSangre());
            insertStatement.setString(2, datosMedicos.getAlergias());
            insertStatement.setString(3, datosMedicos.getEnfermedades());
            insertStatement.setString(4, datosMedicos.getMedicamentos());
            insertStatement.setInt(5, datosMedicos.getContactoDeEmergencia());
            insertStatement.setString(6, datosMedicos.getIdEstudiante());
            insertStatement.execute();
           
        } catch (SQLException ex) {
               throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }
        
    }
    public void updateDatosMedicos(DatosMedicosEstudiante updateDatos, String id) throws DatosNoValidosExcepcion{
                   String sql = "UPDATE datos_medicos SET tipo_sangre = ?,alergias = ?,enfermedades = ?, medicamentos = ?, contacto_de_emergencia = ? WHERE id_estudiante = ? ";
                     try ( Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement updateStatement = conn.prepareStatement(sql);){
            updateStatement.setString(1, updateDatos.getTipoDeSangre());
            updateStatement.setString(2, updateDatos.getAlergias());
            updateStatement.setString(3, updateDatos.getEnfermedades());
            updateStatement.setString(4, updateDatos.getMedicamentos());
            updateStatement.setInt(5, updateDatos.getContactoDeEmergencia());
            updateStatement.setString(6, id);
            updateStatement.executeUpdate();
           
        } catch (SQLException ex) {
               throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }
    }
    
      public DatosMedicosEstudiante obtenerDatosPorDpiUs(String dpi){
        String sql = "SELECT * FROM datos_medicos WHERE id_estudiante = ?";
        DatosMedicosEstudiante datosMedicos = null;    
        try(    Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, dpi);
                ResultSet resultado = queryStatement.executeQuery();
                while(resultado.next()){
                    datosMedicos = new DatosMedicosEstudiante(dpi,
                            resultado.getString("alergias"),
                              resultado.getString("enfermedades"), 
                             resultado.getString("tipo_sangre"), 
                            resultado.getString("medicamentos"), 
                             resultado.getInt("contacto_de_emergencia"));
                }
                return datosMedicos;
        
        } catch (SQLException ex) {
              return datosMedicos;
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
