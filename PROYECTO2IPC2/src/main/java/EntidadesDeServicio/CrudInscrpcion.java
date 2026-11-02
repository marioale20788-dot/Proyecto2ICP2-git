/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.CicloLectivo;
import Entidades.CostoPorAnio;
import Entidades.Prestamo;
import Entidades.Seccion;
import Entidades.TipoUsuarioEnum;
import Entidades.Usuario;
import Entidades.inscripcion;
import EntidadesDAO.CicloLectivoDB;
import EntidadesDAO.CostoPorAnioDB;
import EntidadesDAO.PrestamoDB;
import EntidadesDAO.SeccionDB;
import EntidadesDAO.UsuarioDB;
import EntidadesDAO.inscripcionDB;
import EntidadesDto.Inscripcion.IncripcionRespuesta;
import EntidadesDto.Inscripcion.NewIncricpionRequest;
import EntidadesDto.Inscripcion.UpdateIncripcionRequest;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import Excepciones.ElemtoAElminarEnUsoExcepcion;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class CrudInscrpcion {

    public void inscribirEstudiante(NewIncricpionRequest nuevaInscripcion) throws DatosNoValidosExcepcion, ElementoNoEncotradoExcepcion {
        inscripcion incripcionEstudiante = new inscripcion(nuevaInscripcion.getFechaInscripcion(),
                nuevaInscripcion.getIdEstudiante(), nuevaInscripcion.getIdSeccion(), nuevaInscripcion.getIdCicloLectivo());

        if (incripcionEstudiante.esValido() == false) {
            throw new DatosNoValidosExcepcion("DATOS INVALIDOS");
        }
        CostoPorAnioDB costoDB = new CostoPorAnioDB();
        boolean pagosColegiaturaPendientes = costoDB.costosPendientesParaInscripcion(incripcionEstudiante.getIdEstudiante());
        if (pagosColegiaturaPendientes == true) {
            throw new DatosNoValidosExcepcion("PAGOS PENDIENTES DE COLEGIATURA POR REALIZAR ");

        }
        PrestamoDB prestamoDb = new PrestamoDB();
        ArrayList<Prestamo> prestamosPendiantes = prestamoDb.obtenerPrestamosEstudiante(incripcionEstudiante.getIdEstudiante(),"DEVUELTO_CON_MORA");
        if(!prestamosPendiantes.isEmpty()){
             throw new DatosNoValidosExcepcion("PAGOS PENDIENTES  DE BIBLIOTECA POR REALIZAR ");
        }
        

        incripcionEstudiante.crearIdInscripcion();
        UsuarioDB usuarioDB = new UsuarioDB();
        ArrayList<Usuario> usuario = usuarioDB.obtenerUsuarioPorDpi(incripcionEstudiante.getIdEstudiante());
        if (!usuario.isEmpty()) {
            if (usuario.getFirst().getTipo() != TipoUsuarioEnum.Estudiante) {
                throw new DatosNoValidosExcepcion("EL USUARIO INGRESADO NO ES UN ESTUDIANTE");
            }
        }
        CicloLectivoDB cicloDb = new CicloLectivoDB();
        ArrayList<CicloLectivo> ciclo = cicloDb.buscarCicloPorAnio(incripcionEstudiante.getIdCicloLectivo());
        if(!ciclo.isEmpty()){
            if(ciclo.getFirst().getEstado()==false){
                  throw new DatosNoValidosExcepcion("EL CICLO NO ESTA ACTIVO");
            }
        }
        
        
        inscripcionDB inscripcionDB = new inscripcionDB();
        inscripcionDB.inscribirEstudiante(incripcionEstudiante);
        ArrayList<CostoPorAnio> costos = configurarPagosInscripcion(incripcionEstudiante);

        for (CostoPorAnio costo : costos) {
            costoDB.insertarCosto(costo);
        }

    }

    public IncripcionRespuesta updateInscripcion(UpdateIncripcionRequest update, String idInscripcion) throws ElementoNoEncotradoExcepcion, DatosNoValidosExcepcion {
        inscripcionDB inscripcionDb = new inscripcionDB();
        ArrayList<inscripcion> inscripcion = new ArrayList<>();
        inscripcion = inscripcionDb.incripcionesPorId(idInscripcion);
        if (inscripcion.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUNA INSCRIPCION");
        }
        inscripcion inscripcionEntidad = inscripcion.getFirst();
        inscripcionEntidad.setIdSeccion(update.getIdSeccion());
        if (inscripcionEntidad.esValido() == false) {
            throw new DatosNoValidosExcepcion("DATOS INVALIDOS");
        }
        inscripcionDb.updateInscripcion(inscripcionEntidad, idInscripcion);

        SeccionDB seccionDb = new SeccionDB();
        ArrayList<Seccion> seccion = seccionDb.obtenerSeccionPorId(inscripcionEntidad.getIdSeccion());

        IncripcionRespuesta respuesta = new IncripcionRespuesta(inscripcionEntidad.getIdInscripcion(),
                inscripcionEntidad.getFechaInscripcion(),
                inscripcionEntidad.getIdEstudiante(),
                inscripcionEntidad.getIdSeccion(),
                seccion.getFirst().getNombreSeccion() + "-" + seccion.getFirst().getIdGrado(),
                inscripcionEntidad.getIdCicloLectivo());

        return respuesta;

    }

    public void elminiarInscipcion(NewIncricpionRequest inscripcionAEliminar) throws ElementoNoEncotradoExcepcion, ElemtoAElminarEnUsoExcepcion {
        inscripcionDB inscripcionDb = new inscripcionDB();
        boolean existeInscripcion = inscripcionDb.existeIncripcionAElminar(inscripcionAEliminar.getIdEstudiante(), inscripcionAEliminar.getIdCicloLectivo());
        if (existeInscripcion == false) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN ESTUDIANTE");
        }
        inscripcionDb.eliminarInscipcion(inscripcionAEliminar);
        CostoPorAnioDB costosDb = new CostoPorAnioDB();
        costosDb.eliminarCostosPorAnio(inscripcionAEliminar);

    }

    public ArrayList<IncripcionRespuesta> obtenerInscripcionesPorEstuidante(String idSturiante) throws DatosNoValidosExcepcion, ElementoNoEncotradoExcepcion {
        UsuarioDB usDb = new UsuarioDB();
        ArrayList<Usuario> usuario = usDb.obtenerUsuarioPorDpi(idSturiante);
        if (usuario.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN ESTUDIANTE");
        }

        inscripcionDB inscripcionDB = new inscripcionDB();

        ArrayList<IncripcionRespuesta> incripcionesEstudiantesRespuesta = new ArrayList<>();
        ArrayList<inscripcion> incripcionesEstudiante = new ArrayList<>();
        incripcionesEstudiante = inscripcionDB.incripcionesEstudiante(idSturiante);

        incripcionesEstudiantesRespuesta = iniciarInscripcionEstudianteRespuesta(incripcionesEstudiante);

        return incripcionesEstudiantesRespuesta;

    }
    public IncripcionRespuesta obtenerInscripcionPorId(String id) throws DatosNoValidosExcepcion, ElementoNoEncotradoExcepcion {
     
        inscripcionDB inscripcionDB = new inscripcionDB();

        ArrayList<inscripcion> ins = inscripcionDB.incripcionesPorId(id);

       if(ins.isEmpty()){
                       throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUNA INSCRIPCION");
       }
        ArrayList<IncripcionRespuesta> respuesta = iniciarInscripcionEstudianteRespuesta(ins);
        return respuesta.getFirst();
    }

    public ArrayList<IncripcionRespuesta> obtenerSeccionesPorCiclo(int ciclo) throws ElementoNoEncotradoExcepcion {
        inscripcionDB inscripcionDB = new inscripcionDB();

        ArrayList<IncripcionRespuesta> incripcionesEstudiantesRespuesta = new ArrayList<>();
        ArrayList<inscripcion> incripcionesEstudiante = new ArrayList<>();

        incripcionesEstudiante = inscripcionDB.incripcionesCiclo(ciclo);

        if (incripcionesEstudiante.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN ESTUDIANTE");
        }
        incripcionesEstudiantesRespuesta = iniciarInscripcionEstudianteRespuesta(incripcionesEstudiante);
        return incripcionesEstudiantesRespuesta;
    }

    public ArrayList<IncripcionRespuesta> iniciarInscripcionEstudianteRespuesta(ArrayList<inscripcion> incripciones) {
        ArrayList<IncripcionRespuesta> incripcionesEstudiantesRespuesta = new ArrayList<>();
        SeccionDB seccionDb = new SeccionDB();
        if (!incripciones.isEmpty()) {
            for (inscripcion inscripcion : incripciones) {
                ArrayList<Seccion> seccion = seccionDb.obtenerSeccionPorId(inscripcion.getIdSeccion());
                if (!seccion.isEmpty()) {
                    IncripcionRespuesta respuesta = new IncripcionRespuesta(inscripcion.getIdInscripcion(),
                            inscripcion.getFechaInscripcion(),
                            inscripcion.getIdEstudiante(),
                            inscripcion.getIdSeccion(),
                            seccion.getFirst().getNombreSeccion() + "-" + seccion.getFirst().getIdGrado(),
                            inscripcion.getIdCicloLectivo());
                    incripcionesEstudiantesRespuesta.add(respuesta);

                }
            }

        }
        return incripcionesEstudiantesRespuesta;

    }

    public ArrayList<CostoPorAnio> configurarPagosInscripcion(inscripcion inscripcionEstudiante) {
        int[] mesesPago = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayList<CostoPorAnio> costos = new ArrayList<>();

        int anio = inscripcionEstudiante.getIdCicloLectivo();
        String idEstudiante = inscripcionEstudiante.getIdEstudiante();

        String idInscripcion = "PAGO-INSCRIPCION-" + idEstudiante + "-" + anio;

        LocalDate fechaLimiteInscripcion = inscripcionEstudiante.getFechaInscripcion();

        costos.add(new CostoPorAnio(
                idInscripcion,
                500.00,
                fechaLimiteInscripcion,
                false,
                idEstudiante,
                anio
        ));

        for (int mes : mesesPago) {
            String idMensualidad = "MES-" + mes + "-" + idEstudiante + "-" + anio;
            int ultimoDia = java.time.YearMonth.of(anio, mes).lengthOfMonth();
            LocalDate fechaLimiteMes = LocalDate.of(anio, mes, ultimoDia);

            CostoPorAnio pagoMes = new CostoPorAnio(
                    idMensualidad,
                    350.00,
                    fechaLimiteMes,
                    false,
                    idEstudiante,
                    anio
            );
            costos.add(pagoMes);
        }

        return costos;
    }

}
