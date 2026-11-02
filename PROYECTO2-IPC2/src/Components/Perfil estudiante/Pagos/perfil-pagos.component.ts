import { Component, OnInit, signal } from '@angular/core';

import { servicios } from '../../../Services/Eventos/event.service';
import { CostosPendientes, Pagos } from '../../../Models/PerfilEstudiante/perfil';
import { AuthService } from '../../Login/auth/auth.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
 
  imports: [],
  selector: 'app-perfil-pagos',
  templateUrl: './perfil-pagos.component.html'
})
export class PerfilPagosComponent implements OnInit {

  idEstudiante: string = '';
  cicloActual = signal<number>(new Date().getFullYear());
  pagos = signal<Pagos[]>([]);
  costos = signal<CostosPendientes[]>([]);

  constructor(private servicios: servicios, private dpi:AuthService) {}

  ngOnInit(): void {
    this.idEstudiante = this.dpi.getUserName()!
    this.cargarDatosPagos();
  }

  cambiarCiclo(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    this.cicloActual.set(Number(selectElement.value));
    this.cargarDatosPagos();
  }
cargarDatosPagos(): void {
  if (!this.idEstudiante) return;


  this.servicios.obtenerPagosRealizados(this.idEstudiante, this.cicloActual()).subscribe({
    next: (res) => {
      this.pagos.set(res)
    },
    error: (err: HttpErrorResponse) => {
   
      this.pagos.set([]);
      if (err.status !== 404) {
     
      }
    }
  });

 
  this.servicios.obtenerCostosPendientes(this.idEstudiante, this.cicloActual()).subscribe({
    next: (res) => {
      this.costos.set(res)}
      ,
    error: (err: HttpErrorResponse) => { 
      this.costos.set([]); 
      if (err.status !== 404) {
        
      }
    }
  });
}
}