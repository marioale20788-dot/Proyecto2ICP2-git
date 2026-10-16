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
public class CicloLectivo {

    private int anio;
    private LocalDate fechaInicio;
    private LocalDate fechaFinal;
    private boolean estado;

    public CicloLectivo(int anio, LocalDate fechaInicio, LocalDate fechaFinal, boolean estado) {
        this.anio = anio;
        this.fechaInicio = fechaInicio;
        this.fechaFinal = fechaFinal;
        this.estado = estado;
        
    }
    
    
    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    public boolean datosValidos(){
          if(anioCorrecto()&&fechasCorrectas()){
              return true;
          }
        return false;
    }
    
    public boolean fechasCorrectas(){
        if(fechaFinal.isAfter(fechaInicio)){
            return true;
        }
        return false;
        
    }
    public boolean anioCorrecto(){
        if(fechaInicio.getYear()==fechaFinal.getYear() &&fechaInicio.getYear()==anio){
            return true;
        }
        return false;
    }
  
    
    
}
