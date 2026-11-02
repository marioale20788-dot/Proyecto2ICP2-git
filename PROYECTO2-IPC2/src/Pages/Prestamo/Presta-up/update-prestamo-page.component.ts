import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';

import { HttpErrorResponse } from '@angular/common/http';
import { PrestamoRespuesta } from '../../../Models/Prestamo/Prestamo';
import { servicios } from '../../../Services/Eventos/event.service';
import { FormPrestamoComponent } from '../../../Components/Prestamo/Prestamo form/form-pretammo.component';


@Component({
  imports: [RouterLink, FormPrestamoComponent],
  selector: 'app-update-prestamo-page.component',
  templateUrl: './update-prestamo-page.component.html',
})
export class UpdatePrestamoPageComponent implements OnInit {

  codigoPrestamo!: string;
  errorMensaje = signal<string | null>(null);
  prestamoUpdate = signal<PrestamoRespuesta | undefined>(undefined);
  existe = signal<boolean>(true);

  constructor(private router: ActivatedRoute, private servicios: servicios) {}

  ngOnInit(): void {
    this.codigoPrestamo = this.router.snapshot.params['id'];
    this.servicios.obtenerPrestamoPorId(this.codigoPrestamo).subscribe({
      next: (prestamoAEditar) => {
        this.prestamoUpdate.set(prestamoAEditar);
      },
      error: (Error: HttpErrorResponse) => {
        if (Error.status == 400 || Error.status == 404) {
          this.errorMensaje.set("PRÉSTAMO NO ENCONTRADO");
        }
      }
    });
  }
}