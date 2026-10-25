/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.CicloLectivo;
import Entidades.CostoPorAnio;
import Entidades.Estudiante;
import Entidades.Pagos;
import Entidades.Prestamo;
import Entidades.Usuario;
import Entidades.inscripcion;
import EntidadesDAO.CicloLectivoDB;
import EntidadesDAO.CostoPorAnioDB;
import EntidadesDAO.EstudianteDB;
import EntidadesDAO.PagoDb;
import EntidadesDAO.PrestamoDB;
import EntidadesDAO.UsuarioDB;
import EntidadesDAO.inscripcionDB;
import EntidadesDto.Solvencia.GetCostosParaGenerarSolvencia;
import EntidadesDto.Solvencia.GetPagosPorFechaRequest;
import EntidadesDto.Solvencia.SolvenciaDePago;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import Excepciones.PagosPendientesExcepcion;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author mario
 */
public class VerificarPagosSolvencia {

    public ArrayList<Pagos> obtenerPagosDeUsuarioAnio(GetPagosPorFechaRequest obtenerPagos) throws ElementoNoEncotradoExcepcion, DatosNoValidosExcepcion {
        GetPagosPorFechaRequest pagosAObtener = obtenerPagos;
        PagoDb pagoDb = new PagoDb();
        if (pagosAObtener.getFechaIncio().isAfter(pagosAObtener.getFechaFinal())) {
            throw new DatosNoValidosExcepcion("LAS FECHAS INGRESADAS SON INCORRECTAS");
        }

        ArrayList<Pagos> pagos = pagoDb.obtenerPagoPorUsuarioAnio(obtenerPagos.getIdUsuario(), obtenerPagos.getFechaIncio(), obtenerPagos.getFechaFinal());
        if (pagos.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCOTRARON PAGOS CON LOS DATOS ENVIADOS");
        }
        return pagos;

    }

    public ArrayList<GetCostosParaGenerarSolvencia> obtenerCostosyEstados(String idEstudiante, int ciclo) throws ElementoNoEncotradoExcepcion {

        CostoPorAnioDB costosDb = new CostoPorAnioDB();
        PrestamoDB prestamoDb = new PrestamoDB();
        inscripcionDB buscarInscripcion = new inscripcionDB();
        boolean inscripcion = buscarInscripcion.existeIncripcionAElminar(idEstudiante, ciclo);
        if (inscripcion == false) {
            throw new ElementoNoEncotradoExcepcion("NO EXISTE UNA INSCRIPCION DEL ESTUDINATE EN ESTE CICLO");
        }

        ArrayList<CostoPorAnio> costosEstados = costosDb.obtenerCostosDeEstudiante(ciclo, idEstudiante, false);
        ArrayList<Prestamo> prestamosPendiantes = prestamoDb.obtenerPrestamosEstudiante(idEstudiante,"DEVUELTO_CON_MORA");

        ArrayList<GetCostosParaGenerarSolvencia> costosConsulta = new ArrayList<>();
        costosConsulta = construirCostos(costosEstados, prestamosPendiantes);
        costosConsulta.sort(Comparator.comparingInt(GetCostosParaGenerarSolvencia::getNumero));
        return costosConsulta;

    }
    
    public SolvenciaDePago crearSolvenciaDePago(String idUsuario, String ciclo) throws DatosNoValidosExcepcion, ElementoNoEncotradoExcepcion, PagosPendientesExcepcion{
     int cicloInt=0;
        try {
           cicloInt= Integer.valueOf(ciclo);
            
        } catch (NumberFormatException e) {
            throw  new DatosNoValidosExcepcion("DATOS INVALIDOS");
        }
        ArrayList<GetCostosParaGenerarSolvencia> verificarSolvencia = obtenerCostosyEstados(idUsuario, cicloInt);
        boolean pagosPendientes = false;
        for (GetCostosParaGenerarSolvencia getCostosParaGenerarSolvencia : verificarSolvencia) {
            if(getCostosParaGenerarSolvencia.getNumero()==2||getCostosParaGenerarSolvencia.getNumero()==3){
                pagosPendientes=true;
                break;
                
            }
            
        }
        
        if(pagosPendientes==true){
            throw new PagosPendientesExcepcion("PAGOS PENDIENTES, NO SE PUEDE GENERAR UNA SOLVENCIA");
            
        }else{
            EstudianteDB esDb = new EstudianteDB();
            
            Estudiante estudiante = esDb.obtenerEstudiantePorId(idUsuario);
            if(estudiante==null){
                throw  new ElementoNoEncotradoExcepcion("USUARIO NO ENCOTRADO");
            }
             LocalDate fechaActual = LocalDate.now();
             String fechaActualSt= fechaActual.toString();
            
            
            SolvenciaDePago solvencia = new SolvenciaDePago(estudiante.getNombreEstudiante(), idUsuario, fechaActualSt);
            return solvencia;
        }
        
        
     
    }

    public ArrayList<GetCostosParaGenerarSolvencia> construirCostos(ArrayList<CostoPorAnio> costosEstadosA, ArrayList<Prestamo> prestamos) {
        ArrayList<CostoPorAnio> costosEstados = costosEstadosA;
        ArrayList<GetCostosParaGenerarSolvencia> costosConsulta = new ArrayList<>();
        int numero;
        for (CostoPorAnio costos : costosEstados) {
            LocalDate fechaActual = LocalDate.now();
            String estadoCosto;

            if (costos.getEstado() == true) {
                estadoCosto = "PAGADO";
                numero = 1;
            } else {
                if (costos.getFechaLimite().isBefore(fechaActual)) {
                    estadoCosto = "PENDIENTE DE PAGO CON DEMORA";
                    numero = 2;
                } else {
                    estadoCosto = "PENDIENTE";
                    numero = 4;
                }
            }

            GetCostosParaGenerarSolvencia generarSolvencia = new GetCostosParaGenerarSolvencia(costos.getIdCosto(),
                    costos.getMonto(),
                    costos.getEstado(),
                    estadoCosto,
                    costos.getFechaLimite(),
                    numero);
            costosConsulta.add(generarSolvencia);

        }

        ArrayList<Prestamo> prestamosPendianates = prestamos;
        for (Prestamo prestamosPendianate : prestamosPendianates) {

            GetCostosParaGenerarSolvencia generarSolvencia = new GetCostosParaGenerarSolvencia(prestamosPendianate.getIdPrestamo(),
                    prestamosPendianate.getMora(),
                    true,
                    prestamosPendianate.getEstado().toString(),
                    prestamosPendianate.getFechaEsperadaEntrega(),
                    3);
            costosConsulta.add(generarSolvencia);
        }
        return costosConsulta;

    }

}
