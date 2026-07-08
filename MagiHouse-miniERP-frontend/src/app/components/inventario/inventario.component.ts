import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { InventarioService, Categoria, ModeloDisfraz } from '../../services/inventario.service';
import { TranslatePipe } from '../../pipes/translate.pipe';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-inventario',
  standalone: true,
  imports: [CommonModule, FormsModule, TranslatePipe],
  templateUrl: './inventario.component.html',
  styleUrl: './inventario.component.css'
})
export class InventarioComponent implements OnInit {

  modelos: ModeloDisfraz[] = [];
  categorias: Categoria[] = [];
  searchTerm: string = '';

  get modelosFiltrados(): ModeloDisfraz[] {
    if (!this.searchTerm) return this.modelos;
    const term = this.searchTerm.toLowerCase();
    return this.modelos.filter(m => 
      m.nombreModelo.toLowerCase().includes(term) ||
      (m.nombreCategoria && m.nombreCategoria.toLowerCase().includes(term))
    );
  }

  // UI State
  showModal = false;
  showModalCategoria = false;
  isSubmitting = false;
  isSubmittingCategoria = false;
  errorMessage = '';
  errorMessageCategoria = '';

  // Form State
  nuevoModelo: ModeloDisfraz = {
    idCategoria: 0,
    nombreModelo: '',
    descripcion: '',
    estaActivo: true,
    stockDisponible: 1,
    precioAlquiler: 10
  };

  nuevaCategoria: Categoria = {
    idCategoria: 0,
    nombreCategoria: '',
    descripcion: '',
    estaActiva: true
  };

  constructor(private inventarioService: InventarioService, private cdr: ChangeDetectorRef) {}

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos(): void {
    this.inventarioService.getCategorias().subscribe({
      next: (res) => this.categorias = res,
      error: (err) => console.error('Error cargando categorias', err)
    });

    this.inventarioService.getModelosDisfraz().subscribe({
      next: (res) => {
        this.modelos = res;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error cargando modelos', err)
    });
  }

  abrirModal(): void {
    this.errorMessage = '';
    this.nuevoModelo = { idCategoria: 0, nombreModelo: '', descripcion: '', estaActivo: true, stockDisponible: 1, precioAlquiler: 10 };
    this.showModal = true;
  }

  cerrarModal(): void {
    this.showModal = false;
  }

  editarModelo(modelo: ModeloDisfraz): void {
    this.errorMessage = '';
    this.nuevoModelo = { ...modelo };
    this.showModal = true;
  }

  guardarModelo(): void {
    if (!this.nuevoModelo.nombreModelo || !this.nuevoModelo.idCategoria) {
      this.errorMessage = 'El nombre y la categoría son obligatorios.';
      return;
    }

    this.isSubmitting = true;
    
    if (this.nuevoModelo.idModeloDisfraz) {
      // Editar
      const id = this.nuevoModelo.idModeloDisfraz;
      this.inventarioService.actualizarModeloDisfraz(this.nuevoModelo).subscribe({
        next: () => {
          this.isSubmitting = false;
          this.cerrarModal();
          this.cargarDatos();
        },
        error: (err) => {
          this.isSubmitting = false;
          this.errorMessage = 'Error al actualizar el modelo.';
        }
      });
    } else {
      // True Optimistic UI for create
      const tempModelo = { ...this.nuevoModelo, idModeloDisfraz: Date.now() };
      this.modelos.unshift(tempModelo);
      this.cerrarModal();

      this.inventarioService.crearModeloDisfraz(this.nuevoModelo).subscribe({
        next: () => {
          this.isSubmitting = false;
          this.cargarDatos(); // Refresh for real ID in background
        },
        error: (err) => {
          this.isSubmitting = false;
          this.modelos = this.modelos.filter(m => m.idModeloDisfraz !== tempModelo.idModeloDisfraz);
          this.errorMessage = 'Error al guardar el modelo de disfraz.';
          console.error(err);
        }
      });
    }
  }

  eliminarModelo(id: number): void {
    if (confirm('¿Estás seguro de eliminar este modelo de disfraz? Esta acción no se puede deshacer.')) {
      this.inventarioService.eliminarModeloDisfraz(id).subscribe({
        next: () => {
          this.cargarDatos();
        },
        error: (err) => {
          console.error('Error al eliminar modelo', err);
          alert(err.error?.message || 'Error al eliminar modelo. Puede que esté siendo usado en un alquiler.');
        }
      });
    }
  }

  // --- Modal Categorias ---
  abrirModalCategoria(): void {
    this.errorMessageCategoria = '';
    this.nuevaCategoria = { idCategoria: 0, nombreCategoria: '', descripcion: '', estaActiva: true };
    this.showModalCategoria = true;
  }

  cerrarModalCategoria(): void {
    this.showModalCategoria = false;
  }

  guardarCategoria(): void {
    if (!this.nuevaCategoria.nombreCategoria) {
      this.errorMessageCategoria = 'El nombre de la categoría es obligatorio.';
      return;
    }

    this.isSubmittingCategoria = true;
    
    // True Optimistic UI
    const tempCat = { ...this.nuevaCategoria, idCategoria: Date.now() };
    this.categorias.unshift(tempCat);
    this.cerrarModalCategoria();

    this.inventarioService.crearCategoria(this.nuevaCategoria).subscribe({
      next: () => {
        this.isSubmittingCategoria = false;
        this.cargarDatos(); // Refresh for real ID in background
      },
      error: (err) => {
        this.isSubmittingCategoria = false;
        this.categorias = this.categorias.filter(c => c.idCategoria !== tempCat.idCategoria);
        this.errorMessageCategoria = 'Error al guardar la categoría.';
        console.error(err);
      }
    });
  }

  exportarExcel(): void {
    const data = this.modelos.map(m => ({
      'ID': m.idModeloDisfraz,
      'Nombre': m.nombreModelo,
      'Descripción': m.descripcion,
      'Precio Alquiler': m.precioAlquiler,
      'Stock': m.stockDisponible,
      'Precio (S/)': m.precioAlquiler,
      'Estado': m.estaActivo ? 'Activo' : 'Inactivo'
    }));

    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(data);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Inventario');

    XLSX.writeFile(wb, 'Inventario_MagiHouse.xlsx');
  }
}
