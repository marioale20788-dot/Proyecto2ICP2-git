export interface LoginRequest {
  idUsuario: string;
  contrasena: string;
}

export interface Usuario {
  idUsuario: string;
  correoElectronico: string;
  tipo: string;
  estado: boolean;
}