import { Component, Input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { servicios } from '../../../Services/Eventos/event.service';
import { EnumPago, GetCostosPorAnioEstudianteRequest, NewPagoRequest } from '../../../Models/Pagos/Pago';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-lista-costos',
  templateUrl: './lista-costos.component.html'
})
export class ListaCostosComponent implements OnInit {

  @Input() costos = signal<GetCostosPorAnioEstudianteRequest[]>([]);
  @Input() idEstudiante: string = '';

  costoSeleccionado = signal<GetCostosPorAnioEstudianteRequest | null>(null);
  pagoForm!: FormGroup;
  enumPago = EnumPago;

  operacionTerminada = signal<boolean>(false);
  errorOperacion = signal<string | null>(null);

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    const fechaHoy = new Date().toISOString().split('T')[0];

    this.pagoForm = this.formBuilder.group({
      tipoPago: [EnumPago.MENSUALIDAD, [Validators.required]],
      monto: [null, [Validators.required, Validators.min(0.01)]],
      fechaDePago: [fechaHoy, [Validators.required]],
      idSecretaria: ['', [Validators.required]]
    });
  }

  seleccionarCosto(costo: GetCostosPorAnioEstudianteRequest): void {
    this.costoSeleccionado.set(costo);
    this.pagoForm.patchValue({
      monto: costo.monto,
      tipoPago: EnumPago.MENSUALIDAD
    });
  }

  procesarPago(): void {
    if (this.pagoForm.invalid) return;

    const { tipoPago, monto, fechaDePago, idSecretaria } = this.pagoForm.value;

    const pago: NewPagoRequest = {
      tipoPago,
      monto: Number(monto),
      fechaDePago: fechaDePago, 
      idEstudiante: this.idEstudiante,
      idSecretaria,
      idCosto: this.costoSeleccionado()?.idCosto || null,
      idPrestamo: null
    };

    this.servicios.registrarPago(pago).subscribe({
      next: (idPago: string) => {
        this.operacionTerminada.set(true);
        const urlPdf = this.servicios.obtenerUrlReportePdf(idPago);
        window.open(urlPdf, '_blank');
        this.costoSeleccionado.set(null);
        
        const fechaHoy = new Date().toISOString().split('T')[0];
        this.pagoForm.reset({ fechaDePago: fechaHoy, tipoPago: EnumPago.MENSUALIDAD });
      },
      error: (error: HttpErrorResponse) => {
        const tipoError = error.error;
        this.errorOperacion.set(tipoError);
      }
    });
  }
}