import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ReporteAcademico } from '../../../Models/Reportes/Reporte';
import { servicios } from '../../../Services/Eventos/event.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-reporte-academico',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './reporte-academico.component.html'
})
export class ReporteAcademicoComponent implements OnInit {

  filtroForm!: FormGroup;
  cursos: ReporteAcademico[] = [];
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
     this.errorMensaje.set('COMPLETE LOS CAMPOS REQUERIDOS');
      return;
    }

    const { idSeccion, ciclo } = this.filtroForm.value;
  this.errorMensaje.set('');

    this.servicios.obtenerReporteAcademico(idSeccion, ciclo).subscribe({
      next: (cursosObtenidos) => {
        this.cursos = cursosObtenidos;
      },
      error: (err:HttpErrorResponse) => {
       this.errorMensaje.set(err.error);
      }
    });
  }

 abrirPdf(): void {
    if (this.filtroForm.invalid) {
      return;
    }

    const { idSeccion, ciclo } = this.filtroForm.value;
    const url = this.servicios.obtenerUrlReporteAcademicoPdf(idSeccion, ciclo);
    window.open(url, '_blank');
  }
}