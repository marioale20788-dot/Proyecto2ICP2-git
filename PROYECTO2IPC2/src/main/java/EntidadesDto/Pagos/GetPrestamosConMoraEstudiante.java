/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Pagos;

import Entidades.Prestamo;
import Entidades.PrestamoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;

/**
 *
 * @author mario
 */
public class GetPrestamosConMoraEstudiante {

    private String idPrestamo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaPrestamo;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaEsperadaEntrega;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaEntregaReal;
    private Double mora;
    private PrestamoEnum estado;
    private String idLibro;
    private String idUsuario;

    public GetPrestamosConMoraEstudiante(Prestamo prestamo) {
        this.idPrestamo = prestamo.getIdPrestamo();
        this.fechaPrestamo = prestamo.getFechaPrestamo();
        this.fechaEsperadaEntrega = prestamo.getFechaEsperadaEntrega();
        this.fechaEntregaReal = prestamo.getFechaEntregaReal();
        this.mora = prestamo.getMora();
        this.estado = prestamo.getEstado();
        this.idLibro = prestamo.getIdLibro();
        this.idUsuario = prestamo.getIdUsuario();
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
