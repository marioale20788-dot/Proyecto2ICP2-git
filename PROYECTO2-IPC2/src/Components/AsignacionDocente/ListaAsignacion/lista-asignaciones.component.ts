import { Component, EventEmitter, Input, Output } from '@angular/core';
import { NuevaAsignacionRespuesta } from '../../../Models/Asignacion Docente/AsignacionDocente';


@Component({
  selector: 'app-lista-asignaciones',
  imports: [],
  templateUrl: './lista-asignaciones.component.html',
})
export class ListaAsignacionesComponent {

  @Input() asignaciones: NuevaAsignacionRespuesta[] = [];
  @Output() idAsignacionAEliminar = new EventEmitter<string>();

  eliminar(idAsignacion: string): void {
    this.idAsignacionAEliminar.emit(idAsignacion);
  }
}