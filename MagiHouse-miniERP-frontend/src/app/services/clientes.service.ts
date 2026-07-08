import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface EstadoCliente {
  idEstadoCliente: number;
  nombreEstadoCliente: string;
  descripcion: string;
  estaActivo: boolean;
}

export interface Cliente {
  idCliente?: number;
  nombresCliente: string;
  apellidosCliente: string;
  dni: string;
  numCelular: string;
  dniDevuelto: boolean;
  direccion: string;
  idEstadoCliente: number;
  nombreEstadoCliente?: string;
}

@Injectable({
  providedIn: 'root'
})
export class ClientesService {

  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null;
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  // --- Estados de Cliente ---
  getEstadosCliente(): Observable<EstadoCliente[]> {
    return this.http.get<EstadoCliente[]>(`${this.apiUrl}/estados-cliente`, { headers: this.getHeaders() });
  }

  // --- Clientes ---
  getClientes(): Observable<Cliente[]> {
    return this.http.get<Cliente[]>(`${this.apiUrl}/clientes`, { headers: this.getHeaders() });
  }

  crearCliente(cliente: Cliente): Observable<any> {
    return this.http.post(`${this.apiUrl}/clientes`, cliente, { headers: this.getHeaders() });
  }

  actualizarCliente(cliente: Cliente): Observable<any> {
    return this.http.put(`${this.apiUrl}/clientes/${cliente.idCliente}`, cliente, { headers: this.getHeaders() });
  }

  eliminarCliente(idCliente: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/clientes/${idCliente}`, { headers: this.getHeaders() });
  }
}
