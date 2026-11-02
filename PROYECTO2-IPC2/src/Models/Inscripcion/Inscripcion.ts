
export interface NewIncricpionRequest {
  fechaInscripcion: Date;
  idEstudiante: string;
  idSeccion: string;
  idCicloLectivo: number;
}


export interface IncripcionRespuesta {
  idIncripcion: string;
  fechaInscripcion: Date;
  idEstudiante: string;
  idSeccion: string;
  descripcionSeccion: string;
  idCicloLectivo: number;
}


export interface UpdateIncripcionRequest {
  idSeccion: string;
}