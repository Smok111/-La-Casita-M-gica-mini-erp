import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.css'
})
export class RegistroComponent {
  registroForm: FormGroup;
  errorMessage: string = '';

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.registroForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  onSubmit() {
    if (this.registroForm.valid) {
      const { username, password } = this.registroForm.value;
      this.authService.register(username, password).subscribe({
        next: () => {
          // Si el registro es exitoso, enviamos al login
          this.router.navigate(['/login']);
        },
        error: (err) => {
          if (err.status === 409) {
            this.errorMessage = 'El nombre de usuario ya está en uso. Por favor, elige otro.';
          } else {
            this.errorMessage = 'Error al registrar usuario. Inténtalo de nuevo.';
          }
        }
      });
    }
  }

  clearFields() {
    this.registroForm.reset();
    this.errorMessage = '';
  }
}
