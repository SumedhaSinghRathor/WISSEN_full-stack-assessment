import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class PortfolioService {
  baseUrl = 'http://localhost:8080/api/trade';

  constructor(private http: HttpClient) {}

  getPortfolio(userId:number) {
    return this.http.get(`${this.baseUrl}/portfolio/${userId}`);
  }

  getPortfolioDetails(userId:number, portfolioId:number) {
    return this.http.get(`${this.baseUrl}/portfolio/${userId}/${portfolioId}`);
  }
}
