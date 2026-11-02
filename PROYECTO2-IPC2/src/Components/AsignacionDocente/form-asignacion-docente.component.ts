import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { GradoRespuesta } from '../../Models/Grados/Grados';
import { CurriculoRespuesta } from '../../Models/Curriculo/Curriculo';
import { NuevaSeccionRequest, SeccionRespuesta } from '../../Models/Seccion/Seccion';
import { servicios } from '../../Services/Eventos/event.service';
import { NuevaAsignacionRequest } from '../../Models/Asignacion Docente/AsignacionDocente';



@Component({
  selector: 'app-form-asignacion-docente',
  imports: [ReactiveFormsModule],
  templateUrl: './form-asignacion-docente.component.html',
})
export class FormAsignacionDocenteComponent implements OnInit {

  asignacionForm!: FormGroup;
  operacionTerminada = signal<boolean>(false);
  errorOperacion = signal<string | null>(null);
  nuevaAsignacion!:NuevaSeccionRequest;
  grados = signal<GradoRespuesta[]>([]);
  curriculos = signal<CurriculoRespuesta[]>([]);
  secciones = signal<SeccionRespuesta[]>([]);

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    const anioActual = new Date().getFullYear();
    this.asignacionForm = this.formBuilder.group({
      idGrado: [null, [Validators.required]],
      cicloLectivo: [anioActual, [Validators.required, Validators.min(2000)]],
      idCurriculo: [{ value: null, disabled: true }, [Validators.required]],
      idSeccion: [{ value: null, disabled: true }, [Validators.required]],
      idUsuario: ['', [Validators.required]] 
    });

    this.cargarGrados();
  }

  cargarGrados(): void {
    this.servicios.obtenerGradosSelect().subscribe({
      next: (grados) => {
        this.grados.set(grados)
      },
        error: () => {
          this.errorOperacion.set('Error al cargar la lista de grados')
        }
        });
  }

  FiltroGradoCiclo(): void {
    const idGrado = this.asignacionForm.get('idGrado')?.value;
    const ciclo = this.asignacionForm.get('cicloLectivo')?.value;

    if (!idGrado || !ciclo) return;

    this.servicios.obtenerCurriculoPorGradoCiclo(idGrado, ciclo).subscribe({
      next: (curriculo: CurriculoRespuesta[]) =>
         {
        this.curriculos.set(curriculo);
        this.asignacionForm.get('idCurriculo')?.enable();
        this.asignacionForm.get('idCurriculo')?.setValue(null);
      },
      error: () => {
        this.curriculos.set([]);
        this.asignacionForm.get('idCurriculo')?.disable();
      }
    });

    this.servicios.obtenerSeccionesPorGrado(idGrado).subscribe({
      next: (seccion: SeccionRespuesta[]) => {
        this.secciones.set(seccion);
        this.asignacionForm.get('idSeccion')?.enable();
        this.asignacionForm.get('idSeccion')?.setValue(null);
      },
      error: () => {
        this.secciones.set([]);
        this.asignacionForm.get('idSeccion')?.disable();
      }
    });
  }

guardarAsignacion(): void {
  if (this.asignacionForm.invalid) return;

 const { idGrado, cicloLectivo, ...nuevaAsignacionRequest } = this.asignacionForm.getRawValue();

  this.servicios.asignarDocente(nuevaAsignacionRequest).subscribe({
    next: () => {
      this.operacionTerminada.set(true);
      this.resetearForm();
    },
    error: (error: HttpErrorResponse) => {
      this.errorOperacion.set(error.error );
    }
  });
}
  resetearForm(): void {
    const anioActual = new Date().getFullYear();
    this.asignacionForm.reset({ cicloLectivo: anioActual, idUsuario: '' });
    this.asignacionForm.get('idCurriculo')?.disable();
    this.asignacionForm.get('idSeccion')?.disable();
  }
}