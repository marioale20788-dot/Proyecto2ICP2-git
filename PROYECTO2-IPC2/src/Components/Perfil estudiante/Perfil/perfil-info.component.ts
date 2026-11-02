import { Component, OnInit, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';

import { servicios } from '../../../Services/Eventos/event.service';
import { EstudianteRespuesta } from '../../../Models/Estudiantes/Estudiante';
import { AuthService } from '../../Login/auth/auth.service';

@Component({
  
  imports: [],
  selector: 'app-perfil-info',
  templateUrl: './perfil-info.component.html'
})
export class PerfilInfoComponent implements OnInit {

  idEstudiante: string = '';
  estudiante = signal<EstudianteRespuesta | null>(null);
  errorOperacion = signal<string | null>(null);

  constructor(private servicios: servicios, private dp:AuthService ) {}

  ngOnInit(): void {
    this.idEstudiante  = this.dp.getUserName()!;

    if (!this.idEstudiante) {
      this.errorOperacion.set('No se encontró un usuario autenticado.');
      return;
    }

    this.servicios.obtenerEstudiantePorDpi(this.idEstudiante).subscribe({
      next: (res) => this.estudiante.set(res),
      error: (error: HttpErrorResponse) => {
        this.errorOperacion.set(error.error );
      }
    });
  }
}