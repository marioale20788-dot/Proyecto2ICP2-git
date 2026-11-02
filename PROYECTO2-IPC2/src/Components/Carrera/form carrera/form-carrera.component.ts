import { Component, Input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, ɵInternalFormsSharedModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

import { servicios } from '../../../Services/Eventos/event.service';
import { Carrera, UpdateCarrera } from '../../../Models/Carrera/carrera';

@Component({
  imports: [ɵInternalFormsSharedModule, ReactiveFormsModule],
  selector: 'app-form-carrera',
  templateUrl: './form-carrera.component.html',
})
export class FormCarreraComponent implements OnInit {

  updateCarreraForm!: FormGroup;
  operacionTerminada = signal<boolean>(false);
  errorOperacion = signal<string | null>(null);

  @Input() carreraAEditar!: Carrera;
  @Input() modoEditar: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.updateCarreraForm = this.formBuilder.group({
      idCarrera: [null],
      nombre: [null, [Validators.required]],
      estado: [true, [Validators.required]]
    });

    if (this.modoEditar && this.carreraAEditar) {
      this.cargarDatosEdicion();
    } else {
      this.resetearForm();
    }
  }

  cargarDatosEdicion(): void {
    this.updateCarreraForm.patchValue({
      idCarrera: this.carreraAEditar.idCarrera,
      nombre: this.carreraAEditar.nombre,
      estado: this.carreraAEditar.estado
    });

    this.updateCarreraForm.get('idCarrera')?.disable();
  }

  crearCarrera(): void {
    const nuevaCarrera = this.updateCarreraForm.value as Carrera;
    this.servicios.crearCarrera(nuevaCarrera).subscribe({
      next: () => {
        this.resetearForm();
        this.operacionTerminada.set(true);
      },
      error: (error: HttpErrorResponse) => {
        this.errorOperacion.set(error.error );
      }
    });
  }

  editarCarrera(): void {
    const carreraData: UpdateCarrera = {
      nombre: this.updateCarreraForm.get('nombre')?.value,
      estado: this.updateCarreraForm.get('estado')?.value
    };

    this.servicios.actualizarCarrera(this.carreraAEditar.idCarrera, carreraData).subscribe({
      next: () => {
        this.operacionTerminada.set(true);
      },
      error: (error: HttpErrorResponse) => {
        this.errorOperacion.set(error.error );
      }
    });
  }

  accion(): void {
    if (this.updateCarreraForm.invalid) return;

    if (this.modoEditar) {
      this.editarCarrera();
    } else {
      this.crearCarrera();
    }
  }

  resetearForm(): void {
    this.updateCarreraForm.reset({ estado: true });
    this.updateCarreraForm.get('idCarrera')?.enable();
  }
}