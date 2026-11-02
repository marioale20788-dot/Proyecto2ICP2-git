import { Component, OnInit, signal } from '@angular/core';

import { FormBuilder, FormGroup } from '@angular/forms';
import { servicios } from '../../../Services/Eventos/event.service';
import { ActivatedRoute } from '@angular/router';
import {  FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { libro } from '../../../Models/Libros/Libros';
import { HttpErrorResponse } from '@angular/common/http';
import { ListaLibrosComponent } from '../../../Components/Libros/Lista/lista-libros.component/lista-libros.component';
import { ModalConfirmationComponent } from '../../../Components/Modal/modal-confirmation.component';

@Component({

  imports: [ReactiveFormsModule, ListaLibrosComponent, ModalConfirmationComponent],
  selector: 'app-libros-page',
  templateUrl: './libros-page.html',
})
export class LibrosPage implements OnInit {
  libros = signal<libro[]>([]);
  errorMensage = signal<string | null>(null);
  valorABuscarForm !:FormGroup
  paginaActual:number=1;
  paginacionActiva:boolean=true;
  idLibroAeliminar =signal<string| undefined>(undefined);
  eliminado = signal<boolean>(false);
 
  
  constructor(private formBuiler:FormBuilder,
    private servicios:servicios
  ){

  }

  
  ngOnInit(): void {
  this.valorABuscarForm = this.formBuiler.group(
     {
      valor:[null,[Validators.required]]
    }
  
  );
  this.buscarTodos();
  }

  buscarTodos() :void{
    this.paginacionActiva=true;
    this.servicios.obtenerTodosLosLibros(this.paginaActual).subscribe({
    next:(librosObtenidos:libro[])=>{
      this.libros.set(librosObtenidos);
    },
    error:(error: any)=>{
      const errorTipo = error.error;
     this.errorMensage.set(errorTipo);
         
    }


    });
  }

siguientePagina():void{
this.paginaActual++;
this.buscarTodos();
}
paginaAnterior():void{
  if(this.paginaActual>1){
    this.paginaActual--;
    this.buscarTodos();
  }

}
eliminarLibro(idLibroAElminar:string):void{
  this.idLibroAeliminar.set(idLibroAElminar);
  this.eliminado.set(false);

}
eliminar():void{
    const idLibro = this.idLibroAeliminar();

    if (!idLibro) return;

   
      this.servicios.eliminarLibro(idLibro).subscribe({
        next: () => {
          
          this.eliminado.set(true);
this.buscarTodos();
        },
        error: (error: HttpErrorResponse) => { 
          const tipoError = error.error;
          this.errorMensage.set(tipoError);
        }
      });
}
buscarPorCodigoNombre(): void{
  this.paginacionActiva=false;
  const valorCodigo = this.valorABuscarForm.value.valor;
  if (!valorCodigo ) {
    this.paginacionActiva=true;
  return;
}
  this.servicios.obtenerLibroPorCodigoNombre(valorCodigo).subscribe({
    next:(libros:libro)=>{
      this.libros.set([libros]);

    },
    error:(error:HttpErrorResponse)=>{
     this.paginacionActiva=true;
     if(error.status==404){
     this.errorMensage.set("No se encontro ningun libro con ese id");
     }
    }
  } );

}





}