import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { servicios } from '../../../Services/Eventos/event.service';
import { AsignacionMaestro } from '../../../Models/Maestro/Maestro';
import { AuthService } from '../../Login/auth/auth.service';

@Component({
  selector: 'app-maestro-asignaciones',
  imports: [RouterLink],
  templateUrl: './maestro-asignaciones.component.html'
})
export class MaestroAsignacionesComponent implements OnInit {

  public cicloInput = signal<number>(new Date().getFullYear());
  public asignaciones = signal<AsignacionMaestro[]>([]);
  public errorMsg = signal<string | null>(null);

  constructor(
    private servicios: servicios,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.buscarAsignaciones();
  }

  CicloChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.cicloInput.set(Number(input.value));
  }

  buscarAsignaciones(event?: Event): void {
    if (event) {
      event.preventDefault();
    }

    const idMaestro = this.authService.getUserName();
    this.errorMsg.set(null);

    if (!idMaestro) {
      this.errorMsg.set('No se encontró una sesión activa para el maestro.');
      return;
    }

    this.servicios.obtenerAsignaciones(idMaestro, this.cicloInput()).subscribe({
      next: (asignaciones) => {
        this.asignaciones.set(asignaciones);
      },
      error: (err) => {
        this.asignaciones.set([]);
        if (err.status === 404) {
          this.errorMsg.set(`No se encontraron cursos asignados para el ciclo ${this.cicloInput()}.`);
        } else {
          this.errorMsg.set('Error al conectar con el servidor para obtener las asignaciones.');
        }
      }
    });
  }
}