import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormAsignacionDocenteComponent } from '../../../Components/AsignacionDocente/form-asignacion-docente.component';


@Component({
  imports: [RouterLink, FormAsignacionDocenteComponent],
  selector: 'app-create-asignacion-docente-page',
  templateUrl: './create-asignacion-docete.component.html'
})
export class CreateAsignacionDocenteComponent {}