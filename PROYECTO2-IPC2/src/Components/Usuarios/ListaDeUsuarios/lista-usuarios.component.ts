import { Component, Input } from '@angular/core';
import { RespuestaEmpleado } from '../../../Models/UsuariosEmpleado/EmpleadoUsuarioRespuesta';
import { CurrencyPipe, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  imports: [CurrencyPipe, DatePipe, RouterLink],
  selector: 'app-lista-usuarios',
  templateUrl: './lista-usuarios.component.html',
})
export class ListaUsuariosComponent {

@Input() 
listaUsuarios: RespuestaEmpleado[]=[];



}
