/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Solvencia;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;

/**
 *
 * @author mario
 */
public class GetCostosParaGenerarSolvencia {
    
    
    private String idCosto;
    private Double monto;
    private boolean estado;
    private String estadoDePago;
    private int numero;
    
       @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate  fechaLimite;

    public GetCostosParaGenerarSolvencia(String idCosto, Double monto, boolean estado, String estadoDePago, LocalDate fechaLimite, int numero) {
        this.idCosto = idCosto;
        this.monto = monto;
        this.estado = estado;
        this.estadoDePago = estadoDePago;
        this.fechaLimite = fechaLimite;
        this.numero = numero;    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getEstadoDePago() {
        return estadoDePago;
    }

    public void setEstadoDePago(String estadoDePago) {
        this.estadoDePago = estadoDePago;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
    
    
    
    
    
}
