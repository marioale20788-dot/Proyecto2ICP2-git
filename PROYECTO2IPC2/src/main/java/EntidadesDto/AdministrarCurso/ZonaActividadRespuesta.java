/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.AdministrarCurso;

import Entidades.ActividadZona;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;

/**
 *
 * @author mario
 */
public class ZonaActividadRespuesta {
    
    private String nombreActividad;
    private String idActividad;
    private Double ponderacion;
      @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaEntrega;
    private String idAsignacion;

    public ZonaActividadRespuesta(ActividadZona actividad) {
        this.nombreActividad = actividad.getNombreActividad();
        this.idActividad = actividad.getIdActividad();
        this.ponderacion = actividad.getPonderacion();
        this.fechaEntrega = actividad.getFechaEntrega();
        this.idAsignacion = actividad.getIdAsignacion();
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
