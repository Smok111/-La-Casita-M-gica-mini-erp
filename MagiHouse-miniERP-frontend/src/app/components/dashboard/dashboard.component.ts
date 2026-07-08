import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { BaseChartDirective } from 'ng2-charts';
import { ChartConfiguration, ChartData, ChartType } from 'chart.js';
import { AlquileresService, AlquilerDTO } from '../../services/alquileres.service';
import { ClientesService } from '../../services/clientes.service';
import { InventarioService } from '../../services/inventario.service';
import { TranslatePipe } from '../../pipes/translate.pipe';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, BaseChartDirective, TranslatePipe],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit {
  
  kpiStats = [
    { title: 'Alquileres Activos', value: '0', icon: 'clock', color: '#3b82f6' },
    { title: 'Modelos de Disfraz', value: '0', icon: 'package', color: '#10b981' },
    { title: 'Clientes Registrados', value: '0', icon: 'users', color: '#8b5cf6' },
    { title: 'Ingresos Totales', value: 'S/ 0', icon: 'trending-up', color: '#f59e0b' }
  ];

  recentActivities: any[] = [];

  // Bar Chart
  public barChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    maintainAspectRatio: false,
    plugins: { legend: { display: false } }
  };
  public barChartType: ChartType = 'bar';
  public barChartData: ChartData<'bar'> = {
    labels: ['Lun', 'Mar', 'Mie', 'Jue', 'Vie', 'Sab', 'Dom'],
    datasets: [ { data: [0, 0, 0, 0, 0, 0, 0], label: 'Ingresos (S/)', backgroundColor: '#3b82f6', borderRadius: 6 } ]
  };

  // Pie Chart
  public pieChartOptions: ChartConfiguration['options'] = {
    responsive: true,
    maintainAspectRatio: false
  };
  public pieChartType: ChartType = 'doughnut';
  public pieChartData: ChartData<'doughnut'> = {
    labels: ['Sin Datos'],
    datasets: [ { data: [1], backgroundColor: ['#d1d5db'] } ]
  };

  constructor(
    private alquileresService: AlquileresService,
    private clientesService: ClientesService,
    private inventarioService: InventarioService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarDatosReales();
  }

  cargarDatosReales(): void {
    // 1. Cargar Alquileres e Ingresos
    this.alquileresService.getAlquileres().subscribe({
      next: (alquileres) => {
        const activos = alquileres.filter(a => a.estaActivo).length;
        this.kpiStats[0].value = activos.toString();

        const ingresos = alquileres.reduce((sum, a) => sum + a.total, 0);
        this.kpiStats[3].value = `S/ ${ingresos.toFixed(2)}`;

        // Calcular ingresos por los últimos 7 días
        const today = new Date();
        const last7Days = Array.from({length: 7}, (_, i) => {
          const d = new Date(today);
          d.setDate(d.getDate() - (6 - i));
          return d.toISOString().split('T')[0];
        });
        
        let dailyTotals = [0, 0, 0, 0, 0, 0, 0];
        alquileres.forEach(a => {
          const idx = last7Days.indexOf(a.fechaAlquiler);
          if(idx !== -1) {
            dailyTotals[idx] += a.total;
          }
        });
        
        
        this.barChartData = {
          labels: this.barChartData.labels,
          datasets: [
            {
              ...this.barChartData.datasets[0],
              data: dailyTotals
            }
          ]
        };

        // Últimos 5 alquileres para la tabla reciente
        this.recentActivities = alquileres.slice(-5).reverse().map(a => ({
          id: `ALQ-${a.idAlquiler}`,
          customer: `${a.nombresCliente} ${a.apellidosCliente}`,
          item: 'Alquiler', // Simplified for dashboard
          status: a.estaActivo ? 'En Curso' : 'Devuelto',
          date: a.fechaAlquiler
        }));
        this.cdr.detectChanges();
      }
    });

    // 2. Cargar Modelos de Disfraz
    this.inventarioService.getModelosDisfraz().subscribe({
      next: (modelos) => {
        this.kpiStats[1].value = modelos.length.toString();
        
        if (modelos.length > 0) {
          // Dummy data based on stock for the pie chart
          const top3 = modelos.sort((a,b) => (b.stockDisponible || 0) - (a.stockDisponible || 0)).slice(0, 3);
          
          this.pieChartData = {
            labels: top3.map(m => m.nombreModelo),
            datasets: [
              {
                data: top3.map(m => m.stockDisponible || 1),
                backgroundColor: ['#8b5cf6', '#10b981', '#f59e0b']
              }
            ]
          };
        }
        
        this.cdr.detectChanges();
      }
    });

    // 3. Cargar Clientes
    this.clientesService.getClientes().subscribe({
      next: (clientes) => {
        this.kpiStats[2].value = clientes.length.toString();
        this.cdr.detectChanges();
      }
    });
  }
}
