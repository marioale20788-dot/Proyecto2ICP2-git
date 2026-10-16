/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Grado;

import Entidades.Grado;

/**
 *
 * @author mario
 */
public class GradoRespuesta {
    
        private String nombreGrado;
    private String idGrado;
    private String idNivel;
    private String idCarrera;

    public GradoRespuesta(){
        
    }
    public GradoRespuesta(Grado grado) {
        this.nombreGrado = grado.getNombreGrado();
        this.idGrado = grado.getIdGrado();
        this.idNivel = grado.getIdNivel();
        this.idCarrera = grado.getIdCarrera();
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
    
    
    
    
    
}
