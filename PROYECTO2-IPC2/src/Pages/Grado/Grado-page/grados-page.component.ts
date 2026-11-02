import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { servicios } from '../../../Services/Eventos/event.service';

import { HttpErrorResponse } from '@angular/common/http';

import { ModalConfirmationComponent } from '../../../Components/Modal/modal-confirmation.component';
import { GradoRespuesta } from '../../../Models/Grados/Grados';
import { ListaGradosComponent } from '../../../Components/Grados/Lista grados/lista-grados.component';

@Component({
  imports: [ReactiveFormsModule, ListaGradosComponent, ModalConfirmationComponent],
  selector: 'app-grados-page',
  templateUrl: './grados-page.component.html',
})
export class GradosPage implements OnInit {
  grados = signal<GradoRespuesta[]>([]);
  errorMensage = signal<string | null>(null);
  valorABuscarForm!: FormGroup;
  paginaActual: number = 1;
  paginacionActiva: boolean = true;
  idGradoAeliminar = signal<string | undefined>(undefined);
  eliminado = signal<boolean>(false);

  constructor(
    private formBuiler: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.valorABuscarForm = this.formBuiler.group({
      valor: [null, [Validators.required]]
    });
    this.buscarTodos();
  }

  buscarTodos(): void {
    this.paginacionActiva = true;
    this.servicios.obtenerTodosLosGrados(this.paginaActual).subscribe({
      next: (gradosObtenidos: GradoRespuesta[]) => {
        this.grados.set(gradosObtenidos);
      },
      error: (error: any) => {
        const errorTipo = error.error;
        this.errorMensage.set(errorTipo);
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

  eliminarGrado(idGradoAElminar: string): void {
    this.idGradoAeliminar.set(idGradoAElminar);
    this.eliminado.set(false);
  }

  eliminar(): void {
    const idGrado = this.idGradoAeliminar();

    if (!idGrado) return;

    this.servicios.eliminarGrado(idGrado).subscribe({
      next: () => {
        this.eliminado.set(true);
        this.buscarTodos();
      },
      error: (error: HttpErrorResponse) => {
        const tipoError = error.error;
        this.errorMensage.set(tipoError);
      }
    });
  }

  buscarPorNivel(): void {
    this.paginacionActiva = false;
    const valorNivel = this.valorABuscarForm.value.valor;
    if (!valorNivel) {
      this.paginacionActiva = true;
      return;
    }
    this.servicios.obtenerGradosPorNivel(valorNivel).subscribe({
      next: (gradosObtenidos: GradoRespuesta[]) => {
        this.grados.set(gradosObtenidos);
      },
      error: (error: HttpErrorResponse) => {
        this.paginacionActiva = true;
        if (error.status == 404) {
          this.errorMensage.set("No se encontraron grados para ese nivel");
        }
      }
    });
  }
}