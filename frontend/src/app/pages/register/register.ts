import { Component } from '@angular/core';
import { AuthService } from '../../services/auth';
import { Router, RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class RegisterComponent {
  user = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    age: null,
    role: 'USER',
  };

  constructor(
    private auth: AuthService,
    private router: Router,
  ) {}

  register() {
    console.log(this.user);
    this.auth.register(this.user).subscribe({
      next: () => {
        alert('Registered Successfully');
        this.router.navigate(['/']);
      },
      error: () => alert('Error in registration'),
    });
  }
}
