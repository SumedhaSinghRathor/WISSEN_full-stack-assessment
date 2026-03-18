import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ProductService } from '../../services/product';
import { TradeService } from '../../services/trade';
import { ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgChartsModule } from 'ng2-charts';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule,NgChartsModule],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardComponent implements OnInit {
  products: any[] = [];
  selectedStock: any = null;
  buyQuantities: { [key:number]: number } = {};
  priceSeries: number[] = [];
  userId = Number(localStorage.getItem('userId') || 1);
chartData: { labels: string[]; datasets: Array<{ data: number[]; label: string; tension: number; fill: boolean; }> } = {
  labels: [],
  datasets: [
    {
      data: [],
      label: 'Price',
      tension: 0.4,
      fill: true
    }
  ]
};

chartOptions = {
  responsive: true,
  plugins: {
    legend: { display: false }
  },
  scales: {
    x: { display: false },
    y: { display: true }
  }
};
  constructor(private service: ProductService, private tradeService: TradeService, private cdr: ChangeDetectorRef) { }

  ngOnInit() {
    this.loadData();
    setInterval(() => this.loadData(), 1000);
  }

  loadData() {
    this.service.getAll().subscribe({
      next: (res: any) => {
        this.products = res;
        if (!this.selectedStock && this.products.length > 0) {
          this.selectStock(this.products[0]);
        }
        if (this.selectedStock) {
          const updated = this.products.find((p:any) => p.asset_id === this.selectedStock.asset_id);
          if (updated) {
            this.selectedStock = updated;
            this.priceSeries.push(updated.current_price);
             this.addChartPoint(updated.current_price);
            if (this.priceSeries.length > 20) this.priceSeries.shift();
          }
        }
        this.cdr.detectChanges();
      },
      error: (err) => console.log(err)
    });
  }

  buy(stock: any) {
    const qty = Number(this.buyQuantities[stock.asset_id] || 0);
    if (qty <= 0) {
      alert('Enter a positive quantity');
      return;
    }
    this.tradeService.buy({ userId: this.userId, productId: stock.asset_id, quantity: qty }).subscribe({
      next: () => {
        alert('Bought successfully');
        this.buyQuantities[stock.asset_id] = 0;
        this.tradeService.tradeChanged$.next();
      },
      error: (err) => alert(err?.error || 'Buy failed')
    });
  }
addChartPoint(price: number) {

  const newPrice = Math.round(price * 100) / 100;

  // current data
  const data = [...this.chartData.datasets[0].data];
  const labels = [...this.chartData.labels];

  // limit graph length (last 20 points)
  if (data.length >= 20) {
    data.shift();
    labels.shift();
  }

  data.push(newPrice);
  labels.push(new Date().toLocaleTimeString());

  // 🔥 IMPORTANT: reassign object (Angular change detection)
  this.chartData = {
    labels: labels,
    datasets: [
      {
        ...this.chartData.datasets[0],
        data: data
      }
    ]
  };
}
  selectStock(stock: any) {
    this.selectedStock = stock;
    this.priceSeries = [stock.current_price];
  }
}
