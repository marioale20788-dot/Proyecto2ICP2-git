/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.Empleado;
import Entidades.TipoUsuarioEnum;
import Entidades.Usuario;
import EntidadesDAO.EmpleadoDB;
import EntidadesDAO.UsuarioDB;
import EntidadesDto.Empleado.EmpleadoUsuarioRespuesta;
import EntidadesDto.Empleado.NuevoUsuarioRequest;
import EntidadesDto.Empleado.UpdateEmpleadoRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import jakarta.data.repository.Update;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class CrudUsuario {

    public void crearUsuario(NuevoUsuarioRequest nuevoUsuario) throws DatosNoValidosExcepcion {

        UsuarioDB usuarioBaseDeDatos = new UsuarioDB();
        EmpleadoDB empleadoBaseDeDatos = new EmpleadoDB();
        Usuario usuario = new Usuario(nuevoUsuario.getDpiUsuario(),
                nuevoUsuario.getCorreoElectronico(), nuevoUsuario.getContrasena(),
                nuevoUsuario.getTipoUsuario(), true);
        if (usuario.datosValidosCrearUsuario() == false) {

            throw new DatosNoValidosExcepcion("DATOS NO VALIDOS");
        }
        Empleado empleado = new Empleado(usuario.getIdUsuario(), nuevoUsuario.getSalario(),
                nuevoUsuario.getNombreCompleto(), nuevoUsuario.getFechaDeContratacion());
        if (empleado.datosValidos() == false) {
            throw new DatosNoValidosExcepcion("DATOS NO VALIDOS");
        }
        usuarioBaseDeDatos.crearUsuario(usuario);
        empleadoBaseDeDatos.CrearEmpleado(usuario.getIdUsuario(), empleado);
    }

    public ArrayList<EmpleadoUsuarioRespuesta> buscarTodosUsuariosRespuesta(boolean categoriaSi, String categoria,boolean estado) throws DatosNoValidosExcepcion {
        UsuarioDB usuarioBaseDeDatos = new UsuarioDB();
        EmpleadoDB empleadoBaseDeDatos = new EmpleadoDB();
        ArrayList<EmpleadoUsuarioRespuesta> respuesta = new ArrayList<>();

        ArrayList<Usuario> usuarios = usuarioBaseDeDatos.obtenerTodosLosUsuarios(categoriaSi, categoria,estado);
        for (Usuario usuario : usuarios) {
            Empleado empleado = empleadoBaseDeDatos.obtenerEmpleadoPorId(usuario.getIdUsuario());
            EmpleadoUsuarioRespuesta respuestaEU = new EmpleadoUsuarioRespuesta(usuario, empleado);
            respuesta.add(respuestaEU);

        }
        return respuesta;

    }

    public EmpleadoUsuarioRespuesta buscarEmpleadoUsuarioPorId(String dpi) throws DatosNoValidosExcepcion, ElementoNoEncotradoExcepcion {
        UsuarioDB usuarioBaseDeDatos = new UsuarioDB();
        EmpleadoDB empleadoBaseDeDatos = new EmpleadoDB();

        ArrayList<Usuario> usuarios = usuarioBaseDeDatos.obtenerUsuarioPorDpi(dpi);
        if (usuarios.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("no se enoncotro ninguna coincidencia con el dpi");
        }
        Usuario usuario = usuarios.getFirst();
        Empleado empleado = empleadoBaseDeDatos.obtenerEmpleadoPorId(usuario.getIdUsuario());
        EmpleadoUsuarioRespuesta respuestaEU = new EmpleadoUsuarioRespuesta(usuario, empleado);
        return respuestaEU;
    }

    public EmpleadoUsuarioRespuesta actualizarUsuario(String dpi, UpdateEmpleadoRequest updateEmpleado) throws DatosNoValidosExcepcion, ElementoNoEncotradoExcepcion {
        UsuarioDB usuarioBaseDeDatos = new UsuarioDB();
        ArrayList<Usuario> usuario = usuarioBaseDeDatos.obtenerUsuarioPorDpi(dpi);
        if (usuario.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO UN USARUIO CON ESE DPI");
        }

        if (updateEmpleado.actualizarUsuario() == true) {
 
            Usuario usarioAEditar = usuario.getFirst();
            usarioAEditar.updateUsuario(updateEmpleado);
            if(usarioAEditar.datosValidosCrearUsuario()==false){
                   throw new DatosNoValidosExcepcion("DATOS NO VALIDOS");
            }
            usuarioBaseDeDatos.updateUsuario(usarioAEditar, dpi);
        } else {
            EmpleadoDB empleadoBaseDeDatos = new EmpleadoDB();
            Empleado empleadoAEditar = empleadoBaseDeDatos.obtenerEmpleadoPorId(dpi);
            empleadoAEditar.updateEmpleado(updateEmpleado);
            if(empleadoAEditar.datosValidos()==false){
                     throw new DatosNoValidosExcepcion("NO SE ENCONTRO UN USARUIO CON ESE DPI");
        
            }
            empleadoBaseDeDatos.updateEmpleado(dpi, empleadoAEditar);
            

        }
        return buscarEmpleadoUsuarioPorId(dpi);

    }

}
