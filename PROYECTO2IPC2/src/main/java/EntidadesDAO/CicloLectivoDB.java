/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.CicloLectivo;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
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
public class CicloLectivoDB {

    public CicloLectivo crearCicloLectivo(CicloLectivo ciclo) throws DatosNoValidosExcepcion {
        String sql = "INSERT INTO ciclo_lectivo(anio,fecha_inicio,fecha_final,estado) values(?,?,?,?)";
        if (ciclo.getEstado() == true) {
            if (verificarCicloActivo() == true) {
                throw new DatosNoValidosExcepcion("YA EXISTE UN CICLO ACTIVO");
            }
        }
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement insertStatement = conn.prepareStatement(sql);) {

            insertStatement.setInt(1, ciclo.getAnio());
            insertStatement.setDate(2, Date.valueOf(ciclo.getFechaInicio()));
            insertStatement.setDate(3, Date.valueOf(ciclo.getFechaFinal()));
            insertStatement.setBoolean(4, ciclo.getEstado());
            insertStatement.execute();
            return ciclo;
        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }

    public ArrayList<CicloLectivo> obtenerTodosLosCiclos() {
        String sql = "SELECT * FROM ciclo_lectivo";
        ArrayList<CicloLectivo> ciclos = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {

            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                CicloLectivo ciclo = new CicloLectivo(resultado.getInt("anio"),
                        resultado.getDate("fecha_inicio").toLocalDate(),
                        resultado.getDate("fecha_final").toLocalDate(),
                        resultado.getBoolean("estado"));
                ciclos.add(ciclo);

            }
            return ciclos;
        } catch (SQLException ex) {
            return ciclos;
        }

    }

    public CicloLectivo updateCiclo(int ciclo, CicloLectivo cambiosCiclo) throws DatosNoValidosExcepcion {
        String sql = "UPDATE ciclo_lectivo SET fecha_inicio = ?, fecha_final = ?, estado = ? WHERE anio = ?";
        if (cambiosCiclo.getEstado() == true) {
            if (verificarCicloActivoParaUpdate(ciclo) == true) {
                throw new DatosNoValidosExcepcion("YA EXISTE UN CICLO ACTIVO");
            }
        }
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement updateStatement = conn.prepareStatement(sql);) {

            updateStatement.setDate(1, Date.valueOf(cambiosCiclo.getFechaInicio()));
            updateStatement.setDate(2, Date.valueOf(cambiosCiclo.getFechaFinal()));
            updateStatement.setBoolean(3, cambiosCiclo.getEstado());
            updateStatement.setInt(4, ciclo);
            updateStatement.execute();
            return cambiosCiclo;

        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }

    public ArrayList<CicloLectivo> buscarCicloPorAnio(int anio) {
        String sql = "SELECT * FROM ciclo_lectivo WHERE anio = ?";
        ArrayList<CicloLectivo> ciclos = new ArrayList<>();

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {

            queryStatement.setInt(1, anio);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                CicloLectivo ciclo = new CicloLectivo(resultado.getInt("anio"),
                        resultado.getDate("fecha_inicio").toLocalDate(),
                        resultado.getDate("fecha_final").toLocalDate(),
                        resultado.getBoolean("estado"));
                ciclos.add(ciclo);

            }
            return ciclos;
        } catch (SQLException ex) {
            return ciclos;
        }

    }

    public boolean eliminarCicloSinUso(int anio) throws ElemtoAElminarEnUsoExcepcion {
        String sql = "DELETE FROM ciclo_lectivo WHERE anio = ?";

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement updateStatement = conn.prepareStatement(sql);) {

            updateStatement.setInt(1, anio);
            return updateStatement.execute();
        } catch (SQLException ex) {
            throw new ElemtoAElminarEnUsoExcepcion(sqlExcepcion(ex));
        }

    }

    public boolean verificarCicloActivo() throws DatosNoValidosExcepcion {
        String sql = "SELECT * FROM ciclo_lectivo WHERE estado = true";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {

            ResultSet resultado = queryStatement.executeQuery();
            return resultado.next();
        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }

    public boolean verificarCicloActivoParaUpdate(int anio) throws DatosNoValidosExcepcion {
        String sql = "SELECT * FROM ciclo_lectivo WHERE estado = true AND anio != ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {

            queryStatement.setInt(1, anio);
            ResultSet resultado = queryStatement.executeQuery();
            return resultado.next();
        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }

    public String sqlExcepcion(SQLException excepcion) {
        String error;

        switch (excepcion.getErrorCode()) {

            case 1062:
                error = "El anio  ingresado ya se encuentra registrado.";
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
