/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mario
 */
public class NotaActividad {
    private String idNota;
    private Double punteoObtenido;
    private String idActividad;
    private String idEstudiante;

    public NotaActividad(Double punteoObtenido, String idActividad, String idEstudiante) {
        this.punteoObtenido = punteoObtenido;
        this.idActividad = idActividad;
        this.idEstudiante = idEstudiante;
    }
    public NotaActividad(){
        
    }
    
   public boolean esValido(){
        return  StringUtils.isNotBlank(idActividad)
                && StringUtils.isNotBlank(idEstudiante)
                && punteoObtenido != null
        && punteoObtenido>=0;
   }
    
    
    public void generarId(){
        this.idNota = idActividad+"-"+idEstudiante;
    }

    public String getIdNota() {
        return idNota;
    }

    public void setIdNota(String idNota) {
        this.idNota = idNota;
    }

    public Double getPunteoObtenido() {
        return punteoObtenido;
    }

    public void setPunteoObtenido(Double punteoObtenido) {
        this.punteoObtenido = punteoObtenido;
    }

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
    
    
    
    
    
    
}
