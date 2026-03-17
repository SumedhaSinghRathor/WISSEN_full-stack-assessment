import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product';

@Component({
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './admin-add-product.html'
})
export class AdminAddProductComponent {

  product = {
    name: '',
    price: 0,
    description: ''
  };

  constructor(private service: ProductService){}

  addProduct(){
    this.service.add(this.product).subscribe(()=>{
      alert("Product Added ✅");
      this.product = { name:'', price:0, description:'' };
    });
  }
}