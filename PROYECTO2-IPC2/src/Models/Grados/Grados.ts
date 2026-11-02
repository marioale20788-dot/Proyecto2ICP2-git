export interface GradoRespuesta {
  nombreGrado: string;
  idGrado: string;
  idNivel: string;
  idCarrera: string;
}

export interface NewGradoRequest {
  nombreGrado: string;
  idNivel: string;
  idCarrera: string;
}