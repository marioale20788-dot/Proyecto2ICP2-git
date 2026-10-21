/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.Curriculo;
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
public class CurriculoDB {

    public void insertarCurriculo(Curriculo curriculo) throws DatosNoValidosExcepcion {
        String sql = "INSERT INTO curriculo(id_curriculo,id_grado,codigo_curso,id_ciclo_lectivo) values(?,?,?,?)";
        CicloLectivoDB cicloLectivoDb = new CicloLectivoDB();
        if (cicloLectivoDb.verificarCicloActivoParaUpdate(curriculo.getCicloLectivo()) == true) {
            throw new DatosNoValidosExcepcion("EL CICLO INDICADO NO ESTA ACTIVO");
        }

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement inserStatement = conn.prepareStatement(sql);) {
            inserStatement.setString(1, curriculo.getIdCurriculo());
            inserStatement.setString(2, curriculo.getIdGrado());
            inserStatement.setString(3, curriculo.getCodigoCurso());
            inserStatement.setInt(4, curriculo.getCicloLectivo());
            inserStatement.execute();

        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }

    public ArrayList<Curriculo> obtenerCurriculoCicloGrado(String idGrado, int ciclo) {
        String sql = "SELECT * FROM curriculo WHERE id_grado = ? AND id_ciclo_lectivo = ? ";
        ArrayList<Curriculo>  curriculo = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, idGrado);
            queryStatement.setInt(2, ciclo);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Curriculo curso = new Curriculo(resultado.getString("id_grado"),
                        resultado.getString("codigo_curso"),
                        resultado.getInt("id_ciclo_lectivo"));
                curso.setIdCurriculo(resultado.getString("id_curriculo"));
                curriculo.add(curso);
                
            }
            return curriculo;

        } catch (SQLException ex) {
             return curriculo;
        }

    }
    public boolean curriculoExiste(String idCurriculo){
          String sql = "SELECT * FROM curriculo WHERE id_curriculo = ? ";
           try (Connection conn = DBConnection.getInstance().getConnection(); 
                   PreparedStatement queryStatement = conn.prepareStatement(sql);) {
               queryStatement.setString(1, idCurriculo);
               ResultSet resultado = queryStatement.executeQuery();
               return resultado.next();
           } catch (SQLException ex) {
            return false;
        }
        
    }
    public Curriculo obtenreCurriculoPorId(String id){
         String sql = "SELECT * FROM curriculo WHERE id_curriculo = ? ";
      Curriculo curso = null;
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, id);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                 curso = new Curriculo(resultado.getString("id_grado"),
                        resultado.getString("codigo_curso"),
                        resultado.getInt("id_ciclo_lectivo"));
                curso.setIdCurriculo(resultado.getString("id_curriculo"));

                
            }
            return curso;

        } catch (SQLException ex) {
             return curso;
        }
        
    }
    public void eliminarCursoCurriculo(String idCurriculo) throws ElemtoAElminarEnUsoExcepcion{
          String sql = "DELETE FROM curriculo WHERE id_curriculo = ?";

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement updateStatement = conn.prepareStatement(sql);) {

            updateStatement.setString(1, idCurriculo);
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
                error = "El grado, curso o ciclo seleccionado no existe en el sistema.";
                break;

            default:
                error = "Ocurrió un inconveniente al procesar los datos.";
                break;
        }

        return error;
    }
}
