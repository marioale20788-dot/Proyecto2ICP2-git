/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.Carrera;
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
public class CarreraDB {

    public void crearCarrera(Carrera carrera) throws DatosNoValidosExcepcion {
        String sql = "INSERT INTO carrera(id_carrera,nombre,estado) values(?,?,?)";
        if (!buscarCarreraPorNombreOId(carrera.getNombre()).isEmpty()) {
            throw new DatosNoValidosExcepcion("NOMBRE DE CARRERA REPETIDO");
        }
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement insertStatement = conn.prepareStatement(sql);) {

            insertStatement.setString(1, carrera.getIdCarrera());

            insertStatement.setString(2, carrera.getNombre());
            insertStatement.setBoolean(3, carrera.getEstado());
            insertStatement.execute();

        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }

    public ArrayList<Carrera> buscarTodasLasCarreras() {
        String sql = "SELECT * FROM carrera";
        ArrayList<Carrera> carreras = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {

            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Carrera carrera = new Carrera(resultado.getString("id_carrera"),
                        resultado.getString("nombre"),
                        resultado.getBoolean("estado"));
                carreras.add(carrera);
            }
            return carreras;

        } catch (SQLException ex) {
            return carreras;
        }

    }

    public Carrera updateCarrera(Carrera carrera, String idCarrera) throws DatosNoValidosExcepcion {
        String sql = "UPDATE carrera SET nombre = ?,estado = ? WHERE id_carrera = ? ";
        try (Connection conn = DBConnection.getInstance().getConnection(); 
                PreparedStatement updateStatement = conn.prepareStatement(sql);) {

            updateStatement.setString(1, carrera.getNombre());
            updateStatement.setBoolean(2, carrera.getEstado());
            updateStatement.setString(3, idCarrera);
            updateStatement.executeUpdate();
            return carrera;
        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }

    public ArrayList<Carrera> buscarCarreraPorNombreOId(String dato) {
        String sql = "SELECT * FROM carrera  WHERE (id_carrera = ? OR nombre = ?)";
        ArrayList<Carrera> carreras = new ArrayList<>();

        try ( Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement queryStatement = conn.prepareStatement(sql);){
           
            queryStatement.setString(1, dato);
            queryStatement.setString(2, dato);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Carrera carrera = new Carrera(resultado.getString("id_carrera"),
                        resultado.getString("nombre"),
                        resultado.getBoolean("estado"));
                carreras.add(carrera);

            }
            return carreras;

        } catch (SQLException ex) {
            return carreras;
        }

    }

    public void EliminarCarrera(String id) throws ElemtoAElminarEnUsoExcepcion {
        String sql = "DELETE FROM carrera WHERE id_carrera = ?";
        try ( Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement updateStatement = conn.prepareStatement(sql);){
           
            updateStatement.setString(1, id);
            updateStatement.execute();

        } catch (SQLException ex) {
            throw new ElemtoAElminarEnUsoExcepcion(sqlExcepcion(ex));
        }

    }

    public String sqlExcepcion(SQLException excepcion) {
        String error;

        switch (excepcion.getErrorCode()) {

            case 1062:
                error = "El id  ingresado ya se encuentra registrado.";
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
