import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ProductService } from '../../services/product';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { ChangeDetectorRef } from '@angular/core';
@Component({
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './admin-edit-product.html',
  styleUrls:['./admin-edit-product.css']
})
export class AdminEditProductComponent implements OnInit {

  product:any = {};
  id!: number;

  constructor(
    private route: ActivatedRoute,
    private service: ProductService,
    private router: Router,
    private cdr:ChangeDetectorRef
  ){}

  goBack() {
    window.history.back();
  }

  ngOnInit(){
    this.id = this.route.snapshot.params['id'];

    this.service.getById(this.id).subscribe((res:any)=>{
      console.log(res);
      
      this.product = res;
      this.cdr.detectChanges();
    });
  }

  update(){
    this.service.update(this.id, this.product).subscribe(()=>{
      alert("Updated Successfully ✅");
      this.router.navigate(['/admin/manage-products']);
    });
  }
}