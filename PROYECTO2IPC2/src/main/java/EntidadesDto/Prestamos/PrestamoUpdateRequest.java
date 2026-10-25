/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Prestamos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;

/**
 *
 * @author mario
 */
public class PrestamoUpdateRequest {

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    LocalDate fechaDePrestamo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    LocalDate FechaEsperadaEntrega;
    
    String idLibro;
    String idUsuario;
    Integer cantidad;

    public LocalDate getFechaDePrestamo() {
        return fechaDePrestamo;
    }

    public void setFechaDePrestamo(LocalDate fechaDePrestamo) {
        this.fechaDePrestamo = fechaDePrestamo;
    }

    public LocalDate getFechaEsperadaEntrega() {
        return FechaEsperadaEntrega;
    }

    public void setFechaEsperadaEntrega(LocalDate FechaEsperadaEntrega) {
        this.FechaEsperadaEntrega = FechaEsperadaEntrega;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}
