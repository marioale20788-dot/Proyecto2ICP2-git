import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { servicios } from '../../../Services/Eventos/event.service';
import { SeccionRespuesta } from '../../../Models/Seccion/Seccion';
import { FormSeccionComponent } from '../../../Components/Seccion/form seccion component/form-seccion.component';


@Component({
  imports: [FormSeccionComponent, RouterLink],
  selector: 'app-edit-seccion-page',
  templateUrl: './editar-seccion-page.component.html',
})
export class EditSeccionPageComponent implements OnInit {

  seccion = signal<SeccionRespuesta | null>(null);
  errorCarga = signal<string | null>(null);

  constructor(
    private route: ActivatedRoute,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    const idSeccion = this.route.snapshot.paramMap.get('id');
    if (idSeccion) {
      this.cargarSeccion(idSeccion);
    }
  }

  cargarSeccion(id: string): void {
    this.servicios.obtenerSeccionPorId(id).subscribe({
      next: (seccionObtenida: SeccionRespuesta) => {
        this.seccion.set(seccionObtenida);
      },
      error: (error: HttpErrorResponse) => {
        this.errorCarga.set(error.error || 'Error al obtener la información de la sección.');
      }
    });
  }
}