import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null;

  if (token) {
    return true; // Acceso permitido
  } else {
    // Si no hay token, redirigir al login
    return router.parseUrl('/login');
  }
};
