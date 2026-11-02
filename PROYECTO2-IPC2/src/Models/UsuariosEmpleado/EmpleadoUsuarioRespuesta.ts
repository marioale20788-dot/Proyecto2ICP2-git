import { tipoUsuario } from "./TipoUsuario";

export interface RespuestaEmpleado{
        id:String,
    correoElectronico:String
   rol:tipoUsuario,
 estado:boolean
salario:number
   nombreCompleto:String,
fechaDeContratacion:Date
}