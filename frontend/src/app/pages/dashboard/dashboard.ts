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
  this.products = this.service.getAll(); // no subscribe needed
}
}