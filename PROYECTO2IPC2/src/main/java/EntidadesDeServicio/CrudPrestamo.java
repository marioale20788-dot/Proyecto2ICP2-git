/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.Libro;
import Entidades.Prestamo;
import Entidades.PrestamoEnum;
import EntidadesDAO.LibroDB;
import EntidadesDAO.PrestamoDB;
import EntidadesDto.Prestamos.FinalizarPrestamo;
import EntidadesDto.Prestamos.NewPrestamoRequest;
import EntidadesDto.Prestamos.PrestamoUpdateRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class CrudPrestamo {

    public void registrarPrestamo(NewPrestamoRequest nuevoPrestamo) throws DatosNoValidosExcepcion, ElementoNoEncotradoExcepcion {
        PrestamoDB prestamoDb = new PrestamoDB();
        String idPrestamo = prestamoDb.generarIdAleatorio();

        Prestamo prestamo = new Prestamo(idPrestamo, nuevoPrestamo.getFechaDePrestamo(), nuevoPrestamo.getFechaDeEntregaEsperada(), null, 0.0, PrestamoEnum.ACTIVO, nuevoPrestamo.getIdLibro(),
                nuevoPrestamo.getIdUsuario());
        prestamo.setCantidad(nuevoPrestamo.getCantidad());
        if (prestamo.prestamoEsValido() == false) {
            throw new DatosNoValidosExcepcion("DATOS INVALIDOS");
        }
        LibroDB libroDb = new LibroDB();

        ArrayList<Libro> libro = libroDb.buscarLibroPorIdONombre(prestamo.getIdLibro());

        if (libro.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO EXISTE NINGUN LIBRO CON ESE ID");
        }
        Libro libroAprestar = libro.getFirst();
        Integer cantidad = prestamo.getCantidad();

        if (libroAprestar.getCantidad() < cantidad) {

            throw new DatosNoValidosExcepcion("NO EXITE ESA CANTIDAD DE LIBROS PARA PRESTAR");

        }

        prestamoDb.registrarPrestamo(prestamo);
        int cantidadFinal = libroAprestar.getCantidad() - cantidad;
        libroAprestar.setCantidad(cantidadFinal);
        libroDb.updateLibro(libroAprestar.getIdLibro(), libroAprestar);

    }

    public Prestamo obtenerPrestamoPorId(String idPrestamo) throws ElementoNoEncotradoExcepcion {
        PrestamoDB prestamoDb = new PrestamoDB();
        ArrayList<Prestamo> prestamo = prestamoDb.Prestamo(idPrestamo);
        if (prestamo.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN PRESTAMO CON ESTE ID");
        }
        return prestamo.getFirst();
    }

    public ArrayList<Prestamo> obtenerPrestamosActivos(String idUsuario) throws ElementoNoEncotradoExcepcion {
        PrestamoDB prestamoDb = new PrestamoDB();
        ArrayList<Prestamo> prestamos = prestamoDb.obtenerPrestamosEstudiante(idUsuario, "ACTIVO");
        if (prestamos.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO EXISTEN PRESTAMOS REGISTRADOS");
        }
        double estimacionMora;
        for (Prestamo prestamo : prestamos) {
            LocalDate fechaActual = LocalDate.now();
            LocalDate fechaEntrega = prestamo.getFechaEsperadaEntrega();
            long diasDiferencia = Duration.between(
                    fechaEntrega.atStartOfDay(),
                    fechaActual.atStartOfDay()
            ).toDays();
            if (diasDiferencia <= 0) {
                estimacionMora = 0.0;
            } else {
                estimacionMora = 2 * diasDiferencia;
                prestamo.setMora(estimacionMora);
            }

        }

        return prestamos;

    }
    

    public void eliminarPretamo(String idPrestamo) throws ElementoNoEncotradoExcepcion, ElemtoAElminarEnUsoExcepcion, DatosNoValidosExcepcion {
        PrestamoDB prestamoDb = new PrestamoDB();
        ArrayList<Prestamo> prestamo = prestamoDb.Prestamo(idPrestamo);
        if (prestamo.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN PRESTAMO CON ESE ID");
        }
        String idLibro = prestamo.getFirst().getIdLibro();
        int cantidad = prestamo.getFirst().getCantidad();
        prestamoDb.eliminarPrestamo(idPrestamo);
        LibroDB libroDb = new LibroDB();
        ArrayList<Libro> libro = libroDb.buscarLibroPorIdONombre(idLibro);
        Libro libroUpdate = libro.getFirst();
        int cantidadFinal = cantidad + libroUpdate.getCantidad();
        libroUpdate.setCantidad(cantidadFinal);
        libroDb.updateLibro(idLibro, libroUpdate);

    }

    public Prestamo updatePrestamo(String idPrestamo, PrestamoUpdateRequest prestamoUpdate) throws ElementoNoEncotradoExcepcion, DatosNoValidosExcepcion {
        PrestamoDB prestamoDb = new PrestamoDB();
        Prestamo prestamo = obtenerPrestamoPorId(idPrestamo);
        String idLibro = prestamo.getIdLibro();
        int cantidadPrestamo = prestamo.getCantidad();

        prestamo.updatePrestamo(prestamoUpdate);
        if (prestamo.prestamoEsValido() == false) {
            throw new DatosNoValidosExcepcion("DATOS INVALIDOS");
        }

        LibroDB libroDb = new LibroDB();
        ArrayList<Libro> libroNuevo = libroDb.buscarLibroPorIdONombre(prestamo.getIdLibro());
        if (libroNuevo.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO EXISTE NINGUN LIBRO CON ESE ID");
        }

        Libro libroAPrestar = libroNuevo.getFirst();
        int cantidadLibros;

        if (idLibro.equalsIgnoreCase(prestamo.getIdLibro())) {
            cantidadLibros = libroAPrestar.getCantidad() + cantidadPrestamo;
        } else {

            cantidadLibros = libroAPrestar.getCantidad();
        }

        if (cantidadLibros < prestamo.getCantidad()) {
            throw new DatosNoValidosExcepcion("NO EXITE ESA CANTIDAD DE LIBROS PARA PRESTAR");
        }

        if (!idLibro.equalsIgnoreCase(prestamo.getIdLibro())) {
            ArrayList<Libro> libroAnteriorLista = libroDb.buscarLibroPorIdONombre(idLibro);
            if (!libroAnteriorLista.isEmpty()) {
                Libro libroAnterior = libroAnteriorLista.getFirst();
                libroAnterior.setCantidad(libroAnterior.getCantidad() + cantidadPrestamo);
                libroDb.updateLibro(libroAnterior.getIdLibro(), libroAnterior);
            }
        }

        prestamoDb.updatePrestamo(prestamo, idPrestamo);

        int cantidad = cantidadLibros - prestamo.getCantidad();
        libroAPrestar.setCantidad(cantidad);
        libroDb.updateLibro(libroAPrestar.getIdLibro(), libroAPrestar);

        return obtenerPrestamoPorId(idPrestamo);
    }

    public void terminarPrestamo(FinalizarPrestamo datos) throws DatosNoValidosExcepcion, ElementoNoEncotradoExcepcion {
        if (datos.getFechaEntregaFinal() == null || datos.getIdPrestamo() == null || datos.getIdEstudiante() == null) {
            throw new DatosNoValidosExcepcion("DATOS INVALIDOS");
        }
        PrestamoDB prestamoDb = new PrestamoDB();
        ArrayList<Prestamo> prestamo = prestamoDb.obtenerPrestamoPorId(datos.getIdPrestamo(), datos.getIdEstudiante());
        if (prestamo.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO EXISTE NINGUN PRESTAMO CON ESAS ESPECIFICACIONES");
        }
        Prestamo prestamoATerminar = prestamo.getFirst();
        if (datos.getFechaEntregaFinal().isBefore(prestamoATerminar.getFechaPrestamo())) {
            throw new DatosNoValidosExcepcion("LA FECHA DE ENTREGA FINAL ESTA ANTES DE LA FECHA DE REALIZACION DEL PRESTAMO");
        }
        String estado;
        double mora;

        if (datos.getFechaEntregaFinal().isAfter(prestamoATerminar.getFechaEsperadaEntrega())) {
            estado = "DEVUELTO_CON_MORA";
            long diasDiferencia = Duration.between(
                    prestamoATerminar.getFechaEsperadaEntrega().atStartOfDay(),
                    datos.getFechaEntregaFinal().atStartOfDay()
            ).toDays();
            mora = diasDiferencia * 2;
        } else {
            estado = "DEVUELTO";
            mora = 0.0;
        }
        prestamoATerminar.setEstado(PrestamoEnum.valueOf(estado));
        prestamoATerminar.setFechaEntregaReal(datos.getFechaEntregaFinal());
        prestamoATerminar.setMora(mora);

        prestamoDb.updatePrestamo(prestamoATerminar, prestamoATerminar.getIdPrestamo());
        LibroDB libroDb = new LibroDB();
        ArrayList<Libro> libroUpdate = libroDb.buscarLibroPorIdONombre(prestamoATerminar.getIdLibro());
        int cantidad = libroUpdate.getFirst().getCantidad() + prestamoATerminar.getCantidad();
        libroUpdate.getFirst().setCantidad(cantidad);
        libroDb.updateLibro(prestamoATerminar.getIdLibro(), libroUpdate.getFirst());

    }

}
