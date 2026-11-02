import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { IncripcionRespuesta, NewIncricpionRequest } from '../../../Models/Inscripcion/Inscripcion';

import { ModalConfirmationComponent } from '../../../Components/Modal/modal-confirmation.component';
import { ListaInscripcionesComponent } from '../../../Components/Inscripcion/Lista inscripciones/lista-incripciones.component';
import { servicios } from '../../../Services/Eventos/event.service';

@Component({
  imports: [ReactiveFormsModule, ModalConfirmationComponent, ListaInscripcionesComponent],
  selector: 'app-inscripciones-pate',
  templateUrl: './inscripciones-pate.html',
})
export class InscripcionesPage implements OnInit {

  inscripciones = signal<IncripcionRespuesta[]>([]);
  errorMensage = signal<string | null>(null);
  valorABuscarForm!: FormGroup;
  cicloActual: number = new Date().getFullYear();

  inscripcionAEliminar = signal<IncripcionRespuesta | undefined>(undefined);
  eliminado = signal<boolean>(false);

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.valorABuscarForm = this.formBuilder.group({
      valor: [null, [Validators.required]]
    });

    this.buscarTodas();
  }

  buscarTodas(): void {
    this.servicios.obtenerInscripcionesPorCiclo(this.cicloActual).subscribe({
      next: (inscripcionesObtenidas: IncripcionRespuesta[]) => {
        this.inscripciones.set(inscripcionesObtenidas);
      },
      error: (error: any) => {
        const errorTipo = error.error;
        this.errorMensage.set(errorTipo);
      }
    });
  }

  seleccionarParaEliminar(inscripcion: IncripcionRespuesta): void {
    this.inscripcionAEliminar.set(inscripcion);
    this.eliminado.set(false);
  }

  eliminar(): void {
    const item = this.inscripcionAEliminar();
    if (!item) return;

    const inscripcionAEliminar: NewIncricpionRequest = {
      fechaInscripcion: item.fechaInscripcion,
      idEstudiante: item.idEstudiante,
      idSeccion: item.idSeccion,
      idCicloLectivo: item.idCicloLectivo
    };

    this.servicios.borrarInscripcion(inscripcionAEliminar).subscribe({
      next: () => {
        this.eliminado.set(true);
        this.buscarTodas();
      },
      error: (error: HttpErrorResponse) => {
        const tipoError = error.error;
        this.errorMensage.set(tipoError);
      }
    });
  }

  buscarPorEstudiante(): void {
    const idEstudiante = this.valorABuscarForm.value.valor;
    if (!idEstudiante) return;

    this.servicios.obtenerInscripcionesPorEstudiante(idEstudiante).subscribe({
      next: (inscripcionesObtenidas: IncripcionRespuesta[]) => {
        this.inscripciones.set(inscripcionesObtenidas);
      },
      error: (error: HttpErrorResponse) => {
        if (error.status === 404) {
          this.errorMensage.set('No se encontraron inscripciones para el estudiante especificado.');
        } else {
          this.errorMensage.set('Error al procesar la solicitud.');
        }
      }
    });
  }

}