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

    public Estudiante(String dpiUsuario, String nombreEstudiante) {
        this.dpiUsuario = dpiUsuario;
        this.nombreEstudiante = nombreEstudiante;
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

   
    public boolean esValido(){
               return StringUtils.isNotBlank(dpiUsuario)
                && StringUtils.isNotBlank(nombreEstudiante);

    }
    public void updateEstudiante(UpdateEstudiante update){
        this.nombreEstudiante = update.getNombreEstudiante();
        
    }

    
    
}
