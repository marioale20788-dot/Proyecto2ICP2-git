/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import EntidadesDto.Empleado.UpdateEmpleadoRequest;
import EntidadesDto.Estudiante.UpdateEstudiante;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mario
 */
public class Usuario {
    private String idUsuario;
    private String correoElectronico;
    private String contrasena;
    private TipoUsuarioEnum tipo;
    private Boolean estado;

    public Usuario(String idUsuario, String correoElectronico, String contrasena, TipoUsuarioEnum tipo, Boolean estado) {
        this.idUsuario = idUsuario;

        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.tipo = tipo;
        this.estado = estado;
    }
    public void updateUsuario(UpdateEmpleadoRequest update){
        if(update.getCorreoElectronico()!=null){
            this.correoElectronico = update.getCorreoElectronico();
        }
        if(update.getEstado()!=null){
            this.estado=update.getEstado();
        }
        
        
    }
     public void updateUsuarioEstudiante(UpdateEstudiante update){
        if(update.getCorreoElectronico()!=null){
            this.correoElectronico = update.getCorreoElectronico();
        }
        if(update.getEstado()!=null){
            this.estado=update.getEstado();
        }
        
        
    }
    

    
    
    
    
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }



    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public TipoUsuarioEnum getTipo() {
        return tipo;
    }

    public void setTipo(TipoUsuarioEnum tipo) {
        this.tipo = tipo;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }
    
    public boolean datosValidosCrearUsuario(){
        return StringUtils.isNotBlank(correoElectronico)
                && StringUtils.isNotBlank(idUsuario)
                && StringUtils.isNotBlank(contrasena)
                && tipo != null;
 
    }
    
    
}
