/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.DatosMedicosEstudiante;
import Entidades.EncargadoEstudiante;
import Entidades.Estudiante;
import Entidades.TipoUsuarioEnum;
import Entidades.Usuario;
import EntidadesDAO.DatosMedicosEstudianteDB;
import EntidadesDAO.EncargadoEstudianteDB;
import EntidadesDAO.EstudianteDB;
import EntidadesDAO.UsuarioDB;
import EntidadesDto.Estudiante.EstudianteRespuesta;
import EntidadesDto.Estudiante.NuevoEstudianteDatosMedicosRequest;
import EntidadesDto.Estudiante.NuevoEstudianteEncargadoRequest;
import EntidadesDto.Estudiante.NuevoEstudianteRequest;
import EntidadesDto.Estudiante.UpdateEstudiante;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class CrudEstudiante {

    public void crearEstudiante(NuevoEstudianteRequest nuevoEstudiante,
            NuevoEstudianteEncargadoRequest encargado,
            NuevoEstudianteDatosMedicosRequest datosMedicos) throws DatosNoValidosExcepcion {

        Usuario usuario = new Usuario(nuevoEstudiante.getDpiUsuario(), nuevoEstudiante.getCorreoElectronico(), nuevoEstudiante.getContrasena(),
                TipoUsuarioEnum.Estudiante, true);
        if (usuario.datosValidosCrearUsuario() == false) {
            throw new DatosNoValidosExcepcion("DATOS INGRESADOS NO VALIDOS");

        }
        Estudiante estudiante = new Estudiante(usuario.getIdUsuario(), nuevoEstudiante.getNombreEstudiante());
        if (estudiante.esValido() == false) {
            throw new DatosNoValidosExcepcion("DATOS INGRESADOS NO VALIDOS");
        }
        EncargadoEstudiante encargadoEstudiante = new EncargadoEstudiante(encargado.getIdEstudiante(),
                encargado.getDpiEncargado(), encargado.getNombreEncargado(), encargado.getDireccionEncargado());
        if (encargadoEstudiante.esValido() == false) {
            throw new DatosNoValidosExcepcion("DATOS INGRESADOS NO VALIDOS");
        }
        DatosMedicosEstudiante datosMedicosEstdudiante = new DatosMedicosEstudiante(datosMedicos.getIdEstudiante(),
                datosMedicos.getAlergias(), datosMedicos.getEnfermedades(),
                datosMedicos.getTipoDeSangre(), datosMedicos.getMedicamentos(), datosMedicos.getContactoDeEmergencia());

        if (datosMedicosEstdudiante.esValido() == false) {
            throw new DatosNoValidosExcepcion("DATOS INGRESADOS NO VALIDOS");
        }

        UsuarioDB usuarioDB = new UsuarioDB();
        EstudianteDB estudianteDB = new EstudianteDB();
        EncargadoEstudianteDB encargadoDB = new EncargadoEstudianteDB();
        DatosMedicosEstudianteDB datosMedicosDb = new DatosMedicosEstudianteDB();
        usuarioDB.crearUsuario(usuario);
        estudianteDB.crearEstudiante(estudiante);
        encargadoDB.crearEncargadoEstudiante(encargadoEstudiante);
        datosMedicosDb.crearDatosMedicos(datosMedicosEstdudiante);

    }

    public ArrayList<EstudianteRespuesta> obtnerTodosLosEstudiantes(boolean estado) throws DatosNoValidosExcepcion {
        UsuarioDB usuarioBaseDeDatos = new UsuarioDB();

        ArrayList<EstudianteRespuesta> respuesta = new ArrayList<>();
        EstudianteDB estudianteDB = new EstudianteDB();
        EncargadoEstudianteDB encargadoDB = new EncargadoEstudianteDB();
        DatosMedicosEstudianteDB datosMedicosDb = new DatosMedicosEstudianteDB();
        ArrayList<Usuario> usuarios = usuarioBaseDeDatos.obtenerTodosLosUsuarios(true, "Estudiante", estado);
        for (Usuario usuario : usuarios) {
            Estudiante estudiante = estudianteDB.obtenerEstudiantePorId(usuario.getIdUsuario());
            EncargadoEstudiante encargado = encargadoDB.obtenerEncargadoPorDpiUs(usuario.getIdUsuario());
            DatosMedicosEstudiante datosMedicos = datosMedicosDb.obtenerDatosPorDpiUs(usuario.getIdUsuario());
            EstudianteRespuesta respuestaEstudiante = new EstudianteRespuesta(usuario, estudiante, encargado, datosMedicos);
            respuesta.add(respuestaEstudiante);

        }
        return respuesta;

    }

    public EstudianteRespuesta buscarEstudiantePorId(String id) throws DatosNoValidosExcepcion, ElementoNoEncotradoExcepcion {
        UsuarioDB usuarioBaseDeDatos = new UsuarioDB();
        EstudianteDB estudianteDB = new EstudianteDB();
        EncargadoEstudianteDB encargadoDB = new EncargadoEstudianteDB();
        DatosMedicosEstudianteDB datosMedicosDb = new DatosMedicosEstudianteDB();

        ArrayList<Usuario> usuarios = usuarioBaseDeDatos.obtenerUsuarioEstudianteTemmporalPorDpi(id);
        if (usuarios.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("no se enoncotro ninguna coincidencia con el dpi");
        }
        Usuario usuario = usuarios.getFirst();
        Estudiante estudiante = estudianteDB.obtenerEstudiantePorId(usuario.getIdUsuario());
        EncargadoEstudiante encargado = encargadoDB.obtenerEncargadoPorDpiUs(usuario.getIdUsuario());
        DatosMedicosEstudiante datosMedicos = datosMedicosDb.obtenerDatosPorDpiUs(usuario.getIdUsuario());
       
        EstudianteRespuesta respuesta = new EstudianteRespuesta(usuario, estudiante, encargado, datosMedicos);
        return respuesta;
    }

    public EstudianteRespuesta updateEstudiante(String id, UpdateEstudiante update) throws ElementoNoEncotradoExcepcion, DatosNoValidosExcepcion {

        UsuarioDB usuarioBaseDeDatos = new UsuarioDB();
        ArrayList<Usuario> usuarios = usuarioBaseDeDatos.obtenerUsuarioEstudianteTemmporalPorDpi(id);
        if (usuarios.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("no se enoncotro ninguna coincidencia con el dpi");
        }
       if(update.editarUsuario()){
         Usuario usuario = usuarios.getFirst();
         usuario.updateUsuarioEstudiante(update);
         if(usuario.datosValidosCrearUsuario()==false){
              throw new DatosNoValidosExcepcion("DATOS INGRESADOS NO VALIDOS");
         }
         usuarioBaseDeDatos.updateUsuario(usuario, id);
       }else  if(update.editarEstudiante()){
             EstudianteDB estudianteDB = new EstudianteDB();
             Estudiante estudiante = estudianteDB.obtenerEstudiantePorId(id);
             estudiante.updateEstudiante(update);
             if(estudiante.esValido()==false){
                   throw new DatosNoValidosExcepcion("DATOS INGRESADOS NO VALIDOS");
             }
             estudianteDB.updateEstudiante(estudiante,id);
           
       }else if(update.editarEncargado()){
            EncargadoEstudianteDB encargadoDB = new EncargadoEstudianteDB();
            EncargadoEstudiante encargado = encargadoDB.obtenerEncargadoPorDpiUs(id);
            encargado.updateEncargado(update);
            if(encargado.esValido()==false){
                  throw new DatosNoValidosExcepcion("DATOS INGRESADOS NO VALIDOS");
            }
            encargadoDB.updateEncargado(encargado,id);
           
       }else if(update.editarDatosMedicos()){
            DatosMedicosEstudianteDB datosMedicosDb = new DatosMedicosEstudianteDB();
            DatosMedicosEstudiante datosMedicos = datosMedicosDb.obtenerDatosPorDpiUs(id);
            datosMedicos.updateDatosMedicos(update);
            if(datosMedicos.esValido()==false){
                throw new DatosNoValidosExcepcion("DATOS INGRESADOS NO VALIDOS");   
            }
           datosMedicosDb.updateDatosMedicos(datosMedicos, id);
       }
       return buscarEstudiantePorId(id);

    }

}
