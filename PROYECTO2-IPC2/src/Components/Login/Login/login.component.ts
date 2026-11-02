import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../auth/auth.service';
import { LoginRequest } from '../../../Models/Login/Login';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  imports: [FormsModule],

})
export class LoginComponent {

  credentials: LoginRequest = {
    idUsuario: '',
    contrasena: ''
  };

  errorMessage: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(): void {
    this.authService.login(this.credentials).subscribe({
      next: (response) => {
        this.authService.saveToken(response.token);
        this.router.navigate(['/Home']);
      },
      error: (err) => {
        this.errorMessage = err.error?.mensaje || 'Credenciales inválidas';
      }
    });
  }
}