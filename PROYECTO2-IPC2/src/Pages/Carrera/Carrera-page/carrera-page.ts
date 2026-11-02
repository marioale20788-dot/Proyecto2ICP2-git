import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

import { ModalConfirmationComponent } from '../../../Components/Modal/modal-confirmation.component';
import { ListaCarrerasComponent } from '../../../Components/Carrera/lista carrera/lista-carreras.component';
import { Carrera } from '../../../Models/Carrera/carrera';
import { servicios } from '../../../Services/Eventos/event.service';

@Component({
  imports: [ReactiveFormsModule, ListaCarrerasComponent, ModalConfirmationComponent],
  selector: 'app-carreras-page',
  templateUrl: './carrera-page.html',
})
export class CarrerasPage implements OnInit {

  carreras = signal<Carrera[]>([]);
  errorMensage = signal<string | null>(null);
  valorABuscarForm!: FormGroup;
  paginaActual: number = 1;
  paginacionActiva: boolean = true;
  idCarreraAeliminar = signal<string | undefined>(undefined);
  eliminado = signal<boolean>(false);

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {

    this.valorABuscarForm = this.formBuilder.group({
      valor: [null],
      estado: [true]
    });

    this.buscarTodas();
  }

  buscarTodas(): void {
    this.paginacionActiva = true;
    const estadoSeleccionado = this.valorABuscarForm.value.estado ?? true;

    this.servicios.obtenerTodasLasCarrerasCrud(estadoSeleccionado, this.paginaActual).subscribe({
      next: (carrerasObtenidas: Carrera[]) => {
        this.carreras.set(carrerasObtenidas);
      },
      error: (error: HttpErrorResponse) => {
        this.errorMensage.set(error.error || 'Error al obtener carreras');
      }
    });
  }
  cambiarEstadoFiltro(): void {
    this.paginaActual = 1;
    this.buscarTodas();
  }

  siguientePagina(): void {
    this.paginaActual++;
    this.buscarTodas();
  }

  paginaAnterior(): void {
    if (this.paginaActual > 1) {
      this.paginaActual--;
      this.buscarTodas();
    }
  }

  eliminarCarrera(idCarreraAEliminar: string): void {
    this.idCarreraAeliminar.set(idCarreraAEliminar);
    this.eliminado.set(false);
  }

  eliminar(): void {
    const idCarrera = this.idCarreraAeliminar();
    if (!idCarrera) return;

    this.servicios.eliminarCarrera(idCarrera).subscribe({
      next: () => {
        this.eliminado.set(true);
        this.buscarTodas();
      },
      error: (error: HttpErrorResponse) => {
        if (error.status === 409) {
          this.errorMensage.set('No se puede eliminar la carrera porque está en uso.');
        } else {
          this.errorMensage.set(error.error || 'Error al eliminar la carrera');
        }
      }
    });
  }

  buscar(): void {
    const valor = this.valorABuscarForm.value.valor;

    if (!valor) {
      this.buscarTodas();
      return;
    }

    this.paginacionActiva = false;
    this.servicios.obtenerCarreraPorIdONombre(valor).subscribe({
      next: (res: Carrera) => this.carreras.set([res]),
      error: (err: HttpErrorResponse) => this.errorMensage.set(err.error || 'No se encontró la carrera.')
    });
  }
}