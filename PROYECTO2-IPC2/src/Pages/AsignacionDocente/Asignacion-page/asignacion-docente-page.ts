import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';

import { NuevaAsignacionRespuesta } from '../../../Models/Asignacion Docente/AsignacionDocente';
import { CurriculoRespuesta } from '../../../Models/Curriculo/Curriculo';
import { GradoRespuesta } from '../../../Models/Grados/Grados';
import { SeccionRespuesta } from '../../../Models/Seccion/Seccion';
import { servicios } from '../../../Services/Eventos/event.service';
import { ModalConfirmationComponent } from '../../../Components/Modal/modal-confirmation.component';
import { ListaAsignacionesComponent } from '../../../Components/AsignacionDocente/ListaAsignacion/lista-asignaciones.component';

@Component({
  selector: 'app-asignacion-docente-page',
  imports: [ReactiveFormsModule, ModalConfirmationComponent, ListaAsignacionesComponent],
  templateUrl: './asignacion-docente-page.html',
})
export class AsignacionDocentePage implements OnInit {

  asignaciones = signal<NuevaAsignacionRespuesta[]>([]);
  grados = signal<GradoRespuesta[]>([]);
  curriculos = signal<CurriculoRespuesta[]>([]);
  secciones = signal<SeccionRespuesta[]>([]);
  errorMensage = signal<string | null>(null);

  filtroForm!: FormGroup;
  idAsignacionAEliminar = signal<string | undefined>(undefined);
  eliminado = signal<boolean>(false);

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    const anioActual = new Date().getFullYear();

    this.filtroForm = this.formBuilder.group({
      idGrado: [null, [Validators.required]],
      ciclo: [anioActual, [Validators.required, Validators.min(2000)]],
      idCurriculo: [{ value: null, disabled: true }, [Validators.required]],
      idSeccion: [{ value: null, disabled: true }, [Validators.required]]
    });

    this.cargarGrados();
  }

  cargarGrados(): void {
    this.servicios.obtenerGradosSelect().subscribe({
      next: (grados) => this.grados.set(grados),
      error: () => this.errorMensage.set('Error al cargar la lista de grados')
    });
  }

  cargarCurriculosYSecciones(): void {
    const idGrado = this.filtroForm.get('idGrado')?.value;
    const ciclo = this.filtroForm.get('ciclo')?.value;

    if (!idGrado || !ciclo) return;

    this.servicios.obtenerCurriculoPorGradoCiclo(idGrado, ciclo).subscribe({
      next: (curriculos) => {
        this.curriculos.set(curriculos);
        this.filtroForm.get('idCurriculo')?.enable();
        this.filtroForm.get('idCurriculo')?.setValue(null);
      },
      error: () => {
        this.curriculos.set([]);
        this.filtroForm.get('idCurriculo')?.disable();
      }
    });

   
    this.servicios.obtenerSeccionesPorGrado(idGrado).subscribe({
      next: (secciones) => {
        this.secciones.set(secciones);
        this.filtroForm.get('idSeccion')?.enable();
        this.filtroForm.get('idSeccion')?.setValue(null);
      },
      error: () => {
        this.secciones.set([]);
        this.filtroForm.get('idSeccion')?.disable();
      }
    });
  }

  buscarAsignaciones(): void {
    if (this.filtroForm.invalid) return;

    const { idSeccion, idCurriculo } = this.filtroForm.getRawValue();

    this.servicios.obtenerAsignacionPorSeccionCurriculo(idSeccion, idCurriculo).subscribe({
      next: (res: NuevaAsignacionRespuesta) => {
        this.asignaciones.set([res]);
        this.errorMensage.set(null);
      },
      error: (err: HttpErrorResponse) => {
        this.asignaciones.set([]);
        this.errorMensage.set(err.error);
      }
    });
  }

  prepararEliminar(idAsignacion: string): void {
    this.idAsignacionAEliminar.set(idAsignacion);
    this.eliminado.set(false);
  }

  eliminar(): void {
    const id = this.idAsignacionAEliminar();
    if (!id) return;

    this.servicios.borrarAsignacionDocente(id).subscribe({
      next: () => {
        this.eliminado.set(true);
        this.buscarAsignaciones();
      },
      error: (error: HttpErrorResponse) => {
        this.errorMensage.set(error.error);
      }
    });
  }
}