import { Component, inject, signal } from '@angular/core';
import { RouterOutlet, Router, NavigationEnd } from '@angular/router';
import { filter } from 'rxjs/operators';
import { HeaderComponent } from '../Components/Header/header.component'; 

@Component({
  imports: [RouterOutlet, HeaderComponent],
  selector: 'app-root',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('PROYECTO2-IPC2');
  private router = inject(Router);

  mostrarHeader: boolean = false;

  constructor() {
 
    this.router.events.pipe(
      filter((event): event is NavigationEnd => event instanceof NavigationEnd)
    ).subscribe((event: NavigationEnd) => {
      this.mostrarHeader = !event.urlAfterRedirects.includes('/login');
    });
  }
}