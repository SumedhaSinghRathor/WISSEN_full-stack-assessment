import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ProductService {

  private baseUrl = "http://localhost:8080/products";

  constructor(private http: HttpClient) {}

  // getAll(){
  //   return this.http.get(this.baseUrl);
  // }
getAll(){
  return [
    {
      asset_id: 1,
      name: "Microsoft Corp",
      ticker: "MSFT",
      current_price: 402.15
    },
    {
      asset_id: 2,
      name: "Alphabet Inc",
      ticker: "GOOGL",
      current_price: 142.87
    },
    {
      asset_id: 3,
      name: "Apple Inc",
      ticker: "AAPL",
      current_price: 189.32
    }
    // 👉 you can paste full data here
  ];
}
  add(product:any){
    return this.http.post(this.baseUrl, product);
  }

  delete(id:number){
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  update(id:number, product:any){
    return this.http.put(`${this.baseUrl}/${id}`, product);
  }
}