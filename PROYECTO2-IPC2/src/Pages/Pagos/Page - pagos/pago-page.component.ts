import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { ListaCostosComponent } from '../../../Components/Pago/Lista pago/lista-costos.component';
import { ListaMoraComponent } from '../../../Components/Pago/Lista mora/lista-mora.component';
import { FormPagoComponent } from '../../../Components/Pago/Form pago/form-pago.component';
import { GetCostosPorAnioEstudianteRequest, GetPrestamosConMoraEstudiante } from '../../../Models/Pagos/Pago';
import { servicios } from '../../../Services/Eventos/event.service';


type TipoOperacion = 'COSTOS' | 'MORA' | 'EXTRA';

@Component({
  imports: [
    ReactiveFormsModule, 
    ListaCostosComponent, 
    ListaMoraComponent, 
    FormPagoComponent
  ],
  selector: 'app-pago-page',
  templateUrl: './pago-page.component.html'
})
export class PagoPageComponent implements OnInit {

  filtroForm!: FormGroup;
  tipoOperacionActual = signal<TipoOperacion>('COSTOS');

  costos = signal<GetCostosPorAnioEstudianteRequest[]>([]);
  prestamos = signal<GetPrestamosConMoraEstudiante[]>([]);
  
  estudianteBuscado = signal<string | null>(null);
  errorBusqueda = signal<string | null>(null);

  constructor(
    private formBuilder: FormBuilder,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.filtroForm = this.formBuilder.group({
      idEstudiante: ['', [Validators.required]],
      idCiclo: [new Date().getFullYear(), [Validators.required]]
    });
  }

  cambiarTipo(tipo: TipoOperacion): void {
    this.tipoOperacionActual.set(tipo);
    this.costos.set([]);
    this.prestamos.set([]);
    this.estudianteBuscado.set(null);
    this.errorBusqueda.set(null);
  }

  buscar(): void {
    if (this.filtroForm.invalid) return;

    const { idEstudiante, idCiclo } = this.filtroForm.value;
    this.errorBusqueda.set(null);

    if (this.tipoOperacionActual() === 'COSTOS') {
      this.servicios.obtenerCostosPendientes(idEstudiante, idCiclo).subscribe({
        next: (res) => {
          this.costos.set(res);
          this.estudianteBuscado.set(idEstudiante);
          this.errorBusqueda.set(null);
        },
        error: (error: HttpErrorResponse) => {
          this.costos.set([]);
          this.estudianteBuscado.set(null);
          const tipoError = error.error;
          this.errorBusqueda.set(tipoError);
        }
      });
    } else if (this.tipoOperacionActual() === 'MORA') {
      this.servicios.obtenerPrestamosConMora(idEstudiante).subscribe({
        next: (res) => {
          this.prestamos.set(res);
          this.estudianteBuscado.set(idEstudiante);
          this.errorBusqueda.set(null);
        },
        error: (error: HttpErrorResponse) => {
          this.prestamos.set([]);
          this.estudianteBuscado.set(null);
          const tipoError = error.error;
          this.errorBusqueda.set(tipoError);
        }
      });
    } else if (this.tipoOperacionActual() === 'EXTRA') {

      this.estudianteBuscado.set(idEstudiante);
    }
  }
}