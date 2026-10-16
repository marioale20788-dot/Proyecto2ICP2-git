/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.Libro;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class LibroDB {

    public void crearLibro(Libro libro) throws DatosNoValidosExcepcion {
        String sql = "INSERT INTO libro(id_libro,titulo,autor,editorial,cantidad) values(?,?,?,?,?)";
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement insertStatement = conn.prepareStatement(sql);
            insertStatement.setString(1, libro.getIdLibro());
            insertStatement.setString(2, libro.getNombreLibro());
            insertStatement.setString(3, libro.getAutor());
            insertStatement.setString(4, libro.getEditorial());
            insertStatement.setInt(5, libro.getCantidad());
            insertStatement.executeUpdate();

        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }

    public ArrayList<Libro> devolverTodosLosLibros() {
        String sql = "SELECT * FROM libro WHERE activo = true";
        ArrayList<Libro> libros = new ArrayList<>();

        Connection conn;
        try {
            conn = DBConnection.getInstance().getConnection();
            PreparedStatement queryStatement = conn.prepareStatement(sql);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Libro libro = new Libro(
                        resultado.getString("id_libro"),
                        resultado.getString("titulo"),
                        resultado.getString("autor"),
                        resultado.getString("editorial"),
                        resultado.getInt("cantidad"));
                libros.add(libro);
            }
            return libros;
        } catch (SQLException ex) {
            return libros;

        }

    }

    public ArrayList<Libro> buscarLibroPorIdONombre(String valor) {

        String sqlId = "SELECT * FROM libro WHERE (id_libro = ? OR titulo = ?) AND activo = true;";
        Connection conn;
       ArrayList<Libro> libro = new ArrayList<>();
        try {
            conn = DBConnection.getInstance().getConnection();
            PreparedStatement queryStatement = conn.prepareStatement(sqlId);
            queryStatement.setString(1, valor);
            queryStatement.setString(2, valor);
            ResultSet resultadoBusquedaID = queryStatement.executeQuery();
            if (resultadoBusquedaID.next()) {
               Libro libroEntidad = new Libro(resultadoBusquedaID.getString("id_libro"),
                        resultadoBusquedaID.getString("titulo"),
                        resultadoBusquedaID.getString("autor"),
                        resultadoBusquedaID.getString("editorial"),
                        resultadoBusquedaID.getInt("cantidad"));

              libro.add(libroEntidad);
              return libro;
            }
        } catch (SQLException ex) {
            sqlExcepcion(ex);

        }
        return libro;

    }
    public Libro updateLibro(String idLIbro, Libro LibroConUpdates) throws DatosNoValidosExcepcion{
        String sql = "UPDATE libro SET titulo = ?, autor = ?, editorial = ?, cantidad = ? WHERE id_libro = ?";
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement updateStatement = conn.prepareStatement(sql);
            updateStatement.setString(1, LibroConUpdates.getNombreLibro());
            updateStatement.setString(2, LibroConUpdates.getAutor());
            updateStatement.setString(3, LibroConUpdates.getEditorial());
            updateStatement.setInt(4, LibroConUpdates.getCantidad());
            updateStatement.setString(5, idLIbro);
            int resultado = updateStatement.executeUpdate();
            return LibroConUpdates;
        } catch (SQLException ex) {
                throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }
        
    }
   
    public void eliminarLibro(String idLibro) throws ElementoNoEncotradoExcepcion{
        String sql = "UPDATE libro SET activo = false WHERE id_libro = ?";
         try {
            Connection conn = DBConnection.getInstance().getConnection();
            PreparedStatement updateStatement = conn.prepareStatement(sql);
            updateStatement.setString(1, idLibro);
            int resultado = updateStatement.executeUpdate();

        } catch (SQLException ex) {
                throw new ElementoNoEncotradoExcepcion(sqlExcepcion(ex));
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
