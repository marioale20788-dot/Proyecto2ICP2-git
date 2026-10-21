/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Seccion;

import Entidades.Seccion;

/**
 *
 * @author mario
 */
public class SeccionRespuesta {
     private String idSeccion;
    private String nombreSeccion;
    private String idGrado;

    public SeccionRespuesta(Seccion seccion) {
        this.idSeccion = seccion.getIdSeccion();
        this.nombreSeccion = seccion.getNombreSeccion();
        this.idGrado = seccion.getIdGrado();
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
}
