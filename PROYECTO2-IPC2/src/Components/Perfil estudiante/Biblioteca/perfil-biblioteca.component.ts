import { Component, OnInit, signal } from '@angular/core';

import { servicios } from '../../../Services/Eventos/event.service';
import { PrestamoRespuesta } from '../../../Models/Prestamo/Prestamo';
import { GetPrestamosConMoraEstudiante } from '../../../Models/Pagos/Pago';
import { libro } from '../../../Models/Libros/Libros';
import { AuthService } from '../../Login/auth/auth.service';

@Component({

  imports: [],
  selector: 'app-perfil-biblioteca',
  templateUrl: './perfil-biblioteca.component.html'
})
export class PerfilBibliotecaComponent implements OnInit {

  idEstudiante: string = '';
  vistaActiva = signal<'activos' | 'moras' | 'catalogo'>('activos');
  paginaActual = signal<number>(1);

  prestamosActivos = signal<PrestamoRespuesta[]>([]);
  prestamosMora = signal<GetPrestamosConMoraEstudiante[]>([]);
  libros = signal<libro[]>([]);

  constructor(private servicios: servicios, private id:AuthService) {}

  ngOnInit(): void {
    this.idEstudiante = this.id.getUserName()!;
    this.cargarPrestamosActivos();
    this.cargarPrestamosConMora();
    this.cargarLibros();
  }

  cambiarVista(vista: 'activos' | 'moras' | 'catalogo'): void {
    this.vistaActiva.set(vista);
  }

  cargarPrestamosActivos(): void {
    if (!this.idEstudiante) return;

    this.servicios.obtenerPrestamosActivos(this.idEstudiante).subscribe({
      next: (res) => {
        this.prestamosActivos.set(res)
      },
      error: () => {
        this.prestamosActivos.set
        ([])
      }
        });
  }

  cargarPrestamosConMora(): void {
    if (!this.idEstudiante) return;

    this.servicios.obtenerPrestamosConMora(this.idEstudiante).subscribe({
      next: (res) => {
        this.prestamosMora.set(res)
      },
      error: () => {
        this.prestamosMora.set([])
   }
   });
  }

  cargarLibros(): void {
    this.servicios.obtenerLibrosCatalog(this.paginaActual()).subscribe({
      next: (res) =>{ this.libros.set(res)
      },
      error: () => {
        this.libros.set([])
  }
});
  }

  siguientePagina(): void {
    this.paginaActual.update(p => p + 1);
    this.cargarLibros();
  }

  anteriorPagina(): void {
    if (this.paginaActual() > 1) {
      this.paginaActual.update(p => p - 1);
      this.cargarLibros();
    }
  }
}