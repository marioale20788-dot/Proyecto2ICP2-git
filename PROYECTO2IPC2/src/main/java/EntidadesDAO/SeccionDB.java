/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.Seccion;
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
public class SeccionDB {

    public void crearSeccion(Seccion seccion) throws DatosNoValidosExcepcion {
        String sql = "INSERT INTO seccion(id_seccion,nombre_seccion,id_grado) values(?,?,?)";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement insertStatement = conn.prepareStatement(sql);) {
            insertStatement.setString(1, seccion.getIdSeccion());
            insertStatement.setString(2, seccion.getNombreSeccion());
            insertStatement.setString(3, seccion.getIdGrado());
            insertStatement.execute();

        } catch (SQLException e) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(e));
        }
    }

    public boolean buscarSeccionPorNombreId(String nombre, String idGrado) throws DatosNoValidosExcepcion {
        String sql = "SELECT * FROM seccion WHERE nombre_seccion = ? AND id_grado = ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, nombre);
            queryStatement.setString(2, idGrado);
            ResultSet resultado = queryStatement.executeQuery();
            return resultado.next();
        } catch (SQLException e) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(e));
        }

    }

    public void eliminarSeccion(String idSeccion) throws ElemtoAElminarEnUsoExcepcion {
        String sql = "DELETE FROM seccion WHERE id_seccion = ?";

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement updateStatement = conn.prepareStatement(sql);) {

            updateStatement.setString(1, idSeccion);
            updateStatement.execute();
        } catch (SQLException ex) {
            throw new ElemtoAElminarEnUsoExcepcion(sqlExcepcion(ex));
        }
    }

    public Seccion updateSeccion(Seccion seccion) throws DatosNoValidosExcepcion {
        String sql = "UPDATE seccion SET nombre_seccion = ?, id_grado = ? WHERE id_seccion = ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement updateStatement = conn.prepareStatement(sql);) {

            updateStatement.setString(1, seccion.getNombreSeccion());
            updateStatement.setString(2, seccion.getIdGrado());
            updateStatement.setString(3, seccion.getIdSeccion());

            updateStatement.executeUpdate();
            return seccion;
        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }

    public ArrayList<Seccion> obtenerSeccionPorId(String id) {
        String sql = "SELECT * FROM seccion WHERE id_seccion = ?";
        ArrayList<Seccion> secciones = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {

            queryStatement.setString(1, id);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Seccion seccion = new Seccion(resultado.getString("id_seccion"),
                        resultado.getString("nombre_seccion"),
                        resultado.getString("id_grado"));
                secciones.add(seccion);
            }
            return secciones;

        } catch (SQLException e) {
            return secciones;
        }
    }
 public ArrayList<Seccion> obtenerTodasLasSeccionPorGrado( String idGrado) {
        String sql = "SELECT * FROM seccion WHERE id_grado = ?";
      
        ArrayList<Seccion> secciones = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
           
                queryStatement.setString(1, idGrado);
            
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Seccion seccion = new Seccion(resultado.getString("id_seccion"),
                        resultado.getString("nombre_seccion"),
                        resultado.getString("id_grado"));
                secciones.add(seccion);
            }
            return secciones;

        } catch (SQLException e) {
            return secciones;
        }
    }
    public ArrayList<Seccion> obtenerTodasLasSeccion(boolean buscarSeccionPorNombre, String nombre) {
        String sql = "SELECT * FROM seccion";
        if (buscarSeccionPorNombre == true) {
            sql = sql + " WHERE nombre_seccion = ?";
        }
        ArrayList<Seccion> secciones = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            if (buscarSeccionPorNombre == true) {
                queryStatement.setString(1, nombre);
            }

            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Seccion seccion = new Seccion(resultado.getString("id_seccion"),
                        resultado.getString("nombre_seccion"),
                        resultado.getString("id_grado"));
                secciones.add(seccion);
            }
            return secciones;

        } catch (SQLException e) {
            return secciones;
        }
    }

    public String generarIdAleatorio() {
        String sql = "SELECT * FROM seccion WHERE id_seccion = ?";
        boolean codigoNoRepetido = false;
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        String codigo = "";
        while (codigoNoRepetido == false) {
            codigo = "";
            for (int i = 0; i < 10; i++) {
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
                error = "El grado seleccionado no existe en el sistema.";
                break;

            default:
                error = "Ocurrió un inconveniente al procesar los datos.";
                break;
        }

        return error;
    }
}
