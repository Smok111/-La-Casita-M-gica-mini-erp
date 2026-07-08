import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Categoria {
  idCategoria: number;
  nombreCategoria: string;
  descripcion: string;
  estaActiva: boolean;
}

export interface ModeloDisfraz {
  idModeloDisfraz?: number;
  idCategoria: number;
  nombreModelo: string;
  descripcion: string;
  stockDisponible: number;
  precioAlquiler: number;
  estaActivo: boolean;
  nombreCategoria?: string; // from backend mapping
}

@Injectable({
  providedIn: 'root'
})
export class InventarioService {

  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null;
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  // --- Categorias ---
  getCategorias(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(`${this.apiUrl}/categorias`, { headers: this.getHeaders() });
  }

  crearCategoria(categoria: Categoria): Observable<any> {
    return this.http.post(`${this.apiUrl}/categorias`, categoria, { headers: this.getHeaders() });
  }

  actualizarCategoria(categoria: Categoria): Observable<any> {
    return this.http.put(`${this.apiUrl}/categorias/${categoria.idCategoria}`, categoria, { headers: this.getHeaders() });
  }

  eliminarCategoria(idCategoria: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/categorias/${idCategoria}`, { headers: this.getHeaders() });
  }

  // --- Modelos de Disfraz ---
  getModelosDisfraz(): Observable<ModeloDisfraz[]> {
    return this.http.get<ModeloDisfraz[]>(`${this.apiUrl}/modelos-disfraz`, { headers: this.getHeaders() });
  }

  crearModeloDisfraz(modelo: ModeloDisfraz): Observable<any> {
    return this.http.post(`${this.apiUrl}/modelos-disfraz`, modelo, { headers: this.getHeaders() });
  }

  actualizarModeloDisfraz(modelo: ModeloDisfraz): Observable<any> {
    return this.http.put(`${this.apiUrl}/modelos-disfraz/${modelo.idModeloDisfraz}`, modelo, { headers: this.getHeaders() });
  }

  eliminarModeloDisfraz(idModeloDisfraz: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/modelos-disfraz/${idModeloDisfraz}`, { headers: this.getHeaders() });
  }
}
