export interface NuevaAsistenciaRequest {
  fecchaAsistencia: string;
  estado: boolean;         
  idEstudiante: string;
  idAsignacion: string;
}

export interface AsistenciaRepuesta {
  idEstudiante: string;
  faltas: number;
  llegadas: number;
}

export interface EstudianteAsistenciaRow {
  idEstudiante: string;
  nombreEstudiante: string;
  estado: boolean; 
  faltas?: number;
  llegadas?: number;
}