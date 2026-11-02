import { Component } from '@angular/core';
import { servicios } from '../../../Services/Eventos/event.service';

@Component({
  selector: 'app-solvencia',
  templateUrl: './solvencia-pago.component.html'
})
export class SolvenciaComponent {

  cicloPorDefecto: string = new Date().getFullYear().toString();

  constructor(private servicios: servicios) {}

  generarSolvencia(idEstudiante: string, ciclo: string): void {
    const idLimpio = idEstudiante.trim();
    const cicloLimpio = ciclo.trim();

    if (!idLimpio || !cicloLimpio) {
      alert('Por favor ingrese el ID del estudiante y el ciclo.');
      return;
    }

    const urlPdf = this.servicios.obtenerUrlSolvenciaPdf(idLimpio, cicloLimpio);
    window.open(urlPdf, '_blank');
  }
}