import { Injectable } from "@angular/core";
import { RestConstants } from "../../Shared/api-rest/aplicacionBackend";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { libro } from "../../Models/Libros/Libros";
import { updateLibroRequest } from "../../Models/Libros/Update";
import { RespuestaEmpleado } from "../../Models/UsuariosEmpleado/EmpleadoUsuarioRespuesta";
import { tipoUsuario } from "../../Models/UsuariosEmpleado/TipoUsuario";
import { nuevoUsuario } from "../../Models/UsuariosEmpleado/NuevoUsuario";
import { updateUsuario } from "../../Models/UsuariosEmpleado/UpdaateUsuario";
import { AlumnoMoroso, EstudianteRespuesta, RegistroEstudianteCompleto, UpdateEstudianteRequest } from "../../Models/Estudiantes/Estudiante";
import { CicloLectivoRespuesta, NewCicloRequest, UpdateCicloRequest } from "../../Models/CicloLectivo/CicloLectivo";
import { GradoRespuesta, NewGradoRequest } from "../../Models/Grados/Grados";
import { Carrera, UpdateCarrera } from "../../Models/Carrera/carrera";
import { Curso, UpdateCurso } from "../../Models/Curso/Curso";
import { NuevaSeccionRequest, SeccionRespuesta } from "../../Models/Seccion/Seccion";
import { CurriculoRespuesta, NuevoCurriculoRequest } from "../../Models/Curriculo/Curriculo";
import { NuevaAsignacionRequest, NuevaAsignacionRespuesta } from "../../Models/Asignacion Docente/AsignacionDocente";
import { IncripcionRespuesta, NewIncricpionRequest, UpdateIncripcionRequest } from "../../Models/Inscripcion/Inscripcion";
import { EnumPago, GetCostosPorAnioEstudianteRequest, GetPrestamosConMoraEstudiante, NewPagoRequest } from "../../Models/Pagos/Pago";

import { AsignacionMaestro, EstudianteInscrito, EstudiantesInscritosRequest } from "../../Models/Maestro/Maestro";
import { AsistenciaRepuesta, NuevaAsistenciaRequest } from "../../Models/Maestro/Asistencia";
import { NuevaActividadZona, UpdateActividadRequest, ZonaActividadRespuesta } from "../../Models/CursosZona/Zona";
import { GetNotas, NuevanotaFinalRequest, NuevaNotaRequest } from "../../Models/Notas/Notas";
import { FinalizarPrestamo, PrestamoRequest, PrestamoRespuesta } from "../../Models/Prestamo/Prestamo";
import { CostosPendientes, NotaFinalResponse, Pagos } from "../../Models/PerfilEstudiante/perfil";
import { ReporteAcademico } from "../../Models/Reportes/Reporte";



@Injectable({

providedIn: 'root'


})
export class servicios{

RestDireccion = new RestConstants;
constructor (private httpClient:HttpClient) {}

public obtenerTodosLosLibros(pagina:number): Observable<libro[]>{
    return this.httpClient.get<libro[]>(`${this.RestDireccion.getApiURL()}/Libro/pagina/${pagina}`);
}
public obtenerLibroPorCodigoNombre(valor:string):Observable<libro>{
    return this.httpClient.get<libro>(`${this.RestDireccion.getApiURL()}/Libro/${valor}`);
}
public updateLibro(id: string, UpdateLibro: updateLibroRequest): Observable<libro> {
   
    return this.httpClient.put<libro>(`${this.RestDireccion.getApiURL()}/Libro/${id}`, UpdateLibro);
}
public crearLibro(Libro:libro): Observable<void>{

return this.httpClient.post<void>(`${this.RestDireccion.getApiURL()}/Libro`,Libro);

}
public eliminarLibro(idLibro:String):Observable<void>{
    return this.httpClient.delete<void>(`${this.RestDireccion.getApiURL()}/Libro/${idLibro}`);
}

public buscarUsuarioEstado(estado:boolean, pagina:number):Observable<RespuestaEmpleado[]>{
    return this.httpClient.get<RespuestaEmpleado[]>(`${this.RestDireccion.getApiURL()}/Administradores/${estado}/${pagina}`);
}
public buscarUsuariosEstadoRol(estado:boolean,rol :tipoUsuario,pagina:number):Observable<RespuestaEmpleado[]>{
    return this.httpClient.get<RespuestaEmpleado[]>(`${this.RestDireccion.getApiURL()}/Administradores/rol/${rol}/${estado}/${pagina}`);
}
public buscarUsuariosDpi(dpi:String):Observable<RespuestaEmpleado>{
    return this.httpClient.get<RespuestaEmpleado>(`${this.RestDireccion.getApiURL()}/Administradores/dpi/${dpi}`);
}
public crearUsuario(Usuario:nuevoUsuario):Observable<void>{
    return this.httpClient.post<void>(`${this.RestDireccion.getApiURL()}/Administradores`,Usuario);

    
}
public updateUsuario(id:String , updateUsuario:updateUsuario): Observable<RespuestaEmpleado>{
     return this.httpClient.put<RespuestaEmpleado>(`${this.RestDireccion.getApiURL()}/Administradores/${id}`, updateUsuario);
}


obtenerTodosLosEstudiantes(estado: boolean, paginaActual:number): Observable<EstudianteRespuesta[]> {
  return this.httpClient.get<EstudianteRespuesta[]>(`${this.RestDireccion.getApiURL()}/Estudiante/${estado}/${paginaActual}`);
}

buscarEstudiantePorDpi(dpi: string): Observable<EstudianteRespuesta> {
  return this.httpClient.get<EstudianteRespuesta>(`${this.RestDireccion.getApiURL()}/Estudiante/dpi/${dpi}`);
}

crearEstudiante(registroCompleto: RegistroEstudianteCompleto): Observable<void> {
  return this.httpClient.post<void>(`${this.RestDireccion.getApiURL()}/Estudiante`, registroCompleto);
}

updateEstudiante(dpi: string, updateEstudiante: UpdateEstudianteRequest): Observable<EstudianteRespuesta> {
  return this.httpClient.put<EstudianteRespuesta>(`${this.RestDireccion.getApiURL()}/Estudiante/${dpi}`, updateEstudiante);
}

desactivarEstudiante(dpi: string, estado: boolean): Observable<EstudianteRespuesta> {
  return this.httpClient.put<EstudianteRespuesta>(`${this.RestDireccion.getApiURL()}/Estudiante/${dpi}`, { estado });
}


obtenerTodosLosCiclos(pagina: number): Observable<CicloLectivoRespuesta[]> {
  return this.httpClient.get<CicloLectivoRespuesta[]>( `${this.RestDireccion.getApiURL()}/CicloLectivo/${pagina}`
  );
}

obtenerCicloPorAnio(anio: number): Observable<CicloLectivoRespuesta> {
  return this.httpClient.get<CicloLectivoRespuesta>(  `${this.RestDireccion.getApiURL()}/CicloLectivo/Anio/${anio}`
  );
}

crearCiclo(nuevoCiclo: NewCicloRequest): Observable<void> {
  return this.httpClient.post<void>( `${this.RestDireccion.getApiURL()}/CicloLectivo`, nuevoCiclo
  );
}

actualizarCiclo(anio: number, updatesCiclo: UpdateCicloRequest): Observable<CicloLectivoRespuesta> {
  return this.httpClient.put<CicloLectivoRespuesta>(  `${this.RestDireccion.getApiURL()}/CicloLectivo/${anio}`,   updatesCiclo
  );    
}

eliminarCiclo(anio: number): Observable<void> {
  return this.httpClient.delete<void>( `${this.RestDireccion.getApiURL()}/CicloLectivo/${anio}`
  );
}


obtenerTodosLosGrados(pagina: number): Observable<GradoRespuesta[]> {
  return this.httpClient.get<GradoRespuesta[]>(   `${this.RestDireccion.getApiURL()}/Grado/${pagina}` );
}

obtenerGradosPorNivel(idNivel: string): Observable<GradoRespuesta[]> {
return this.httpClient.get<GradoRespuesta[]>(`${this.RestDireccion.getApiURL()}/Grado/Nivel/${idNivel}` );
}

crearGrado(nuevoGrado: NewGradoRequest): Observable<void> {
  return this.httpClient.post<void>(`${this.RestDireccion.getApiURL()}/Grado`, nuevoGrado );
}

eliminarGrado(idGrado: string): Observable<void> {
  return this.httpClient.delete<void>(`${this.RestDireccion.getApiURL()}/Grado/${idGrado}`);
}

obtenerCarrerasPorEstado(estado: boolean = true): Observable<Carrera[]> {
  return this.httpClient.get<Carrera[]>( `${this.RestDireccion.getApiURL()}/Carreras/Estado/${estado}` );
}

obtenerTodosLosCursos(pagina: number): Observable<Curso[]> {
  return this.httpClient.get<Curso[]>(  `${this.RestDireccion.getApiURL()}/Curso/${pagina}`
  );
}

obtenerCursoPorCodigoNombre(identificador: string): Observable<Curso> {
  return this.httpClient.get<Curso>(`${this.RestDireccion.getApiURL()}/Curso/Id/${identificador}` );
}

crearCurso(nuevoCurso: Curso): Observable<void> {
  return this.httpClient.post<void>(`${this.RestDireccion.getApiURL()}/Curso`, nuevoCurso);
}

actualizarCurso(codigoCurso: string, cursoUpdate: UpdateCurso): Observable<Curso> {
  return this.httpClient.put<Curso>( `${this.RestDireccion.getApiURL()}/Curso/${codigoCurso}`,  cursoUpdate
  );
}

eliminarCurso(codigoCurso: string): Observable<void> { return this.httpClient.delete<void>(   `${this.RestDireccion.getApiURL()}/Curso/${codigoCurso}` );
}
obtenerGradoPorId(idGrado: string): Observable<GradoRespuesta> {
  return this.httpClient.get<GradoRespuesta>( `${this.RestDireccion.getApiURL()}/Grado/id/${idGrado}`
  );
}

obtenerTodasLasSecciones(pagina: number): Observable<SeccionRespuesta[]> {
  return this.httpClient.get<SeccionRespuesta[]>( `${this.RestDireccion.getApiURL()}/Seccion/pagina/${pagina}`
);
}

obtenerSeccionesPorNombre(nombreSeccion: string, pagina: number): Observable<SeccionRespuesta[]> {
  return this.httpClient.get<SeccionRespuesta[]>( `${this.RestDireccion.getApiURL()}/Seccion/${nombreSeccion}/${pagina}` );
}

obtenerSeccionesPorGrado(idGrado: string): Observable<SeccionRespuesta[]> {
  return this.httpClient.get<SeccionRespuesta[]>( `${this.RestDireccion.getApiURL()}/Seccion/Grado/${idGrado}` );
}

crearSeccion(nuevaSeccion: NuevaSeccionRequest): Observable<void> {
  return this.httpClient.post<void>( `${this.RestDireccion.getApiURL()}/Seccion`,  nuevaSeccion
  );
}

actualizarSeccion(idSeccion: string, seccionUpdate: NuevaSeccionRequest): Observable<SeccionRespuesta> {
  return this.httpClient.put<SeccionRespuesta>( `${this.RestDireccion.getApiURL()}/Seccion/${idSeccion}`,  seccionUpdate);
}

eliminarSeccion(idSeccion: string): Observable<void> {
  return this.httpClient.delete<void>( `${this.RestDireccion.getApiURL()}/Seccion/${idSeccion}`);
}
obtenerSeccionPorId(idSeccion: string): Observable<SeccionRespuesta> {
  return this.httpClient.get<SeccionRespuesta>( `${this.RestDireccion.getApiURL()}/Seccion/id/${idSeccion}`
  );
}


obtenerTodasLasCarrerasCrud(estado: boolean, pagina: number): Observable<Carrera[]> {
  return this.httpClient.get<Carrera[]>( `${this.RestDireccion.getApiURL()}/Carreras/Estado/${estado}/${pagina}` );
}

obtenerCarreraPorIdONombre(identificador: string): Observable<Carrera> {
  return this.httpClient.get<Carrera>(   `${this.RestDireccion.getApiURL()}/Carreras/${identificador}`);
}

crearCarrera(nuevaCarrera: Carrera): Observable<void> {
  return this.httpClient.post<void>(
`${this.RestDireccion.getApiURL()}/Carreras`,  nuevaCarrera);
}

actualizarCarrera(idCarrera: string, updateCarrera: UpdateCarrera): Observable<Carrera> {
  return this.httpClient.put<Carrera>( `${this.RestDireccion.getApiURL()}/Carreras/${idCarrera}`,updateCarrera
  );
}

eliminarCarrera(idCarrera: string): Observable<void> {
  return this.httpClient.delete<void>(`${this.RestDireccion.getApiURL()}/Carreras/${idCarrera}`);
}

obtenerTodasLasSeccionesSelect(): Observable<SeccionRespuesta[]> {
  return this.httpClient.get<SeccionRespuesta[]>(`${this.RestDireccion.getApiURL()}/Seccion` );
}

obtenerCurriculoPorGradoCiclo(idGrado: string, ciclo: number): Observable<CurriculoRespuesta[]> {
  return this.httpClient.get<CurriculoRespuesta[]>( `${this.RestDireccion.getApiURL()}/Curriculo/${idGrado}/${ciclo}`);
}

insertarCurriculo(nuevoCurriculo: NuevoCurriculoRequest): Observable<void> {
  return this.httpClient.post<void>(`${this.RestDireccion.getApiURL()}/Curriculo`, nuevoCurriculo);
}

borrarCursoDelCurriculo(idCurriculo: string): Observable<void> {
  return this.httpClient.delete<void>( `${this.RestDireccion.getApiURL()}/Curriculo/${idCurriculo}`);
}
obtenerGradosSelect(): Observable<GradoRespuesta[]> {
  return this.httpClient.get<GradoRespuesta[]>( `${this.RestDireccion.getApiURL()}/Grado`);
}

obtenerCursosSelect(): Observable<Curso[]> {
  return this.httpClient.get<Curso[]>(`${this.RestDireccion.getApiURL()}/Curso` );
}

asignarDocente(request: NuevaAsignacionRequest): Observable<void> {
return this.httpClient.post<void>( `${this.RestDireccion.getApiURL()}/AsignacionDocente`,request);
}

obtenerAsignacionPorSeccionCurriculo(idSeccion: string, idCurriculo: string): Observable<NuevaAsignacionRespuesta> {
  return this.httpClient.get<NuevaAsignacionRespuesta>( `${this.RestDireccion.getApiURL()}/AsignacionDocente/${idSeccion}/${idCurriculo}`);
}

borrarAsignacionDocente(idAsignacion: string): Observable<void> {
  return this.httpClient.delete<void>(`${this.RestDireccion.getApiURL()}/AsignacionDocente/${idAsignacion}`);
}
 inscribirEstudiante(nuevaInscripcion: NewIncricpionRequest): Observable<void> {
    return this.httpClient.post<void>( `${this.RestDireccion.getApiURL()}/Incripcion`, nuevaInscripcion);
  }

  obtenerInscripcionesPorEstudiante(idEstudiante: string): Observable<IncripcionRespuesta[]> {
    return this.httpClient.get<IncripcionRespuesta[]>(  `${this.RestDireccion.getApiURL()}/Incripcion/Estudiante/${idEstudiante}`
);
  }

  obtenerInscripcionesPorCiclo(ciclo: number): Observable<IncripcionRespuesta[]> {
    return this.httpClient.get<IncripcionRespuesta[]>( `${this.RestDireccion.getApiURL()}/Incripcion/Ciclo/${ciclo}` );
  }

  updateInscripcion(idInscripcion: string, update: UpdateIncripcionRequest): Observable<IncripcionRespuesta> {
    return this.httpClient.put<IncripcionRespuesta>( `${this.RestDireccion.getApiURL()}/Incripcion/${idInscripcion}`,update);
  }

  borrarInscripcion(inscripcionAEliminar: NewIncricpionRequest): Observable<void> {
    return this.httpClient.request<void>( 'delete', `${this.RestDireccion.getApiURL()}/Incripcion`, { body: inscripcionAEliminar } );
  }
  obtenerInscripcionPorId(idInscripcion: string): Observable<IncripcionRespuesta> {
  return this.httpClient.get<IncripcionRespuesta>(`${this.RestDireccion.getApiURL()}/Incripcion/id/${idInscripcion}` );
}

obtenerCostosPendientes(idEstudiante: string, idCiclo: number): Observable<GetCostosPorAnioEstudianteRequest[]> {
    return this.httpClient.get<GetCostosPorAnioEstudianteRequest[]>( `${this.RestDireccion.getApiURL()}/Pagos/${idEstudiante}/${idCiclo}`
    );
  }

  obtenerPrestamosConMora(idEstudiante: string): Observable<GetPrestamosConMoraEstudiante[]> {
    return this.httpClient.get<GetPrestamosConMoraEstudiante[]>(`${this.RestDireccion.getApiURL()}/Pagos/${idEstudiante}`
    );
  }

  registrarPago(nuevoPago: NewPagoRequest): Observable<string> {
    return this.httpClient.post( `${this.RestDireccion.getApiURL()}/Pagos`, nuevoPago,  { responseType: 'text' }
    );
  }


obtenerUrlReportePdf(idPago: string): string {
  return `http://localhost:8080/PROYECTO2IPC2/CrearReporte?idPago=${idPago}`;
}
obtenerUrlSolvenciaPdf(idUsuario: string, ciclo: string): string {
  return `http://localhost:8080/PROYECTO2IPC2/CrearSolvenciaDePago?idUsuario=${idUsuario}&ciclo=${ciclo}`;
}
obtenerAsignaciones(idMaestro: string, ciclo: number): Observable<AsignacionMaestro[]> {
    return this.httpClient.get<AsignacionMaestro[]>(`${this.RestDireccion.getApiURL()}/Maestros/${idMaestro}/${ciclo}`);
  }

  
  obtenerEstudiantesInscritos(parametros: EstudiantesInscritosRequest): Observable<EstudianteInscrito[]> {
     return  this.httpClient.get<EstudianteInscrito[]>(`${this.RestDireccion.getApiURL()}/Maestros/${parametros.idSeccion}/${parametros.ciclo}/${parametros.idAsignacion}`);
   
  }
  
  registrarAsistencia(listaAsistencia: NuevaAsistenciaRequest[]): Observable<void> {
    return this.httpClient.post<void>(`${this.RestDireccion.getApiURL()}/Administrarcurso`, listaAsistencia);
  }

obtenerEstadoAsistencia(idEstudiante: string): Observable<AsistenciaRepuesta[]> {
  return this.httpClient.get<AsistenciaRepuesta[]>(`${this.RestDireccion.getApiURL()}/Administrarcurso/${idEstudiante}`);
}


obtenerActividadesZona(idAsignacion: string): Observable<ZonaActividadRespuesta[]> {
  return this.httpClient.get<ZonaActividadRespuesta[]>(`${this.RestDireccion.getApiURL()}/Zona/${idAsignacion}`);
}


crearActividadZona(actividad: NuevaActividadZona): Observable<void> {
  return this.httpClient.post<void>(`${this.RestDireccion.getApiURL()}/Zona`, actividad);
}


actualizarActividadZona(idActividad: string, update: UpdateActividadRequest): Observable<ZonaActividadRespuesta> {
  return this.httpClient.put<ZonaActividadRespuesta>(`${this.RestDireccion.getApiURL()}/Zona/${idActividad}`, update);
}

eliminarActividadZona(idActividad: string): Observable<void> {
  return this.httpClient.delete<void>(`${this.RestDireccion.getApiURL()}/Zona/${idActividad}`);
}
obtenerNotasEstudiante(idRegistro: string, idUsuario: string): Observable<GetNotas[]> {
  return this.httpClient.get<GetNotas[]>(`${this.RestDireccion.getApiURL()}/Notas/${idRegistro}/${idUsuario}`);
}


insertarNotaActividad(nota: NuevaNotaRequest): Observable<any> {
  return this.httpClient.post<any>(`${this.RestDireccion.getApiURL()}/Notas`, nota);
}


insertarNotaFinal(nota: NuevanotaFinalRequest): Observable<void> {
  return this.httpClient.post<void>(`${this.RestDireccion.getApiURL()}/Notas/NotaFinal`, nota);
}

obtenerPrestamosActivos(idUsuario: string): Observable<PrestamoRespuesta[]> {
  return this.httpClient.get<PrestamoRespuesta[]>(`${this.RestDireccion.getApiURL()}/Prestamo/Prestamo/${idUsuario}`);
}


obtenerPrestamoPorId(idPrestamo: string): Observable<PrestamoRespuesta> {
  return this.httpClient.get<PrestamoRespuesta>(`${this.RestDireccion.getApiURL()}/Prestamo/${idPrestamo}`);
}


registrarPrestamo(prestamo: PrestamoRequest): Observable<void> {
  return this.httpClient.post<void>(`${this.RestDireccion.getApiURL()}/Prestamo`, prestamo);
}

actualizarPrestamo(idPrestamo: string, prestamo: PrestamoRequest): Observable<PrestamoRespuesta> {
  return this.httpClient.put<PrestamoRespuesta>(`${this.RestDireccion.getApiURL()}/Prestamo/Prestamo/${idPrestamo}`, prestamo);
}


devolverLibro(finalizarPrestamo: FinalizarPrestamo): Observable<void> {
  return this.httpClient.put<void>(`${this.RestDireccion.getApiURL()}/Prestamo`, finalizarPrestamo);
}


eliminarPrestamo(idPrestamo: string): Observable<void> {
  return this.httpClient.delete<void>(`${this.RestDireccion.getApiURL()}/Prestamo/${idPrestamo}`);
}

obtenerEstudiantePorDpi(dpi: string): Observable<EstudianteRespuesta> {
    return this.httpClient.get<EstudianteRespuesta>(`${this.RestDireccion.getApiURL()}/PerfilEstudiante/dpi/${dpi}`);
  }
obtenerPagos(idUsuario: string, ciclo: number): Observable<Pagos[]> {
    return this.httpClient.get<Pagos[]>(`${this.RestDireccion.getApiURL()}/PerfilEstudiante/Pagos/${idUsuario}/${ciclo}`);
  }
 
  obtenerNotas(idUsuario: string, ciclo: number): Observable<NotaFinalResponse[]> {
    return this.httpClient.get<NotaFinalResponse[]>(`${this.RestDireccion.getApiURL()}/PerfilEstudiante/Notas/${idUsuario}/${ciclo}`);
  }

  
  obtenerPagosRealizados(idUsuario: string, ciclo: number): Observable<Pagos[]> {
    return this.httpClient.get<Pagos[]>(`${this.RestDireccion.getApiURL()}/PerfilEstudiante/Pagos/${idUsuario}/${ciclo}`);
  }

 
 

  obtenerLibrosCatalog(pagina: number): Observable<libro[]> {
    return this.httpClient.get<libro[]>(`${this.RestDireccion.getApiURL()}/PerfilEstudiante/Libros/pagina/${pagina}`);
  }

obtenerReporteSolventes(idSeccion: string, ciclo: number): Observable<AlumnoMoroso[]> {
    return this.httpClient.get<AlumnoMoroso[]>(`${this.RestDireccion.getApiURL()}/Reportes/solvente/${idSeccion}/${ciclo}`);
  }

  obtenerUrlReporteMorososPdf(idSeccion: string, ciclo: number | string): string {
    return `http://localhost:8080/PROYECTO2IPC2/ReporteEstudiantesMorososPdf?idSeccion=${idSeccion}&ciclo=${ciclo}`;
  }
obtenerReporteAcademico(idSeccion: string, ciclo: number): Observable<ReporteAcademico[]> {
    return this.httpClient.get<ReporteAcademico[]>(`${this.RestDireccion.getApiURL()}/Reportes/RendimientoAcademico/${idSeccion}/${ciclo}`);
  }


  obtenerUrlReporteAcademicoPdf(idSeccion: string, ciclo: number | string): string {
    return `http://localhost:8080/PROYECTO2IPC2/ReporteAcademicoPdf?idSeccion=${idSeccion}&ciclo=${ciclo}`;
  }

  obtenerPagosReporte(idUsuario: string, fechaInicio: string, fechaFinal: string): Observable<Pagos[]> {
    return this.httpClient.get<Pagos[]>(`${this.RestDireccion.getApiURL()}/Reportes/Pagos/${idUsuario}/${fechaInicio}/${fechaFinal}`);
  }


  obtenerCostos(idEstudiante: string, fechaInicio: string, fechaFinal: string): Observable<GetCostosPorAnioEstudianteRequest[]> {
    return this.httpClient.get<GetCostosPorAnioEstudianteRequest[]>(`${this.RestDireccion.getApiURL()}/Reportes/Costos/${idEstudiante}/${fechaInicio}/${fechaFinal}`);
  }

 
  obtenerUrlPagosPdf(idUsuario: string, fechaInicio: string, fechaFinal: string): string {
    return `http://localhost:8080/PROYECTO2IPC2/ReportePagosPdf?idUsuario=${idUsuario}&fechaInicio=${fechaInicio}&fechaFinal=${fechaFinal}`;
  }

  obtenerUrlCostosPdf(idUsuario: string, fechaInicio: string, fechaFinal: string): string {
    return `http://localhost:8080/PROYECTO2IPC2/ReporteCostosPdf?idUsuario=${idUsuario}&fechaInicio=${fechaInicio}&fechaFinal=${fechaFinal}`;
  }
  obtenerReporteIngresos(tipo: EnumPago, fechaInicio: string, fechaFinal: string): Observable<Pagos[]> {
    return this.httpClient.get<Pagos[]>(`${this.RestDireccion.getApiURL()}/Reportes/Ingresos/${tipo}/${fechaInicio}/${fechaFinal}`);
  }

  obtenerUrlReporteIngresosPdf(tipo: EnumPago, fechaInicio: string, fechaFinal: string): string {
   return `http://localhost:8080/PROYECTO2IPC2/ReporteIngresosPdf?tipo=${tipo}&fechaInicio=${fechaInicio}&fechaFinal=${fechaFinal}`;
  }
  obtenerReporteBiblioteca(): Observable<GetPrestamosConMoraEstudiante[]> {
    return this.httpClient.get<GetPrestamosConMoraEstudiante[]>(`${this.RestDireccion.getApiURL()}/Reportes/Biblioteca`);
  }

  obtenerUrlReporteBibliotecaPdf(): string {
    return `http://localhost:8080/PROYECTO2IPC2/ReporteBibliotecaPdf`;
  }
}

