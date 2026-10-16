/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.Libro;
import EntidadesDAO.DBConnection;
import EntidadesDAO.LibroDB;
import EntidadesDAO.PrestamoDB;
import EntidadesDto.Libro.NewLibroRequest;
import EntidadesDto.Libro.UpdateLibroRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class CrudLibro {

    public void crearLibro(NewLibroRequest libroEntrada) throws DatosNoValidosExcepcion {
        Libro libro = libroEntidad(libroEntrada);
        LibroDB libroDB = new LibroDB();
        libroDB.crearLibro(libro);

    }

    public ArrayList<Libro> buscarLibros() {
        LibroDB libroDB = new LibroDB();
        ArrayList<Libro> libros = libroDB.devolverTodosLosLibros();
        return libros;
    }

    public Libro obtenerLibroPorNombreId(String valor) throws ElementoNoEncotradoExcepcion {
        LibroDB libroDb = new LibroDB();
        ArrayList<Libro> libro = libroDb.buscarLibroPorIdONombre(valor);
        if (libro.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO UN OBJETO CON ESTE VALOR");
        } else {
            return libro.getFirst();
        }
    }

    public Libro updateLibro(String idLibro, UpdateLibroRequest updatesLibro) throws ElementoNoEncotradoExcepcion, DatosNoValidosExcepcion {

        LibroDB libroDb = new LibroDB();
        Libro libroConUpdates = obtenerLibroPorNombreId(idLibro);

        if (updatesLibro.getAutor() != null) {
            libroConUpdates.setAutor(updatesLibro.getAutor());
        } else if (updatesLibro.getEditorial() != null) {
            libroConUpdates.setEditorial(updatesLibro.getEditorial());
        } else if (updatesLibro.getNombreLibro() != null) {
            libroConUpdates.setNombreLibro(updatesLibro.getNombreLibro());
        } else {
            libroConUpdates.setCantidad(updatesLibro.getCantidad());
        }

        if (!libroConUpdates.esValido()) {
            throw new DatosNoValidosExcepcion("DATOS NO VALIDOS");
        }

        return libroDb.updateLibro(idLibro, libroConUpdates);

    }

    public void eliminarLibro(String idLibro) throws ElementoNoEncotradoExcepcion, ElemtoAElminarEnUsoExcepcion {
        PrestamoDB prestamoDB = new PrestamoDB();
        LibroDB libroDb = new LibroDB();
        ArrayList<Libro> libro = new ArrayList<>();
        libro.add(obtenerLibroPorNombreId(idLibro));
        if (libro.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO EL LIBRO");
        }

        if (prestamoDB.buscarLibroEnPrestamo(idLibro) == true) {
            throw new ElemtoAElminarEnUsoExcepcion("EL LIBRO ESTA EN USO");
        }
        libroDb.eliminarLibro(idLibro);

    }

    public Libro libroEntidad(NewLibroRequest libroEn) throws DatosNoValidosExcepcion {
        try {
            Libro libro = new Libro(libroEn.getIdLibro(), libroEn.getNombreLibro(),
                    libroEn.getAutor(), libroEn.getEditorial(), libroEn.getCantidad());

            if (libro.esValido() == false) {
                throw new DatosNoValidosExcepcion("Datos enviados incorrectos");
            }

            return libro;
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new DatosNoValidosExcepcion("Datos enviados incorrectos");
        }
    }

}
