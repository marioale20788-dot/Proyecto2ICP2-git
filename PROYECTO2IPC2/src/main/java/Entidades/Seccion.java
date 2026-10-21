/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import EntidadesDto.Seccion.NuevaSeccionRequest;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mario
 */
public class Seccion {
    private String idSeccion;
    private String nombreSeccion;
    private String idGrado;


  

    public Seccion(String idSeccion, String nombreSeccion, String idGrado) {
        this.idSeccion = idSeccion;
        this.nombreSeccion = nombreSeccion;
        this.idGrado = idGrado;
    }

    public String getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(String idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getNombreSeccion() {
        return nombreSeccion;
    }

    public void setNombreSeccion(String nombreSeccion) {
        this.nombreSeccion = nombreSeccion;
    }

    public String getIdGrado() {
        return idGrado; 
    }

    public void setIdGrado(String idGrado) {
        this.idGrado = idGrado;
    }
    
    public boolean esValido(){
        return StringUtils.isNotBlank(idSeccion)
                && StringUtils.isNotBlank(nombreSeccion)
                && StringUtils.isNotBlank(idGrado);

    }
    public void editarSeccion(NuevaSeccionRequest update){
        if(update.getIdGrado()!=null){
            this.idGrado = update.getIdGrado();
        }
        if(update.getNombreSeccion()!=null){
            this.nombreSeccion = update.getNombreSeccion();
        }
        
    }
    
    
    
    
    
}
