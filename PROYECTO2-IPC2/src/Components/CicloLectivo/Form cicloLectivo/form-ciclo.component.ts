import { Component, Input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, ɵInternalFormsSharedModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { CicloLectivoRespuesta, NewCicloRequest } from '../../../Models/CicloLectivo/CicloLectivo';
import { servicios } from '../../../Services/Eventos/event.service';


@Component({
  imports: [ɵInternalFormsSharedModule, ReactiveFormsModule],
  selector: 'app-form-ciclo',
  templateUrl: './form-ciclo.component.html',
})
export class FormCicloComponent implements OnInit {

  updateCicloForm!: FormGroup;
  nuevoCiclo!: NewCicloRequest;
  operacionTerminada = signal<boolean>(false);
  errorOperacion = signal<String | null>(null);

  @Input()
  cicloAEditar!: CicloLectivoRespuesta;
  @Input()
  modoEditar: boolean = false;

  constructor(private formBuilder: FormBuilder, private serivicios: servicios) {}

  ngOnInit(): void {
    this.updateCicloForm = this.formBuilder.group({
      anio: [null, [Validators.required]],
      fechaInicio: [null, [Validators.required]],
      fechaFinal: [null, [Validators.required]],
      estado: [true, [Validators.required]],
    });
    this.resetearForm();
  }

  updateCiclo(): void {
    this.cicloAEditar = this.updateCicloForm.value as CicloLectivoRespuesta;
    const { anio, ...updateCicloRequest } = this.cicloAEditar;

    this.serivicios.actualizarCiclo(anio, updateCicloRequest).subscribe({
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

  crearCiclo(): void {
    this.nuevoCiclo = this.updateCicloForm.value as NewCicloRequest;
    this.serivicios.crearCiclo(this.nuevoCiclo).subscribe({
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
    if (this.updateCicloForm.invalid) return;

    if (this.modoEditar) {
      this.updateCiclo();
    } else {
      this.crearCiclo();
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
    this.updateCicloForm.patchValue(this.cicloAEditar);
  }

  private resetCrear() {
    this.updateCicloForm.reset();
  }

  private resetUpdate() {
    this.updateCicloForm.reset(this.cicloAEditar);
    this.cambiarInputs();
  }
}