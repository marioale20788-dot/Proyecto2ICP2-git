import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { CurriculoRespuesta } from '../../../Models/Curriculo/Curriculo';
import { GradoRespuesta } from '../../../Models/Grados/Grados';
import { servicios } from '../../../Services/Eventos/event.service';

import { ModalConfirmationComponent } from '../../../Components/Modal/modal-confirmation.component';
import { ListaCurriculoComponent } from '../../../Components/Curriculo/curriculo lista/lista-curriculo.component';

@Component({
  imports: [ReactiveFormsModule, ListaCurriculoComponent, ModalConfirmationComponent],
  selector: 'app-curriculo-page',
  templateUrl: './curriculo-page.html',
})
export class CurriculoPage implements OnInit {

  curriculoList = signal<CurriculoRespuesta[]>([]);
  grados = signal<GradoRespuesta[]>([]);
  errorMensage = signal<string | null>(null);
  filtroForm!: FormGroup;

  idCurriculoAeliminar = signal<string | undefined>(undefined);
  eliminado = signal<boolean>(false);

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    const anioActual = new Date().getFullYear();

    this.filtroForm = this.formBuilder.group({
      idGrado: [null, [Validators.required]],
      ciclo: [anioActual, [Validators.required, Validators.min(2000)]]
    });

    this.cargarGrados();
  }

  cargarGrados(): void {
    this.servicios.obtenerTodosLosGrados(1).subscribe({
      next: (Grados: GradoRespuesta[]) => {
     this.grados.set(Grados)
      } ,   error: () => {
        this.errorMensage.set('Error al cargar la lista de grados')
      }
    });
  }

  buscarCurriculo(): void {
    if (this.filtroForm.invalid) return;

    const idGrado = this.filtroForm.value.idGrado;
    const ciclo = this.filtroForm.value.ciclo;

    this.servicios.obtenerCurriculoPorGradoCiclo(idGrado, ciclo).subscribe({
      next: (res: CurriculoRespuesta[]) => {
        this.curriculoList.set(res);
        this.errorMensage.set(null);
      },
      error: (err: HttpErrorResponse) => {
        this.curriculoList.set([]);
        this.errorMensage.set(err.error );
      }
    });
  }

  prepararEliminar(idCurriculo: string): void {
    this.idCurriculoAeliminar.set(idCurriculo);
    this.eliminado.set(false);
  }

  eliminar(): void {
    const id = this.idCurriculoAeliminar();
    if (!id) return;

    this.servicios.borrarCursoDelCurriculo(id).subscribe({
      next: () => {
        this.eliminado.set(true);
        this.buscarCurriculo();
      },
      error: (error: HttpErrorResponse) => {
        if (error.status === 409) {
          this.errorMensage.set('No se puede quitar el curso del currículo porque está en uso.');
        } else {
          this.errorMensage.set(error.error );
        }
      }
    });
  }
}