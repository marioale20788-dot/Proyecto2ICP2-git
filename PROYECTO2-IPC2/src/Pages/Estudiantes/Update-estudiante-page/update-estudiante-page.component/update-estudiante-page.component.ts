import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';

import { EstudianteRespuesta } from '../../../../Models/Estudiantes/Estudiante';
import { servicios } from '../../../../Services/Eventos/event.service';
import { FormEstudianteComponent } from '../../../../Components/Estudiante/Form estudiante/form-estudiante.component/form-estudiante.component';


@Component({
  selector: 'app-update-estudiante-page',
  imports: [RouterLink, FormEstudianteComponent],
  templateUrl: './update-estudiante-page.component.html',
})
export class UpdateEstudiantePageComponent implements OnInit {

  dpiEstudiante!: string;
  errorMensaje = signal<string | null>(null);
  estudianteUpdate = signal<EstudianteRespuesta | undefined>(undefined);

  constructor(
    private router: ActivatedRoute, 
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.dpiEstudiante = this.router.snapshot.params['dpi'];

    this.servicios.buscarEstudiantePorDpi(this.dpiEstudiante).subscribe({
      next: (estudianteAEditar) => {
        this.estudianteUpdate.set(estudianteAEditar);
      },
      error: (error: HttpErrorResponse) => {
       const errora= error.error;
          this.errorMensaje.set(errora);
        
      }
    });
  }
}