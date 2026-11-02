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
 private String idAsignacion;
    private String idUsuario;
    private String idSeccion;
    private String idCurriculo;

    public NuevaAsingacionRespuesta(AsignacionDocente asignacion) {
        this.idAsignacion = asignacion.getIdAsginacion();
        this.idUsuario = asignacion.getIdMaestro();
        this.idSeccion = asignacion.getIdSeccion();
        this.idCurriculo = asignacion.getIdCurricuolo();
    }

    public String getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(String idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(String idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getIdCurriculo() {
        return idCurriculo;
    }

    public void setIdCurriculo(String idCurriculo) {
        this.idCurriculo = idCurriculo;
    }

    
    
    
    
}
