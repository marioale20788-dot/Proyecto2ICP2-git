import { Component, OnInit, signal } from '@angular/core';

import { CurrencyPipe } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Pagos } from '../../../Models/PerfilEstudiante/perfil';
import { GetCostosPorAnioEstudianteRequest } from '../../../Models/Pagos/Pago';
import { servicios } from '../../../Services/Eventos/event.service';
import { HttpErrorResponse } from '@angular/common/http';


@Component({
  selector: 'app-estado-cuenta',
  standalone: true,
  imports: [ReactiveFormsModule, CurrencyPipe],
  templateUrl: './estado-cuenta.component.html'
})
export class EstadoCuentaComponent implements OnInit {

  filtroForm!: FormGroup;
  pagos: Pagos[] = [];
  costos: GetCostosPorAnioEstudianteRequest[] = [];
  tipoConsulta: 'PAGOS' | 'COSTOS' = 'PAGOS';
  
  errorMensaje = signal<string>('');

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {

    this.filtroForm = this.formBuilder.group({
      idUsuario: ['', [Validators.required]],
      fechaInicio: [''],
      fechaFinal: ['']
    });
  }

  private obtenerParametrosFechas() {
    const { idUsuario, fechaInicio, fechaFinal } = this.filtroForm.value;
    return {
      idUsuario,
      inicio: fechaInicio && fechaInicio.trim() !== '' ? fechaInicio : 'null',
      fin: fechaFinal && fechaFinal.trim() !== '' ? fechaFinal : 'null'
    };
  }

  consultarPagos(): void {
    if (this.filtroForm.invalid) {
      this.errorMensaje.set('Ingrese el ID del estudiante / usuario.');
      return;
    }

    const { idUsuario, inicio, fin } = this.obtenerParametrosFechas();
    this.errorMensaje.set('');
    this.tipoConsulta = 'PAGOS';

    this.servicios.obtenerPagosReporte(idUsuario, inicio, fin).subscribe({
      next: (pagosObtenidos) => {
        this.pagos = pagosObtenidos;
        this.costos = [];
      },
      error: (err:HttpErrorResponse) => {
     
        this.errorMensaje.set(err.error);
      }
    });
  }

  consultarCostos(): void {
    if (this.filtroForm.invalid) {
      this.errorMensaje.set('Ingrese el ID del estudiante / usuario.');
      return;
    }

    const { idUsuario, inicio, fin } = this.obtenerParametrosFechas();
    this.errorMensaje.set('');
    this.tipoConsulta = 'COSTOS';

    this.servicios.obtenerCostos(idUsuario, inicio, fin).subscribe({
      next: (costosObtenidos) => {
        this.costos = costosObtenidos;
        this.pagos = [];
      },
      error: (err:HttpErrorResponse) => {
   
        this.errorMensaje.set(err.error);
      }
    });
  }

  abrirPdfPagos(): void {
    if (this.filtroForm.invalid) return;
    const { idUsuario, inicio, fin } = this.obtenerParametrosFechas();
    const url = this.servicios.obtenerUrlPagosPdf(idUsuario, inicio, fin);
    window.open(url, '_blank');
  }

  abrirPdfCostos(): void {
    if (this.filtroForm.invalid) return;
    const { idUsuario, inicio, fin } = this.obtenerParametrosFechas();
    const url = this.servicios.obtenerUrlCostosPdf(idUsuario, inicio, fin);
    window.open(url, '_blank');
  }
}