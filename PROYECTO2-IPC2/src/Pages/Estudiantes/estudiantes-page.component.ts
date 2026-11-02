import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { EstudianteRespuesta } from '../../Models/Estudiantes/Estudiante';
import { servicios } from '../../Services/Eventos/event.service';
import { ListaEstudiantesComponent } from '../../Components/Estudiante/Lista estudiantes/lista-estudiantes.component';

@Component({
  selector: 'app-estudiantes-page',
  standalone: true,
  imports: [ReactiveFormsModule, ListaEstudiantesComponent],
  templateUrl: './estudiantes-page.component.html',
})
export class EstudiantesPage implements OnInit {
  estudiantes = signal<EstudianteRespuesta[]>([]);
  errorMensage = signal<string | null>(null);
  valorABuscarForm!: FormGroup;
  
  paginaActual: number = 1;
  paginacionActiva: boolean = true;

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.valorABuscarForm = this.formBuilder.group({
      valor: [null],
      estadoFiltro: [true, [Validators.required]]
    });
    this.buscarTodos();
  }

  buscarTodos(): void {
    this.paginaActual = 1;
    this.paginacionActiva = true;
    const estado = this.valorABuscarForm.value.estadoFiltro ?? true;

    this.servicios.obtenerTodosLosEstudiantes(estado, this.paginaActual).subscribe({
      next: (estudiantesObtenidos: EstudianteRespuesta[]) => {
        this.estudiantes.set(estudiantesObtenidos);
        this.errorMensage.set(null);
      },
      error: (error: HttpErrorResponse) => {
        this.estudiantes.set([]);
        const errorTipo = error.error;
        this.errorMensage.set(errorTipo);
      }
    });
  }

  siguientePagina(): void {
    this.paginaActual++;
    const estado = this.valorABuscarForm.value.estadoFiltro ?? true;

    this.servicios.obtenerTodosLosEstudiantes(estado, this.paginaActual).subscribe({
      next: (estudiantesObtenidos: EstudianteRespuesta[]) => {
        this.estudiantes.set(estudiantesObtenidos);
      },
      error: (error: HttpErrorResponse) => {
        const errorTipo = error.error;
        this.errorMensage.set(errorTipo);
      }
    });
  }

  paginaAnterior(): void {
    if (this.paginaActual > 1) {
      this.paginaActual--;
      const estado = this.valorABuscarForm.value.estadoFiltro ?? true;

      this.servicios.obtenerTodosLosEstudiantes(estado, this.paginaActual).subscribe({
        next: (estudiantesObtenidos: EstudianteRespuesta[]) => {
          this.estudiantes.set(estudiantesObtenidos);
        },
        error: (error: HttpErrorResponse) => {
          const errorTipo = error.error;
          this.errorMensage.set(errorTipo);
        }
      });
    }
  }

  buscarPorDpi(): void {
    this.paginacionActiva = false;
    const valorDpi = this.valorABuscarForm.value.valor;

    if (!valorDpi) {
      this.paginacionActiva = true;
      return;
    }

    this.servicios.buscarEstudiantePorDpi(valorDpi).subscribe({
      next: (estudiante: EstudianteRespuesta) => {
        this.estudiantes.set([estudiante]);
        this.errorMensage.set(null);
      },
      error: (error: HttpErrorResponse) => {
        this.paginacionActiva = true;
        const errorTipo = error.error;
        this.errorMensage.set(errorTipo);
      }
    });
  }
}