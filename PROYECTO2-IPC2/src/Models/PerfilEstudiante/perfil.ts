export interface EstudianteRespuesta {
  dpiUsuario: string;
  correoElectronico: string;
  nombreEstudiante: string;
  dpiEncargado: string;
  nombreEncargado: string;
  direccionEncargado: string;
  alergias: string;
  enfermedades: string;
  tipoDeSangre: string;
  medicamentos: string;
  estado: boolean;
  contactoDeEmergencia: number;
  graduado: boolean;
}

export interface NotaFinalResponse {
  idNota: string;
  notaFinal: number;
  idEstudiante: string;
  codigoCurso: string;
  ciclo: number;
  nombreCurso: string;
}

export interface Pagos {
  idPago: string;
  tipoPago: string;
  monto: number;
  fechaPago: string;
  idEstuidante: string;
  idSecretaria: string;
  idCosto: string;
  idPrestamo: string;
}

export interface CostosPendientes {
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