import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductService } from '../../services/product';
import { ChangeDetectorRef } from '@angular/core';
@Component({
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardComponent implements OnInit {

  products: any[] = [];

  constructor(private service: ProductService, private cdr: ChangeDetectorRef) { }

  ngOnInit() {

    this.loadData();

    setInterval(() => {
      this.loadData();
    }, 1000);
  }
  loadData() {
    this.service.getAll().subscribe({
      next: (res: any) => {
        this.products = res;

        this.cdr.detectChanges(); // 🔥 FORCE UI UPDATE
      },
      error: (err) => console.log(err)
    });
  }
}