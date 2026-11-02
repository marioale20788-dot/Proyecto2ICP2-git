
export interface PrestamoRequest {
  idPrestamo?: string;
  idLibro: string;
  idUsuario: string;
  cantidad: number;
  fechaDePrestamo: string;        
  fechaDeEntregaEsperada: string;
}


export interface PrestamoRespuesta {
  idPrestamo: string;
  idLibro: string;
  cantidad: number;
  fechaPrestamo: string;
  fechaDeEntregaEsperada: string;
  estimacionMora: number;
  idUsuario:string
}

export interface FinalizarPrestamo {
  idPrestamo: string;
  idEstudiante: string;
  fechaEntregaFinal: string;
}