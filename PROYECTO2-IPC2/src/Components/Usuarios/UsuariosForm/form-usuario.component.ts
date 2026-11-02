import { Component, Input, OnInit, OnChanges, SimpleChanges, signal } from '@angular/core';
import { CommonModule, KeyValuePipe } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

import { RespuestaEmpleado } from '../../../Models/UsuariosEmpleado/EmpleadoUsuarioRespuesta';
import { nuevoUsuario } from '../../../Models/UsuariosEmpleado/NuevoUsuario';
import { tipoUsuario } from '../../../Models/UsuariosEmpleado/TipoUsuario';
import { servicios } from '../../../Services/Eventos/event.service';
import { updateUsuario } from '../../../Models/UsuariosEmpleado/UpdaateUsuario';

@Component({
  selector: 'app-form-usuario',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, KeyValuePipe],
  templateUrl: './form-usuario.component.html',
})
export class FormUsuarioComponent implements OnInit, OnChanges {

  updateUsuarioForm!: FormGroup;
  TipoUsuarioEnum = tipoUsuario;
  nuevoUsuario!: nuevoUsuario;

  operacionTerminada = signal<boolean>(false);
  errorOperacion = signal<string | null>(null);

  @Input() usuarioAEditar!: RespuestaEmpleado;
  @Input() modoEditar: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private serivicios: servicios
  ) {}

  ngOnInit(): void {
    this.updateUsuarioForm = this.formBuilder.group({
      id: [null, [Validators.required]],
      contrasena: [null], 
      correoElectronico: [null, [Validators.required, Validators.email]],
      rol: [null, [Validators.required]],
      salario: [null, [Validators.required, Validators.min(0)]],
      nombreCompleto: [null, [Validators.required]],
      fechaDeContratacion: [null, [Validators.required]],
      estado: [true, [Validators.required]]
    });

    this.resetearForm();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if ((changes['usuarioAEditar'] || changes['modoEditar']) && this.updateUsuarioForm) {
      this.resetearForm();
    }
  }

  accion(): void {
  

    if (this.modoEditar) {
      this.updateUsuario();
    } else {
      this.crearUsuario();
    }
  }

  updateUsuario(): void {
    const rawValues = this.updateUsuarioForm.getRawValue();
    const id = this.usuarioAEditar.id;

    const datosActualizacion: updateUsuario = {
      correoElectronico: rawValues.correoElectronico,
      estado: rawValues.estado,
      salario: rawValues.salario,
      nombreCompleto: rawValues.nombreCompleto,
      fechaDeContratacion: rawValues.fechaDeContratacion
    };

    this.serivicios.updateUsuario(id, datosActualizacion).subscribe({
      next: () => {
        this.operacionTerminada.set(true);
      },
      error: (error: HttpErrorResponse) => {
        this.errorOperacion.set(error.error);
      }
    });
  }

  crearUsuario(): void {
    this.nuevoUsuario = this.updateUsuarioForm.getRawValue() as nuevoUsuario;

    this.serivicios.crearUsuario(this.nuevoUsuario).subscribe({
      next: () => {
        this.resetearForm();
        this.operacionTerminada.set(true);
      },
      error: (error: HttpErrorResponse) => {
        this.errorOperacion.set(error.error);
      }
    });
  }

  resetearForm(): void {
    if (!this.updateUsuarioForm) return;

    if (this.modoEditar && this.usuarioAEditar) {
      this.updateUsuarioForm.get('id')?.disable();
      this.updateUsuarioForm.get('contrasena')?.disable();
      this.updateUsuarioForm.get('rol')?.disable();

      this.updateUsuarioForm.patchValue(this.usuarioAEditar);
    } else {
  
      this.updateUsuarioForm.get('id')?.enable();
      this.updateUsuarioForm.get('contrasena')?.enable();
      this.updateUsuarioForm.get('rol')?.enable();

      this.updateUsuarioForm.reset({ estado: true });
    }
  }
}