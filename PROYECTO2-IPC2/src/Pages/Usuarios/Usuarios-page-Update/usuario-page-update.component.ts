import { Component, OnInit, signal } from '@angular/core';
import { RespuestaEmpleado } from '../../../Models/UsuariosEmpleado/EmpleadoUsuarioRespuesta';
import { servicios } from '../../../Services/Eventos/event.service';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { FormUsuarioComponent } from '../../../Components/Usuarios/UsuariosForm/form-usuario.component';

@Component({
  imports: [RouterLink,FormUsuarioComponent],
  selector: 'app-usuario-page-update',
  templateUrl: './usuario-page-update.component.html',
})
export class UsuarioPageUpdateComponent implements OnInit {
idUsuario!:string
errorMensaje=signal<string | null>(null);
Usuario = signal<RespuestaEmpleado | undefined>(undefined);
existe = signal<boolean>(true);


constructor(private services:servicios, private router:ActivatedRoute){

}

ngOnInit(): void {
  this.idUsuario = this.router.snapshot.params['id']
  this.services.buscarUsuariosDpi(this.idUsuario).subscribe({
    next:(usuario: RespuestaEmpleado )=>{
      this.Usuario.set(usuario)
      
    }, 
    error:(error:HttpErrorResponse)=>{
      const errorC = error.error;
      this.errorMensaje.set(errorC);

    }

  });



}


}
