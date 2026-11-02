import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CurriculoRespuesta } from '../../../Models/Curriculo/Curriculo';

@Component({
  imports: [],
  selector: 'app-lista-curriculo',
  templateUrl: './lista-curriculo.component.html',
})
export class ListaCurriculoComponent {

  idCurriculo!: string;
  @Input() curriculoList: CurriculoRespuesta[] = [];

  @Output() idCurriculoAEliminar = new EventEmitter<string>();

  eliminar(idCurriculo: string): void {
    this.idCurriculo = idCurriculo;
    this.idCurriculoAEliminar.emit(this.idCurriculo);
  }
}