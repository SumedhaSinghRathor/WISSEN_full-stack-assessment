import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface AuthUser {
  token: string | null;
  role: string;
  name: string;
  email: string;
}

@Injectable({ providedIn: 'root' })
export class AuthStateService {
  private userSubject = new BehaviorSubject<AuthUser>({
    token: null,
    role: '',
    name: '',
    email: ''
  });

  readonly user$ = this.userSubject.asObservable();

  initialize() {
    this.updateFromLocalStorage();
  }

  updateFromLocalStorage() {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role') || '';
    const name = localStorage.getItem('name') || '';
    const email = localStorage.getItem('email') || '';

    this.userSubject.next({
      token,
      role,
      name,
      email
    });
  }

  setUser(token: string, role: string, name: string, email: string) {
    localStorage.setItem('token', token);
    localStorage.setItem('role', role);
    localStorage.setItem('name', name);
    localStorage.setItem('email', email);
    this.userSubject.next({ token, role, name, email });
  }

  clearUser() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('name');
    localStorage.removeItem('email');
    this.userSubject.next({ token: null, role: '', name: '', email: '' });
  }
}
