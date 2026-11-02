import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { IncripcionRespuesta } from '../../../Models/Inscripcion/Inscripcion';
import { servicios } from '../../../Services/Eventos/event.service';
import { FormInscripcionComponent } from '../../../Components/Inscripcion/Form inscripcion/form-inscripcion.component';

@Component({
  imports: [ RouterLink, FormInscripcionComponent],
  selector: 'app-update-inscripcion-page',
  templateUrl: './update-inscripcion-page.component.html',
})
export class UpdateInscripcionPageComponent implements OnInit {

  codigoInscripcion!: string;
  errorMensaje = signal<string | null>(null);
  inscripcionUpdate = signal<IncripcionRespuesta | undefined>(undefined);

  constructor(
    private router: ActivatedRoute,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    this.codigoInscripcion = this.router.snapshot.params['id'];

    this.servicios.obtenerInscripcionPorId(this.codigoInscripcion).subscribe({
      next: (inscripcionAEditar) => {
        this.inscripcionUpdate.set(inscripcionAEditar);
      },
      error: (error: HttpErrorResponse) => {
        if (error.status === 404 || error.status === 400) {
          this.errorMensaje.set("INSCRIPCIÓN NO ENCONTRADA");
        }
      }
    });
  }

}