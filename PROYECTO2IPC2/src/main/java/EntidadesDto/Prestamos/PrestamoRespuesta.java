/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Prestamos;

import Entidades.Prestamo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;

/**
 *
 * @author mario
 */
public class PrestamoRespuesta {

    private String idPrestamo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaPrestamo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaEsperadaEntrega;
    private double estimacionMora;
    private String idLibro;
    private int cantidad;

    public PrestamoRespuesta(Prestamo prestamo) {
        this.idPrestamo = prestamo.getIdPrestamo();
        this.fechaPrestamo = prestamo.getFechaPrestamo();
        this.fechaEsperadaEntrega = prestamo.getFechaEsperadaEntrega();
        this.estimacionMora = prestamo.getMora();
        this.idLibro = prestamo.getIdLibro();
        this.cantidad = prestamo.getCantidad();
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

    public void setFechaPrestamo(String LocalDate) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaEsperadaEntrega() {
        return fechaEsperadaEntrega;
    }

    public void setFechaEsperadaEntrega(LocalDate fechaEsperadaEntrega) {
        this.fechaEsperadaEntrega = fechaEsperadaEntrega;
    }

    public double getEstamcionMora() {
        return estimacionMora;
    }

    public void setEstamcionMora(double estamcionMora) {
        this.estimacionMora = estamcionMora;
    }

    public String getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(String idLibro) {
        this.idLibro = idLibro;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
