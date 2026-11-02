import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { IncripcionRespuesta } from '../../../Models/Inscripcion/Inscripcion';


@Component({
  imports: [RouterLink],
  selector: 'app-lista-inscripciones',
  templateUrl: './lista-incripciones.component.html',
})
export class ListaInscripcionesComponent {

  @Input() inscripciones: IncripcionRespuesta[] = [];

  @Output() idInscripcionAEliminar = new EventEmitter<IncripcionRespuesta>();

  eliminarInscripcion(inscripcion: IncripcionRespuesta): void {
    this.idInscripcionAEliminar.emit(inscripcion);
  }

}