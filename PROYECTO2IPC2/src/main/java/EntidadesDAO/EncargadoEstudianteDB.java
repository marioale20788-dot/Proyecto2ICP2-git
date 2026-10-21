/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.EncargadoEstudiante;
import Excepciones.DatosNoValidosExcepcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mario
 */
public class EncargadoEstudianteDB {

    public void crearEncargadoEstudiante(EncargadoEstudiante encargado) throws DatosNoValidosExcepcion {
        String sql = "INSERT INTO encargado_estudiante(dpi_encargado,nombre,direccion,id_estudiante) values(?,?,?,?)";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement insertStatement = conn.prepareStatement(sql);) {
            insertStatement.setString(1, encargado.getDpiEncargado());
            insertStatement.setString(2, encargado.getNombreEncargado());
            insertStatement.setString(3, encargado.getDireccionEncargado());
            insertStatement.setString(4, encargado.getIdEstudiante());
            insertStatement.execute();

        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }

    public void updateEncargado(EncargadoEstudiante update, String idEstudiante) throws DatosNoValidosExcepcion {
        String sql = "UPDATE encargado_estudiante SET nombre = ?,direccion = ? WHERE id_estudiante = ? ";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement updateStatement = conn.prepareStatement(sql);) {
            updateStatement.setString(1, update.getNombreEncargado());
            updateStatement.setString(2, update.getDireccionEncargado());
            updateStatement.setString(3, idEstudiante);
            updateStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }
    }

    public EncargadoEstudiante obtenerEncargadoPorDpiUs(String dpi) {
        String sql = "SELECT * FROM encargado_estudiante WHERE id_estudiante = ?";
        EncargadoEstudiante encargado = null;
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, dpi);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                encargado = new EncargadoEstudiante(resultado.getString("id_estudiante"),
                        resultado.getString("dpi_encargado"),
                        resultado.getString("nombre"),
                        resultado.getString("direccion"));
            }
            return encargado;

        } catch (SQLException ex) {
            return encargado;
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
