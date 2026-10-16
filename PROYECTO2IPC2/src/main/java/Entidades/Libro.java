/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mario
 */
public class Libro {

    private String idLibro;
    private String nombreLibro;
    private String autor;
    private String editorial;
    private int cantidad;

    public Libro(String idLibro, String nombreLibro, String autor, String editorial, int cantidad) {
        this.idLibro = idLibro;
        this.nombreLibro = nombreLibro;
        this.autor = autor;
        this.editorial = editorial;
        this.cantidad = cantidad;
    }

    public String getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(String idLibro) {
        this.idLibro = idLibro;
    }

    public String getNombreLibro() {
        return nombreLibro;
    }

    public void setNombreLibro(String nombreLibro) {
        this.nombreLibro = nombreLibro;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public boolean esValido() {
        return StringUtils.isNotBlank(idLibro)
                && StringUtils.isNotBlank(nombreLibro)
                && StringUtils.isNotBlank(autor)
                && StringUtils.isNotBlank(editorial)
                && cantidad >= 0;
    }
    public boolean editarCantidad(){
         return  StringUtils.isNotBlank(nombreLibro)
                || StringUtils.isNotBlank(autor)
                || StringUtils.isNotBlank(editorial);
    }
    
    
    

}
