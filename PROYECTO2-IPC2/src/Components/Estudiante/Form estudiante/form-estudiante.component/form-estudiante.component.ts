import { Component, Input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

import { EstudianteRespuesta, RegistroEstudianteCompleto, UpdateEstudianteRequest } from '../../../../Models/Estudiantes/Estudiante';
import { servicios } from '../../../../Services/Eventos/event.service';

@Component({
  selector: 'app-form-estudiante',
  imports: [ReactiveFormsModule],
  templateUrl: './form-estudiante.component.html',
})
export class FormEstudianteComponent implements OnInit {

  estudianteForm!: FormGroup;
  operacionTerminada = signal<boolean>(false);
  errorOperacion = signal<string | null>(null);

  @Input() estudianteAEditar!: EstudianteRespuesta;
  @Input() modoEditar: boolean = false;

  constructor(
    private formBuilder: FormBuilder, 
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.estudianteForm = this.formBuilder.group({
      dpiUsuario: [null, [Validators.required]],
      contrasena: [null],
      correoElectronico: [null, [Validators.required, Validators.email]],
      nombreEstudiante: [null, [Validators.required]],
      estado: [true, [Validators.required]],
      graduado: [false, [Validators.required]],

      dpiEncargado: [null, [Validators.required]],
      nombreEncargado: [null, [Validators.required]],
      direccionEncargado: [null, [Validators.required]],

      alergias: [null, [Validators.required]],
      enfermedades: [null, [Validators.required]],
      tipoDeSangre: [null, [Validators.required]],
      medicamentos: [null, [Validators.required]],
      contactoDeEmergencia: [null, [Validators.required]]
    });

    this.resetearForm();
  }

  accion(): void {
    if (this.modoEditar) {
      this.updateEstudiante();
    } else {
      this.crearEstudiante();
    }
  }

  updateEstudiante(): void {
    const valores = this.estudianteForm.getRawValue();
    const dpi = this.estudianteAEditar.dpiUsuario;

    const datosActualizacion: UpdateEstudianteRequest = {
      correoElectronico: valores.correoElectronico,
      estado: valores.estado,
      nombreEstudiante: valores.nombreEstudiante,
      nombreEncargado: valores.nombreEncargado,
      direccion: valores.direccionEncargado,
      alergias: valores.alergias,
      enfermedades: valores.enfermedades,
      tipoDeSangre: valores.tipoDeSangre,
      medicamentos: valores.medicamentos,
      contactoDeEmergencia: Number(valores.contactoDeEmergencia),
      graduado: valores.graduado
    };

    this.servicios.updateEstudiante(dpi, datosActualizacion).subscribe({
      next: () => {
        this.operacionTerminada.set(true);
      },
      error: (error: HttpErrorResponse) => {
        const tipoError = error.error;
        this.errorOperacion.set(tipoError);
      }
    });
  }

  crearEstudiante(): void {
    const valores = this.estudianteForm.getRawValue();

    const registroCompleto: RegistroEstudianteCompleto = {
      estudiante: {
        dpiUsuario: valores.dpiUsuario,
        contrasena: valores.contrasena,
        correoElectronico: valores.correoElectronico,
        nombreEstudiante: valores.nombreEstudiante,
        graduado: valores.graduado
      },
      encargado: {
        idEstudiante: valores.dpiUsuario,
        dpiEncargado: valores.dpiEncargado,
        nombreEncargado: valores.nombreEncargado,
        direccionEncargado: valores.direccionEncargado
      },
      datosMedicos: {
        idEstudiante: valores.dpiUsuario,
        alergias: valores.alergias,
        enfermedades: valores.enfermedades,
        tipoDeSangre: valores.tipoDeSangre,
        medicamentos: valores.medicamentos,
        contactoDeEmergencia: Number(valores.contactoDeEmergencia)
      }
    };

    this.servicios.crearEstudiante(registroCompleto).subscribe({
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

  resetearForm(): void {
    if (!this.estudianteForm) return;

    if (this.modoEditar && this.estudianteAEditar) {
      this.estudianteForm.get('dpiUsuario')?.disable();
      this.estudianteForm.get('contrasena')?.disable();
      this.estudianteForm.get('dpiEncargado')?.disable();

      this.estudianteForm.patchValue({
        ...this.estudianteAEditar,
        estado: this.estudianteAEditar.estado ?? true,
        graduado: this.estudianteAEditar.graduado ?? false,
        direccionEncargado: this.estudianteAEditar.direccionEncargado
      });
    } else {
      this.estudianteForm.get('dpiUsuario')?.enable();
      this.estudianteForm.get('contrasena')?.enable();
      this.estudianteForm.get('dpiEncargado')?.enable();

      this.estudianteForm.reset({ estado: true, graduado: false });
    }
  }
}