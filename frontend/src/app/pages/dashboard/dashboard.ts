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
  portfolios: any[] = [];
  selectedPortfolioId: number | null = null;
  newPortfolioName = '';
  priceSeries: number[] = [];
  userId = Number(localStorage.getItem('userId') || 1);
  showBuyModal = false;
  buyModalStock: any = null;
  buyModalQty = 1;
  buyModalPortfolioId: number | null = null;
  buyModalNewPortfolioName = '';
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
    this.loadPortfolios();
    setInterval(() => this.loadData(), 1000);
  }

  loadPortfolios() {
    this.tradeService.getPortfolios(this.userId).subscribe({
      next: (res:any) => {
        this.portfolios = res;
        if (this.portfolios.length > 0 && !this.selectedPortfolioId) {
          this.selectedPortfolioId = this.portfolios[0].id;
        }
      },
      error: (err) => {
        console.error('Load portfolios error', err);
        this.portfolios = [];
      }
    });
  }

  createPortfolio() {
    const name = this.newPortfolioName?.trim();
    if (!name) {
      alert('Enter a portfolio name');
      return;
    }
    this.tradeService.createPortfolio({ userId: this.userId, name }).subscribe({
      next: () => {
        this.newPortfolioName = '';
        this.loadPortfolios();
      },
      error: (err) => {
        alert(err?.error || 'Could not create portfolio');
      }
    });
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

  openBuyModal(stock: any) {
    this.buyModalStock = stock;
    this.buyModalQty = 1;
    this.buyModalPortfolioId = this.selectedPortfolioId;
    this.buyModalNewPortfolioName = '';
    this.showBuyModal = true;
  }

  closeBuyModal() {
    this.showBuyModal = false;
    this.buyModalStock = null;
  }

  private getErrorMessage(err: any, fallback: string) {
    if (!err) return fallback;
    if (typeof err === 'string') return err;
    if (err?.error) {
      if (typeof err.error === 'string') return err.error;
      if (err.error?.message) return err.error.message;
      if (typeof err.error === 'object') return JSON.stringify(err.error);
    }
    if (err.message) return err.message;
    return fallback;
  }

  createPortfolioAndSelect() {
    const name = (this.buyModalNewPortfolioName || '').trim();
    if (!name) {
      alert('Enter a portfolio name');
      return;
    }
    this.tradeService.createPortfolio({ userId: this.userId, name }).subscribe({
      next: (created:any) => {
        const createdId = created?.id || created?.portfolioId;
        this.loadPortfolios();
        if (createdId) {
          this.buyModalPortfolioId = createdId;
          this.selectedPortfolioId = createdId;
        }
        this.buyModalNewPortfolioName = '';
      },
      error: (err) => alert(this.getErrorMessage(err, 'Could not create portfolio'))
    });
  }

  confirmBuy() {
    if (!this.buyModalStock) {
      return;
    }
    const qty = Number(this.buyModalQty || 0);
    if (qty <= 0) {
      alert('Enter a positive quantity');
      return;
    }
    const portfolioId = this.buyModalPortfolioId || this.selectedPortfolioId;
    if (!portfolioId) {
      alert('Select or create a portfolio before buying');
      return;
    }
    this.tradeService.buy({ userId: this.userId, productId: this.buyModalStock.asset_id, quantity: qty, portfolioId: portfolioId }).subscribe({
      next: () => {
        alert('Bought successfully');
        this.buyQuantities[this.buyModalStock.asset_id] = 0;
        this.selectedPortfolioId = portfolioId;
        this.loadPortfolios();
        this.tradeService.tradeChanged$.next();
        this.closeBuyModal();
      },
      error: (err) => alert(this.getErrorMessage(err, 'Buy failed'))
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
