/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author mario
 */
public class Carrera {
    
    private String idCarrera;
    private String   nombre;
    private boolean estado;

    public Carrera(String idCarrera, String nombre, boolean Estado) {
        this.idCarrera = idCarrera;
        this.nombre = nombre;
        this.estado = Estado;
    }
    
  public Carrera(){
      
  }

    public String getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(String idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean Estado) {
        this.estado  = Estado;
    }
    
    
}
