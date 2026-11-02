import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AlumnoMoroso } from '../../Models/Estudiantes/Estudiante';
import { servicios } from '../../Services/Eventos/event.service';
import { HttpErrorResponse } from '@angular/common/http';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-reporte-morosos',
  standalone: true,
  imports: [ ReactiveFormsModule,CurrencyPipe],
  templateUrl: './reporte-morosos.component.html'
})
export class ReporteMorososComponent implements OnInit {

  filtroForm!: FormGroup;
  alumnos: AlumnoMoroso[] = [];
 errorMensaje = signal<string>('');

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.filtroForm = this.formBuilder.group({
      idSeccion: ['', [Validators.required]],
      ciclo: [2026, [Validators.required, Validators.min(2000)]]
    });
  }

  consultar(): void {
    if (this.filtroForm.invalid) {
      this.filtroForm.markAllAsTouched();
     this.errorMensaje.set('Por favor complete todos los campos requeridos correctamente.');
      return;
    }

    const { idSeccion, ciclo } = this.filtroForm.value;
   this.errorMensaje.set('');

    this.servicios.obtenerReporteSolventes(idSeccion, ciclo).subscribe({
      next: (alumnosObtenidos) => {
        this.alumnos = alumnosObtenidos;
      },
      error: (err:HttpErrorResponse) => {
        this.errorMensaje.set(err.error);
      }
    });
  }

  abrirPdf(): void {
    if (this.filtroForm.invalid) {
      this.filtroForm.markAllAsTouched();
      return;
    }

    const { idSeccion, ciclo } = this.filtroForm.value;
    const url = this.servicios.obtenerUrlReporteMorososPdf(idSeccion, ciclo);
    window.open(url, '_blank');
  }
}