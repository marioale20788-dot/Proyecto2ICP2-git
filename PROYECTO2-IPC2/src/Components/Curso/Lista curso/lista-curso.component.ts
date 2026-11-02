import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { Curso } from '../../../Models/Curso/Curso';


@Component({
  imports: [RouterLink],
  selector: 'app-lista-cursos',
  templateUrl: './lista-curso.component.html',
})
export class ListaCursosComponent {

  @Input() cursos: Curso[] = [];
  @Output() codigoCursoAEliminar = new EventEmitter<string>();

  eliminarCurso(codigoCurso: string): void {
    this.codigoCursoAEliminar.emit(codigoCurso);
  }
}