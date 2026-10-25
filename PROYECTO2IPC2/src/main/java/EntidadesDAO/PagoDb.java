/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.EnumPago;
import Entidades.Pagos;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
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
public class PagoDb {

    public void registrarPago(Pagos pagoARegistrar) throws DatosNoValidosExcepcion, ElementoNoEncotradoExcepcion {

        String sql = "INSERT INTO pagos(id_pago, tipo, monto, fecha_de_pago, id_estudiante, id_secretaria, id_costo, id_prestamo) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getInstance().getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement insertStatement = conn.prepareStatement(sql)) {

                insertStatement.setString(1, pagoARegistrar.getIdPago());
                insertStatement.setString(2, pagoARegistrar.getTipoPago().toString());
                insertStatement.setDouble(3, pagoARegistrar.getMonto());
                insertStatement.setDate(4, Date.valueOf(pagoARegistrar.getFechaPago()));
                insertStatement.setString(5, pagoARegistrar.getIdEstuidante());
                insertStatement.setString(6, pagoARegistrar.getIdSecretaria());
                insertStatement.setString(7, pagoARegistrar.getIdCosto());
                insertStatement.setString(8, pagoARegistrar.getIdPrestamo());

                insertStatement.executeUpdate();

                if (pagoARegistrar.getIdCosto() != null) {
                    CostoPorAnioDB costoDb = new CostoPorAnioDB();
                    boolean costoRegistrado = costoDb.pagarCosto(conn, pagoARegistrar.getIdCosto(), pagoARegistrar.getMonto(), pagoARegistrar.getIdEstuidante());

                    if (!costoRegistrado) {
                        conn.rollback();
                        throw new DatosNoValidosExcepcion("NO SE PUDO REALIZAR EL PAGO");
                    }
                }

                if (pagoARegistrar.getIdPrestamo() != null) {
                    PrestamoDB prestamoDb = new PrestamoDB();
                    boolean prestamoRegistrado = prestamoDb.pagarPrestamo(conn, pagoARegistrar.getIdPrestamo(), pagoARegistrar.getMonto(), pagoARegistrar.getIdEstuidante());

                    if (!prestamoRegistrado) {
                        conn.rollback();
                        throw new DatosNoValidosExcepcion("NO SE PUDO REALIZAR EL PAGO");
                    }
                }

                conn.commit();

            } catch (SQLException e) {
                conn.rollback();

            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion("OCURRIO UN ERROR");
        }
    }

    public ArrayList<Pagos> obtenerPagoPorId(String id) {
        String sql = "SELECT * FROM pagos WHERE id_pago = ?";
        ArrayList<Pagos> pago = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, id);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Pagos pagoId = new Pagos(EnumPago.valueOf(resultado.getString("tipo")),
                        resultado.getDouble("monto"),
                        resultado.getDate("fecha_de_pago").toLocalDate(),
                        resultado.getString("id_estudiante"),
                        resultado.getString("id_secretaria"),
                        resultado.getString("id_costo"),
                        resultado.getString("id_prestamo"));
                pagoId.setIdPago(resultado.getString("id_pago"));
                pago.add(pagoId);
            }
            return pago;

        } catch (SQLException ex) {
            return pago;
        }

    }

    public ArrayList<Pagos> obtenerPagoPorUsuarioAnio(String idUsuario, LocalDate fechaInicio, LocalDate fechaFinal) {
        String sql = "SELECT * FROM pagos WHERE id_estudiante = ? AND (fecha_de_pago BETWEEN ? AND ?)";
        ArrayList<Pagos> pagos = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, idUsuario);
            queryStatement.setDate(2, Date.valueOf(fechaInicio));
            queryStatement.setDate(3, Date.valueOf(fechaFinal));
               ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Pagos pagoId = new Pagos(EnumPago.valueOf(resultado.getString("tipo")),
                        resultado.getDouble("monto"),
                        resultado.getDate("fecha_de_pago").toLocalDate(),
                        resultado.getString("id_estudiante"),
                        resultado.getString("id_secretaria"),
                        resultado.getString("id_costo"),
                        resultado.getString("id_prestamo"));
                pagoId.setIdPago(resultado.getString("id_pago"));
                pagos.add(pagoId);
            }
            return pagos;
            

        } catch (SQLException ex) {
           return pagos;
        }

    }

    public String generarIdPagoAleatorio() {
        String sql = "SELECT * FROM pagos WHERE id_pago = ?";
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
