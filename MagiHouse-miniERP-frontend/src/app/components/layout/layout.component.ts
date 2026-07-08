import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { TranslatePipe } from '../../pipes/translate.pipe';

@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [CommonModule, RouterModule, TranslatePipe],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {
  
  username: string = 'Administrador'; // This should ideally come from the AuthService decoding the JWT

  constructor(private authService: AuthService, private router: Router) {
    // Attempt to extract the username from localStorage or token if available
    if (typeof window !== 'undefined') {
       const token = localStorage.getItem('token');
       if (token) {
           try {
               const payload = JSON.parse(atob(token.split('.')[1]));
               if (payload.sub) {
                   this.username = payload.sub;
               }
           } catch (e) {
               console.error('Error decoding token', e);
           }
       }
    }
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
