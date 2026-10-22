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
public class NewIncricpionRequest { 
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaInscripcion;
    
    private String idEstudiante;
    private String idSeccion;
    private Integer idCicloLectivo;

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

    public Integer getIdCicloLectivo() {
        return idCicloLectivo;
    }

    public void setIdCicloLectivo(Integer idCicloLectivo) {
        this.idCicloLectivo = idCicloLectivo;
    }
    
    
    
    
    
}
