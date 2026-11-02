import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { servicios } from '../../../Services/Eventos/event.service';
import { NuevaActividadZona, UpdateActividadRequest, ZonaActividadRespuesta } from '../../../Models/CursosZona/Zona';
import { HttpErrorResponse } from '@angular/common/http';
import { ModalConfirmationComponent } from '../../Modal/modal-confirmation.component';


@Component({
  selector: 'app-zona-actividades',
  standalone: true,
  imports: [RouterLink, ModalConfirmationComponent],
  templateUrl: './zona-actividades.componente.html'
})
export class ZonaActividadesComponent implements OnInit {

   idAsignacion = signal<string>('');
   nombreCurso = signal<string>('');
   nombreSeccion = signal<string>('');


   actividades = signal<ZonaActividadRespuesta[]>([]);


   modoEdicion = signal<boolean>(false);
   idActividadEdicion = signal<string | null>(null);
 idActividadAEliminar = signal<string | null>(null);
   nombreInput = signal<string>('');
   ponderacionInput = signal<number>(0);
   fechaEntregaInput = signal<string>(new Date().toISOString().substring(0, 10));

   errorMsg = signal<string | null>(null);
   exitoMsg = signal<string | null>(null);

  constructor(
    private route: ActivatedRoute,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idAsignacion.set(params['idAsignacion'] || '');
      this.nombreCurso.set(params['nombreCurso'] || '');
      this.nombreSeccion.set(params['nombreSeccion'] || '');

      if (this.idAsignacion()) {
        this.cargarActividades();
      }
    });
  }

  cargarActividades(): void {
    this.errorMsg.set(null);
    this.servicios.obtenerActividadesZona(this.idAsignacion()).subscribe({
      next: (data) => this.actividades.set(data),
      error: (err) => {
        this.actividades.set([]);
        if (err.status !== 404) {
          this.errorMsg.set('Error al obtener las actividades de zona.');
        }
      }
    });
  }

  onNombreChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.nombreInput.set(input.value);
  }

  onPonderacionChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.ponderacionInput.set(Number(input.value));
  }

  onFechaChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.fechaEntregaInput.set(input.value);
  }

  guardarActividad(event: Event): void {
    event.preventDefault();

    if (!this.nombreInput().trim()) {
      this.errorMsg.set('El nombre de la actividad es requerido.');
      return;
    }
    if (this.ponderacionInput() <= 0) {
      this.errorMsg.set('La ponderación debe ser mayor a 0.');
      return;
    }
    if (!this.fechaEntregaInput()) {
      this.errorMsg.set('La fecha de entrega es requerida.');
      return;
    }

    this.errorMsg.set(null);
    this.exitoMsg.set(null);

    if (this.modoEdicion() && this.idActividadEdicion()) {
      const updateReq: UpdateActividadRequest = {
        nombre: this.nombreInput(),
        fechaEntrega: this.fechaEntregaInput(),
        ponderacion: this.ponderacionInput()
      };

      this.servicios.actualizarActividadZona(this.idActividadEdicion()!, updateReq).subscribe({
        next: () => {
          this.exitoMsg.set('Actividad de zona actualizada exitosamente.');
          this.limpiarFormulario();
          this.cargarActividades();
        },
        error: (err:HttpErrorResponse) => {
          this.errorMsg.set(err.error);
        }
      });

    } else {
      const nuevaReq: NuevaActividadZona = {
        nombreActividad: this.nombreInput(),
        ponderacion: this.ponderacionInput(),
        fechaEntrega: this.fechaEntregaInput(),
        idAsignacion: this.idAsignacion()
      };

      this.servicios.crearActividadZona(nuevaReq).subscribe({
        next: () => {
          this.exitoMsg.set('Actividad de zona creada exitosamente.');
          this.limpiarFormulario();
          this.cargarActividades();
        },
        error: (err:HttpErrorResponse) => {
          this.errorMsg.set(err.error);
        }
      });
    }
  }

  seleccionarParaEditar(act: ZonaActividadRespuesta): void {
    this.modoEdicion.set(true);
    this.idActividadEdicion.set(act.idActividad);
    this.nombreInput.set(act.nombreActividad);
    this.ponderacionInput.set(act.ponderacion);
    this.fechaEntregaInput.set(act.fechaEntrega);
  }

  prepararEliminacion(idActividad: string): void {
    this.idActividadAEliminar.set(idActividad);
  }

  eliminarConfirmado(): void {
    this.errorMsg.set(null);
    this.exitoMsg.set(null);
    const idActividad = this.idActividadAEliminar();
    if(!idActividad)return;

    this.servicios.eliminarActividadZona(idActividad).subscribe({
      next: () => {
        this.exitoMsg.set('Actividad eliminada correctamente.');
        this.idActividadAEliminar.set(null);
        this.cargarActividades();
      },
      error: (err:HttpErrorResponse) => {
        this.errorMsg.set(err.error);
      }
    });
  }

  limpiarFormulario(): void {
    this.modoEdicion.set(false);
    this.idActividadEdicion.set(null);
    this.nombreInput.set('');
    this.ponderacionInput.set(0);
    this.fechaEntregaInput.set(new Date().toISOString().substring(0, 10));
  }
}