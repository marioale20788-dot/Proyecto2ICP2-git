/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Reportes;

/**
 *
 * @author mario
 */
public class GetAlumnosSolventesMorosos {
    
   private String idEstudiante;
    private String nombreEstudiante;
    private String estado;
    private String deudasPendiantes;
    private Double totalDeudas;

    public GetAlumnosSolventesMorosos(String idEstudiante, String nombreEstudiante, String estado, String deudasPendiantes, Double totalDeudas) {
        this.idEstudiante = idEstudiante;
        this.nombreEstudiante = nombreEstudiante;
        this.estado = estado;
        this.deudasPendiantes = deudasPendiantes;
        this.totalDeudas = totalDeudas;
    }

    
    
    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    
    public String getDeudasPendiantes() {
        return deudasPendiantes;
    }

    public void setDeudasPendiantes(String deudasPendiantes) {
        this.deudasPendiantes = deudasPendiantes;
    }

    public Double getTotalDeudas() {
        return totalDeudas;
    }

    public void setTotalDeudas(Double totalDeudas) {
        this.totalDeudas = totalDeudas;
    }
    
    
    
    
}
