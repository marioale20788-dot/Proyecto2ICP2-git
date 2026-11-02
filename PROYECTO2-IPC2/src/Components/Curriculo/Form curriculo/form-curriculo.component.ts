import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, ɵInternalFormsSharedModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { NuevoCurriculoRequest } from '../../../Models/Curriculo/Curriculo';
import { GradoRespuesta } from '../../../Models/Grados/Grados';
import { Curso } from '../../../Models/Curso/Curso';
import { servicios } from '../../../Services/Eventos/event.service';

@Component({
  imports: [ɵInternalFormsSharedModule, ReactiveFormsModule],
  selector: 'app-form-curriculo',
  templateUrl: './form-curriculo.component.html',
})
export class FormCurriculoComponent implements OnInit {

  curriculoForm!: FormGroup;
  operacionTerminada = signal<boolean>(false);
  errorOperacion = signal<string | null>(null);

  grados = signal<GradoRespuesta[]>([]);
  cursos = signal<Curso[]>([]);

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    const anioActual = new Date().getFullYear();

    this.curriculoForm = this.formBuilder.group({
      idGrado: [null, [Validators.required]],
      codigoCurso: [null, [Validators.required]],
      cicloLectivo: [anioActual, [Validators.required, Validators.min(2000)]]
    });

    this.cargarGrados();
    this.cargarCursos();
  }

  cargarGrados(): void {
    this.servicios.obtenerGradosSelect().subscribe({
      next: (Grados: GradoRespuesta[]) => {
        this.grados.set(Grados)
      },
      error: () => {
       this.errorOperacion.set('Error al cargar la lista de grados') 
      }
    });
  }

  cargarCursos(): void {
    this.servicios.obtenerCursosSelect().subscribe({
      next: (cursos: Curso[]) => {
      this.cursos.set(cursos)
      },
      error: () => {
        this.errorOperacion.set('Error al cargar la lista de cursos')
      }
    });
  }

  insertarCurriculo(): void {
    if (this.curriculoForm.invalid) return;

    const request = this.curriculoForm.value as NuevoCurriculoRequest;

    this.servicios.insertarCurriculo(request).subscribe({
      next: () => {
        this.resetearForm();
        this.operacionTerminada.set(true);
      },
      error: (error: HttpErrorResponse) => {
        this.errorOperacion.set(error.error || 'Error al agregar curso al currículo');
      }
    });
  }

  resetearForm(): void {
    const anioActual = new Date().getFullYear();
    this.curriculoForm.reset({ cicloLectivo: anioActual });
  }
}