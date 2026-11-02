import { Component, EventEmitter, Input, Output } from '@angular/core';

import { GradoRespuesta } from '../../../Models/Grados/Grados';
import { servicios } from '../../../Services/Eventos/event.service';


@Component({
  imports: [],
  selector: 'app-lista-grados',
  templateUrl: './lista-grados.component.html',
})
export class ListaGradosComponent {
  idGrado!: string;
  @Input() grados: GradoRespuesta[] = [];

  @Output()
  idGradoAElminar = new EventEmitter<string>();

  constructor(private servicios: servicios) {}

  eliminarGrado(idGradoa: string): void {
    this.idGrado = idGradoa;
    this.idGradoAElminar.emit(this.idGrado);
  }
}