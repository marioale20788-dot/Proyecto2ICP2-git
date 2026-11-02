import { Component, Input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { EnumPago, GetPrestamosConMoraEstudiante, NewPagoRequest } from '../../../Models/Pagos/Pago';
import { servicios } from '../../../Services/Eventos/event.service';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-lista-mora',
  templateUrl: './lista-mora.component.html'
})
export class ListaMoraComponent implements OnInit {

  @Input() prestamos = signal<GetPrestamosConMoraEstudiante[]>([]);
  @Input() idEstudiante: string = '';

  prestamoSeleccionado = signal<GetPrestamosConMoraEstudiante | null>(null);
  pagoForm!: FormGroup;

  operacionTerminada = signal<boolean>(false);
  errorOperacion = signal<string | null>(null);

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    const fechaHoy = new Date().toISOString().split('T')[0];

    this.pagoForm = this.formBuilder.group({
      tipoPago: [{ value: EnumPago.MORA, disabled: true }],
      monto: [null, [Validators.required, Validators.min(0.01)]],
      fechaDePago: [fechaHoy, [Validators.required]],
      idSecretaria: ['', [Validators.required]]
    });
  }

  seleccionarPrestamo(prestamo: GetPrestamosConMoraEstudiante): void {
    this.prestamoSeleccionado.set(prestamo);
    this.pagoForm.patchValue({
      monto: prestamo.mora
    });
  }

  procesarPago(): void {
    if (this.pagoForm.invalid) return;

    const { monto, fechaDePago, idSecretaria } = this.pagoForm.getRawValue();

    const pago: NewPagoRequest = {
      tipoPago: EnumPago.MORA,
      monto: Number(monto),
      fechaDePago: fechaDePago, 
      idEstudiante: this.idEstudiante,
      idSecretaria,
      idCosto: null,
      idPrestamo: this.prestamoSeleccionado()?.idPrestamo || null
    };

    this.servicios.registrarPago(pago).subscribe({
      next: (idPago: string) => {
        this.operacionTerminada.set(true);
        const urlPdf = this.servicios.obtenerUrlReportePdf(idPago);
        window.open(urlPdf, '_blank');
        this.prestamoSeleccionado.set(null);

        const fechaHoy = new Date().toISOString().split('T')[0];
        this.pagoForm.reset({ 
          tipoPago: EnumPago.MORA, 
          fechaDePago: fechaHoy 
        });
      },
      error: (error: HttpErrorResponse) => {
        const tipoError = error.error;
        this.errorOperacion.set(tipoError);
      }
    });
  }
}