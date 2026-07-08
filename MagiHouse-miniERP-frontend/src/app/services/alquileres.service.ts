import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface MetodoPago {
  idMetodoPago: number;
  nombreMetodoPago: string;
}

export interface AlquilerDTO {
  idAlquiler?: number;
  idCliente: number;
  nombresCliente: string;
  apellidosCliente: string;
  nombreMetodoPago: string;
  fechaAlquiler: string;
  fechaPactada: string;
  fechaEntrega?: string;
  total: number;
  estaActivo: boolean;
}

export interface DetalleAlquilerRequest {
  idModeloDisfraz: number;
  precioUnitarioAlquiler: number;
  descuentoPorMayor: number;
}

export interface AlquilerRequestDTO {
  idCliente: number;
  idMetodoPago: number;
  fechaPactada: string;
  total: number;
  detalles: DetalleAlquilerRequest[];
}

@Injectable({
  providedIn: 'root'
})
export class AlquileresService {
  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null;
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  getMetodosPago(): Observable<MetodoPago[]> {
    return this.http.get<MetodoPago[]>(`${this.apiUrl}/metodos-pago`, { headers: this.getHeaders() });
  }

  getAlquileres(): Observable<AlquilerDTO[]> {
    return this.http.get<AlquilerDTO[]>(`${this.apiUrl}/alquileres`, { headers: this.getHeaders() });
  }

  crearAlquiler(alquiler: AlquilerRequestDTO): Observable<any> {
    return this.http.post(`${this.apiUrl}/alquileres`, alquiler, { headers: this.getHeaders() });
  }

  devolverAlquiler(idAlquiler: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/alquileres/${idAlquiler}/devolver`, {}, { headers: this.getHeaders() });
  }

  devolverAlquilerDanado(idAlquiler: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/alquileres/${idAlquiler}/devolver-danado`, {}, { headers: this.getHeaders() });
  }

  eliminarAlquiler(idAlquiler: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/alquileres/${idAlquiler}`, { headers: this.getHeaders() });
  }
}
