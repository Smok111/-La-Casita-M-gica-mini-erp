import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ConfiguracionService, Negocio, Usuario } from '../../services/configuracion.service';

@Component({
  selector: 'app-configuracion',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './configuracion.component.html',
  styleUrl: './configuracion.component.css'
})
export class ConfiguracionComponent implements OnInit {

  activeTab = 'negocio';

  // State
  negocio: Negocio | null = null;
  usuario: Usuario | null = null;

  isSubmittingNegocio = false;
  isSubmittingUsuario = false;

  successMessage = '';
  errorMessage = '';

  constructor(private confService: ConfiguracionService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos(): void {
    this.confService.getNegocios().subscribe({
      next: (res) => {
        if (res.length > 0) {
          this.negocio = res[0]; 
        } else {
          // Initialize empty if no record exists
          this.negocio = { idNegocio: 1, nombreNegocio: '', rucNegocio: '', direccion: '', numCelular: '', estaActivo: true };
        }
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error cargando negocio', err);
        this.negocio = { idNegocio: 1, nombreNegocio: '', rucNegocio: '', direccion: '', numCelular: '', estaActivo: true };
      }
    });

    this.confService.getUsuarios().subscribe({
      next: (res) => {
        if (res.length > 0) {
          this.usuario = res.find(u => u.idUsuario === 1) || res[0];
        } else {
          this.usuario = { idUsuario: 1, nombreUsuario: '', estaActivo: true };
        }
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Error cargando usuarios', err);
        this.usuario = { idUsuario: 1, nombreUsuario: '', estaActivo: true };
      }
    });
  }

  setTab(tab: string): void {
    this.activeTab = tab;
    this.successMessage = '';
    this.errorMessage = '';
  }

  guardarNegocio(): void {
    if (!this.negocio || !this.negocio.idNegocio) return;
    this.isSubmittingNegocio = true;
    this.successMessage = '';
    this.errorMessage = '';

    this.confService.actualizarNegocio(this.negocio.idNegocio, this.negocio).subscribe({
      next: () => {
        this.isSubmittingNegocio = false;
        this.successMessage = 'Datos del negocio actualizados correctamente.';
      },
      error: (err) => {
        this.isSubmittingNegocio = false;
        this.errorMessage = 'Error al actualizar negocio.';
        console.error(err);
      }
    });
  }

  guardarUsuario(): void {
    if (!this.usuario || !this.usuario.idUsuario) return;
    this.isSubmittingUsuario = true;
    this.successMessage = '';
    this.errorMessage = '';

    this.confService.actualizarUsuario(this.usuario.idUsuario, this.usuario).subscribe({
      next: () => {
        this.isSubmittingUsuario = false;
        this.successMessage = 'Perfil de usuario actualizado correctamente.';
        // Optionally update the top nav username if stored in service
      },
      error: (err) => {
        this.isSubmittingUsuario = false;
        this.errorMessage = 'Error al actualizar usuario.';
        console.error(err);
      }
    });
  }
}
