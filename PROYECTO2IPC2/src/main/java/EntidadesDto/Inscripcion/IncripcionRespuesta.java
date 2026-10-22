/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Inscripcion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;

/**
 *
 * @author mario
 */
public class IncripcionRespuesta {
    
    private String idIncripcion;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaInscripcion;
    
    private String idEstudiante;
    private String idSeccion;
    private String descripcionSeccion;
    private Integer cicloLectivo;

    public IncripcionRespuesta(String idIncripcion, LocalDate fechaInscripcion, String idEstudiante, String idSeccion, String descripcionSeccion, Integer cicloLectivo) {
        this.idIncripcion = idIncripcion;
        this.fechaInscripcion = fechaInscripcion;
        this.idEstudiante = idEstudiante;
        this.idSeccion = idSeccion;
        this.descripcionSeccion = descripcionSeccion;
        this.cicloLectivo = cicloLectivo;
    }
    
    
    

    public String getIdIncripcion() {
        return idIncripcion;
    }

    public void setIdIncripcion(String idIncripcion) {
        this.idIncripcion = idIncripcion;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(String idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getDescripcionSeccion() {
        return descripcionSeccion;
    }

    public void setDescripcionSeccion(String descripcionSeccion) {
        this.descripcionSeccion = descripcionSeccion;
    }

    public Integer getCicloLectivo() {
        return cicloLectivo;
    }

    public void setCicloLectivo(Integer cicloLectivo) {
        this.cicloLectivo = cicloLectivo;
    }
    
    
    
    
}
