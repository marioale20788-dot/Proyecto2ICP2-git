import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { DevolverPrestamoComponent } from '../../../Components/Prestamo/Devolver prestamo/devolver-prestamo.component';
import { PrestamoRespuesta } from '../../../Models/Prestamo/Prestamo';
import { servicios } from '../../../Services/Eventos/event.service';

@Component({
  standalone: true,
  imports: [DevolverPrestamoComponent, RouterLink],
  selector: 'app-devolver-prestamo-page',
  templateUrl: './devolver-prestamo-page.component.html',
})
export class DevolverPrestamoPageComponent implements OnInit {

  public codigoPrestamo!: string;
  public errorMensaje = signal<string | null>(null);
  public prestamoDevolver = signal<PrestamoRespuesta | undefined>(undefined);

  constructor(
    private router: ActivatedRoute,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.codigoPrestamo = this.router.snapshot.params['id'];
    this.servicios.obtenerPrestamoPorId(this.codigoPrestamo).subscribe({
      next: (prestamo) => {
        this.prestamoDevolver.set(prestamo);
      },
      error: (error: HttpErrorResponse) => {
        if (error.status === 400 || error.status === 404) {
          this.errorMensaje.set("PRÉSTAMO NO ENCONTRADO");
        } else {
          this.errorMensaje.set(error.error || "Error al obtener la información del préstamo.");
        }
      }
    });
  }
}