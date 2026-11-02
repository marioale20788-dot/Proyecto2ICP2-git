import { Component, OnInit, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

import { servicios } from '../../../Services/Eventos/event.service';
import { NotaFinalResponse } from '../../../Models/PerfilEstudiante/perfil';
import { AuthService } from '../../Login/auth/auth.service';

@Component({
 
  imports: [],
  selector: 'app-perfil-notas',
  templateUrl: './perfil-notas.component.html'
})
export class PerfilNotasComponent implements OnInit {

  idEstudiante: string = '';
  cicloActual = signal<number>(new Date().getFullYear());
  notas = signal<NotaFinalResponse[]>([]);
  errorOperacion = signal<string | null>(null);

  constructor(private servicios: servicios,private dp:AuthService ) {}

  ngOnInit(): void {
    this.idEstudiante = this.dp.getUserName()!
    this.cargarNotas();
  }

  cambiarCiclo(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    this.cicloActual.set(Number(selectElement.value));
    this.cargarNotas();
  }

  cargarNotas(): void {
    if (!this.idEstudiante) return;

    this.servicios.obtenerNotas(this.idEstudiante, this.cicloActual()).subscribe({
      next: (res) => {
        this.notas.set(res);
        this.errorOperacion.set(null);
      },
      error: (error: HttpErrorResponse) => {
        this.notas.set([]);
        this.errorOperacion.set(error.error );
      }
    });
  }
}