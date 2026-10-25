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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

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

    public ArrayList<CostoPorAnio> obtenerCostoPorId(String idCosto,String idEstudiante) throws DatosNoValidosExcepcion {
        String sql = "SELECT * FROM costos_por_anio WHERE id_costo = ? AND id_estudiante = ?";
        ArrayList<CostoPorAnio> costo = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, idCosto);
            queryStatement.setString(2, idEstudiante);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                CostoPorAnio costoId = new CostoPorAnio(resultado.getString("id_costo"),
                        resultado.getDouble("monto"),
                        resultado.getDate("fecha_limite").toLocalDate(),
                        resultado.getBoolean("estado"),
                        resultado.getString("id_estudiante"),
                        resultado.getInt("id_ciclo_lectivo"));
                costo.add(costoId);
            }
            return costo;

        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }
    }

    public boolean pagarCosto(Connection conne, String idCosto, double monto,String idEstudiante) throws DatosNoValidosExcepcion, SQLException {

        ArrayList<CostoPorAnio> costo = obtenerCostoPorId(idCosto,idEstudiante);
        if (costo.isEmpty()) {
            return false;
        }
        if (monto != costo.getFirst().getMonto()) {
            return false;
        }
        String sql = "UPDATE costos_por_anio SET estado = true WHERE id_costo = ?";
        try (PreparedStatement updateStatement = conne.prepareStatement(sql);) {
            updateStatement.setString(1, idCosto);
            int resultado = updateStatement.executeUpdate();
            return resultado > 0;

        }

    }

    public void eliminarCostosPorAnio(NewIncricpionRequest inscripcionAEliminar) throws ElemtoAElminarEnUsoExcepcion {
        String sql = "DELETE FROM costos_por_anio WHERE id_estudiante = ? AND id_ciclo_lectivo = ?";

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement updateStatement = conn.prepareStatement(sql);) {

            updateStatement.setString(1, inscripcionAEliminar.getIdEstudiante());
            updateStatement.setInt(2, inscripcionAEliminar.getIdCicloLectivo());
            updateStatement.execute();
        } catch (SQLException ex) {
            throw new ElemtoAElminarEnUsoExcepcion(sqlExcepcion(ex));
        }
    }
    public ArrayList<CostoPorAnio> obtenerCostosDeEstudiante(int idCiclo, String idEstudiante,boolean incluirEstado){
   String sql = "SELECT * FROM costos_por_anio WHERE id_estudiante = ? AND id_ciclo_lectivo = ?";
   if(incluirEstado==true){
       sql = sql+" AND estado = false";
   }
   ArrayList<CostoPorAnio> costos = new ArrayList<>();
   
        try (Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement queryStatement = conn.prepareStatement(sql);){
            queryStatement.setString(1, idEstudiante);
            queryStatement.setInt(2, idCiclo);
            ResultSet resultado = queryStatement.executeQuery();
            while(resultado.next()){
                CostoPorAnio costoEstuidante = new CostoPorAnio(resultado.getString("id_costo"), 
                      resultado.getDouble("monto"),
                       resultado.getDate("fecha_limite").toLocalDate(), 
                       resultado.getBoolean("estado"), 
                        resultado.getString("id_estudiante"),
                        resultado.getInt("id_ciclo_lectivo"));
                costos.add(costoEstuidante);
            }
            return costos;
            
        } catch (SQLException e) {
             return costos;
        }
    }
    public boolean costosPendientesParaInscripcion(String idEstudiante){
        String sql = "SELECT * FROM costos_por_anio WHERE id_estudiante = ? AND estado = false";
         
   
        try (Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement queryStatement = conn.prepareStatement(sql);){
            queryStatement.setString(1, idEstudiante);
       
            ResultSet resultado = queryStatement.executeQuery();
          return resultado.next();
            
        } catch (SQLException e) {
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
