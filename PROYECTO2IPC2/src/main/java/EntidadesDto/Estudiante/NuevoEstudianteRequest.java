/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Estudiante;

import Entidades.TipoUsuarioEnum;

/**
 *
 * @author mario
 */
public class NuevoEstudianteRequest {
    
     private String dpiUsuario;    
    private String contrasena;
    private String correoElectronico;
    private String nombreEstudiante;
    private boolean graduado;

    public String getDpiUsuario() {
        return dpiUsuario;
    }

    public boolean getGraduado() {
        return graduado;
    }

    public void setGraduado(boolean graduado) {
        this.graduado = graduado;
    }
    
    

    public void setDpiUsuario(String dpiUsuario) {
        this.dpiUsuario = dpiUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    
    
    
    
}
