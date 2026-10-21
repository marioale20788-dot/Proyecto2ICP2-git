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
public class Curriculo {
    private String idCurriculo;
    private String idGrado;
    private String codigoCurso;
    private Integer cicloLectivo;

    public Curriculo( String idGrado, String codigoCurso, Integer cicloLectivo) {
        
        this.idGrado = idGrado;
        this.codigoCurso = codigoCurso;
        this.cicloLectivo = cicloLectivo;
    }
    public void crearIdCurriculo(){
        this.idCurriculo = idGrado+"-"+codigoCurso+"-"+cicloLectivo;
    }

    public String getIdCurriculo() {
        return idCurriculo;
    }

    public void setIdCurriculo(String idCurriculo) {
        this.idCurriculo = idCurriculo;
    }

    public String getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(String idGrado) {
        this.idGrado = idGrado;
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(String codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public Integer getCicloLectivo() {
        return cicloLectivo;
    }

    public void setCicloLectivo(Integer cicloLectivo) {
        this.cicloLectivo = cicloLectivo;
    }
    public boolean esValido(){
      return StringUtils.isNotBlank(codigoCurso)
                && StringUtils.isNotBlank(idGrado)
                && cicloLectivo!=null;

    
    }
    
    
    
    
    
}
