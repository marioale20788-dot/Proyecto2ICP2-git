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
public class NotaFinal {
    private String idNota;
    private Double notaFinal;
    private String idEstudiante;
    private String codigoCurso;
    private Integer ciclo;

    public NotaFinal(String idNota, Double notaFinal, String idEstudiante, String codigoCurso, Integer ciclo) {
        this.idNota = idNota;
        this.notaFinal = notaFinal;
        this.idEstudiante = idEstudiante;
        this.codigoCurso = codigoCurso;
        this.ciclo = ciclo;
    }
    
    public void crearId(){
        this.idNota = idEstudiante+"-"+codigoCurso+"-"+ciclo;
    }
    public boolean esValido(){
         return StringUtils.isNotBlank(idEstudiante)
                && StringUtils.isNotBlank(codigoCurso)
                && ciclo != null
                 &&notaFinal!=null
                 &&notaFinal>=0;
    }
    

    public String getIdNota() {
        return idNota;
    }

    public void setIdNota(String idNota) {
        this.idNota = idNota;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(String codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }
    
    
    
    
}
