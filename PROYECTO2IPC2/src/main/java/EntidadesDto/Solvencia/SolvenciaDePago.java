/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Solvencia;

/**
 *
 * @author mario
 */
public class SolvenciaDePago {
    
    
    
    private String nombreEstudiante;
    private String idEstudiante;
    private String fechaEmision;

    public SolvenciaDePago(String nombreEstudiante, String idEstudiante, String fechaEmision) {
        this.nombreEstudiante = nombreEstudiante;
        this.idEstudiante = idEstudiante;
        this.fechaEmision = fechaEmision;
    }
    
    
    
    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    
}
