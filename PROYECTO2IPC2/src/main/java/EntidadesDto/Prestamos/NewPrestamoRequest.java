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
public class NewPrestamoRequest {
     @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaDePrestamo;
      @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaDeEntregaEsperada;
    private String idLibro;
    private String idUsuario;
    private Integer cantidad;

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    
    public LocalDate getFechaDePrestamo() {
        return fechaDePrestamo;
    }

    public void setFechaDePrestamo(LocalDate fechaDePrestamo) {
        this.fechaDePrestamo = fechaDePrestamo;
    }

    public LocalDate getFechaDeEntregaEsperada() {
        return fechaDeEntregaEsperada;
    }

    public void setFechaDeEntregaEsperada(LocalDate fechaDeEntregaEsperada) {
        this.fechaDeEntregaEsperada = fechaDeEntregaEsperada;
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
