/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.AdministrarCurso;

/**
 *
 * @author mario
 */
public class GetNotas {
    
    private String idActividad;
    private String nombreActividad;
    private Double  punteActividad;
    private String punteoDefinido;
    private String idEstudiante;

    public GetNotas(String idActividad, String nombreActividad, Double punteActividad, String punteoDefinido, String idEstudiante) {
        this.idActividad = idActividad;
        this.nombreActividad = nombreActividad;
        this.punteActividad = punteActividad;
        this.punteoDefinido = punteoDefinido;
        this.idEstudiante = idEstudiante;
    }
    
    
    

    public String getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(String idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public Double getPunteActividad() {
        return punteActividad;
    }

    public void setPunteActividad(Double punteActividad) {
        this.punteActividad = punteActividad;
    }

    public String getPunteoDefinido() {
        return punteoDefinido;
    }

    public void setPunteoDefinido(String punteoDefinido) {
        this.punteoDefinido = punteoDefinido;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }
    
    
    
    
    
    
    
    
}
