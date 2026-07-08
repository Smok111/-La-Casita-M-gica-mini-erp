import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AlquileresService, AlquilerDTO, MetodoPago, AlquilerRequestDTO } from '../../services/alquileres.service';
import { ClientesService, Cliente } from '../../services/clientes.service';
import { InventarioService, ModeloDisfraz } from '../../services/inventario.service';
import { TranslatePipe } from '../../pipes/translate.pipe';
import { jsPDF } from 'jspdf';
import 'jspdf-autotable';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-alquileres',
  standalone: true,
  imports: [CommonModule, FormsModule, TranslatePipe],
  templateUrl: './alquileres.component.html',
  styleUrl: './alquileres.component.css'
})
export class AlquileresComponent implements OnInit {
  alquileres: AlquilerDTO[] = [];
  clientes: Cliente[] = [];
  metodosPago: MetodoPago[] = [];
  searchTerm: string = '';

  get alquileresFiltrados(): AlquilerDTO[] {
    if (!this.searchTerm) return this.alquileres;
    const term = this.searchTerm.toLowerCase();
    return this.alquileres.filter(a => 
      (a.nombresCliente + ' ' + a.apellidosCliente).toLowerCase().includes(term) ||
      (a.idAlquiler?.toString() === term)
    );
  }
  modelos: ModeloDisfraz[] = [];

  showModal = false;
  showModalDetalles = false;
  isSubmitting = false;
  errorMessage = '';
  alquilerSeleccionado: AlquilerDTO | null = null;

  nuevoAlquiler: AlquilerRequestDTO = {
    idCliente: 0,
    idMetodoPago: 0,
    fechaPactada: '',
    total: 0,
    detalles: []
  };

  selectedModeloId: number = 0;

  constructor(
    private alquileresService: AlquileresService,
    private cdr: ChangeDetectorRef,
    private clientesService: ClientesService,
    private inventarioService: InventarioService
  ) {}

  ngOnInit(): void {
    this.cargarDatos();
  }

  cargarDatos(): void {
    this.alquileresService.getAlquileres().subscribe({
      next: (res) => {
        this.alquileres = res;
        this.cdr.detectChanges();
      },
      error: (err) => console.error('Error cargando alquileres', err)
    });

    this.clientesService.getClientes().subscribe({
      next: (res) => this.clientes = res,
      error: (err) => console.error(err)
    });

    this.alquileresService.getMetodosPago().subscribe({
      next: (res) => {
        this.metodosPago = res;
        if (res.length > 0) this.nuevoAlquiler.idMetodoPago = res[0].idMetodoPago;
      },
      error: (err) => console.error(err)
    });

    this.inventarioService.getModelosDisfraz().subscribe({
      next: (res) => this.modelos = res,
      error: (err) => console.error(err)
    });
  }

  abrirModal(): void {
    this.errorMessage = '';
    const date = new Date();
    date.setDate(date.getDate() + 3); // Fecha pactada 3 días después por defecto
    const fechaStr = date.toISOString().split('T')[0];

    this.nuevoAlquiler = {
      idCliente: 0,
      idMetodoPago: this.metodosPago.length > 0 ? this.metodosPago[0].idMetodoPago : 0,
      fechaPactada: fechaStr,
      total: 0,
      detalles: []
    };
    this.selectedModeloId = 0;
    this.showModal = true;
  }

  cerrarModal(): void {
    this.showModal = false;
  }

  verDetalles(alq: AlquilerDTO): void {
    this.alquilerSeleccionado = alq;
    this.showModalDetalles = true;
  }

  cerrarModalDetalles(): void {
    this.showModalDetalles = false;
    this.alquilerSeleccionado = null;
  }

  agregarDetalle(): void {
    if (this.selectedModeloId === 0) return;
    const modelo = this.modelos.find(m => m.idModeloDisfraz == this.selectedModeloId);
    if (!modelo) return;

    this.nuevoAlquiler.detalles.push({
      idModeloDisfraz: modelo.idModeloDisfraz!,
      precioUnitarioAlquiler: modelo.precioAlquiler || 0,
      descuentoPorMayor: 0
    });
    this.calcularTotal();
  }

  quitarDetalle(index: number): void {
    this.nuevoAlquiler.detalles.splice(index, 1);
    this.calcularTotal();
  }

  calcularTotal(): void {
    let total = 0;
    for (let det of this.nuevoAlquiler.detalles) {
      total += (det.precioUnitarioAlquiler - det.descuentoPorMayor);
    }
    this.nuevoAlquiler.total = total;
  }

  getNombreModelo(id: number): string {
    const mod = this.modelos.find(m => m.idModeloDisfraz === id);
    return mod ? mod.nombreModelo : 'Desconocido';
  }

  guardarAlquiler(): void {
    if (this.nuevoAlquiler.idCliente === 0) {
      this.errorMessage = 'Debe seleccionar un cliente.';
      return;
    }
    if (this.nuevoAlquiler.detalles.length === 0) {
      this.errorMessage = 'Debe agregar al menos un disfraz al alquiler.';
      return;
    }

    this.isSubmitting = true;
    
    // True Optimistic UI
    const tempAlq = { ...this.nuevoAlquiler, idAlquiler: Date.now(), estaActivo: true, total: this.nuevoAlquiler.total };
    // Not pushing fully constructed DTO to the table since table expects GET DTO format,
    // but the table will reload in background. Let's just close modal.
    this.cerrarModal();
    // alert('Alquiler registrado, procesando en segundo plano...');

    this.alquileresService.crearAlquiler(this.nuevoAlquiler).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.cargarDatos(); // Refresh table in background
      },
      error: (err) => {
        this.isSubmitting = false;
        // In real app we'd show a toast here
        console.error('Error al guardar alquiler', err);
      }
    });
  }

  devolverAlquiler(id: number): void {
    if (confirm('¿Confirmar devolución normal de este alquiler? El stock del disfraz será restaurado.')) {
      this.alquileresService.devolverAlquiler(id).subscribe({
        next: () => {
          this.cargarDatos();
        },
        error: (err) => console.error('Error devolviendo alquiler', err)
      });
    }
  }

  devolverAlquilerDanado(id: number): void {
    if (confirm('¿Confirmar que el disfraz fue devuelto dañado o se perdió? El stock NO será restaurado.')) {
      this.alquileresService.devolverAlquilerDanado(id).subscribe({
        next: () => {
          this.cargarDatos();
        },
        error: (err) => console.error('Error reportando alquiler dañado', err)
      });
    }
  }

  eliminarAlquiler(id: number): void {
    if (confirm('¿Estás seguro de eliminar este alquiler? Se eliminarán también sus detalles. Esta acción no se puede deshacer.')) {
      this.alquileresService.eliminarAlquiler(id).subscribe({
        next: () => {
          this.cargarDatos();
        },
        error: (err) => {
          console.error('Error al eliminar alquiler', err);
          alert(err.error?.message || 'Error al eliminar alquiler.');
        }
      });
    }
  }

  generarTicketPDF(alq: AlquilerDTO): void {
    const doc = new jsPDF();
    
    // Header
    doc.setFontSize(20);
    doc.text('MagiHouse - Ticket de Alquiler', 105, 20, { align: 'center' });
    
    doc.setFontSize(12);
    doc.text(`ID Alquiler: ${alq.idAlquiler}`, 14, 30);
    doc.text(`Fecha Alquiler: ${alq.fechaAlquiler}`, 14, 38);
    doc.text(`Fecha Pactada: ${alq.fechaPactada}`, 14, 46);
    doc.text(`Estado: ${alq.estaActivo ? 'Pendiente' : 'Devuelto'}`, 14, 54);
    
    // Total
    doc.setFontSize(14);
    doc.text(`TOTAL: S/ ${alq.total.toFixed(2)}`, 14, 70);

    // Footer
    doc.setFontSize(10);
    doc.text('¡Gracias por confiar en MagiHouse!', 105, 90, { align: 'center' });
    
    doc.save(`Ticket_Alquiler_${alq.idAlquiler}.pdf`);
  }

  exportarExcel(): void {
    const data = this.alquileres.map(a => ({
      'ID Alquiler': a.idAlquiler,
      'Cliente': this.clientes.find(c => c.idCliente === a.idCliente)?.nombresCliente || 'Desconocido',
      'Fecha Alquiler': a.fechaAlquiler,
      'Fecha Pactada': a.fechaPactada,
      'Total (S/)': a.total,
      'Estado': a.estaActivo ? 'Pendiente' : 'Devuelto'
    }));

    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(data);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Alquileres');

    XLSX.writeFile(wb, 'Alquileres_MagiHouse.xlsx');
  }
}
