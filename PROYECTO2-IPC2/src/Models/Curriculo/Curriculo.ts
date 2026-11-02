export interface NuevoCurriculoRequest {
  idGrado: string;
  codigoCurso: string;
  cicloLectivo: number;
}

export interface CurriculoRespuesta {
  idCurriculo: string;
  idGrado: string;
  codigoCurso: string;
  nombreCurso: string;
  cicloLectivo: number;
}