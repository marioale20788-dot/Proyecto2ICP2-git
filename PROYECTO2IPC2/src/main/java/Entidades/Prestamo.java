/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import EntidadesDto.Prestamos.PrestamoUpdateRequest;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mario
 */
public class Prestamo {

    private String idPrestamo;
    private LocalDate fechaPrestamo;
    private LocalDate fechaEsperadaEntrega;
    private LocalDate fechaEntregaReal;
    private Double mora;
    private PrestamoEnum estado;
    private String idLibro;
    private String idUsuario;
    private Integer cantidad;
    public Prestamo(){
        
    }

    public Prestamo(String idPrestamo, LocalDate fechaPrestamo, LocalDate fechaEsperadaEntrega, LocalDate fechaEntregaReal, Double mora, PrestamoEnum estado, String idLibro, String idUsuario) {
        this.idPrestamo = idPrestamo;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaEsperadaEntrega = fechaEsperadaEntrega;
        this.fechaEntregaReal = fechaEntregaReal;
        this.mora = mora;
        this.estado = estado;
        this.idLibro = idLibro;
        this.idUsuario = idUsuario;
    }
    
    public boolean prestamoEsValido(){
          return  StringUtils.isNotBlank(idPrestamo)
                  &&fechaPrestamo!=null
                  &&fechaEsperadaEntrega!=null
                && StringUtils.isNotBlank(idLibro)
                && StringUtils.isNotBlank(idUsuario)
                  &&cantidad!=null
                  &&cantidad>0
          &&fechasValidas();
       
    }
    public void updatePrestamo(PrestamoUpdateRequest update){
        this.fechaPrestamo = update.getFechaDePrestamo();
        this.fechaEsperadaEntrega = update.getFechaEsperadaEntrega();
        this.idLibro = update.getIdLibro();
        this.idUsuario = update.getIdUsuario();
        this.cantidad = update.getCantidad();
        
    }
    public boolean fechasValidas(){
        return !fechaPrestamo.isAfter(fechaEsperadaEntrega);
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    

    public String getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(String idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaEsperadaEntrega() {
        return fechaEsperadaEntrega;
    }

    public void setFechaEsperadaEntrega(LocalDate fechaEsperadaEntrega) {
        this.fechaEsperadaEntrega = fechaEsperadaEntrega;
    }

    public LocalDate getFechaEntregaReal() {
        return fechaEntregaReal;
    }

    public void setFechaEntregaReal(LocalDate fechaEntregaReal) {
        this.fechaEntregaReal = fechaEntregaReal;
    }

    public Double getMora() {
        return mora;
    }

    public void setMora(Double mora) {
        this.mora = mora;
    }

    public PrestamoEnum getEstado() {
        return estado;
    }

    public void setEstado(PrestamoEnum estado) {
        this.estado = estado;
    }

    public String getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(String idLibro) {
        this.idLibro = idLibro;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

}
