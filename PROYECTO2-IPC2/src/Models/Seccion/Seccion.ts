export interface SeccionRespuesta {
  idSeccion: string;
  nombreSeccion: string;
  idGrado: string;
}

export interface NuevaSeccionRequest {
  nombreSeccion: string;
  idGrado: string;
  anio: number;
}