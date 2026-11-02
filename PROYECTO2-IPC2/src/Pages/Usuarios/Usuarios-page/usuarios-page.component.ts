import { Component, OnInit, signal, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { KeyValuePipe } from "@angular/common";
import { servicios } from '../../../Services/Eventos/event.service';
import { tipoUsuario } from '../../../Models/UsuariosEmpleado/TipoUsuario';
import { RespuestaEmpleado } from '../../../Models/UsuariosEmpleado/EmpleadoUsuarioRespuesta';
import { ListaUsuariosComponent } from '../../../Components/Usuarios/ListaDeUsuarios/lista-usuarios.component';


@Component({
  selector: 'app-usuarios-page',
  templateUrl: './usuarios-page.component.html',
  imports: [ReactiveFormsModule, KeyValuePipe, ListaUsuariosComponent]
})
export class UsuariosPageComponent implements OnInit {
  
  

    TipoUsuarioEnum = tipoUsuario;
    
  usuarios = signal<RespuestaEmpleado[]>([]);
  errorMensage = signal<string | null>(null);
  filtroForm!: FormGroup;
  paginaActual: number = 1;
  paginacionActiva: boolean = true;

  constructor(private servicios:servicios,
  private  formBuilder:FormBuilder
  ){

  }

  ngOnInit(): void {
    this.filtroForm = this.formBuilder.group({
      dpi: [''],
      rol: ['TODOS'],
      estado: [true] 
    });

    this.buscarUsuarios();
  }

  
  buscarUsuarios(resetPágina: boolean = false): void {
    if (resetPágina) {
      this.paginaActual = 1;
    }

    this.errorMensage.set(null);
    const { dpi, rol, estado } = this.filtroForm.value;

    if (dpi && dpi.trim() !== '') {
      this.paginacionActiva = false; 
      this.servicios.buscarUsuariosDpi(dpi.trim()).subscribe({
        next:(usuarioObtenido:RespuestaEmpleado) => {
            this.usuarios.set([usuarioObtenido]);


        },
        error:(err: HttpErrorResponse) => {
        this.manejarError(err);
              

        }
      });
      return;
    }

    this.paginacionActiva = true;

  
    if (rol && rol !== 'TODOS') {
      this.servicios.buscarUsuariosEstadoRol(estado, rol as tipoUsuario,this.paginaActual).subscribe({
        next: (usuarios:RespuestaEmpleado[]) =>{
          this.usuarios.set(usuarios);
        },
        error:(err:HttpErrorResponse)=>{
          this.manejarError(err);
        }
      });
      return;
    }

    this.servicios.buscarUsuarioEstado(estado,this.paginaActual).subscribe({
      next: (usuarios:RespuestaEmpleado[])=>{
        this.usuarios.set(usuarios);
      },
      error:(err:HttpErrorResponse)=>{
          this.manejarError(err);
        }
      
    });
  }

  siguientePagina(): void {
    this.paginaActual++;
    this.buscarUsuarios();
  }

  paginaAnterior(): void {
    if (this.paginaActual > 1) {
      this.paginaActual--;
      this.buscarUsuarios();
    }
  }

  limpiarFiltros(): void {
    this.filtroForm.patchValue({
      dpi: '',
      rol: 'TODOS',
      estado: true
    });
    this.buscarUsuarios(true);
  }

  public manejarError(error: HttpErrorResponse): void {
    const errorC = error.error;
  this.errorMensage.set(errorC);
  }
}