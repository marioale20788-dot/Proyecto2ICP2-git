/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto1.proyecto2ipc2.resources;

import Entidades.TipoUsuarioEnum;
import Entidades.Usuario;
import EntidadesDAO.UsuarioDB;
import EntidadesDeServicio.LoginService;

import EntidadesDto.Login.Jwt;
import EntidadesDto.Login.NewLogin;
import EntidadesDto.Login.RespuestaLogin;

import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author mario
 */
@Path("auth")
@RequestScoped
public class Login {

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(NewLogin solicitud) {
        if (solicitud == null || solicitud.getIdUsuario() == null || solicitud.getContrasena() == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("mensaje", "Solicitud incompleta"))
                    .build();
        }

        UsuarioDB usDb = new UsuarioDB();
        Usuario usuario = usDb.obtenerUsuarioLogin(solicitud);

        if (usuario == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(Map.of("mensaje", "Credenciales inválidas"))
                    .build();
        }

        Jwt servicioToken = new Jwt();
        String token = servicioToken.generar(usuario);
        
        return Response.ok(new RespuestaLogin(token)).build();
    }
}