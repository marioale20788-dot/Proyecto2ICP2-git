import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { FormCurriculoComponent } from '../../../Components/Curriculo/Form curriculo/form-curriculo.component';


@Component({
  imports: [RouterLink, FormCurriculoComponent],
  selector: 'app-create-curriculo-page',
  templateUrl: './create-curriculo-page.component.html',
})
export class CreateCurriculoPageComponent {

}