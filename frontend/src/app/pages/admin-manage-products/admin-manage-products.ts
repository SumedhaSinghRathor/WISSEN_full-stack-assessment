import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product';

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-manage-products.html'
})
export class AdminManageProductsComponent implements OnInit {

  products:any[] = [];

  constructor(private service: ProductService){}

 ngOnInit(){
  this.loadProducts();
}

loadProducts(){
  this.service.getAll().subscribe((res:any)=>{
    this.products = res;
  });
}

delete(id:number){
  this.service.delete(id).subscribe(()=>{
    alert("Deleted ❌");
    this.loadProducts();
  });
}
}