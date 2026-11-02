import { tipoUsuario } from "./TipoUsuario"

export interface nuevoUsuario{
 id:string,
 contrasena:string,
 correoElectronico:String,
rol:tipoUsuario,  
salario: number,
nombreCompleto: String
    fechaDeContratacion:Date
    estado:boolean
}