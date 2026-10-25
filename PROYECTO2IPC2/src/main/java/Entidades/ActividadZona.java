/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import EntidadesDto.AdministrarCurso.UpdateActividadRequest;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mario
 */
public class ActividadZona {

    private String nombreActividad;
    private String idActividad;
    private Double ponderacion;
    private LocalDate fechaEntrega;
    private String idAsignacion;

    public ActividadZona(String nombreActividad, Double ponderacion, LocalDate fechaEntrega, String idAsignacion) {
        this.nombreActividad = nombreActividad;
        this.ponderacion = ponderacion;
        this.fechaEntrega = fechaEntrega;
        this.idAsignacion = idAsignacion;
    }

    public boolean esValido() {
        return fechaEntrega != null
                && StringUtils.isNotBlank(nombreActividad)
                && StringUtils.isNotBlank(idAsignacion)
                && ponderacion != null
                && ponderacion > 0;
    }
    public void updateActividad(UpdateActividadRequest update){
        this.nombreActividad = update.getNombre();
        this.ponderacion = update.getPonderacion();
        this.fechaEntrega = update.getFechaEntrega();
    }
    
 
    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public Double getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(Double ponderacion) {
        this.ponderacion = ponderacion;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(String idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

}
