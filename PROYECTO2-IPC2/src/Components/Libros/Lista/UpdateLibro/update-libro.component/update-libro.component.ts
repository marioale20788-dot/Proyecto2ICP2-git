import { Component, Input, input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ɵInternalFormsSharedModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { libro } from '../../../../../Models/Libros/Libros';
import { KeyValuePipe } from "@angular/common";

import { servicios } from '../../../../../Services/Eventos/event.service';
import { HttpErrorResponse } from '@angular/common/http';


@Component({
  imports: [ɵInternalFormsSharedModule, ReactiveFormsModule],
  selector: 'app-update-libro',
  templateUrl: './update-libro.component.html',
})
export class UpdateLibroComponent implements OnInit{

updateLibroForm!: FormGroup;
nuevoLibro!:libro;
operacionTerminada = signal<boolean>(false);
errorOperacion = signal<String|null>(null);






@Input()
libroAEditar!:libro;
@Input()
modoEditar:boolean=false;


constructor(private formBuilder:FormBuilder, private serivicios:servicios){

}


ngOnInit(): void {
  this.updateLibroForm = this.formBuilder.group(
    {
     idLibro: [null,[Validators.required]],
      nombreLibro :[null,[Validators.required]],
    autor:[null,[Validators.required]],
    editorial :[null,[Validators.required]],
    cantidad :[null,[Validators.required]],

    }
   
  );
 this.resetearForm();
  
}
updateLibro():void{
  this.libroAEditar = this.updateLibroForm.value as libro;
  const{idLibro, ...updateLibroRequest}=this.libroAEditar;
  this.serivicios.updateLibro(idLibro,updateLibroRequest).subscribe({
   next:()=>{
    this.resetearForm();
    this.operacionTerminada.set(true);
   },
   error:(error:HttpErrorResponse)=>{
  const tipoError = error.error;

    this.errorOperacion.set(tipoError);

   
}

  })
  

}
crearLibro():void{
this.nuevoLibro = this.updateLibroForm.value as libro;
this.serivicios.crearLibro(this.nuevoLibro).subscribe({
next:()=>{
  this.resetearForm();
  this.operacionTerminada.set(true);
},
error:(error:HttpErrorResponse)=>{
  const tipoError = error.error;

    this.errorOperacion.set(tipoError);
   }
})
}

accion(): void {
  if (this.updateLibroForm.invalid) return;

  if (this.modoEditar) {
    this.updateLibro();
  } else {
    this.crearLibro();
  }
}

resetearForm():void{
  if(this.modoEditar){
    this.resetUpdate();

  }else{
this.resetCrear();
  }
}
private cambiarInputs(){
  this.updateLibroForm.patchValue(this.libroAEditar);
}

private resetCrear() {
  this.updateLibroForm.reset()

}
private resetUpdate(){
  this.updateLibroForm.reset(this.libroAEditar);
  this.cambiarInputs();
}







}
