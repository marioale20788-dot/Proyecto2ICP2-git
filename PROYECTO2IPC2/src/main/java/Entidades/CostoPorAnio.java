/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.time.LocalDate;

/**
 *
 * @author mario
 */
public class CostoPorAnio {
    
    
    private String idCosto;
    private Double monto;
    private LocalDate fechaLimite;
    private LocalDate fechaPago;
    private boolean estado;
    private String idEstudiante;
    private Integer cicloLectivo;

    public CostoPorAnio(String idCosto, Double monto, LocalDate fechaLimite, LocalDate fechaPago, boolean estado, String idEstudiante, Integer cicloLectivo) {
        this.idCosto = idCosto;
        this.monto = monto;
        this.fechaLimite = fechaLimite;
        this.fechaPago = fechaPago;
        this.estado = estado;
        this.idEstudiante = idEstudiante;
        this.cicloLectivo = cicloLectivo;
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

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
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
