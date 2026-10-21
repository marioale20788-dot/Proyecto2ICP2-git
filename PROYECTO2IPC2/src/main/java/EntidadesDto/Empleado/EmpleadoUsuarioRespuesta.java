/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Empleado;

import Entidades.Empleado;
import Entidades.TipoUsuarioEnum;
import Entidades.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;

/**
 *
 * @author mario
 */
public class EmpleadoUsuarioRespuesta {
    
    private String id;
    private String correoElectronico;
    private TipoUsuarioEnum rol;
    private boolean estado;
    private double salario;
    private String nombreCompleto;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate fechaDeContratacion;

    public EmpleadoUsuarioRespuesta(){
        
    }
    
    public EmpleadoUsuarioRespuesta(Usuario usuario, Empleado empleado) {
        this.id = usuario.getIdUsuario();

        this.correoElectronico = usuario.getCorreoElectronico();
        this.rol = usuario.getTipo();
        this.estado = usuario.getEstado();
        this.salario = empleado.getSalario();
        this.nombreCompleto = empleado.getNombre();
        this.fechaDeContratacion = empleado.getFechaDeContratacion();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public TipoUsuarioEnum getRol() {
        return rol;
    }

    public void setRol(TipoUsuarioEnum rol) {
        this.rol = rol;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public LocalDate getFechaDeContratacion() {
        return fechaDeContratacion;
    }

    public void setFechaDeContratacion(LocalDate fechaDeContratacion) {
        this.fechaDeContratacion = fechaDeContratacion;
    }
    
    
   
    
    
}
