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
public class inscripcion {

    private String idInscripcion;
    private LocalDate fechaInscripcion;
    private String idEstudiante;
    private String idSeccion;
    private Integer idCicloLectivo;

    public inscripcion(LocalDate fechaInscripcion, String idEstudiante, String idSeccion, Integer idCicloLectivo) {

        this.fechaInscripcion = fechaInscripcion;
        this.idEstudiante = idEstudiante;
        this.idSeccion = idSeccion;
        this.idCicloLectivo = idCicloLectivo;
    }

    public void crearIdInscripcion() {
        this.idInscripcion = idEstudiante + idCicloLectivo;
    }

    public boolean esValido() {
        return fechaInscripcion != null
                && StringUtils.isNotBlank(idEstudiante)
                && StringUtils.isNotBlank(idSeccion)
                && idCicloLectivo != null
        && fechaInscripcion.getYear() ==  idCicloLectivo;
    }

    public String getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(String idInscripcion) {
        this.idInscripcion = idInscripcion;
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

    public Integer getIdCicloLectivo() {
        return idCicloLectivo;
    }

    public void setIdCicloLectivo(Integer idCicloLectivo) {
        this.idCicloLectivo = idCicloLectivo;
    }

}
