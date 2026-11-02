/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package EntidadesDeServicio;

import Entidades.CostoPorAnio;
import Entidades.Curriculo;
import Entidades.Curso;
import Entidades.EnumPago;
import Entidades.Estudiante;
import Entidades.Grado;
import Entidades.NotaFinal;
import Entidades.Pagos;
import Entidades.Prestamo;
import Entidades.Seccion;
import Entidades.Usuario;
import Entidades.inscripcion;
import EntidadesDAO.CostoPorAnioDB;
import EntidadesDAO.CurriculoDB;
import EntidadesDAO.CursoDB;
import EntidadesDAO.EstudianteDB;
import EntidadesDAO.GradoDB;
import EntidadesDAO.NotaFinalDb;
import EntidadesDAO.NotasDb;
import EntidadesDAO.PagoDb;
import EntidadesDAO.PrestamoDB;
import EntidadesDAO.SeccionDB;
import EntidadesDAO.UsuarioDB;
import EntidadesDAO.inscripcionDB;
import EntidadesDto.Pagos.GetPrestamosConMoraEstudiante;
import EntidadesDto.Reportes.GetAlumnosSolventesMorosos;
import EntidadesDto.Reportes.GetReporteAcademico;
import EntidadesDto.Reportes.GetReporteAcademicoInformacionGeneral;
import Excepciones.DatosNoValidosExcepcion;
import Excepciones.ElementoNoEncotradoExcepcion;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author mario
 */
public class Reportes {

    public ArrayList<GetAlumnosSolventesMorosos> reporteAlumosMorosos(String idSeccion, int ciclo) throws ElementoNoEncotradoExcepcion, DatosNoValidosExcepcion {
        inscripcionDB insDb = new inscripcionDB();

        ArrayList<inscripcion> estudiantesInscritos = insDb.ListaDeEstudiantesInscritos(ciclo, idSeccion);

        if (estudiantesInscritos.isEmpty()) {
            throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN ESTUDIANTE INSCRITO");
        }
        ArrayList<GetAlumnosSolventesMorosos> estadoAlumnos = new ArrayList<>();

        EstudianteDB esDb = new EstudianteDB();
        String estado;
        double totalDedudas;
        String deduasPendiantes;
        ArrayList<Prestamo> prestamos;
        ArrayList<CostoPorAnio> costosPendientes;

        for (inscripcion estudiantesInscrito : estudiantesInscritos) {
            totalDedudas = 0;
            deduasPendiantes = "";
            estado = "";
            Estudiante estudiante = esDb.obtenerEstudiantePorId(estudiantesInscrito.getIdEstudiante());

            prestamos = buscarPrestamosDeudas(estudiantesInscrito.getIdEstudiante());
            costosPendientes = buscarCostosPendientes(estudiantesInscrito.getIdEstudiante());

            if (prestamos.isEmpty() && costosPendientes.isEmpty()) {
                estado = "SOLVENTE";
            } else {
                estado = "MOROSO";

            }
            if (!prestamos.isEmpty()) {
                for (Prestamo prestamo : prestamos) {
                    totalDedudas = totalDedudas + prestamo.getMora();
                    deduasPendiantes = deduasPendiantes + "Deuda de mora en biblioteca";

                }
            }
            int contador = 0;
            if (!costosPendientes.isEmpty()) {
                for (CostoPorAnio costosPendiente : costosPendientes) {
                    contador++;
                    totalDedudas = totalDedudas + costosPendiente.getMonto();

                }
                deduasPendiantes = deduasPendiantes + ", Deudadas en " + contador + " meses con atraso";
            }

            GetAlumnosSolventesMorosos alumno = new GetAlumnosSolventesMorosos(estudiantesInscrito.getIdEstudiante(),
                    estudiante.getNombreEstudiante(), estado,
                    deduasPendiantes, totalDedudas);
            estadoAlumnos.add(alumno);

        }
        return estadoAlumnos;

    }
    
public ArrayList<GetReporteAcademico> promedioCursos(String idSeccion, int ciclo) throws ElementoNoEncotradoExcepcion {
    
    inscripcionDB insDb = new inscripcionDB();
    ArrayList<inscripcion> estudiantesInscritos = insDb.ListaDeEstudiantesInscritos(ciclo, idSeccion);

    if (estudiantesInscritos.isEmpty()) {
        throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN ESTUDIANTE INSCRITO");
    }

    SeccionDB seccionDb = new SeccionDB();
    ArrayList<Seccion> seccion = seccionDb.obtenerSeccionPorId(idSeccion);
    
    if (seccion.isEmpty()) {
        throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUNA SECCION");
    }
    
    String idGrado = seccion.getFirst().getIdGrado();
    
    CurriculoDB curriculoDb = new CurriculoDB();
    ArrayList<Curriculo> curriculoCursos = curriculoDb.obtenerCurriculoCicloGrado(idGrado, ciclo);
    
    if (curriculoCursos.isEmpty()) {
        throw new ElementoNoEncotradoExcepcion("NO SE ENCONTRO NINGUN CURRICULO ASOOCIADO A ESTE GRADO EN EL CICLO DEFINIDO");
    }

    NotaFinalDb notas = new NotaFinalDb();
    CursoDB cursoDb = new CursoDB();
    double notasIndividuales;
    double promedio = 0;
    int contador = 0;
    String dificultad;
    ArrayList<GetReporteAcademico> reporteAcademico = new ArrayList<>();

    for (Curriculo curriculoCurso : curriculoCursos) {
        notasIndividuales = 0;
        promedio = 0;
        contador = 0;
        
        String curso = curriculoCurso.getCodigoCurso();
        for (inscripcion estduiante : estudiantesInscritos) {
            ArrayList<NotaFinal> nota = notas.obtenerNotasFinalesReporte(estduiante.getIdEstudiante(), ciclo, curso);
            if (!nota.isEmpty()) {
                contador++;
                notasIndividuales = notasIndividuales + nota.getFirst().getNotaFinal();
            }
        }

    
        if (contador > 0) {
            promedio = notasIndividuales / contador;
        } else {
            promedio = 0;
        }
        
        ArrayList<Curso> cursoNombre = cursoDb.bucarCursoPorNombreCodigo(curso);
        
        String nombreCurso;
        if (!cursoNombre.isEmpty()) {
            nombreCurso = cursoNombre.getFirst().getNombreCurso();
        } else {
            nombreCurso = "SIN NOMBRE";
        }

        if (promedio > 80) {
            dificultad = "FACIL";
        } else if (promedio > 60) {
            dificultad = "MODERADA";
        } else {
            dificultad = "DICIL";
        }
        
        GetReporteAcademico promedioCurso = new GetReporteAcademico(curso, nombreCurso, promedio, dificultad, contador);
        reporteAcademico.add(promedioCurso);
    }

    return reporteAcademico;
}
    

    
    public ArrayList<Prestamo> buscarPrestamosDeudas(String idEstudiante) throws ElementoNoEncotradoExcepcion {

        PrestamoDB presDb = new PrestamoDB();
        ArrayList<Prestamo> mora = presDb.obtenerPrestamosEstudiante(idEstudiante, "DEVUELTO_CON_MORA");
        return mora;

    }

    public ArrayList<CostoPorAnio> buscarCostosPendientes(String idEstudiante) {
        CostoPorAnioDB costoDb = new CostoPorAnioDB();
        LocalDate fechaActual = LocalDate.now();
        ArrayList<CostoPorAnio> costos = costoDb.costosMorosos(idEstudiante, fechaActual);
        return costos;

    }
    public ArrayList<Pagos> reportePagos(EnumPago tipoPago , String fechaIncio, String fechaFinal) throws ElementoNoEncotradoExcepcion,DatosNoValidosExcepcion{
        PagoDb pagodb = new PagoDb();
        ArrayList<Pagos> pagos = new ArrayList<>();
       
        
        try {
            LocalDate fechaIncioA = LocalDate.parse(fechaIncio);
             LocalDate fechaFinalA = LocalDate.parse(fechaFinal);
             if(fechaIncioA.isAfter(fechaFinalA)){
                 throw new DatosNoValidosExcepcion("FECHA INCICIO DESPUES DE FECHA FINAL");
             }
            pagos = pagodb.obtenerPagoPorUsuarioAnioTipo(tipoPago, fechaIncioA, fechaFinalA);

            
        } catch (Exception e) {
            pagos  = pagodb.obtenerTodsLosPagosTipo(tipoPago);
        }
        
        if(pagos.isEmpty()){
            throw  new ElementoNoEncotradoExcepcion("NO SE ENCONTRARON PAGOS REGISTRADOS");
        }
        return pagos;
    }
    public ArrayList<Prestamo> prestamosConMora() throws ElementoNoEncotradoExcepcion{
     PrestamoDB prestamoDb = new PrestamoDB();
     ArrayList<Prestamo> prestamo = prestamoDb.obtenerPrestamosPendientes();
     if(prestamo.isEmpty()){
         throw new ElementoNoEncotradoExcepcion("SIN PRESTAMOS CON MORA");
     }
     return prestamo;
        
    }
   
    
    

}
