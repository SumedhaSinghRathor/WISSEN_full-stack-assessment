import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private baseUrl = 'http://localhost:8081/api/auth';

  constructor(private http: HttpClient) {}

  register(data: any) {
    return this.http.post(`${this.baseUrl}/register`, data);
  }

  login(data: any) {
    return this.http.post(`${this.baseUrl}/login`, data);
  }

  changePassword(data: any) {
    return this.http.put(`${this.baseUrl}/change-password`, data);
  }

  addWallet(data: any) {
    return this.http.put(`${this.baseUrl}/wallet`, data);
  }

  getUser(id: number) {
    return this.http.get(`http://localhost:8081/api/auth/user/${id}`);
  }
}
