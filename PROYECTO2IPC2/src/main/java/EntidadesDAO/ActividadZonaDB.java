/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.ActividadZona;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author mario
 */
public class ActividadZonaDB {

    public void crearActividad(ActividadZona actividad) throws DatosNoValidosExcepcion {
        String sql = "INSERT INTO actividad(id_actividad,nombre,ponderacion,fecha_entrega,id_asignacion) VALUES(?,?,?,?,?)";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement insertStatement = conn.prepareStatement(sql);) {
            insertStatement.setString(1, actividad.getIdActividad());
            insertStatement.setString(2, actividad.getNombreActividad());
            insertStatement.setDouble(3, actividad.getPonderacion());
            insertStatement.setDate(4, Date.valueOf(actividad.getFechaEntrega()));
            insertStatement.setString(5, actividad.getIdAsignacion());
            insertStatement.execute();

        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }
    
    public ActividadZona updateActividad(ActividadZona update,String idActividad) throws DatosNoValidosExcepcion{
             String sql = "UPDATE actividad SET nombre = ?,ponderacion = ?,fecha_entrega = ? WHERE id_actividad = ? ";
        try (    Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement updateStatement = conn.prepareStatement(sql);){
          
        
            updateStatement.setString(1, update.getNombreActividad());
            updateStatement.setDouble(2,update.getPonderacion());
            updateStatement.setDate(3, Date.valueOf(update.getFechaEntrega()));
            updateStatement.setString(4, idActividad);
       
            updateStatement.executeUpdate();
        return update;
        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }
     public void EliminarActividad(String idActividad) throws ElemtoAElminarEnUsoExcepcion {
        String sql = "DELETE FROM actividad WHERE id_actividad = ?";
        try ( Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement updateStatement = conn.prepareStatement(sql);){
           
            updateStatement.setString(1, idActividad);
            updateStatement.execute();

        } catch (SQLException ex) {
            throw new ElemtoAElminarEnUsoExcepcion(sqlExcepcion(ex));
        }

    }

    public ArrayList<ActividadZona> obtenerActividadesAsignacion(String idAsignacion){
        String sql = "SELECT * FROM actividad WHERE id_asignacion = ?";
        ArrayList<ActividadZona> actividades = new ArrayList<>();
         try (Connection conn = DBConnection.getInstance().getConnection(); 
                 PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, idAsignacion);
            ResultSet resultado = queryStatement.executeQuery();
            while(resultado.next()){
                ActividadZona actividad = new ActividadZona(resultado.getString("nombre"),
                       resultado.getDouble("ponderacion"), 
                       resultado.getDate("fecha_entrega").toLocalDate(), 
                        resultado.getString("id_asignacion"));
                actividad.setIdActividad(resultado.getString("id_actividad"));
                actividades.add(actividad);
                
            }
            return actividades;

        } catch (SQLException ex) {
               return actividades;
        }
    }
  
        public ArrayList<ActividadZona> obtenerActividadePorId(String idActividad){
        String sql = "SELECT * FROM actividad WHERE id_actividad = ?";
        ArrayList<ActividadZona> actividades = new ArrayList<>();
         try (Connection conn = DBConnection.getInstance().getConnection(); 
                 PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, idActividad);
            ResultSet resultado = queryStatement.executeQuery();
            while(resultado.next()){
                ActividadZona actividad = new ActividadZona(resultado.getString("nombre"),
                       resultado.getDouble("ponderacion"), 
                       resultado.getDate("fecha_entrega").toLocalDate(), 
                        resultado.getString("id_asignacion"));
                actividad.setIdActividad(resultado.getString("id_actividad"));
                actividades.add(actividad);
                
            }
            return actividades;

        } catch (SQLException ex) {
               return actividades;
        }
    }

    public String generarIdAleatorio() {
        String sql = "SELECT * FROM actividad WHERE id_actividad = ?";
        boolean codigoNoRepetido = false;
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        String codigo = "";
        while (codigoNoRepetido == false) {
            codigo = "";
            for (int i = 0; i < 15; i++) {
                codigo = codigo + caracteres.charAt(random.nextInt(caracteres.length()));
            }
            try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {

                queryStatement.setString(1, codigo);
                ResultSet resultado = queryStatement.executeQuery();
                if (resultado.next()) {
                    codigoNoRepetido = false;
                } else {
                    codigoNoRepetido = true;
                }
            } catch (SQLException ex) {

            }

        }

        return codigo;
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
                error = "la asignacion ingersada no existe en el sistema.";
                break;

            default:
                error = "Ocurrió un inconveniente al procesar los datos.";
                break;
        }

        return error;
    }

}
