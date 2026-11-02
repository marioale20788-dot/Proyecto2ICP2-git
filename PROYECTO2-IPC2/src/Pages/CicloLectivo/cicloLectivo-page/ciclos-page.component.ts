import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { servicios } from '../../../Services/Eventos/event.service';
import { CicloLectivoRespuesta } from '../../../Models/CicloLectivo/CicloLectivo';
import { HttpErrorResponse } from '@angular/common/http';

import { ModalConfirmationComponent } from '../../../Components/Modal/modal-confirmation.component';
import { ListaCiclosComponent } from '../../../Components/CicloLectivo/Form lista/lista-ciclos.component';

@Component({
  imports: [ReactiveFormsModule, ModalConfirmationComponent, ListaCiclosComponent],
  selector: 'app-ciclos-page',
  templateUrl: './ciclos-page.component.html',
})
export class CiclosPage implements OnInit {
  ciclos = signal<CicloLectivoRespuesta[]>([]);
  errorMensage = signal<string | null>(null);
  valorABuscarForm!: FormGroup;
  paginaActual: number = 1;
  paginacionActiva: boolean = true;
  idCicloAeliminar = signal<number | undefined>(undefined);
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
    this.servicios.obtenerTodosLosCiclos(this.paginaActual).subscribe({
      next: (ciclosObtenidos: CicloLectivoRespuesta[]) => {
        this.ciclos.set(ciclosObtenidos);
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

  eliminarCiclo(idCicloAElminar: number): void {
    this.idCicloAeliminar.set(idCicloAElminar);
    this.eliminado.set(false);
  }

  eliminar(): void {
    const idCiclo = this.idCicloAeliminar();

    if (!idCiclo) return;

    this.servicios.eliminarCiclo(idCiclo).subscribe({
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

  buscarPorAnio(): void {
    this.paginacionActiva = false;
    const valorAnio = this.valorABuscarForm.value.valor;
    if (!valorAnio) {
      this.paginacionActiva = true;
      return;
    }
    this.servicios.obtenerCicloPorAnio(Number(valorAnio)).subscribe({
      next: (ciclo: CicloLectivoRespuesta) => {
        this.ciclos.set([ciclo]);
      },
      error: (error: HttpErrorResponse) => {
        this.paginacionActiva = true;
        const tipoError = error.error;
        this.errorMensage.set(tipoError);
      }
    });
  }
}