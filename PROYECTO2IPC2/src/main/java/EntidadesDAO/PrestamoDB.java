/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.Prestamo;
import Entidades.PrestamoEnum;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author mario
 */
public class PrestamoDB {

    public boolean buscarLibroEnPrestamo(String idLibro) throws ElementoNoEncotradoExcepcion {
        String sql = "SELECT * FROM prestamo WHERE id_libro = ? AND estado = 'ACTIVO'";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {

            queryStatement.setString(1, idLibro);
            ResultSet resultado = queryStatement.executeQuery();
            return resultado.next();
        } catch (SQLException ex) {
            throw new ElementoNoEncotradoExcepcion(sqlExcepcion(ex));
        }

    }

    public void registrarPrestamo(Prestamo prestamo) throws DatosNoValidosExcepcion {
        String sql = "INSERT INTO prestamo (id_prestamo,fecha_prestamo,fecha_esperada_entrega,estado,id_libro,id_usuario,cantidad) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement insertStatement = conn.prepareStatement(sql);) {
            insertStatement.setString(1, prestamo.getIdPrestamo());
            insertStatement.setDate(2, Date.valueOf(prestamo.getFechaPrestamo()));
            insertStatement.setDate(3, Date.valueOf(prestamo.getFechaEsperadaEntrega()));
            insertStatement.setString(4, prestamo.getEstado().toString());
            insertStatement.setString(5, prestamo.getIdLibro());
            insertStatement.setString(6, prestamo.getIdUsuario());
            insertStatement.setInt(7, prestamo.getCantidad());
            insertStatement.execute();

        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }
    public void eliminarPrestamo(String idPrestamo) throws ElemtoAElminarEnUsoExcepcion{
           String sql = "DELETE FROM prestamo WHERE id_prestamo = ?";

        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement updateStatement = conn.prepareStatement(sql);) {

            updateStatement.setString(1, idPrestamo);
        
            updateStatement.execute();
        } catch (SQLException ex) {
            throw new ElemtoAElminarEnUsoExcepcion(sqlExcepcion(ex));
        }
    }
        

    public void updatePrestamo(Prestamo prestamo, String idPrestamo) throws DatosNoValidosExcepcion {
        String sql = "UPDATE prestamo SET fecha_prestamo = ?,fecha_esperada_entrega = ?,fecha_entrega_real = ?,mora = ?,estado = ?,id_libro = ?,id_usuario = ?,cantidad = ?"
                + " WHERE id_prestamo = ?";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement updateStatement = conn.prepareStatement(sql);) {
            updateStatement.setDate(1, Date.valueOf(prestamo.getFechaPrestamo()));
            updateStatement.setDate(2, Date.valueOf(prestamo.getFechaEsperadaEntrega()));
            if (prestamo.getFechaEntregaReal() != null) {
                updateStatement.setDate(3, Date.valueOf(prestamo.getFechaEntregaReal()));
            }else{
                updateStatement.setDate(3, null);
            }

            updateStatement.setDouble(4, prestamo.getMora());
            updateStatement.setString(5, prestamo.getEstado().toString());
            updateStatement.setString(6, prestamo.getIdLibro());
            updateStatement.setString(7, prestamo.getIdUsuario());
            updateStatement.setInt(8, prestamo.getCantidad());
            updateStatement.setString(9, idPrestamo);
            updateStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }

    public ArrayList<Prestamo> obtenerPrestamoPorId(String idPrestamo, String idEstudiate) throws ElementoNoEncotradoExcepcion {
        String sql = "SELECT * FROM prestamo WHERE id_prestamo = ? AND id_usuario = ?";
        ArrayList<Prestamo> prestamo = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {

            queryStatement.setString(1, idPrestamo);
            queryStatement.setString(2, idEstudiate);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Prestamo prestamoid = crearPrestamo(resultado);
                prestamo.add(prestamoid);
            }
            return prestamo;

        } catch (SQLException ex) {
            throw new ElementoNoEncotradoExcepcion(sqlExcepcion(ex));
        }
    }

    public ArrayList<Prestamo> Prestamo(String idPrestamo) throws ElementoNoEncotradoExcepcion {
        String sql = "SELECT * FROM prestamo WHERE id_prestamo = ? AND estado = 'ACTIVO'";
        ArrayList<Prestamo> prestamo = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {

            queryStatement.setString(1, idPrestamo);

            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Prestamo prestamoid = crearPrestamo(resultado);
                prestamo.add(prestamoid);
            }
            return prestamo;

        } catch (SQLException ex) {
            throw new ElementoNoEncotradoExcepcion(sqlExcepcion(ex));
        }
    }

    public ArrayList<Prestamo> obtenerPrestamosEstudiante(String idEstudiante, String estado) throws ElementoNoEncotradoExcepcion {
        String sql = "SELECT * FROM prestamo WHERE id_usuario = ? AND estado = ?";
        ArrayList<Prestamo> prestamo = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, idEstudiante);
            queryStatement.setString(2, estado);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Prestamo prestamoid = crearPrestamo(resultado);
                prestamo.add(prestamoid);
            }
            return prestamo;

        } catch (SQLException ex) {
            throw new ElementoNoEncotradoExcepcion(sqlExcepcion(ex));
        }
    }
     public ArrayList<Prestamo> obtenerPrestamosPendientes() throws ElementoNoEncotradoExcepcion {
        String sql = "SELECT * FROM prestamo WHERE estado = 'DEVUELTO_CON_MORA'";
        ArrayList<Prestamo> prestamo = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
  
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Prestamo prestamoid = crearPrestamo(resultado);
                prestamo.add(prestamoid);
            }
            return prestamo;

        } catch (SQLException ex) {
            throw new ElementoNoEncotradoExcepcion(sqlExcepcion(ex));
        }
    }

    public Prestamo crearPrestamo(ResultSet resultado) throws SQLException {
        Prestamo prestamoid = new Prestamo(resultado.getString("id_prestamo"),
                resultado.getDate("fecha_prestamo").toLocalDate(),
                resultado.getDate("fecha_esperada_entrega").toLocalDate(),
                null,
                resultado.getDouble("mora"),
                PrestamoEnum.valueOf(resultado.getString("estado")),
                resultado.getString("id_libro"),
                resultado.getString("id_usuario"));

        if (resultado.getDate("fecha_entrega_real") != null) {
            prestamoid.setFechaEntregaReal(resultado.getDate("fecha_entrega_real").toLocalDate());

        }
        prestamoid.setCantidad(resultado.getInt("cantidad"));

        return prestamoid;
    }

    public boolean pagarPrestamo(Connection conne, String idPrestamo, double monto, String idEstudiante) throws ElementoNoEncotradoExcepcion, SQLException {

        ArrayList<Prestamo> prestamo = obtenerPrestamoPorId(idPrestamo, idEstudiante);
        if (prestamo.isEmpty()) {
            return false;
        }
        if (monto != prestamo.getFirst().getMora()) {
            return false;
        }
        String sql = "UPDATE prestamo SET estado = 'DEVUELTO' WHERE id_prestamo = ?";
        try (PreparedStatement updateStatement = conne.prepareStatement(sql);) {

            updateStatement.setString(1, idPrestamo);

            int resultado = updateStatement.executeUpdate();
            return resultado > 0;

        }
    }

    public String generarIdAleatorio() {
        String sql = "SELECT * FROM prestamo WHERE id_prestamo = ?";
        boolean codigoNoRepetido = false;
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();

        String codigo = "";
        while (codigoNoRepetido == false) {
            codigo = "";
            for (int i = 0; i < 15; i++) {
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

            default:
                error = "Ocurrió un inconveniente al procesar los datos.";
                break;
        }

        return error;
    }
}
