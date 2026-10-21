/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import EntidadesDto.Empleado.UpdateEmpleadoRequest;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mario
 */
public class Empleado {

    private String idEmpleado;
    private double salario;
    private String nombre;
    private LocalDate fechaDeContratacion;

    public Empleado(String idEmpleado, double salario, String nombre, LocalDate fechaDeContratacion) {
        this.idEmpleado = idEmpleado;
        this.salario = salario;
        this.nombre = nombre;
        this.fechaDeContratacion = fechaDeContratacion;
    }
   public void updateEmpleado(UpdateEmpleadoRequest update){
       if(update.getNombreCompleto()!=null){
           this.nombre=update.getNombreCompleto();
       }else if(update.getFechaContratacion()!=null){
           this.fechaDeContratacion = update.getFechaContratacion();
       }else if(update.getSalario()!=null){
           this.salario = update.getSalario();
           
       }
   }
    
    
    
    
    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaDeContratacion() {
        return fechaDeContratacion;
    }

    public void setFechaDeContratacion(LocalDate fechaDeContratacion) {
        this.fechaDeContratacion = fechaDeContratacion;
    }
    
    public boolean datosValidos(){
            return StringUtils.isNotBlank(idEmpleado)
                && salario >0
                && StringUtils.isNotBlank(nombre)
                 && fechaDeContratacion!=null;
    }

}
