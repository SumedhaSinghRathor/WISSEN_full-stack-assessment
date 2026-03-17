import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product';

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html'
})
export class DashboardComponent implements OnInit {

  products:any[] = [];

  constructor(private service: ProductService){}

 ngOnInit(){
  this.service.getAll().subscribe({
    next: (res:any) => {
      this.products = res;
    },
    error: (err) => {
      console.log(err);
    }
  });
}
}