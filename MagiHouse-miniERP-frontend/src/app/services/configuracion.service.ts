import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Negocio {
  idNegocio: number;
  nombreNegocio: string;
  rucNegocio: string;
  direccion: string;
  numCelular: string;
  // correo: string; backend model does not have email
  estaActivo: boolean;
}

export interface Usuario {
  idUsuario: number;
  nombreUsuario: string;
  // email: string; backend model does not have email
  estaActivo: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class ConfiguracionService {

  private apiUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null;
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  // --- Negocio ---
  getNegocios(): Observable<Negocio[]> {
    return this.http.get<Negocio[]>(`${this.apiUrl}/negocios`, { headers: this.getHeaders() });
  }

  actualizarNegocio(id: number, negocio: Negocio): Observable<any> {
    return this.http.put(`${this.apiUrl}/negocios/${id}`, negocio, { headers: this.getHeaders() });
  }

  // --- Usuario ---
  getUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.apiUrl}/usuarios`, { headers: this.getHeaders() });
  }

  actualizarUsuario(id: number, usuario: Usuario): Observable<any> {
    return this.http.put(`${this.apiUrl}/usuarios/${id}`, usuario, { headers: this.getHeaders() });
  }
}
