/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Pagos;

import Entidades.CostoPorAnio;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;

/**
 *
 * @author mario
 */
public class GetCostosPorAnioEstudianteRequest {
      private String idCosto;
    private Double monto;
      @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaLimite;
    private boolean estado;
    private String idEstudiante;
    private Integer cicloLectivo;

    public GetCostosPorAnioEstudianteRequest(CostoPorAnio costo) {
        this.idCosto = costo.getIdCosto();
        this.monto = costo.getMonto();
        this.fechaLimite = costo.getFechaLimite();
        this.estado = costo.getEstado();
        this.idEstudiante = costo.getIdEstudiante();
        this.cicloLectivo = costo.getCicloLectivo();
    }
    
    
    

    public String getIdCosto() {
        return idCosto;
    }

    public void setIdCosto(String idCosto) {
        this.idCosto = idCosto;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(String idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Integer getCicloLectivo() {
        return cicloLectivo;
    }

    public void setCicloLectivo(Integer cicloLectivo) {
        this.cicloLectivo = cicloLectivo;
    }
    
    
    
}


