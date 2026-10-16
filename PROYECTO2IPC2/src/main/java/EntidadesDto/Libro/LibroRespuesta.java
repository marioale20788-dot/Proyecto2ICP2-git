/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDto.Libro;

import Entidades.Libro;

/**
 *
 * @author mario
 */
public class LibroRespuesta {
     private String idLibro;
    private String nombreLibro;
    private String autor;
    private String editorial;
    private int cantidad;
    
    public LibroRespuesta(){
        
    }

    public LibroRespuesta(Libro libro) {
        this.idLibro = libro.getIdLibro();
        this.nombreLibro = libro.getNombreLibro();
        this.autor = libro.getAutor();
        this.editorial = libro.getEditorial();
        this.cantidad = libro.getCantidad();
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
    

    
}
