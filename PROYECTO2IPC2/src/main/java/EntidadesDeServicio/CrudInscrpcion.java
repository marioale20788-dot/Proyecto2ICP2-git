/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.CostoPorAnio;
import Entidades.Seccion;
import Entidades.Usuario;
import Entidades.inscripcion;
import EntidadesDAO.CostoPorAnioDB;
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

    public void inscribirEstudiante(NewIncricpionRequest nuevaInscripcion) throws DatosNoValidosExcepcion {
        inscripcion incripcionEstudiante = new inscripcion(nuevaInscripcion.getFechaInscripcion(),
                nuevaInscripcion.getIdEstudiante(), nuevaInscripcion.getIdSeccion(), nuevaInscripcion.getIdCicloLectivo());

        if (incripcionEstudiante.esValido() == false) {
            throw new DatosNoValidosExcepcion("DATOS INVALIDOS");
        }
        incripcionEstudiante.crearIdInscripcion();
        inscripcionDB inscripcionDB = new inscripcionDB();
        inscripcionDB.inscribirEstudiante(incripcionEstudiante);
        ArrayList<CostoPorAnio> costos = configurarPagosInscripcion(incripcionEstudiante);
        CostoPorAnioDB costoDB = new CostoPorAnioDB();
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
    
    public void elminiarInscipcion(NewIncricpionRequest inscripcionAEliminar) throws ElementoNoEncotradoExcepcion, ElemtoAElminarEnUsoExcepcion{
        inscripcionDB inscripcionDb = new inscripcionDB();
       boolean existeInscripcion= inscripcionDb.existeIncripcionAElminar(inscripcionAEliminar.getIdEstudiante(),inscripcionAEliminar.getIdCicloLectivo());
        if(existeInscripcion==false){
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
                null,
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
                    null,
                    false,
                    idEstudiante,
                    anio
            );
            costos.add(pagoMes);
        }

        return costos;
    }

}
