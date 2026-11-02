import { Component, Input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ɵInternalFormsSharedModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { PrestamoRequest, PrestamoRespuesta } from '../../../Models/Prestamo/Prestamo';
import { servicios } from '../../../Services/Eventos/event.service';

@Component({
  imports: [ɵInternalFormsSharedModule, ReactiveFormsModule],
  selector: 'app-form-prestamo',
  templateUrl: './form-pretammo.component.html',
})
export class FormPrestamoComponent implements OnInit {

  updatePrestamoForm!: FormGroup;
  nuevoPrestamo!: PrestamoRequest;
  operacionTerminada = signal<boolean>(false);
  errorOperacion = signal<String|null>(null);

  @Input() prestamoAEditar!: PrestamoRespuesta;
  @Input() modoEditar: boolean = false;

  constructor(private formBuilder: FormBuilder, private serivicios: servicios) {}

  ngOnInit(): void {
    this.updatePrestamoForm = this.formBuilder.group({
      idPrestamo: [null],
      idLibro: [null, [Validators.required]],
      idUsuario: [null, this.modoEditar ? [] : [Validators.required]],
      cantidad: [1, [Validators.required]],
      fechaDePrestamo: [new Date().toISOString().split('T')[0], [Validators.required]],
      fechaDeEntregaEsperada: [null, [Validators.required]]
    });
    
    this.resetearForm();
  }

  updatePrestamo(): void {
    const rawValue = this.updatePrestamoForm.value;
    const { idPrestamo, ...updatePrestamoRequest } = rawValue;

    this.serivicios.actualizarPrestamo(idPrestamo, updatePrestamoRequest as PrestamoRequest).subscribe({
      next: () => {
        this.operacionTerminada.set(true);
      },
      error: (error: HttpErrorResponse) => {
        const tipoError = error.error;
        this.errorOperacion.set(tipoError);
      }
    });
  }

  crearPrestamo(): void {
    const { idPrestamo, ...nuevoPrestamoRequest } = this.updatePrestamoForm.value;

    this.serivicios.registrarPrestamo(nuevoPrestamoRequest as PrestamoRequest).subscribe({
      next: () => {
        this.resetearForm();
        this.operacionTerminada.set(true);
      },
      error: (error: HttpErrorResponse) => {
        const tipoError = error.error;
        this.errorOperacion.set(tipoError);
      }
    });
  }

  accion(): void {
    if (this.updatePrestamoForm.invalid) return;

    if (this.modoEditar) {
      this.updatePrestamo();
    } else {
      this.crearPrestamo();
    }
  }

  resetearForm(): void {
    if (this.modoEditar) {
      this.resetUpdate();
    } else {
      this.resetCrear();
    }
  }

private cambiarInputs() {
  if (!this.prestamoAEditar) return;

  this.updatePrestamoForm.patchValue({
    idPrestamo: this.prestamoAEditar.idPrestamo,
    idLibro: this.prestamoAEditar.idLibro,
    idUsuario: this.prestamoAEditar.idUsuario,
    cantidad: this.prestamoAEditar.cantidad,
    fechaDePrestamo: this.prestamoAEditar.fechaPrestamo,
    fechaDeEntregaEsperada: this.prestamoAEditar.fechaDeEntregaEsperada
  });
}
  private resetCrear() {
    this.updatePrestamoForm.reset({
      fechaDePrestamo: new Date().toISOString().split('T')[0],
      cantidad: 1
    });
  }

  private resetUpdate() {
    this.cambiarInputs();
  }
}