/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDAO;

import Entidades.TipoUsuarioEnum;
import Entidades.Usuario;
import EntidadesDto.Login.NewLogin;
import Excepciones.DatosNoValidosExcepcion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;

/**
 *
 * @author mario
 */
public class UsuarioDB {

    public void crearUsuario(Usuario usuario) throws DatosNoValidosExcepcion {
        String sql = "INSERT INTO usuarios(id_usuario,correo_electronico,contrasena,rol,estado) values(?,?,?,?,?)";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement insertStatement = conn.prepareStatement(sql);) {

            insertStatement.setString(1, usuario.getIdUsuario());
            insertStatement.setString(2, usuario.getCorreoElectronico());
            insertStatement.setString(3, Base64.getEncoder().encodeToString(usuario.getContrasena().getBytes()));
            insertStatement.setString(4, usuario.getTipo().toString());
            insertStatement.setBoolean(5, usuario.getEstado());
            insertStatement.execute();

        } catch (SQLException ex) {

            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }

    }

    public void updateUsuario(Usuario usuario, String dpi) throws DatosNoValidosExcepcion {
        String sql = "UPDATE usuarios SET correo_electronico = ?,estado = ? WHERE id_usuario = ? ";
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement updateStatement = conn.prepareStatement(sql);) {

            updateStatement.setString(1, usuario.getCorreoElectronico());
            updateStatement.setBoolean(2, usuario.getEstado());
            updateStatement.setString(3, dpi);
            updateStatement.execute();
        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }
    }

    public ArrayList<Usuario> obtenerTodosLosUsuarios(boolean categoria, String tipoDeUsuario, boolean estado, int pagina) throws DatosNoValidosExcepcion {
        int limite = 10;
        int offset = (pagina - 1) * limite;

        String sql = "SELECT * FROM usuarios WHERE estado = ?";
        if (categoria == true) {
            sql = sql + " AND rol = ?";
        }

        sql = sql + " ORDER BY id_usuario ASC LIMIT ? OFFSET ?";

        ArrayList<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            int index = 1;
            queryStatement.setBoolean(index++, estado);

            if (categoria) {
                queryStatement.setString(index++, tipoDeUsuario);
            }

            queryStatement.setInt(index++, limite);
            queryStatement.setInt(index++, offset);

            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Usuario usuario = new Usuario(
                        resultado.getString("id_usuario"),
                        resultado.getString("correo_electronico"),
                        resultado.getString("contrasena"),
                        TipoUsuarioEnum.valueOf(resultado.getString("rol")),
                        resultado.getBoolean("estado"));
                usuarios.add(usuario);
            }
            return usuarios;

        } catch (SQLException ex) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(ex));
        }
    }

    public ArrayList<Usuario> obtenerUsuarioPorDpi(String dpi) throws DatosNoValidosExcepcion {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ?";
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, dpi);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Usuario usuario = new Usuario(
                        resultado.getString("id_usuario"),
                        resultado.getString("correo_electronico"),
                        resultado.getString("contrasena"),
                        TipoUsuarioEnum.valueOf(resultado.getString("rol")),
                        resultado.getBoolean("estado"));
                usuarios.add(usuario);

            }
            return usuarios;

        } catch (SQLException e) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(e));
        }
    }

    public ArrayList<Usuario> obtenerUsuarioEstudianteTemmporalPorDpi(String dpi) throws DatosNoValidosExcepcion {
        String sql = "SELECT * FROM usuarios WHERE id_usuario = ? AND rol = 'Estudiante'";
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try (Connection conn = DBConnection.getInstance().getConnection(); PreparedStatement queryStatement = conn.prepareStatement(sql);) {
            queryStatement.setString(1, dpi);
            ResultSet resultado = queryStatement.executeQuery();
            while (resultado.next()) {
                Usuario usuario = new Usuario(
                        resultado.getString("id_usuario"),
                        resultado.getString("correo_electronico"),
                        resultado.getString("contrasena"),
                        TipoUsuarioEnum.valueOf(resultado.getString("rol")),
                        resultado.getBoolean("estado"));
                usuarios.add(usuario);

            }
            return usuarios;

        } catch (SQLException e) {
            throw new DatosNoValidosExcepcion(sqlExcepcion(e));
        }
    }
    
 public Usuario obtenerUsuarioLogin(NewLogin login){
    String contrasena = Base64.getEncoder().encodeToString(login.getContrasena().getBytes());
    String sql = "SELECT * FROM usuarios WHERE id_usuario = ? AND contrasena = ? AND estado = true";
    Usuario us = null;

    try (Connection conn = DBConnection.getInstance().getConnection(); 
         PreparedStatement queryStatement = conn.prepareStatement(sql);) {

        queryStatement.setString(1, login.getIdUsuario());
        queryStatement.setString(2, contrasena);
        ResultSet resultado = queryStatement.executeQuery();

        while (resultado.next()) {
            us = new Usuario(
                resultado.getString("id_usuario"),
                resultado.getString("correo_electronico"),
                contrasena,
                TipoUsuarioEnum.valueOf(resultado.getString("rol")),
                resultado.getBoolean("estado")
            );
        }
        return us;

    } catch (SQLException e) {
        return us;
    }
}
    
    public boolean esUtimoSuperAdmin(){
        
          String sql = "SELECT * FROM usuarios WHERE rol = 'Super_Admin' AND estado = true";
        try (    Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement queryStatement = conn.prepareStatement(sql);){
            ResultSet resultado = queryStatement.executeQuery();
            int ultimo=0;
            while(resultado.next()){
                ultimo++;
                if(ultimo>1){
                    return false;
                }
            }

                return true;
            
        
        } catch (SQLException ex) {
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

            default:
                error = "Ocurrió un inconveniente al procesar los datos.";
                break;
        }

        return error;
    }

}
