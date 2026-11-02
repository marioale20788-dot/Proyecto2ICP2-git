import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Carrera } from '../../../Models/Carrera/carrera';


@Component({
  imports: [RouterLink],
  selector: 'app-lista-carreras',
  templateUrl: './lista-carreras.component.html',
})
export class ListaCarrerasComponent {

  idCarrera!: string;
  @Input() carreras: Carrera[] = [];

  @Output() idCarreraAEliminar = new EventEmitter<string>();

  eliminarCarrera(idCarrera: string): void {
    this.idCarrera = idCarrera;
    this.idCarreraAEliminar.emit(this.idCarrera);
  }
}