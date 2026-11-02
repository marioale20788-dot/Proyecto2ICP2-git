import { Component } from '@angular/core';
import { FormEstudianteComponent } from '../../../Components/Estudiante/Form estudiante/form-estudiante.component/form-estudiante.component';


@Component({
  selector: 'app-create-estudiante-page',

  imports: [ FormEstudianteComponent],
  templateUrl: './create-estudiante-page.component.html',
})
export class CreateEstudiantePageComponent {}