export interface Curso {
  codigoCurso: string;
  nombreCurso: string;
  descripcionCurso: string;
  estado: boolean;
}

export interface UpdateCurso {
  nombreCurso: string;
  descripcionCurso: string;
  estado: boolean;
}