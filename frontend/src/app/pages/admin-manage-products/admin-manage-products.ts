import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-manage-products.html',
  styleUrls:['./admin-manage-products.css']
})
export class AdminManageProductsComponent implements OnInit {

  products:any[] = [];

  constructor(private service: ProductService,private router:Router){}

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
goToAdd(){
  this.router.navigate(['/admin/add-product']);
}
}