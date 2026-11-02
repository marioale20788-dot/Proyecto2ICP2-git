import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { Curso } from '../../../Models/Curso/Curso';
import { servicios } from '../../../Services/Eventos/event.service';

import { ModalConfirmationComponent } from '../../../Components/Modal/modal-confirmation.component';
import { ListaCursosComponent } from '../../../Components/Curso/Lista curso/lista-curso.component';

@Component({
  imports: [ReactiveFormsModule, ModalConfirmationComponent, ListaCursosComponent],
  selector: 'app-cursos-page',
  templateUrl: './curso-page.html',
})
export class CursosPage implements OnInit {

  cursos = signal<Curso[]>([]);
  errorMensage = signal<string | null>(null);
  valorABuscarForm!: FormGroup;
  paginaActual: number = 1;
  paginacionActiva: boolean = true;
  codigoCursoAEliminar = signal<string | undefined>(undefined);
  eliminado = signal<boolean>(false);

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.valorABuscarForm = this.formBuilder.group({
      valor: [null, [Validators.required]]
    });
    this.buscarTodos();
  }

  buscarTodos(): void {
    this.paginacionActiva = true;
    this.servicios.obtenerTodosLosCursos(this.paginaActual).subscribe({
      next: (cursosObtenidos: Curso[]) => {
        this.cursos.set(cursosObtenidos);
      },
      error: (error: HttpErrorResponse) => {
        this.errorMensage.set(error.error || 'Error al obtener cursos');
      }
    });
  }

  siguientePagina(): void {
    this.paginaActual++;
    this.buscarTodos();
  }

  paginaAnterior(): void {
    if (this.paginaActual > 1) {
      this.paginaActual--;
      this.buscarTodos();
    }
  }

  eliminarCurso(codigoCurso: string): void {
    this.codigoCursoAEliminar.set(codigoCurso);
    this.eliminado.set(false);
  }

  eliminar(): void {
    const codigo = this.codigoCursoAEliminar();
    if (!codigo) return;

    this.servicios.eliminarCurso(codigo).subscribe({
      next: () => {
        this.eliminado.set(true);
        this.buscarTodos();
      },
      error: (error: HttpErrorResponse) => {
        if (error.status === 409) {
          this.errorMensage.set('No se puede eliminar el curso porque está en uso por otros registros.');
        } else {
          this.errorMensage.set(error.error || 'Error al eliminar el curso');
        }
      }
    });
  }

  buscarPorCodigoNombre(): void {
    this.paginacionActiva = false;
    const identificador = this.valorABuscarForm.value.valor;
    if (!identificador) {
      this.paginacionActiva = true;
      return;
    }
    this.servicios.obtenerCursoPorCodigoNombre(identificador).subscribe({
      next: (cursoObtenido: Curso) => {
        this.cursos.set([cursoObtenido]);
      },
      error: (error: HttpErrorResponse) => {
        this.paginacionActiva = true;
        if (error.status === 404) {
          this.errorMensage.set('No se encontró ningún curso con ese código o nombre.');
        } else {
          this.errorMensage.set(error.error || 'Error en la búsqueda.');
        }
      }
    });
  }
}