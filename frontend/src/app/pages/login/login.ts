import { Component } from '@angular/core';
import { AuthService } from '../../services/auth';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  standalone: true,
  imports: [FormsModule, CommonModule,RouterModule],
  styleUrls: ['./login.css']
})
export class LoginComponent {

  user = {
    email: '',
    password: ''
  };

  constructor(private auth: AuthService, private router: Router){}

  login(){
    this.auth.login(this.user).subscribe({
      next: (res:any) => {
        alert("Login Success");
        this.router.navigate(['/dashboard']);
      },
      error: () => alert("Invalid Credentials")
    });
  }
}