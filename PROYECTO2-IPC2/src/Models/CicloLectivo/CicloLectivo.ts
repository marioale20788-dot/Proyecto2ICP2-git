
export interface CicloLectivoRespuesta {
  anio: number;
  fechaInicio: string;
  fechaFinal: string;
  estado: boolean;
}

export interface NewCicloRequest {
  anio: number;
  fechaInicio: string;
  fechaFinal: string;
  estado: boolean;
}

export interface UpdateCicloRequest {
  fechaInicio: string;
  fechaFinal: string;
  estado: boolean;
}