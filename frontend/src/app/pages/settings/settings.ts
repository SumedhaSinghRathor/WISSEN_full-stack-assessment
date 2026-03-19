import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth';
import { AuthStateService } from '../../services/auth-state';

@Component({
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './settings.html',
  styleUrls: ['./settings.css']
})
export class SettingsComponent {
  currentPassword = '';
  newPassword = '';
  addAmount: number | null = null;
  wallet = 0;
  message = '';
  isError = false;
  userId = Number(localStorage.getItem('userId') || 0);
  activeSection: 'password' | 'wallet' = 'wallet';

  constructor(
    private authService: AuthService,
    private authState: AuthStateService,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {
    this.loadUser();
  }

  loadUser() {
    if (this.userId && this.userId > 0) {
      this.authService.getUser(this.userId).subscribe({
        next: (user: any) => {
          this.wallet = user?.wallet || 0;
          this.cdr.detectChanges();
        },
        error: () => {
          this.wallet = 0;
          this.cdr.detectChanges();
        }
      });
    }
  }

  goBack() {
    window.history.back();
  }

  showToast(message: string, error = false) {
    this.message = message;
    this.isError = error;
    setTimeout(() => {
      this.message = '';
    }, 3500);
  }

  changePassword() {
    if (!this.userId || this.userId <= 0) {
      this.showToast('User not authenticated. Please log in again.', true);
      return;
    }
    if (!this.currentPassword || !this.newPassword) {
      this.showToast('Enter both current and new password', true);
      return;
    }
    if (this.newPassword.length < 6) {
      this.showToast('Change password should be at least 6 characters', true);
      return;
    }
    this.authService.changePassword({
      userId: this.userId,
      currentPassword: this.currentPassword,
      newPassword: this.newPassword
    }).subscribe({
      next: () => {
        this.showToast('Password changed successfully');
        this.currentPassword = '';
        this.newPassword = '';
        setTimeout(() => this.router.navigate(['/']), 800);
      },
      error: (err) => {
        this.showToast(err?.error?.message || err?.error || 'Could not change password', true);
      }
    });
  }

  addWallet() {
    if (!this.userId || this.userId <= 0) {
      this.showToast('User not authenticated. Please log in again.', true);
      return;
    }
    if (!this.addAmount || this.addAmount <= 0) {
      this.showToast('Enter a valid amount to add', true);
      return;
    }
    this.authService.addWallet({ userId: this.userId, amount: Number(this.addAmount) }).subscribe({
      next: () => {
        this.wallet += Number(this.addAmount);
        localStorage.setItem('wallet', this.wallet.toString());
        this.showToast(`Successfully added ₹${Number(this.addAmount).toFixed(2)}. Current balance ₹${this.wallet.toFixed(2)}`);
        this.addAmount = null;
        this.cdr.detectChanges();
      },
      error: (err) => {
        this.showToast(err?.error?.message || err?.error || 'Could not update wallet', true);
      }
    });
  }
}

