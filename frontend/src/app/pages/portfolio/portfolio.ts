import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';
import { PortfolioService } from '../../services/portfolio';
import { TradeService } from '../../services/trade';
import { AuthStateService } from '../../services/auth-state';

@Component({
  selector: 'app-portfolio',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './portfolio.html',
  styleUrls: ['./portfolio.css']
})
export class PortfolioComponent implements OnInit, OnDestroy {
  portfolio: any[] = [];
  userId: number = 0;
  selling: { [key: number]: number } = {};
  tradeSub: Subscription | null = null;

  constructor(private portfolioService: PortfolioService, private tradeService: TradeService, private authState: AuthStateService) {}

  ngOnInit() {
    this.authState.initialize();
    const userId = localStorage.getItem('userId');
    this.userId = userId ? +userId : 1;
    this.loadPortfolio();
    this.tradeSub = this.tradeService.tradeChanged$.subscribe(() => this.loadPortfolio());
  }

  ngOnDestroy() {
    this.tradeSub?.unsubscribe();
  }

  loadPortfolio() {
    console.log('Loading portfolio for user', this.userId);
    this.portfolioService.getPortfolio(this.userId).subscribe({
      next: (res: any) => {
        console.log('Portfolio response', res);
        this.portfolio = res;
      },
      error: (err) => {
        console.error('Portfolio load error', err);
        this.portfolio = [];
      }
    });
  }

  sell(position: any) {
    const qty = this.selling[position.productId] || 0;
    if (qty <= 0) {
      alert('Enter sell quantity');
      return;
    }
    this.tradeService.sell({ userId: this.userId, productId: position.productId, quantity: qty }).subscribe({
      next: () => {
        this.selling[position.productId] = 0;
        this.tradeService.tradeChanged$.next();
      },
      error: (err) => alert(err?.error || 'Sell failed')
    });
  }
}
