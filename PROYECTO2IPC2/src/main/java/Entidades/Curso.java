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
public class Curso {

    private String codigoCurso;
    private String nombreCurso;
    private String descripcionCurso;
    private Boolean estado;

    public Curso() {

    }

    public Curso(String codigoCurso, String nombreCurso, String descripcionCurso, Boolean estado) {
        this.codigoCurso = codigoCurso;
        this.nombreCurso = nombreCurso;
        this.descripcionCurso = descripcionCurso;
        this.estado = estado;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(String codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public String getDescripcionCurso() {
        return descripcionCurso;
    }

    public void setDescripcionCurso(String descripcionCurso) {
        this.descripcionCurso = descripcionCurso;
    }

    public boolean cursoValido() {
        return StringUtils.isNotBlank(codigoCurso)
                && StringUtils.isNotBlank(nombreCurso)
                && StringUtils.isNotBlank(descripcionCurso)
                && estado != null;
    }

}
