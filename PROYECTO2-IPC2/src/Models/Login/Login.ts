export interface LoginRequest {
  idUsuario: string;
  contrasena: string;
}
export interface LoginResponse {
  token: string;
}