import { Component, EventEmitter, Input, Output } from '@angular/core';
import { RouterLink } from '@angular/router';
import { servicios } from '../../../Services/Eventos/event.service';
import { PrestamoRespuesta } from '../../../Models/Prestamo/Prestamo';


@Component({
  imports: [RouterLink],
  selector: 'app-lista-prestamos',
  templateUrl: './pretamos-list.component.html',
})
export class ListaPrestamosComponent {
  idPrestamo!: string;
  
  @Input() prestamos: PrestamoRespuesta[] = [];

  @Output()
  idPrestamoAElminar = new EventEmitter<string>();

  constructor(private servicios: servicios) {}

  eliminarPrestamo(idPrestamoa: string): void {
    this.idPrestamo = idPrestamoa;
    this.idPrestamoAElminar.emit(this.idPrestamo);
  }
}