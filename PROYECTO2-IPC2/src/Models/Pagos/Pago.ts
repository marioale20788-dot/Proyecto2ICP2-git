export enum EnumPago {
  INSCRIPCION = 'INSCRIPCION',
  MENSUALIDAD = 'MENSUALIDAD',
  MORA = 'MORA_BIBLIOTECA',
  EXTRA = 'ACTIVIDAD'
}

export interface NewPagoRequest {
  tipoPago: EnumPago;
  monto: number;
  fechaDePago: string;
  idEstudiante: string;
  idSecretaria: string;
  idCosto: string | null;
  idPrestamo: string | null;
}

export interface GetCostosPorAnioEstudianteRequest {
  idCosto: string;
  monto: number;
  fechaLimite: string;
  estado: boolean;
  idEstudiante: string;
  cicloLectivo: number;
}

export interface GetPrestamosConMoraEstudiante {
  idPrestamo: string;
  fechaPrestamo: string;
  fechaEsperadaEntrega: string;
  fechaEntregaReal: string;
  mora: number;
  estado: string;
  idLibro: string;
  idUsuario: string;
}