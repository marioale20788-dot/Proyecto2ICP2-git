/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.CostoPorAnio;
import EntidadesDto.Inscripcion.NewIncricpionRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author mario
 */
public class CostoPorAnioDB {

    public void insertarCosto(CostoPorAnio costo) throws DatosNoValidosExcepcion {
        String sql = "INSERT INTO costos_por_anio(id_costo,monto,fecha_limite,estado,id_estudiante,id_ciclo_lectivo) VALUES(?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement insertStatement = conn.prepareStatement(sql);) {
            insertStatement.setString(1, costo.getIdCosto());
            insertStatement.setDouble(2, costo.getMonto());
            insertStatement.setDate(3, Date.valueOf(costo.getFechaLimite()));
            insertStatement.setBoolean(4, costo.getEstado());
            insertStatement.setString(5, costo.getIdEstudiante());
            insertStatement.setInt(6, costo.getCicloLectivo());
            insertStatement.execute();
        } catch (SQLException ex) {
           throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }
    
    public void eliminarCostosPorAnio(NewIncricpionRequest inscripcionAEliminar) throws ElemtoAElminarEnUsoExcepcion{
           String sql = "DELETE FROM costos_por_anio WHERE id_estudiante = ? AND id_ciclo_lectivo = ?";

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement updateStatement = conn.prepareStatement(sql);) {

            updateStatement.setString(1, inscripcionAEliminar.getIdEstudiante());
            updateStatement.setInt(2, inscripcionAEliminar.getIdCicloLectivo());
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
