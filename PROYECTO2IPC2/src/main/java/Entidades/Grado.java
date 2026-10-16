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
public class Grado {
    
    private String nombreGrado;
    private String idGrado;
    private String idNivel;
    private String idCarrera;

    public Grado(String nombreGrado, String idGrado, String idNivel, String idCarrera) {
        this.nombreGrado = nombreGrado;
        this.idGrado = idGrado;
        this.idNivel = idNivel;
        this.idCarrera = idCarrera;
    }

    public String getNombreGrado() {
        return nombreGrado;
    }

    public void setNombreGrado(String nombreGrado) {
        this.nombreGrado = nombreGrado;
    }

    public String getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(String idGrado) {
        this.idGrado = idGrado;
    }

    public String getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(String idNivel) {
        this.idNivel = idNivel;
    }

    public String getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(String idCarrera) {
        this.idCarrera = idCarrera;
    }
    
public boolean gradoValido() {
    if (StringUtils.isBlank(nombreGrado) || StringUtils.isBlank(idNivel)) {
        return false;
    }

    if ("NIV_DIV".equalsIgnoreCase(idNivel)) {
        return StringUtils.isNotBlank(idCarrera);
    }

    return true;
}
     
    
}
