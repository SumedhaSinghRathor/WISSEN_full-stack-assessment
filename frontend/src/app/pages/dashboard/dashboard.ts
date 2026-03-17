import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product';

@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrls:['./dashboard.css']
})
export class DashboardComponent implements OnInit {

  products:any[] = [{
    asset_id: 1,
    current_price: 402.15,
    name: "Microsoft Corp",
    ticker: "MSFT",
    type: "STOCK"
  },
  {
    asset_id: 2,
    current_price: 189.50,
    name: "Apple Inc",
    ticker: "AAPL",
    type: "STOCK"
  }];

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