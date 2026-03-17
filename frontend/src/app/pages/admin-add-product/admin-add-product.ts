import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './admin-add-product.html',
  styleUrls:['./admin-add-product.css']
})
export class AdminAddProductComponent {

 product = {
  asset_id: 0,
  name: '',
  ticker: '',
  current_price: 0,
  type: ''
};

  constructor(private service: ProductService,private router: Router){}
goBack(){
  this.router.navigate(['/admin/manage-products']);
}
  addProduct(){
  this.service.add(this.product).subscribe({
    next: () => {
      alert("Product Added ✅");
    },
    error: (err) => console.log(err)
  });
}
}