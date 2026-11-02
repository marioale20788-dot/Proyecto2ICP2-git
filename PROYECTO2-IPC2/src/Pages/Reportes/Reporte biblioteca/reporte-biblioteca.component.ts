import { Component, OnInit, signal } from '@angular/core';

import { CurrencyPipe } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { GetPrestamosConMoraEstudiante } from '../../../Models/Pagos/Pago';
import { servicios } from '../../../Services/Eventos/event.service';
import { HttpErrorResponse } from '@angular/common/http';


@Component({
  selector: 'app-reporte-biblioteca',
  standalone: true,
  imports: [ReactiveFormsModule, CurrencyPipe],
  templateUrl: './reporte-biblioteca.component.html'
})
export class ReporteBibliotecaComponent implements OnInit {

  filtroForm!: FormGroup;
  prestamos: GetPrestamosConMoraEstudiante[] = [];
  errorMensaje = signal<string>('');

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.filtroForm = this.formBuilder.group({});
  }

  consultar(): void {
    this.errorMensaje.set('');

    this.servicios.obtenerReporteBiblioteca().subscribe({
      next: (prestamosObtenidos) => {
        this.prestamos = prestamosObtenidos;
      },
      error: (err:HttpErrorResponse) => {
      
        this.errorMensaje.set(err.error);
      }
    });
  }

  abrirPdf(): void {
    const url = this.servicios.obtenerUrlReporteBibliotecaPdf();
    window.open(url, '_blank');
  }
}