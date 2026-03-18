import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AdminGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean {
    const token = localStorage.getItem('token');
    if (!token) {
      this.router.navigate(['/']);
      return false;
    }

    const role = this.extractRoleFromToken(token);
    if (role === 'ADMIN') {
      return true;
    }

    this.router.navigate(['/dashboard']);
    return false;
  }

  private extractRoleFromToken(token: string): string {
    try {
      const payload = token.split('.')[1];
      const decoded = atob(payload.replace(/-/g, '+').replace(/_/g, '/'));
      const parsed = JSON.parse(decoded);
      return parsed.role || '';
    } catch {
      return '';
    }
  }
}
