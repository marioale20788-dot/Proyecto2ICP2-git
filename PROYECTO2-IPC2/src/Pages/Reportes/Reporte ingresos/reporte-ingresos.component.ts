import { Component, OnInit, signal } from '@angular/core';

import { CurrencyPipe } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Pagos } from '../../../Models/PerfilEstudiante/perfil';
import { EnumPago } from '../../../Models/Pagos/Pago';
import { servicios } from '../../../Services/Eventos/event.service';
import { HttpErrorResponse } from '@angular/common/http';


@Component({
  selector: 'app-reporte-ingresos',
  standalone: true,
  imports: [ReactiveFormsModule, CurrencyPipe],
  templateUrl: './reporte-ingresos.component.html'
})
export class ReporteIngresosComponent implements OnInit {

  filtroForm!: FormGroup;
  pagos: Pagos[] = [];
  tiposPago = Object.values(EnumPago);
  errorMensaje = signal<string>('');

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.filtroForm = this.formBuilder.group({
      tipo: [EnumPago.INSCRIPCION, [Validators.required]],
      fechaInicio: [''],
      fechaFinal: ['']
    });
  }

  private obtenerParametrosFechas() {
    const { tipo, fechaInicio, fechaFinal } = this.filtroForm.value;
    return {
      tipo: tipo as EnumPago,
      inicio: fechaInicio && fechaInicio.trim() !== '' ? fechaInicio : 'null',
      fin: fechaFinal && fechaFinal.trim() !== '' ? fechaFinal : 'null'
    };
  }

  consultar(): void {
    if (this.filtroForm.invalid) {
      this.errorMensaje.set('Por favor seleccione un tipo de pago válido.');
      return;
    }

    const { tipo, inicio, fin } = this.obtenerParametrosFechas();
    this.errorMensaje.set('');

    this.servicios.obtenerReporteIngresos(tipo, inicio, fin).subscribe({
      next: (pagosObtenidos) => {
        this.pagos = pagosObtenidos;
      },
      error: (err:HttpErrorResponse) => {
     
        this.errorMensaje.set(err.error);
      }
    });
  }

  abrirPdf(): void {
    if (this.filtroForm.invalid) return;
    const { tipo, inicio, fin } = this.obtenerParametrosFechas();
    const url = this.servicios.obtenerUrlReporteIngresosPdf(tipo, inicio, fin);
    window.open(url, '_blank');
  }
}