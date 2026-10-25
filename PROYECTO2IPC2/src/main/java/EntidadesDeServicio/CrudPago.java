/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.CostoPorAnio;
import Entidades.EnumPago;
import Entidades.Pagos;
import Entidades.Prestamo;
import Entidades.PrestamoEnum;
import Entidades.TipoUsuarioEnum;
import Entidades.Usuario;
import EntidadesDAO.CostoPorAnioDB;
import EntidadesDAO.PagoDb;
import EntidadesDAO.PrestamoDB;
import EntidadesDAO.UsuarioDB;
import EntidadesDto.Pagos.NewPagoRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class CrudPago {

    public String registrarPago(NewPagoRequest nuevoPago) throws DatosNoValidosExcepcion, ElementoNoEncotradoExcepcion {
        Pagos pago = new Pagos(nuevoPago.getTipoPago(),
                nuevoPago.getMonto(),
                nuevoPago.getFechaDePago(),
                nuevoPago.getIdEstudiante(),
                nuevoPago.getIdSecretaria(),
                nuevoPago.getIdCosto(),
                nuevoPago.getIdPrestamo());

        if (pago.esValido() == false) {
            throw new DatosNoValidosExcepcion("ESPACIOS OBLIGATORIOS VACIOS");
        }

        UsuarioDB usuarioDb = new UsuarioDB();
        ArrayList<Usuario> us = usuarioDb.obtenerUsuarioPorDpi(nuevoPago.getIdSecretaria());
        if (us.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN EMPLEADO CON ESE ID");

        } else {
            if (us.getFirst().getTipo() != TipoUsuarioEnum.Secretaria) {
                throw new DatosNoValidosExcepcion("DATOS INVALIDOS EL USUARIO NO ES UN SECRETARIO");
            }
        }
        ArrayList<Usuario> usEstudiante = usuarioDb.obtenerUsuarioPorDpi(nuevoPago.getIdEstudiante());
        if (usEstudiante.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN ESTUDIANTE CON ESE ID");

        } 

        if (pago.getTipoPago() == EnumPago.INSCRIPCION || pago.getTipoPago() == EnumPago.MENSUALIDAD) {
            if (pago.getIdCosto() == null) {
                throw new DatosNoValidosExcepcion("DATOS INVALIDOS");
            }

            CostoPorAnioDB costoDb = new CostoPorAnioDB();
            ArrayList<CostoPorAnio> costo = costoDb.obtenerCostoPorId(pago.getIdCosto(), pago.getIdEstuidante());
            if (costo.isEmpty()) {
                throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN COSTO CON ESE ID");
            } else {
                if (costo.getFirst().getEstado() == true) {
                    throw new DatosNoValidosExcepcion("ESTE PAGO YA A SIDO REALIZADO");
                }
                if (costo.getFirst().getIdCosto().charAt(0) == 'M' && pago.getTipoPago() != EnumPago.MENSUALIDAD) {
                    throw new DatosNoValidosExcepcion("TIPO DE PAGO INCORRECTO");
                }
                if (costo.getFirst().getIdCosto().charAt(0) == 'P' && pago.getTipoPago() != EnumPago.INSCRIPCION) {
                    throw new DatosNoValidosExcepcion("TIPO DE PAGO INCORRECTO");
                }

            }
        }
        if (pago.getTipoPago() == EnumPago.MORA_BIBLIOTECA) {
            if (pago.getIdPrestamo() == null) {
                throw new DatosNoValidosExcepcion("DATOS INVALIDOS");

            }
            PrestamoDB prestamoDb = new PrestamoDB();
            ArrayList<Prestamo> prestamo = prestamoDb.obtenerPrestamoPorId(pago.getIdPrestamo(), pago.getIdEstuidante());
            if (prestamo.isEmpty()) {
                throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN PRESTAMO CON ESE ID");
            } else {
                if (prestamo.getFirst().getEstado() == PrestamoEnum.DEVUELTO || prestamo.getFirst().getEstado() == PrestamoEnum.ACTIVO) {
                    throw new DatosNoValidosExcepcion("ESTE PAGO YA A SDIO REALIZADO");
                }
            if (prestamo.getFirst().getFechaEntregaReal().isAfter(pago.getFechaPago())) {
      throw new DatosNoValidosExcepcion("LA FEHCA DE PAGO DE MORA DEBE SER DESPUES DE LA FEHCA DE ENTREGA FINA;");
}
                
                
            }
        }
        PagoDb pagoDb = new PagoDb();
        pago.setIdPago(pagoDb.generarIdPagoAleatorio());

        pagoDb.registrarPago(pago);
        return pago.getIdPago();

    }
    
    public ArrayList<CostoPorAnio> costosPorEsudianteCiclo(String idEstudiante, int idCiclo) throws ElementoNoEncotradoExcepcion{
        CostoPorAnioDB costosDb = new CostoPorAnioDB();
        ArrayList<CostoPorAnio> costos = costosDb.obtenerCostosDeEstudiante(idCiclo, idEstudiante,true);
        if(costos.isEmpty()){
               throw new ElementoNoEncotradoExcepcion("NINGUN COSTO PENDIENTE REGISTRADO");
        }
        return costos;
        
    }
    public ArrayList<Prestamo> prestamosConMoraEstudiante(String idEstudiante) throws ElementoNoEncotradoExcepcion{
        PrestamoDB prestamoDb = new PrestamoDB();
        ArrayList<Prestamo> prestamos = prestamoDb.obtenerPrestamosEstudiante(idEstudiante,"DEVUELTO_CON_MORA");
        if(prestamos.isEmpty()){
            throw new ElementoNoEncotradoExcepcion("NINGUN PRESTAMO PENDIENTE REGISTRADO");
        }
        return prestamos;
        
    }
    
    

}
