import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginRequest, LoginResponse } from '../../../Models/Login/Login';
import { RestConstants } from '../../../Shared/api-rest/aplicacionBackend';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly tokenKey: string = 'token';
  private restConstants = new RestConstants();

  constructor(private httpClient: HttpClient) {}

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.httpClient.post<LoginResponse>(
      `${this.restConstants.getApiURL()}/auth/login`, 
      credentials
    );
  }

  saveToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
  }

  isLoggedIn(): boolean {
    const token = this.getToken();
    if (!token) return false;
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.exp * 1000 > Date.now();
    } catch {
      return false;
    }
  }

  getRoles(): string[] {
    if (!this.isLoggedIn()) {
      return [];
    }
    const token = this.getToken();
    try {
      const payload = JSON.parse(atob(token!.split('.')[1]));
      return payload.roles || [];
    } catch {
      return [];
    }
  }

  hasRole(role: string): boolean {
    return this.getRoles().includes(role);
  }

  getUserName(): string | undefined {
    if (!this.isLoggedIn()) {
      return undefined;
    }
    const token = this.getToken();
    try {
      const payload = JSON.parse(atob(token!.split('.')[1]));
      return payload.sub;
    } catch {
      return undefined;
    }
  }
}