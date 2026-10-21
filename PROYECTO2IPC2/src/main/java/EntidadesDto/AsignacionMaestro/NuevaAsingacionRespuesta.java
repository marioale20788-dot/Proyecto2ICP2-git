/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.AsignacionMaestro;

import Entidades.AsignacionDocente;

/**
 *
 * @author mario
 */
public class NuevaAsingacionRespuesta {
      private String idAsginacion;
    private String idMaestro;
    private String idSeccion;
    private String idCurricuolo;

    public NuevaAsingacionRespuesta(AsignacionDocente asignacion) {
        this.idAsginacion = asignacion.getIdAsginacion();
        this.idMaestro = asignacion.getIdMaestro();
        this.idSeccion = asignacion.getIdSeccion();
        this.idCurricuolo = asignacion.getIdCurricuolo();
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
