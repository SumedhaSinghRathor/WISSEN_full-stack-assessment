import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product';
import { Router } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';
@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-manage-products.html',
  styleUrls:['./admin-manage-products.css']
})
export class AdminManageProductsComponent implements OnInit {

  products:any[] = [];

  constructor(private service: ProductService,private router:Router,private cdr: ChangeDetectorRef ){}

 ngOnInit(){
  this.loadProducts();
  setInterval(() => {
      this.loadProducts();
    }, 1000);
}

loadProducts(){
  this.service.getAll().subscribe((res:any)=>{
    this.products = res;
     this.cdr.detectChanges();
  });
}

delete(id:number){
  this.service.delete(id).subscribe(()=>{
    alert("Deleted ❌");
    this.loadProducts();
  });
}
edit(product:any){
  this.router.navigate(['/admin/edit-product', product.asset_id]);
}
goToAdd(){
  this.router.navigate(['/admin/add-product']);
}
}