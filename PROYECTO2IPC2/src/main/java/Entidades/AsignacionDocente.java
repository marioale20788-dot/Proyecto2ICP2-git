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
public class AsignacionDocente {
    
    private String idAsginacion;
    private String idMaestro;
    private String idSeccion;
    private String idCurricuolo;

    public AsignacionDocente(String idMaestro, String idSeccion, String idCurricuolo) {
      
        this.idMaestro = idMaestro;
        this.idSeccion = idSeccion;
        this.idCurricuolo = idCurricuolo;
    }
    
    public void crearId(){
        this.idAsginacion =idSeccion+ "-"+ idCurricuolo;
    }
    public boolean esValido(){
         return StringUtils.isNotBlank(idMaestro)
                && StringUtils.isNotBlank(idSeccion)
                && StringUtils.isNotBlank(idCurricuolo);

    }
    
    
    
    public String getIdAsginacion() {
        return idAsginacion;
    }

    public void setIdAsginacion(String idAsginacion) {
        this.idAsginacion = idAsginacion;
    }

    public String getIdMaestro() {
        return idMaestro;
    }

    public void setIdMaestro(String idMaestro) {
        this.idMaestro = idMaestro;
    }

    public String getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(String idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getIdCurricuolo() {
        return idCurricuolo;
    }

    public void setIdCurricuolo(String idCurricuolo) {
        this.idCurricuolo = idCurricuolo;
    }
    
    
    
    
}
