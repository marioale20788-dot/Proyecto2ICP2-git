/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.Grado;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author mario
 */
public class GradoDB {

    public void crearGrado(Grado grado) throws DatosNoValidosExcepcion {
        String sql = "INSERT INTO grado(id_grado,nombre_grado,id_nivel,id_carrera) VALUES(?,?,?,?)";
         if (gradoYaCreado(grado.getNombreGrado(),grado.getIdNivel()) == true) {
                throw new DatosNoValidosExcepcion("NOMBRE REPETIDO");
            }
        try ( Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement insertStatement = conn.prepareStatement(sql);){
           
            insertStatement.setString(1, generarIdAleatorio());
           
            insertStatement.setString(2, grado.getNombreGrado());
            insertStatement.setString(3, grado.getIdNivel());
            insertStatement.setString(4, grado.getIdCarrera());
            insertStatement.execute();

        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }

    public ArrayList<Grado> obtenerTodosLosGrados() {
        String sql = "SELECT * FROM grado";
        ArrayList<Grado> gradosCreados = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement queryStatement = conn.prepareStatement(sql);){
            
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Grado grado = new Grado(resultado.getString("nombre_grado"),
                        resultado.getString("id_grado"),
                        resultado.getString("id_nivel"),
                        resultado.getString("id_carrera"));
                
                gradosCreados.add(grado);
            }
            return gradosCreados;
        } catch (SQLException ex) {
          return gradosCreados;
        }

    }
    
    public ArrayList<Grado> obtenerGradoPorId(String id){
              String sql = "SELECT * FROM grado WHERE id_grado = ?";

        ArrayList<Grado> gradosCreados = new ArrayList<>();
        try ( Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement queryStatement = conn.prepareStatement(sql);){
           
            queryStatement.setString(1, id);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Grado grado = new Grado(resultado.getString("nombre_grado"),
                        resultado.getString("id_grado"),
                        resultado.getString("id_nivel"),
                        resultado.getString("id_carrera"));
                
                gradosCreados.add(grado);
            }
            return gradosCreados;
        } catch (SQLException ex) {
          return gradosCreados;
        }

    }
    public boolean eliminarGrado(String id) throws ElemtoAElminarEnUsoExcepcion {
        String sql = "DELETE FROM grado WHERE id_grado = ?";
      
        try (   Connection  conn = DBConnection.getInstance().getConnection();
            PreparedStatement updateStatement = conn.prepareStatement(sql);){
       
            updateStatement.setString(1, id);
            return updateStatement.execute();
        } catch (SQLException ex) {
            throw  new ElemtoAElminarEnUsoExcepcion(sqlExcepcion(ex));
        }

    }

    public ArrayList<Grado> obtenerGradoPorNivel(String idNivel) {
        String sql = "SELECT * FROM grado WHERE id_nivel = ?";

        ArrayList<Grado> gradosCreados = new ArrayList<>();
        try(  Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement queryStatement = conn.prepareStatement(sql);) {
          
            queryStatement.setString(1, idNivel);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Grado grado = new Grado(resultado.getString("nombre_grado"),
                        resultado.getString("id_grado"),
                        resultado.getString("id_nivel"),
                        resultado.getString("id_carrera"));
                
                gradosCreados.add(grado);
            }
            return gradosCreados;
        } catch (SQLException ex) {
          return gradosCreados;
        }

    }
    
    

    public String generarIdAleatorio() {
        String sql = "SELECT * FROM grado WHERE id_grado = ?";
        boolean codigoNoRepetido = false;
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        String codigo = "";
        while (codigoNoRepetido == false) {
            codigo = "";
            for (int i = 0; i < 10; i++) {
                codigo = codigo + caracteres.charAt(random.nextInt(caracteres.length()));
            }
            try( Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement queryStatement = conn.prepareStatement(sql);) {
               
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

    public boolean gradoYaCreado(String nombre, String idNivel) {
        String sql = "SELECT * FROM grado WHERE nombre_grado = ? AND id_nivel = ?";
        try( Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement queryStatement = conn.prepareStatement(sql);) {
           
            queryStatement.setString(1, nombre);
              queryStatement.setString(2, idNivel);
            ResultSet resultado = queryStatement.executeQuery();
            return resultado.next();

        } catch (SQLException ex) {
            return false;
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
