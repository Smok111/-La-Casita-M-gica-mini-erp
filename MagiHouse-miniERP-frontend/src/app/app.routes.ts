import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegistroComponent } from './components/registro/registro.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { InventarioComponent } from './components/inventario/inventario.component';
import { ClientesComponent } from './components/clientes/clientes.component';
import { AlquileresComponent } from './components/alquileres/alquileres.component';
import { ConfiguracionComponent } from './components/configuracion/configuracion.component';

import { authGuard } from './guards/auth.guard';
import { LayoutComponent } from './components/layout/layout.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'registro', component: RegistroComponent },
  { 
    path: '', 
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'inventario', component: InventarioComponent },
      { path: 'clientes', component: ClientesComponent },
      { path: 'alquileres', component: AlquileresComponent },
      { path: 'configuracion', component: ConfiguracionComponent },
      // Future routes will go here
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },
  { path: '**', redirectTo: '/login' }
];
