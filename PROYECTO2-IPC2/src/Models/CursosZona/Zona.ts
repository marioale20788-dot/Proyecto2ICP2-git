export interface ZonaActividadRespuesta {
  idActividad: string;
  nombreActividad: string;
  ponderacion: number;
  fechaEntrega: string; 
  idAsignacion: string;
}

export interface NuevaActividadZona {
  nombreActividad: string;
  ponderacion: number;
  fechaEntrega: string;
  idAsignacion: string;
}

export interface UpdateActividadRequest {
  nombre: string;
  fechaEntrega: string;
  ponderacion: number;
}