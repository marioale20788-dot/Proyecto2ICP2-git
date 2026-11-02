import { Component, Input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, ɵInternalFormsSharedModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { NuevaSeccionRequest, SeccionRespuesta } from '../../../Models/Seccion/Seccion';
import { GradoRespuesta } from '../../../Models/Grados/Grados';
import { servicios } from '../../../Services/Eventos/event.service';

@Component({
  imports: [ɵInternalFormsSharedModule, ReactiveFormsModule],
  selector: 'app-form-seccion',
  templateUrl: './form-seccion.component.html',
})
export class FormSeccionComponent implements OnInit {

  updateSeccionForm!: FormGroup;
  nuevaSeccion!: NuevaSeccionRequest;
  operacionTerminada = signal<boolean>(false);
  errorOperacion = signal<string | null>(null);

  grados = signal<GradoRespuesta[]>([]);

  @Input() seccionAEditar!: SeccionRespuesta;
  @Input() modoEditar: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    const anioActual = new Date().getFullYear();

    this.updateSeccionForm = this.formBuilder.group({
      nombreSeccion: [null, [Validators.required]],
      idGrado: [null, [Validators.required]],
      anio: [anioActual, [Validators.required, Validators.min(2000)]]
    });

    this.cargarGrados();

    if (this.modoEditar && this.seccionAEditar) {
      this.cargarDatosEdicion();
    } else {
      this.resetearForm();
    }
  }

  cargarGrados(): void {
    this.servicios.obtenerGradosSelect().subscribe({
      next: (grados: GradoRespuesta[]) => 
        this.grados.set(grados),
      error: () => 
        this.errorOperacion.set('Error al cargar la lista de grados')
    });
  }

  cargarDatosEdicion(): void {
    this.updateSeccionForm.patchValue({
      nombreSeccion: this.seccionAEditar.nombreSeccion,
      idGrado: this.seccionAEditar.idGrado,
      anio: new Date().getFullYear()
    });

    this.updateSeccionForm.get('nombreSeccion')?.disable();
  }

  crearSeccion(): void {
    this.nuevaSeccion = this.updateSeccionForm.value as NuevaSeccionRequest;
    this.servicios.crearSeccion(this.nuevaSeccion).subscribe({
      next: () => {
        this.resetearForm();
        this.operacionTerminada.set(true);
      },
      error: (error: HttpErrorResponse) => {
        this.errorOperacion.set(error.error || 'Error al crear la sección');
      }
    });
  }

  editarSeccion(): void {
 
    const seccionData = this.updateSeccionForm.getRawValue() as NuevaSeccionRequest;
    this.servicios.actualizarSeccion(this.seccionAEditar.idSeccion, seccionData).subscribe({
      next: () => {
        this.operacionTerminada.set(true);
      },
      error: (error: HttpErrorResponse) => {
        this.errorOperacion.set(error.error || 'Error al actualizar la sección');
      }
    });
  }

  accion(): void {
    if (this.updateSeccionForm.invalid) return;

    if (this.modoEditar) {
      this.editarSeccion();
    } else {
      this.crearSeccion();
    }
  }

  resetearForm(): void {
    this.updateSeccionForm.reset({ anio: new Date().getFullYear() });
    this.updateSeccionForm.get('nombreSeccion')?.enable();
  }
}