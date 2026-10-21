/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.AsignacionDocente;
import Entidades.Curriculo;
import Entidades.Grado;
import Entidades.Seccion;
import Entidades.TipoUsuarioEnum;
import Entidades.Usuario;
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
public class AsignacionDocenteDB {
    
    public void asignarDocente(AsignacionDocente asignacion) throws DatosNoValidosExcepcion {
        UsuarioDB usuarioDB = new UsuarioDB();
        ArrayList<Usuario> usuario = usuarioDB.obtenerUsuarioPorDpi(asignacion.getIdMaestro());
        if (!usuario.isEmpty()) {
            if (usuario.getFirst().getTipo() != TipoUsuarioEnum.Maestro) {
                throw new DatosNoValidosExcepcion("EL USUARIO INGRESADO NO ES MAESTRO");
            }
        }
        verificarGrados(asignacion);
        
        String sql = "INSERT INTO asignacion_maestros(id_asignacion,id_empleado,id_seccion,id_curriculo) values(?,?,?,?)";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement insertStatement = conn.prepareStatement(sql);) {
            insertStatement.setString(1, asignacion.getIdAsginacion());
            insertStatement.setString(2, asignacion.getIdMaestro());
            insertStatement.setString(3, asignacion.getIdSeccion());
            insertStatement.setString(4, asignacion.getIdCurricuolo());
            insertStatement.execute();
            
        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }
        
    }
    public ArrayList<AsignacionDocente> obtenerAsignacionPorId(String idAsignacion) {
        String sql = "SELECT * FROM asignacion_maestros WHERE id_asignacion = ?";
        ArrayList<AsignacionDocente> asignacion = new ArrayList<>();      
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {           
            queryStatement.setString(1, idAsignacion);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                AsignacionDocente asignacionDocente = new AsignacionDocente(resultado.getString("id_empleado"),
                        resultado.getString("id_seccion"),
                        resultado.getString("id_curriculo"));
                asignacionDocente.setIdAsginacion(resultado.getString("id_asignacion"));
                asignacion.add(asignacionDocente);
            }
            return asignacion;
            
        } catch (SQLException ex) {
         return asignacion;
        }
        
    }
    public void eliminarAsignacion(String idAsignacion) throws ElemtoAElminarEnUsoExcepcion{
          String sql = "DELETE FROM asignacion_maestros WHERE id_asignacion = ?";

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement updateStatement = conn.prepareStatement(sql);) {

            updateStatement.setString(1, idAsignacion);
            updateStatement.execute();
        } catch (SQLException ex) {
            throw new ElemtoAElminarEnUsoExcepcion(sqlExcepcion(ex));
        }
    }
    public ArrayList<AsignacionDocente> obtenerAsignacionPorSeccionCurriculo(String idSeccion, String idCurriculo) {
        String sql = "SELECT * FROM asignacion_maestros WHERE id_seccion = ? AND id_curriculo = ? ";
        ArrayList<AsignacionDocente> asignacion = new ArrayList<>();
        
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            
            queryStatement.setString(1, idSeccion);
            queryStatement.setString(2, idCurriculo);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                AsignacionDocente asignacionDocente = new AsignacionDocente(resultado.getString("id_empleado"),
                        resultado.getString("id_seccion"),
                        resultado.getString("id_curriculo"));
                asignacionDocente.setIdAsginacion(resultado.getString("id_asignacion"));
                asignacion.add(asignacionDocente);
            }
            return asignacion;
            
        } catch (SQLException ex) {
         return asignacion;
        }
        
    }

    public void verificarGrados(AsignacionDocente asignacion) throws DatosNoValidosExcepcion {
        
        GradoDB gradoDB = new GradoDB();
        SeccionDB seccionDB = new SeccionDB();
        CurriculoDB curriculoDb = new CurriculoDB();
        
        ArrayList<Seccion> seccion = seccionDB.obtenerSeccionPorId(asignacion.getIdSeccion());
        if (seccion == null || seccion.isEmpty()) {
            throw new DatosNoValidosExcepcion("LA SECCIÓN  NO EXISTE");
        }
        
        Curriculo curriculo = curriculoDb.obtenreCurriculoPorId(asignacion.getIdCurricuolo());
        if (curriculo == null) {
            throw new DatosNoValidosExcepcion("EL CURRÍCULO  NO EXISTE");
        }
        if (!seccion.getFirst().getIdGrado().equals(curriculo.getIdGrado())) {
            throw new DatosNoValidosExcepcion("GRADOS NO COINCIDEN");
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
