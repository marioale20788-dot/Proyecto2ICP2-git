import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Carrera } from '../../../Models/Carrera/carrera';
import { servicios } from '../../../Services/Eventos/event.service';
import { FormCarreraComponent } from '../../../Components/Carrera/form carrera/form-carrera.component';


@Component({
  imports: [RouterLink, FormCarreraComponent],
  selector: 'app-edit-carrera-page',
  templateUrl: './editar-carrera-page.component.html',
})
export class EditCarreraPageComponent implements OnInit {

  carrera = signal<Carrera | null>(null);
  errorCarga = signal<string | null>(null);

  constructor(
    private route: ActivatedRoute,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    const idCarrera = this.route.snapshot.paramMap.get('id');
    if (idCarrera) {
      this.cargarCarrera(idCarrera);
    }
  }

  cargarCarrera(id: string): void {
    this.servicios.obtenerCarreraPorIdONombre(id).subscribe({
      next: (carreraObtenida: Carrera) => {
        this.carrera.set(carreraObtenida);
      },
      error: (error: HttpErrorResponse) => {
        this.errorCarga.set(error.error );
      }
    });
  }
}