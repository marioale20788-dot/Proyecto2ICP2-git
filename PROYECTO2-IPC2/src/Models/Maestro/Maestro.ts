
export interface AsignacionMaestro {
  idSeccion: string;
  nombreSeccion: string;
  idCurriculo: string;
  idEmpleado: string;
  ciclo: number;
  codigoCurso: string;
  nombreCurso: string;
  idGrado: string;
  nombreGrado: string;
  nivelGrado: string;
  idAsignacion: string;
}

export interface EstudiantesInscritosRequest {
  idSeccion: string;
  ciclo: number;
  idAsignacion: string;
}


export interface EstudianteInscrito {
  fechaInscripcion: string;
  idEstudiante: string;
  nombreEstudiante: string;
  idAsignacion: string;
}