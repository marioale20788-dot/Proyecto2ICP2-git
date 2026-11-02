import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { EstudianteRespuesta } from '../../../Models/Estudiantes/Estudiante';


@Component({
  selector: 'app-lista-estudiantes',
  imports: [RouterLink],
  templateUrl: './lista-estudiantes.component.html',
})
export class ListaEstudiantesComponent {

  @Input() estudiantes: EstudianteRespuesta[] = [];

  
}