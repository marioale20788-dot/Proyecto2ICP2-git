import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';

import { HttpErrorResponse } from '@angular/common/http';
import { CicloLectivoRespuesta } from '../../../Models/CicloLectivo/CicloLectivo';
import { servicios } from '../../../Services/Eventos/event.service';
import { FormCicloComponent } from '../../../Components/CicloLectivo/Form cicloLectivo/form-ciclo.component';

@Component({
  imports: [RouterLink, FormCicloComponent],
  selector: 'app-update-ciclo-page.component',
  templateUrl: './update-ciclo-page.component.html',
})
export class UpdateCicloPageComponent implements OnInit {

  anioCiclo!: number;
  errorMensaje = signal<string | null>(null);
  cicloUpdate = signal<CicloLectivoRespuesta | undefined>(undefined);
  existe = signal<boolean>(true);

  constructor(private router: ActivatedRoute, private servicios: servicios) {}

  ngOnInit(): void {
    this.anioCiclo = Number(this.router.snapshot.params['id']);
    this.servicios.obtenerCicloPorAnio(this.anioCiclo).subscribe({
      next: (cicloAEditar) => {
        this.cicloUpdate.set(cicloAEditar);
      },
      error: (Error: HttpErrorResponse) => {
        const tipoError = Error.error;
        this.errorMensaje.set(tipoError);
      }
    });
  }
}