import { Component, Input, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { servicios } from '../../../Services/Eventos/event.service';
import { FinalizarPrestamo, PrestamoRespuesta } from '../../../Models/Prestamo/Prestamo';


@Component({
  standalone: true,
  imports: [RouterLink],
  selector: 'app-devolver-prestamo',
  templateUrl: './devolver-prestamo.component.html'
})
export class DevolverPrestamoComponent {

  @Input() prestamo!: PrestamoRespuesta;

  public operacionTerminada = signal<boolean>(false);
  public errorOperacion = signal<string | null>(null);

  constructor(
    private servicios: servicios,
    private router: Router
  ) {}

  confirmarDevolucion(): void {
    const hoy = new Date().toISOString().split('T')[0];
    const estudianteId =  this.prestamo.idUsuario;

   
    const devolución: FinalizarPrestamo = {
      idPrestamo: this.prestamo.idPrestamo,
      fechaEntregaFinal: hoy,
      idEstudiante: estudianteId
    };


    this.servicios.devolverLibro(devolución).subscribe({
      next: () => {
        this.operacionTerminada.set(true);
    
          this.router.navigate(['/Prestamos']);
  
      },
      error: (error: HttpErrorResponse) => {
        this.errorOperacion.set(error.error );
      }
    });
  }
}