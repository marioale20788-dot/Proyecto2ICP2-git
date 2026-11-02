import { Component, Input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, ɵInternalFormsSharedModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { servicios } from '../../../Services/Eventos/event.service';
import { Curso, UpdateCurso } from '../../../Models/Curso/Curso';


@Component({
  imports: [ɵInternalFormsSharedModule, ReactiveFormsModule],
  selector: 'app-form-curso',
  templateUrl: './form-curso.component.html',
})
export class FormCursoComponent implements OnInit {

  updateCursoForm!: FormGroup;
  operacionTerminada = signal<boolean>(false);
  errorOperacion = signal<string | null>(null);

  @Input() cursoAEditar!: Curso;
  @Input() modoEditar: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.updateCursoForm = this.formBuilder.group({
      codigoCurso: [null, this.modoEditar ? [] : [Validators.required]],
      nombreCurso: [null, [Validators.required]],
      descripcionCurso: [null, [Validators.required]],
      estado: [true, [Validators.required]]
    });

    if (this.modoEditar && this.cursoAEditar) {
      this.cargarDatosEdicion();
    } else {
      this.resetearForm();
    }
  }

  cargarDatosEdicion(): void {
    this.updateCursoForm.patchValue({
      codigoCurso: this.cursoAEditar.codigoCurso,
      nombreCurso: this.cursoAEditar.nombreCurso,
      descripcionCurso: this.cursoAEditar.descripcionCurso,
      estado: this.cursoAEditar.estado
    });
    this.updateCursoForm.get('codigoCurso')?.disable();
  }

  crearCurso(): void {
    const nuevoCurso = this.updateCursoForm.value as Curso;
    this.servicios.crearCurso(nuevoCurso).subscribe({
      next: () => {
        this.resetearForm();
        this.operacionTerminada.set(true);
      },
      error: (error: HttpErrorResponse) => {
        this.errorOperacion.set(error.error || 'Error al crear el curso');
      }
    });
  }

  editarCurso(): void {
    const cursoUpdate: UpdateCurso = {
      nombreCurso: this.updateCursoForm.value.nombreCurso,
      descripcionCurso: this.updateCursoForm.value.descripcionCurso,
      estado: this.updateCursoForm.value.estado
    };

    this.servicios.actualizarCurso(this.cursoAEditar.codigoCurso, cursoUpdate).subscribe({
      next: () => {
        this.operacionTerminada.set(true);
      },
      error: (error: HttpErrorResponse) => {
        this.errorOperacion.set(error.error || 'Error al actualizar el curso');
      }
    });
  }

  accion(): void {
    if (this.updateCursoForm.invalid) return;

    if (this.modoEditar) {
      this.editarCurso();
    } else {
      this.crearCurso();
    }
  }

  resetearForm(): void {
    this.updateCursoForm.reset({ estado: true });
  }
}