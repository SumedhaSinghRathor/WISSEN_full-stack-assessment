import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ProductService {

  private baseUrl = "http://localhost:8080/api/products";

  constructor(private http: HttpClient) {}

  // GET ALL
  getAll(){
    return this.http.get(this.baseUrl);
  }

  // GET BY ID
  getById(id:number){
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  // ADD PRODUCT
  add(product:any){
    return this.http.post(this.baseUrl, product);
  }

  // UPDATE PRODUCT
  update(id:number, product:any){
    return this.http.put(`${this.baseUrl}/${id}`, product);
  }

  // DELETE PRODUCT
  delete(id:number){
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}