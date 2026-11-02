import { Component, OnInit, Signal, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { servicios } from '../../../../Services/Eventos/event.service';
import { libro } from '../../../../Models/Libros/Libros';
import { HttpErrorResponse } from '@angular/common/http';
import { UpdateLibroComponent } from '../../../../Components/Libros/Lista/UpdateLibro/update-libro.component/update-libro.component';

@Component({
  imports: [UpdateLibroComponent, RouterLink],
  selector: 'app-update-libro-page.component',
  templateUrl: './update-libro-page.component.html',
})
export class UpdateLibroPageComponent implements OnInit{

codigoLibro!:string
errorMensaje=signal<string | null>(null);
libroUpdate = signal<libro | undefined>(undefined);
existe = signal<boolean>(true);

constructor(private router:ActivatedRoute, private servicios:servicios){

}


ngOnInit(): void {
  this.codigoLibro = this.router.snapshot.params['id'];
this.servicios.obtenerLibroPorCodigoNombre(this.codigoLibro).subscribe({
next:(libroAEditar)=>{
this.libroUpdate.set(libroAEditar);
},
error:(Error:HttpErrorResponse)=>{

     if(Error.status==400){
this.errorMensaje.set("LIBRO NO ENCONTRADO");
     }

}

})
}

}
