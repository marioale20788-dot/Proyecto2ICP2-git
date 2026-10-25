/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.AdministrarCurso;

/**
 *
 * @author mario
 */
public class NuevaNotaRequest {
    
    private String idEstudiante;
    private String idActividad;
    private Double punteo;

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public Double getPunteo() {
        return punteo;
    }

    public void setPunteo(Double punteo) {
        this.punteo = punteo;
    }
    
    
}
