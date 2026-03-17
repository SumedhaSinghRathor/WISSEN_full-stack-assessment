import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [CommonModule,FormsModule,RouterModule],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {}
