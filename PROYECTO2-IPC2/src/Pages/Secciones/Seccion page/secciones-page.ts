import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { SeccionRespuesta } from '../../../Models/Seccion/Seccion';
import { servicios } from '../../../Services/Eventos/event.service';

import { ModalConfirmationComponent } from '../../../Components/Modal/modal-confirmation.component';
import { ListaSeccionesComponent } from '../../../Components/Seccion/lista seccion component/lista-secciones.component';

@Component({
  imports: [ReactiveFormsModule, ModalConfirmationComponent, ListaSeccionesComponent],
  selector: 'app-secciones-page',
  templateUrl: './secciones-page.html',
})
export class SeccionesPage implements OnInit {

  secciones = signal<SeccionRespuesta[]>([]);
  errorMensage = signal<string | null>(null);
  valorABuscarForm!: FormGroup;
  paginaActual: number = 1;
  paginacionActiva: boolean = true;
  idSeccionAeliminar = signal<string | undefined>(undefined);
  eliminado = signal<boolean>(false);

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.valorABuscarForm = this.formBuilder.group({
      valor: [null, [Validators.required]],
      tipoBusqueda: ['nombre', [Validators.required]]
    });
    this.buscarTodas();
  }

  buscarTodas(): void {
    this.paginacionActiva = true;
    this.servicios.obtenerTodasLasSecciones(this.paginaActual).subscribe({
      next: (seccionesObtenidas: SeccionRespuesta[]) => {
        this.secciones.set(seccionesObtenidas);
      },
      error: (error: HttpErrorResponse) => {
        this.errorMensage.set(error.error || 'Error al obtener secciones');
      }
    });
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

  eliminarSeccion(idSeccionAEliminar: string): void {
    this.idSeccionAeliminar.set(idSeccionAEliminar);
    this.eliminado.set(false);
  }

  eliminar(): void {
    const idSeccion = this.idSeccionAeliminar();
    if (!idSeccion) return;

    this.servicios.eliminarSeccion(idSeccion).subscribe({
      next: () => {
        this.eliminado.set(true);
        this.buscarTodas();
      },
      error: (error: HttpErrorResponse) => {
        if (error.status === 409) {
          this.errorMensage.set('No se puede eliminar la sección porque está en uso por otros registros.');
        } else {
          this.errorMensage.set(error.error || 'Error al eliminar la sección');
        }
      }
    });
  }

  buscar(): void {
    const valor = this.valorABuscarForm.value.valor;
    const tipo = this.valorABuscarForm.value.tipoBusqueda;

    if (!valor) {
      this.buscarTodas();
      return;
    }

    if (tipo === 'nombre') {
      this.paginacionActiva = true;
      this.servicios.obtenerSeccionesPorNombre(valor, this.paginaActual).subscribe({
        next: (res: SeccionRespuesta[]) => this.secciones.set(res),
        error: (err: HttpErrorResponse) => this.errorMensage.set(err.error || 'No se encontraron secciones.')
      });
    } else {
      this.paginacionActiva = false;
      this.servicios.obtenerSeccionesPorGrado(valor).subscribe({
        next: (res: SeccionRespuesta[]) => this.secciones.set(res),
        error: (err: HttpErrorResponse) => this.errorMensage.set(err.error || 'No se encontraron secciones para este grado.')
      });
    }
  }
}