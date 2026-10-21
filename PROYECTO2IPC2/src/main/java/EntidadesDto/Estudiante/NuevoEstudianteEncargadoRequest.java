/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Estudiante;

/**
 *
 * @author mario
 */
public class NuevoEstudianteEncargadoRequest {
    private String idEstudiante;
    private String dpiEncargado;
    private String nombreEncargado;
    private String direccionEncargado;

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
        
}
