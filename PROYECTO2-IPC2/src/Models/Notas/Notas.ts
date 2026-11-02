export interface GetNotas {
  idActividad: string;
  nombreActividad: string;
  punteActividad: number;
  punteoDefinido: string;
  idEstudiante: string;
}

export interface NuevaNotaRequest {
  idEstudiante: string;
  idActividad: string;
  punteo: number;
}

export interface NuevanotaFinalRequest {
  notaFinal: number;
  idEstudiante: string;
  codigoCurso: string;
  cicloLectivo: number;
}

export interface EstudianteNotas{
  idEstudiante: string;
  nombreEstudiante: string;
  notas: GetNotas[];
  totalZona: number;
  notaFinalInput: number;
}
export interface EstudianteMatriz{
  idEstudiante: string;
  nombreEstudiante: string;
  notas: GetNotas[];
  notaFinalInput: number;
}

export interface ActividadCabecera {
  idActividad: string;
  nombreActividad: string;
  punteoDefinido: string;
}
