import { Component, Input, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, ɵInternalFormsSharedModule } from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { GradoRespuesta, NewGradoRequest } from '../../../Models/Grados/Grados';
import { Carrera } from '../../../Models/Carrera/carrera';
import { servicios } from '../../../Services/Eventos/event.service';


@Component({
  imports: [ɵInternalFormsSharedModule, ReactiveFormsModule],
  selector: 'app-form-grado',
  templateUrl: './form-grado.component.html',
})
export class FormGradoComponent implements OnInit {

  updateGradoForm!: FormGroup;
  nuevoGrado!: NewGradoRequest;
  operacionTerminada = signal<boolean>(false);
  errorOperacion = signal<String | null>(null);


  carreras = signal<Carrera[]>([]);

  niveles = [
    { id: 'NIV_PRE', nombre: 'Pre-primaria' },
    { id: 'NIV_PRI', nombre: 'Primaria' },
    { id: 'NIV_BAS', nombre: 'Básico' },
    { id: 'NIV_DIV', nombre: 'Diversificado' }
  ];

  @Input() gradoAEditar!: GradoRespuesta;
  @Input() modoEditar: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private serivicios: servicios
  ) {}

  ngOnInit(): void {
    this.updateGradoForm = this.formBuilder.group({
      nombreGrado: [null, [Validators.required]],
      idNivel: [null, [Validators.required]],
      idCarrera: [null],
    });

    this.cargarCarrerasActivas();
    this.resetearForm();
  }

  cargarCarrerasActivas(): void {
    this.serivicios.obtenerCarrerasPorEstado(true).subscribe({
      next: (data: Carrera[]) => {
        this.carreras.set(data);
      },
      error: (error: HttpErrorResponse) => {
        this.errorOperacion.set('Error al cargar las carreras activas');
      }
    });
  }

  crearGrado(): void {
    this.nuevoGrado = this.updateGradoForm.value as NewGradoRequest;
    this.serivicios.crearGrado(this.nuevoGrado).subscribe({
      next: () => {
        this.resetearForm();
        this.operacionTerminada.set(true);
      },
      error: (error: HttpErrorResponse) => {
        const tipoError = error.error;
        this.errorOperacion.set(tipoError);
      }
    });
  }

  accion(): void {
    if (this.updateGradoForm.invalid) return;
    this.crearGrado();
  }

  resetearForm(): void {
    this.updateGradoForm.reset();
  }
}