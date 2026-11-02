import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CicloLectivoRespuesta } from '../../../Models/CicloLectivo/CicloLectivo';
import { servicios } from '../../../Services/Eventos/event.service';


@Component({
  imports: [RouterLink],
  selector: 'app-lista-ciclos',
  templateUrl: './lista-ciclos.component.html',
})
export class ListaCiclosComponent {
  anioCiclo!: number;
  @Input() ciclos: CicloLectivoRespuesta[] = [];

  @Output()
  idCicloAElminar = new EventEmitter<number>();

  constructor(private servicios: servicios) {}

  eliminarCiclo(anioCicloa: number): void {
    this.anioCiclo = anioCicloa;
    this.idCicloAElminar.emit(this.anioCiclo);
  }
}