import { Component } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../Login/auth/auth.service'; 

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, RouterLinkActive],
  templateUrl: './header.component.html'
})
export class HeaderComponent {


  constructor(
    private authService: AuthService,
    private router: Router
  ) {}


  tieneRol(roles: string[]): boolean {
    return roles.some(rol => this.authService.hasRole(rol));
  }


  get usuarioConectado(): string | undefined {
    return this.authService.getUserName();
  }

  cerrarSesion(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}