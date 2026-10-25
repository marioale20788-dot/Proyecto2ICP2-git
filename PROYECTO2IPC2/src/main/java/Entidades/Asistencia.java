/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mario
 */
public class Asistencia {
    
    private String idAsistencia;
      private LocalDate fecchaAsistencia;
    private Boolean estado;
    private String idEstudiante;
    private String idAsignacion;

    public Asistencia(String idAsistencia, LocalDate fecchaAsistencia, Boolean estado, String idEstudiante, String idAsignacion) {
        this.idAsistencia = idAsistencia;
        this.fecchaAsistencia = fecchaAsistencia;
        this.estado = estado;
        this.idEstudiante = idEstudiante;
        this.idAsignacion = idAsignacion;
    }
    public boolean  esValido(){
           return  StringUtils.isNotBlank(idEstudiante)
                   &&fecchaAsistencia!=null
                && estado!=null
                &&  StringUtils.isNotBlank(idEstudiante)
          &&  StringUtils.isNotBlank(idAsignacion);
    }

    
    public void crearid(){
        this.idAsistencia = fecchaAsistencia.toString()+"-"+idEstudiante+"-"+idAsignacion;
    }
    
    public String getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(String idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public LocalDate getFecchaAsistencia() {
        return fecchaAsistencia;
    }

    public void setFecchaAsistencia(LocalDate fecchaAsistencia) {
        this.fecchaAsistencia = fecchaAsistencia;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(String idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    
    
}
