import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { AuthStateService } from '../../services/auth-state';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.html',
  styleUrls: ['./header.css'],
})
export class Header implements OnInit {
  name = '';
  role = '';
  email = '';

  constructor(private router: Router, private authState: AuthStateService) {}

  ngOnInit() {
    this.authState.initialize();
    this.authState.user$.subscribe((user) => {
      this.name = user.name || (user.email ? user.email : '');
      this.role = user.role || '';
      this.email = user.email || '';
    });
  }

  logout() {
    this.authState.clearUser();
    this.router.navigate(['/']);
  }
}


