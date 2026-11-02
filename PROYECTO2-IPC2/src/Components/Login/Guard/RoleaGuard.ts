import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router, UrlTree } from '@angular/router';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root'
})
export class roleGuard implements CanActivate {


  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(route: ActivatedRouteSnapshot): boolean | UrlTree {
    const allowedRoles = route.data['allowedRoles'] as string[];

    
    if (!allowedRoles || allowedRoles.length === 0) {
      return true;
    }

  
    const tienePermiso = allowedRoles.some(rol => this.authService.hasRole(rol));

    if (tienePermiso) {
      return true;
    }

    return this.router.createUrlTree(['/login']);
  }
}