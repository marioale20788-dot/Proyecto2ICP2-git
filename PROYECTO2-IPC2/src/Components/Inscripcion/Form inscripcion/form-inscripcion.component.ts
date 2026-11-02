import { Component, Input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { GradoRespuesta } from '../../../Models/Grados/Grados';
import { SeccionRespuesta } from '../../../Models/Seccion/Seccion';
import { IncripcionRespuesta, NewIncricpionRequest, UpdateIncripcionRequest } from '../../../Models/Inscripcion/Inscripcion';
import { servicios } from '../../../Services/Eventos/event.service';


@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-form-inscripcion',
  templateUrl: './form-inscripcion.component.html',
})
export class FormInscripcionComponent implements OnInit {

  updateInscripcionForm!: FormGroup;

  grados = signal<GradoRespuesta[]>([]);
  secciones = signal<SeccionRespuesta[]>([]);
  nombreGradoActual = signal<string>('');

  operacionTerminada = signal<boolean>(false);
  errorOperacion = signal<string | null>(null);

  @Input() 
  set inscripcionAEditar(datosInscripcion: IncripcionRespuesta) {
    this.datosInscripcion = datosInscripcion;
    if (datosInscripcion && this.updateInscripcionForm) {
      this.cambiarInputs();
    }
  }

  get inscripcionAEditar(): IncripcionRespuesta {
    return this.datosInscripcion;
  }

  datosInscripcion!: IncripcionRespuesta;

  @Input() modoEditar: boolean = false;

  constructor(
    private formBuilder: FormBuilder, 
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.updateInscripcionForm = this.formBuilder.group({
      idGrado: [null],
      idIncripcion: [null],
      fechaInscripcion: [new Date(), [Validators.required]],
      idCicloLectivo: [null, [Validators.required]],
      idEstudiante: [null, [Validators.required]],
      idSeccion: [null, [Validators.required]]
    });

    if (this.modoEditar) {
      this.cargarTodasLasSecciones();
    } else {
      this.cargarGrados();
    }

    this.resetearForm();
  }

  cargarGrados(): void {
    this.servicios.obtenerGradosSelect().subscribe({
      next: (listaGrados: GradoRespuesta[]) => {
        this.grados.set(listaGrados);
      },
      error: () => {
        this.errorOperacion.set("Error al cargar los grados");
      }
    });
  }

  cargarTodasLasSecciones(): void {
    this.servicios.obtenerTodasLasSeccionesSelect().subscribe({
      next: (listaSecciones: SeccionRespuesta[]) => {
        this.secciones.set(listaSecciones);
        if (this.datosInscripcion) {
          this.cambiarInputs();
        }
      },
      error: () => {
        this.errorOperacion.set("Error al cargar las secciones");
      }
    });
  }

  cargarNombreGradoPorEndpoint(idGrado: string): void {
    if (!idGrado) {
      this.nombreGradoActual.set('');
      return;
    }

    this.servicios.obtenerGradoPorId(idGrado).subscribe({
      next: (respuestaGrado: GradoRespuesta) => {
        this.nombreGradoActual.set(respuestaGrado.nombreGrado);
      },
      error: () => {
        this.nombreGradoActual.set('');
      }
    });
  }

  onGradoChange(idGrado: string): void {
    if (!idGrado) {
      this.secciones.set([]);
      this.nombreGradoActual.set('');
      this.updateInscripcionForm.get('idSeccion')?.setValue(null);
      return;
    }

    this.cargarNombreGradoPorEndpoint(idGrado);

    this.servicios.obtenerSeccionesPorGrado(idGrado).subscribe({
      next: (seccionesPorGrado: SeccionRespuesta[]) => {
        this.secciones.set(seccionesPorGrado);
      },
      error: () => {
        this.errorOperacion.set("Error al cargar las secciones");
      }
    });
  }

  updateInscripcion(): void {
    const datosFormulario = this.updateInscripcionForm.getRawValue();
    const { idIncripcion, idSeccion } = datosFormulario;

    const updatePayload: UpdateIncripcionRequest = { idSeccion };

    this.servicios.updateInscripcion(idIncripcion, updatePayload).subscribe({
      next: (respuesta: IncripcionRespuesta) => {
        if (respuesta) {
          this.datosInscripcion = respuesta;
        }
        this.resetearForm();
        this.operacionTerminada.set(true);
      },
      error: (error: HttpErrorResponse) => {
        const tipoError = error.error;
        this.errorOperacion.set(tipoError);
      }
    });
  }

  crearInscripcion(): void {
    const { idGrado, idIncripcion, ...nuevaInscripcion } = this.updateInscripcionForm.value;

    this.servicios.inscribirEstudiante(nuevaInscripcion as NewIncricpionRequest).subscribe({
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
    if (this.updateInscripcionForm.invalid) return;

    if (this.modoEditar) {
      this.updateInscripcion();
    } else {
      this.crearInscripcion();
    }
  }

  resetearForm(): void {
    if (this.modoEditar) {
      this.resetUpdate();
    } else {
      this.resetCrear();
    }
  }

  private cambiarInputs(): void {
    if (!this.datosInscripcion || !this.updateInscripcionForm) return;

    this.updateInscripcionForm.patchValue(this.datosInscripcion);

    if (this.modoEditar && this.secciones().length > 0 && this.datosInscripcion.idSeccion) {
      const seccionCoincidente = this.secciones().find(s => s.idSeccion === this.datosInscripcion.idSeccion);
      if (seccionCoincidente && seccionCoincidente.idGrado) {
        this.cargarNombreGradoPorEndpoint(seccionCoincidente.idGrado);
      }
    }

    if (this.modoEditar) {
      this.updateInscripcionForm.get('idGrado')?.disable();
      this.updateInscripcionForm.get('fechaInscripcion')?.disable();
      this.updateInscripcionForm.get('idCicloLectivo')?.disable();
      this.updateInscripcionForm.get('idEstudiante')?.disable();
      this.updateInscripcionForm.get('idSeccion')?.enable();
    } else {
      this.updateInscripcionForm.get('idGrado')?.enable();
      this.updateInscripcionForm.get('fechaInscripcion')?.enable();
      this.updateInscripcionForm.get('idCicloLectivo')?.enable();
      this.updateInscripcionForm.get('idEstudiante')?.enable();
      this.updateInscripcionForm.get('idSeccion')?.enable();
    }
  }

  private resetCrear(): void {
    this.updateInscripcionForm.reset();
    this.secciones.set([]);
    this.nombreGradoActual.set('');
  }

  private resetUpdate(): void {
    this.cambiarInputs();
  }
}