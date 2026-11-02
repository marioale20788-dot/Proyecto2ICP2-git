import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { SeccionRespuesta } from '../../../Models/Seccion/Seccion';


@Component({
  imports: [RouterLink],
  selector: 'app-lista-secciones',
  templateUrl: './lista-secciones.component.html',
})
export class ListaSeccionesComponent {

  idSeccion!: string;
  @Input() secciones: SeccionRespuesta[] = [];

  @Output() idSeccionAEliminar = new EventEmitter<string>();

  eliminarSeccion(idSeccion: string): void {
    this.idSeccion = idSeccion;
    this.idSeccionAEliminar.emit(this.idSeccion);
  }
}