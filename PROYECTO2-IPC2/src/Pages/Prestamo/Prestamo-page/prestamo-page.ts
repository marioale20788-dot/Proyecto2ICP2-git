import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { servicios } from '../../../Services/Eventos/event.service';
import { HttpErrorResponse } from '@angular/common/http';

import { ModalConfirmationComponent } from '../../../Components/Modal/modal-confirmation.component';
import { ListaPrestamosComponent } from '../../../Components/Prestamo/Pretamo list/pretamos-list.component';
import { PrestamoRespuesta } from '../../../Models/Prestamo/Prestamo';

@Component({
  imports: [ReactiveFormsModule, ListaPrestamosComponent, ModalConfirmationComponent],
  selector: 'app-prestamos-page',
  templateUrl: './prestamo-page.html',
})
export class PrestamosPage implements OnInit {
  prestamos = signal<PrestamoRespuesta[]>([]);
  errorMensage = signal<string | null>(null);
  valorABuscarForm!: FormGroup;
  idPrestamoAeliminar = signal<string | undefined>(undefined);
  eliminado = signal<boolean>(false);

  constructor(
    private formBuiler: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.valorABuscarForm = this.formBuiler.group({
      valor: [null, [Validators.required]]
    });
  }

  eliminarPrestamo(idPrestamoAElminar: string): void {
    this.idPrestamoAeliminar.set(idPrestamoAElminar);
    this.eliminado.set(false);
  }

  eliminar(): void {
    const idPrestamo = this.idPrestamoAeliminar();

    if (!idPrestamo) return;

    this.servicios.eliminarPrestamo(idPrestamo).subscribe({
      next: () => {
        this.eliminado.set(true);
        if (this.valorABuscarForm.value.valor) {
          this.buscarPorUsuario();
        }
      },
      error: (error: HttpErrorResponse) => {
        const tipoError = error.error;
        this.errorMensage.set(tipoError);
      }
    });
  }

  buscarPorUsuario(): void {
    const valorIdUsuario = this.valorABuscarForm.value.valor;
    if (!valorIdUsuario) return;

    this.servicios.obtenerPrestamosActivos(valorIdUsuario).subscribe({
      next: (prestamosObtenidos: PrestamoRespuesta[]) => {
        this.prestamos.set(prestamosObtenidos);
      },
      error: (error: HttpErrorResponse) => {
        if (error.status == 404) {
          this.errorMensage.set("No se encontro ningun préstamo para ese ID de usuario");
        } else {
          this.errorMensage.set(error.error);
        }
      }
    });
  }
}