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
  contactoDeEmergencia: number;
  estado:boolean;

  graduado:boolean;
}


export interface NuevoEstudianteRequest {
  dpiUsuario: string;
  contrasena: string;
  correoElectronico: string;
  nombreEstudiante: string;

  graduado:boolean
}

export interface NuevoEstudianteEncargadoRequest {
  idEstudiante: string;
  dpiEncargado: string;
  nombreEncargado: string;
  direccionEncargado: string;
}

export interface NuevoEstudianteDatosMedicosRequest {
  idEstudiante: string;
  alergias: string;
  enfermedades: string;
  tipoDeSangre: string;
  medicamentos: string;
  contactoDeEmergencia: number;
}

export interface RegistroEstudianteCompleto {
  estudiante: NuevoEstudianteRequest;
  encargado: NuevoEstudianteEncargadoRequest;
  datosMedicos: NuevoEstudianteDatosMedicosRequest;
}


export interface UpdateEstudianteRequest {
  correoElectronico: string;
  estado: boolean;
  nombreEstudiante: string;
  nombreEncargado: string;
  direccion: string;
  alergias: string;
  enfermedades: string;
  tipoDeSangre: string;
  medicamentos: string;
  contactoDeEmergencia: number;

  graduado:boolean;
}
export interface AlumnoMoroso {
  idEstudiante: string;
  nombreEstudiante: string;
  estado: string;
  deudasPendiantes: string;
  totalDeudas: number;
}