import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormCarreraComponent } from '../../../Components/Carrera/form carrera/form-carrera.component';


@Component({
  imports: [RouterLink, FormCarreraComponent],
  selector: 'app-create-carrera-page',
  templateUrl: './create-carrera-page.component.html',
})
export class CreateCarreraPageComponent {}
