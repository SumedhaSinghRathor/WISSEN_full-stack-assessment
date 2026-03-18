import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Subject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class TradeService {
  baseUrl = 'http://localhost:8080/api/trade';
  tradeChanged$ = new Subject<void>();

  constructor(private http: HttpClient) {}

  buy(payload:any) {
    return this.http.post(`${this.baseUrl}/buy`, payload, { observe: 'response' });
  }

  sell(payload:any) {
    return this.http.post(`${this.baseUrl}/sell`, payload, { observe: 'response' });
  }

  getTransactions(userId:number) {
    return this.http.get(`${this.baseUrl}/transactions/${userId}`);
  }
}
