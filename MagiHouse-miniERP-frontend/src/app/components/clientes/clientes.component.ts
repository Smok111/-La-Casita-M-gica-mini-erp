import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TranslatePipe } from '../../pipes/translate.pipe';
import { ClientesService, Cliente, EstadoCliente } from '../../services/clientes.service';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [CommonModule, FormsModule, TranslatePipe],
  templateUrl: './clientes.component.html',
  styleUrl: './clientes.component.css'
})
export class ClientesComponent implements OnInit {

  clientes: Cliente[] = [];
  estados: EstadoCliente[] = [];
  estadosCliente: EstadoCliente[] = [];
  searchTerm: string = '';

  get clientesFiltrados(): Cliente[] {
    if (!this.searchTerm) return this.clientes;
    const term = this.searchTerm.toLowerCase();
    return this.clientes.filter(c => 
      c.nombresCliente.toLowerCase().includes(term) ||
      c.apellidosCliente.toLowerCase().includes(term) ||
      c.dni.includes(term)
    );
  }

  // UI State
  showModal = false;
  isSubmitting = false;
  errorMessage = '';

  // Form State
  nuevoCliente: Cliente = {
    nombresCliente: '',
    apellidosCliente: '',
    dni: '',
    numCelular: '',
    direccion: '',
    dniDevuelto: false,
    idEstadoCliente: 0
  };

  constructor(private clientesService: ClientesService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos(): void {
    this.clientesService.getEstadosCliente().subscribe({
      next: (res) => {
        this.estados = res;
        // Set default estado si existe 'Confiable' o el primero
        if (this.estados.length > 0 && this.nuevoCliente.idEstadoCliente === 0) {
           this.nuevoCliente.idEstadoCliente = this.estados[0].idEstadoCliente;
        }
      },
      error: (err) => console.error('Error cargando estados de cliente', err)
    });

    this.clientesService.getClientes().subscribe({
      next: (res) => {
        this.clientes = res;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error cargando clientes', err)
    });
  }

  abrirModal(): void {
    this.errorMessage = '';
    const defaultEstado = this.estados.length > 0 ? this.estados[0].idEstadoCliente : 0;
    this.nuevoCliente = {
      nombresCliente: '',
      apellidosCliente: '',
      dni: '',
      numCelular: '',
      direccion: '',
      dniDevuelto: false,
      idEstadoCliente: defaultEstado
    };
    this.showModal = true;
  }

  cerrarModal(): void {
    this.showModal = false;
  }

  editarCliente(cliente: Cliente): void {
    this.errorMessage = '';
    this.nuevoCliente = { ...cliente };
    this.showModal = true;
  }

  guardarCliente(): void {
    if (!this.nuevoCliente.nombresCliente || !this.nuevoCliente.apellidosCliente || !this.nuevoCliente.dni || !this.nuevoCliente.idEstadoCliente) {
      this.errorMessage = 'Los campos Nombres, Apellidos, DNI y Estado son obligatorios.';
      return;
    }
    
    if (this.nuevoCliente.dni.length !== 8) {
      this.errorMessage = 'El DNI debe tener exactamente 8 dígitos.';
      return;
    }

    this.isSubmitting = true;

    if (this.nuevoCliente.idCliente) {
      // Editar
      this.clientesService.actualizarCliente(this.nuevoCliente).subscribe({
        next: () => {
          this.isSubmitting = false;
          this.cerrarModal();
          this.cargarDatos();
        },
        error: (err) => {
          this.isSubmitting = false;
          this.errorMessage = 'Error al actualizar el cliente.';
        }
      });
    } else {
      // True Optimistic UI
      const tempCliente = { ...this.nuevoCliente, idCliente: Date.now() };
      this.clientes.unshift(tempCliente);
      this.cerrarModal();

      this.clientesService.crearCliente(this.nuevoCliente).subscribe({
        next: () => {
          this.isSubmitting = false;
          this.cargarDatos(); // Reload for real ID and formatting
        },
        error: (err) => {
          this.isSubmitting = false;
          this.clientes = this.clientes.filter(c => c.idCliente !== tempCliente.idCliente);
          this.errorMessage = 'Error al guardar el cliente.';
          console.error(err);
        }
      });
    }
  }

  eliminarCliente(id: number): void {
    if (confirm('¿Estás seguro de eliminar este cliente? Esta acción no se puede deshacer.')) {
      this.clientesService.eliminarCliente(id).subscribe({
        next: () => {
          this.cargarDatos();
        },
        error: (err) => {
          console.error('Error al eliminar cliente', err);
          alert(err.error?.message || 'Error al eliminar cliente. Puede que tenga alquileres asociados.');
        }
      });
    }
  }
}
