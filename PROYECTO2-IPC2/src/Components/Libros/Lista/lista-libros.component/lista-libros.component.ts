import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { libro } from '../../../../Models/Libros/Libros';
import { servicios } from '../../../../Services/Eventos/event.service';
import { ModalConfirmationComponent } from '../../../Modal/modal-confirmation.component';

@Component({
  imports: [RouterLink],
  selector: 'app-lista-libros',
  templateUrl: './lista-libros.component.html',
})
export class ListaLibrosComponent {
idLibro!:string
  @Input() libros: libro[] = [];

   @Output()
   idLibroAElminar = new EventEmitter<string>();

  constructor(private servicios: servicios) {}


  eliminarLibro(idLibroa:string): void {
    this.idLibro = idLibroa;
    this.idLibroAElminar.emit(this.idLibro);
    
  }
}


