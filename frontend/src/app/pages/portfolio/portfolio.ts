import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
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
  portfoliosSummary: any[] = [];
  selectedPortfolioId: number | null = null;
  selectedPortfolioName: string = '';
  selectedPortfolioPositions: any[] = [];
  selectedPortfolioTotal: number = 0;
  selectedPortfolioProfitLoss: number = 0;
  userId: number = 0;
  selling: { [key: string]: number } = {};
  tradeSub: Subscription | null = null;

  constructor(private portfolioService: PortfolioService, private tradeService: TradeService, private authState: AuthStateService, private cdr: ChangeDetectorRef) {}

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
    this.portfolioService.getPortfolio(this.userId).subscribe({
      next: (res: any) => {
        this.portfolio = res;
        this.portfoliosSummary = this.buildPortfolioSummary(res);
        if (this.selectedPortfolioId) {
          this.loadPortfolioDetails(this.selectedPortfolioId, this.selectedPortfolioName);
        }
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Portfolio load error', err);
        this.portfolio = [];
        this.portfoliosSummary = [];
        this.selectedPortfolioPositions = [];
        this.cdr.detectChanges();
      }
    });
  }

  buildPortfolioSummary(items: any[]) {
    const map: { [portfolioId: string]: { portfolioId: number; portfolioName: string; value: number; profitLoss: number; count: number } } = {};
    for (const p of items) {
      const pid = p.portfolioId || 0;
      const name = p.portfolioName || 'Default';
      if (!map[pid]) { map[pid] = { portfolioId: pid, portfolioName: name, value: 0, profitLoss: 0, count: 0 }; }
      map[pid].value += Number(p.value || 0);
      map[pid].profitLoss += Number(p.profitLoss || 0);
      map[pid].count += 1;
    }
    return Object.values(map).sort((a,b) => b.value - a.value);
  }

  loadPortfolioDetails(portfolioId: number, portfolioName: string) {
    this.selectedPortfolioId = portfolioId;
    this.selectedPortfolioName = portfolioName;
    this.portfolioService.getPortfolioDetails(this.userId, portfolioId).subscribe({
      next: (res: any) => {
        this.selectedPortfolioPositions = res || [];
        this.selectedPortfolioTotal = this.selectedPortfolioPositions.reduce((sum: number, p: any) => sum + Number(p.value || 0), 0);
        this.selectedPortfolioProfitLoss = this.selectedPortfolioPositions.reduce((sum: number, p: any) => sum + Number(p.profitLoss || 0), 0);
        this.cdr.detectChanges();
      },
      error: (err) => {
        alert('Could not load portfolio details.');
        this.selectedPortfolioPositions = [];
        this.cdr.detectChanges();
      }
    });
  }

  getGraphWidth(value: number) {
    if (!this.selectedPortfolioPositions.length) return 0;
    const max = Math.max(...this.selectedPortfolioPositions.map((p:any) => Number(p.value || 0)));
    if (max <= 0) return 5;
    return Math.max(5, (value / max) * 100);
  }

  sell(position: any) {
    const key = `${position.productId}-${position.portfolioId}`;
    const qty = Number(this.selling[key] || 0);
    if (qty <= 0) {
      alert('Enter sell quantity');
      return;
    }
    if (qty > position.quantity) {
      alert('Cannot sell more than you hold.');
      return;
    }
    this.tradeService.sell({ userId: this.userId, productId: position.productId, quantity: qty, portfolioId: position.portfolioId }).subscribe({
      next: () => {
        this.selling[key] = 0;
        this.tradeService.tradeChanged$.next();
        this.loadPortfolio();
      },
      error: (err) => alert(err?.error || 'Sell failed')
    });
  }
}
