/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.NotaFinal;
import Excepciones.DatosNoValidosExcepcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author mario
 */
public class NotaFinalDb {
    
    
    public void insertarNota(NotaFinal nota) throws DatosNoValidosExcepcion{
        String sql = "INSERT INTO nota(id_nota,nota_final,id_estudiante,codigo_curso,id_ciclo_lectivo) VALUES(?,?,?,?,?)";
        try (   Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement insertStatement = conn.prepareStatement(sql);){
            insertStatement.setString(1, nota.getIdNota());
            insertStatement.setDouble(2, nota.getNotaFinal());
            insertStatement.setString(3, nota.getIdEstudiante());
            insertStatement.setString(4, nota.getCodigoCurso());
            insertStatement.setInt(5, nota.getCiclo());
            insertStatement.execute();
         
        } catch (SQLException ex) {
           throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }
    }
         public String sqlExcepcion(SQLException excepcion) {
        String error;
        
        switch (excepcion.getErrorCode()) {
            
            case 1062:
                error = "La nota final ya ha sido registrada";
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
