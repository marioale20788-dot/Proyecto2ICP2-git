/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import EntidadesDto.Estudiante.UpdateEstudiante;
import jakarta.data.repository.Update;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mario
 */
public class EncargadoEstudiante {

    private String idEstudiante;
    private String dpiEncargado;
    private String nombreEncargado;
    private String direccionEncargado;

    public EncargadoEstudiante(String idEstudiante, String dpiEncargado, String nombreEncargado, String direccionEncargado) {
        this.idEstudiante = idEstudiante;
        this.dpiEncargado = dpiEncargado;
        this.nombreEncargado = nombreEncargado;
        this.direccionEncargado = direccionEncargado;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getDpiEncargado() {
        return dpiEncargado;
    }

    public void setDpiEncargado(String dpiEncargado) {
        this.dpiEncargado = dpiEncargado;
    }

    public String getNombreEncargado() {
        return nombreEncargado;
    }

    public void setNombreEncargado(String nombreEncargado) {
        this.nombreEncargado = nombreEncargado;
    }

    public String getDireccionEncargado() {
        return direccionEncargado;
    }

    public void setDireccionEncargado(String direccionEncargado) {
        this.direccionEncargado = direccionEncargado;
    }
    public void updateEncargado(UpdateEstudiante update){
        if(update.getNombreEncargado()!=null){
            this.nombreEncargado = update.getNombreEncargado();
        }
        if(update.getDireccion()!=null){
            this.direccionEncargado = update.getDireccion();
        }
        
    }

    public boolean esValido() {
        return StringUtils.isNotBlank(idEstudiante)
                && StringUtils.isNotBlank(nombreEncargado)
                && StringUtils.isNotBlank(direccionEncargado)
                && StringUtils.isNotBlank(dpiEncargado);

    }

}
