/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.NotaActividad;
import Excepciones.DatosNoValidosExcepcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class NotasDb {
    
    
    public NotaActividad obtenerNotasPorActividad(String idEstudiante,String idActividad){
        String sql = "SELECT * FROM detalle_nota_actividad WHERE id_estudiante = ? AND id_actividad = ?";
         NotaActividad nota = null;
        try(  Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, idEstudiante);
            queryStatement.setString(2, idActividad);
            ResultSet resultado = queryStatement.executeQuery();
            while(resultado.next()){
                 nota  = new NotaActividad(resultado.getDouble("punteo_obtenido"), 
                        resultado.getString("id_actividad"), 
                        resultado.getString("id_estudiante"));
                nota.setIdNota(resultado.getString("id_detalle_nota"));
              
            }
            return nota;             
          
        } catch (SQLException ex) {
         return nota;
        }
        
    }
        
    public NotaActividad obtenerNotasPorId(String idNota){
        String sql = "SELECT * FROM detalle_nota_actividad WHERE id_detalle_nota = ?";
         NotaActividad nota = null;
        try(  Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, idNota);
            ResultSet resultado = queryStatement.executeQuery();
            while(resultado.next()){
                 nota  = new NotaActividad(resultado.getDouble("punteo_obtenido"), 
                        resultado.getString("id_actividad"), 
                        resultado.getString("id_estudiante"));
                nota.setIdNota(resultado.getString("id_detalle_nota"));
              
            }
            return nota;             
          
        } catch (SQLException ex) {
         return nota;
        }
        
    }
    
    public void crearNota(NotaActividad nota) throws DatosNoValidosExcepcion{
        String sql = "INSERT INTO detalle_nota_actividad(id_detalle_nota,punteo_obtenido,id_actividad,id_estudiante) VALUES(?,?,?,?)";
     
        
        try(    Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement insertStatement = conn.prepareStatement(sql);) {
            insertStatement.setString(1, nota.getIdNota());
            insertStatement.setDouble(2, nota.getPunteoObtenido());
            insertStatement.setString(3, nota.getIdActividad());
            insertStatement.setString(4, nota.getIdEstudiante());
            insertStatement.execute();
        
        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }
        
        
    }
    public void updateNota(NotaActividad nota,String idNota) throws DatosNoValidosExcepcion{
        String sql = "UPDATE detalle_nota_actividad SET punteo_obtenido = ? WHERE id_detalle_nota = ?";
        try( Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement updateStatement = conn.prepareStatement(sql);) {
            updateStatement.setDouble(1, nota.getPunteoObtenido());
           updateStatement.setString(2, idNota);
           updateStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
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
