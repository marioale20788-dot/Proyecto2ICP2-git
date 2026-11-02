/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import EntidadesDto.Estudiante.UpdateEstudiante;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mario
 */
public class Estudiante {
    private String dpiUsuario;    
    private String nombreEstudiante;
    private Boolean graduado;

    public Estudiante(String dpiUsuario, String nombreEstudiante, Boolean graduado) {
        this.dpiUsuario = dpiUsuario;
        this.nombreEstudiante = nombreEstudiante;
        this.graduado = graduado;
    }
  
    public String getDpiUsuario() {
        return dpiUsuario;
    }

    public void setDpiUsuario(String dpiUsuario) {
        this.dpiUsuario = dpiUsuario;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public Boolean getGraduado() {
        return graduado;
    }

    public void setGraduado(Boolean graduado) {
        this.graduado = graduado;
    }

   
    public boolean esValido(){
               return StringUtils.isNotBlank(dpiUsuario)
                && StringUtils.isNotBlank(nombreEstudiante)
               &&graduado!=null;

    }
    public void updateEstudiante(UpdateEstudiante update){
        this.nombreEstudiante = update.getNombreEstudiante();
        this.graduado=update.getGraduado();
        
    }

    
    
}
