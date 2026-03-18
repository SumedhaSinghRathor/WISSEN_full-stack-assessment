import { Component } from '@angular/core';
import { AuthService } from '../../services/auth';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthStateService } from '../../services/auth-state';

@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  styleUrls: ['./login.css']
})
export class LoginComponent {

  user = {
    email: '',
    password: ''
  };

  constructor(private auth: AuthService, private router: Router, private authState: AuthStateService) {}

  login(){
    this.auth.login(this.user).subscribe({
      next: (res:any) => {
        if (!res?.token) {
          alert('Invalid credentials');
          return;
        }

        const nameValue = `${res.firstName || ''} ${res.lastName || ''}`.trim() || res.email || 'User';
        this.authState.setUser(res.token, res.role, nameValue, res.email || '');
        localStorage.setItem('userId', res.userId || '1');

        if (res.role === 'ADMIN') {
          this.router.navigate(['/admin/manage-products']);
        } else {
          this.router.navigate(['/dashboard']);
        }
      },
      error: () => alert('Invalid Credentials')
    });
  }
}