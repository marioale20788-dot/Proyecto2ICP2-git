export interface NuevaAsignacionRequest {
  idUsuario: string;
  idSeccion: string;
  idCurriculo: string;
}

export interface NuevaAsignacionRespuesta {
  idAsignacion: string;
  idUsuario: string;
  nombreDocente: string;
  idSeccion: string;
  nombreSeccion: string;
  idCurriculo: string;
  nombreCurso: string;
}