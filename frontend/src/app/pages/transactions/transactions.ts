import { Component, OnInit, OnDestroy, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Subscription } from 'rxjs';
import { TradeService } from '../../services/trade';

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './transactions.html',
  styleUrls: ['./transactions.css']
})
export class TransactionsComponent implements OnInit, OnDestroy {
  transactions: any[] = [];
  userId: number = 0;
  tradeSub: Subscription | null = null;

  constructor(private tradeService: TradeService, private cdr: ChangeDetectorRef) {}

  ngOnInit() {
    const userId = localStorage.getItem('userId');
    this.userId = userId ? +userId : 1;
    this.loadTransactions();
    this.tradeSub = this.tradeService.tradeChanged$.subscribe(() => this.loadTransactions());
  }

  ngOnDestroy() {
    this.tradeSub?.unsubscribe();
  }

  loadTransactions() {
    console.log('Loading transactions for user', this.userId);
    this.tradeService.getTransactions(this.userId).subscribe({
      next: (res:any) => {
        console.log('Transactions response', res);
        this.transactions = res;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Transactions load error', err);
        this.transactions = [];
        this.cdr.detectChanges();
      }
    });
  }
}
