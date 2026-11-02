import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { servicios } from '../../../Services/Eventos/event.service';
import { Curso } from '../../../Models/Curso/Curso';
import { FormCursoComponent } from '../../../Components/Curso/Form curso/form-curso.component';


@Component({
  imports: [FormCursoComponent, RouterLink],
  selector: 'app-edit-curso-page',
  templateUrl: './editar-curso-page.component.html',
})
export class EditCursoPageComponent implements OnInit {

  curso = signal<Curso | null>(null);
  errorCarga = signal<string | null>(null);

  constructor(
    private route: ActivatedRoute,
    private servicios: servicios
  ) {}

  ngOnInit(): void {
    const idCurso = this.route.snapshot.paramMap.get('id');
    if (idCurso) {
      this.cargarCurso(idCurso);
    }
  }

  cargarCurso(codigo: string): void {
    this.servicios.obtenerCursoPorCodigoNombre(codigo).subscribe({
      next: (cursoObtenido: Curso) => {
        this.curso.set(cursoObtenido);
      },
      error: (error: HttpErrorResponse) => {
        this.errorCarga.set('No se pudo obtener la información del curso a editar.');
      }
    });
  }
}