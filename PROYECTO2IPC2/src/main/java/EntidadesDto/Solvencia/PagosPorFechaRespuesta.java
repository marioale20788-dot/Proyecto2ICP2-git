/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Solvencia;

import Entidades.EnumPago;
import Entidades.Pagos;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;

/**
 *
 * @author mario
 */
public class PagosPorFechaRespuesta {

    private String idPago;
    private EnumPago tipoPago;
    private Double monto;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaPago;
    private String idEstuidante;
    private String idSecretaria;
    private String idCosto;
    private String idPrestamo;

    public PagosPorFechaRespuesta(Pagos pago) {
        this.idPago = pago.getIdPago();
        this.tipoPago = pago.getTipoPago();
        this.monto = pago.getMonto();
        this.fechaPago = pago.getFechaPago();
        this.idEstuidante = pago.getIdEstuidante();
        this.idSecretaria = pago.getIdSecretaria();
        this.idCosto = pago.getIdCosto();
        this.idPrestamo = pago.getIdPrestamo();
    }
    
    
    

    public String getIdPago() {
        return idPago;
    }

    public void setIdPago(String idPago) {
        this.idPago = idPago;
    }

    public EnumPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(EnumPago tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getIdEstuidante() {
        return idEstuidante;
    }

    public void setIdEstuidante(String idEstuidante) {
        this.idEstuidante = idEstuidante;
    }

    public String getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(String idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public String getIdCosto() {
        return idCosto;
    }

    public void setIdCosto(String idCosto) {
        this.idCosto = idCosto;
    }

    public String getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

}
