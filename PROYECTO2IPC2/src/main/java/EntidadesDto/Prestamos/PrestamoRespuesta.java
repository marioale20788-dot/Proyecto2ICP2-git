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
    private LocalDate fechaDeEntregaEsperada;
    private double estimacionMora;
    private String idLibro;
    private int cantidad;
    private String idUsuario;

    public PrestamoRespuesta(Prestamo prestamo) {
        this.idPrestamo = prestamo.getIdPrestamo();
        this.fechaPrestamo = prestamo.getFechaPrestamo();
        this.fechaDeEntregaEsperada = prestamo.getFechaEsperadaEntrega();
        this.estimacionMora = prestamo.getMora();
        this.idLibro = prestamo.getIdLibro();
        this.cantidad = prestamo.getCantidad();
        this.idUsuario=prestamo.getIdUsuario();
    }

    public double getEstimacionMora() {
        return estimacionMora;
    }

    public void setEstimacionMora(double estimacionMora) {
        this.estimacionMora = estimacionMora;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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

    public LocalDate getFechaDeEntregaEsperada() {
        return fechaDeEntregaEsperada;
    }

    public void setFechaDeEntregaEsperada(LocalDate fechaDeEntregaEsperada) {
        this.fechaDeEntregaEsperada = fechaDeEntregaEsperada;
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
