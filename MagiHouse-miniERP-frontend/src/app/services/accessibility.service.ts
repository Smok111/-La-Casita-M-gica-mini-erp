import { Injectable, signal } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AccessibilityService {
  // A11y States
  highContrast = signal<boolean>(false);
  dyslexiaFont = signal<boolean>(false);
  largeCursor = signal<boolean>(false);
  textSpacing = signal<boolean>(false);
  saturation = signal<boolean>(false);

  // Language State: 'es' | 'en' | 'qu'
  currentLanguage = signal<string>('es');

  // Basic Dictionary
  private translations: Record<string, Record<string, string>> = {
    'es': {
      'INVENTARIO': 'Inventario',
      'CLIENTES': 'Clientes',
      'ALQUILERES': 'Alquileres',
      'REPORTES': 'Reportes',
      'CONFIGURACION': 'Configuración',
      'CERRAR_SESION': 'Cerrar Sesión',
      'DASHBOARD': 'Panel de Control',
      'ACCESIBILIDAD': 'Accesibilidad',
      'PERFILES_ACCESIBILIDAD': 'Perfiles de Accesibilidad',
      'CEGUERA': 'Alta Visibilidad',
      'DALTONISMO': 'Contraste +',
      'DISLEXIA': 'Fuente Dislexia',
      'TDAH': 'Cursor Grande',
      'ESPACIADO': 'Espaciado Texto',
      'SATURACION': 'Saturación',
      'IDIOMA': 'Idioma',
      // Common Table Headers and Buttons
      'NUEVO': 'Nuevo',
      'BUSCAR': 'Buscar...',
      'VER_DETALLES': 'Ver Detalles',
      'ACCIONES': 'Acciones',
      'ID': 'ID',
      'ESTADO': 'Estado',
      'MODELO': 'Modelo',
      'CANTIDAD': 'Cantidad',
      'PRECIO': 'Precio',
      'CLIENTE': 'Cliente',
      'FECHA': 'Fecha',
      'TOTAL': 'Total',
      'GUARDAR': 'Guardar',
      'CANCELAR': 'Cancelar',
      'CATEGORIA': 'Categoría',
      'RESUMEN': 'Resumen del Sistema',
      'INGRESOS': 'Ingresos de la Semana',
      'TOP_DISFRACES': 'Top 3 Disfraces',
      'ACTIVIDAD': 'Actividad Reciente',
      // Nuevos Términos
      'GESTIONA_DISFRACES': 'Gestiona los modelos de disfraces y categorías.',
      'BUSCAR_PLACEHOLDER': 'Buscar por nombre o categoría...',
      'EXPORTAR': 'Exportar a Excel',
      'NUEVA_CATEGORIA': 'Nueva Categoría',
      'NUEVO_DISFRAZ': 'Nuevo Disfraz',
      'LISTA_MODELOS': 'Lista de modelos',
      'DESCRIPCION': 'Descripción',
      'STOCK': 'Stock',
      'NO_RESULTADOS': 'No se encontraron disfraces.',
      // Clientes & Alquileres
      'DNI': 'DNI',
      'NOMBRES': 'Nombres y Apellidos',
      'CELULAR': 'Celular',
      'DIRECCION': 'Dirección',
      'NUEVO_CLIENTE': 'Nuevo Cliente',
      'METODO_PAGO': 'Método Pago',
      'FECHA_ALQUILER': 'Fecha Alquiler',
      'FECHA_DEVOLUCION': 'Fecha Devolución',
      'NUEVO_ALQUILER': 'Nuevo Alquiler'
    },
    'en': {
      'INVENTARIO': 'Inventory',
      'CLIENTES': 'Customers',
      'ALQUILERES': 'Rentals',
      'REPORTES': 'Reports',
      'CONFIGURACION': 'Settings',
      'CERRAR_SESION': 'Log Out',
      'DASHBOARD': 'Dashboard',
      'ACCESIBILIDAD': 'Accessibility',
      'PERFILES_ACCESIBILIDAD': 'Accessibility Profiles',
      'CEGUERA': 'High Visibility',
      'DALTONISMO': 'Contrast +',
      'DISLEXIA': 'Dyslexia Font',
      'TDAH': 'Large Cursor',
      'ESPACIADO': 'Text Spacing',
      'SATURACION': 'Saturation',
      'IDIOMA': 'Language',
      'NUEVO': 'New',
      'BUSCAR': 'Search...',
      'VER_DETALLES': 'View Details',
      'ACCIONES': 'Actions',
      'ID': 'ID',
      'ESTADO': 'Status',
      'MODELO': 'Model',
      'CANTIDAD': 'Quantity',
      'PRECIO': 'Price',
      'CLIENTE': 'Customer',
      'FECHA': 'Date',
      'TOTAL': 'Total',
      'GUARDAR': 'Save',
      'CANCELAR': 'Cancel',
      'CATEGORIA': 'Category',
      'RESUMEN': 'System Overview',
      'INGRESOS': 'Weekly Revenue',
      'TOP_DISFRACES': 'Top 3 Costumes',
      'ACTIVIDAD': 'Recent Activity',
      'GESTIONA_DISFRACES': 'Manage costume models and categories.',
      'BUSCAR_PLACEHOLDER': 'Search by name or category...',
      'EXPORTAR': 'Export to Excel',
      'NUEVA_CATEGORIA': 'New Category',
      'NUEVO_DISFRAZ': 'New Costume',
      'LISTA_MODELOS': 'Model List',
      'DESCRIPCION': 'Description',
      'STOCK': 'Stock',
      'NO_RESULTADOS': 'No costumes found.',
      'DNI': 'ID / DNI',
      'NOMBRES': 'Full Name',
      'CELULAR': 'Phone',
      'DIRECCION': 'Address',
      'NUEVO_CLIENTE': 'New Customer',
      'METODO_PAGO': 'Payment Method',
      'FECHA_ALQUILER': 'Rental Date',
      'FECHA_DEVOLUCION': 'Return Date',
      'NUEVO_ALQUILER': 'New Rental'
    },
    'qu': {
      'INVENTARIO': 'Khipukamayuq',
      'CLIENTES': 'Rantiqkuna',
      'ALQUILERES': 'Mañakuykuna',
      'REPORTES': 'Willakuykuna',
      'CONFIGURACION': 'Allichaykuna',
      'CERRAR_SESION': 'Lloqsiy',
      'DASHBOARD': 'Kamachiy K\'iti',
      'ACCESIBILIDAD': 'Yaykuy Atiy',
      'PERFILES_ACCESIBILIDAD': 'Yaykuy Ñawpakuna',
      'CEGUERA': 'Sut\'i Rikuy',
      'DALTONISMO': 'Kallpa Llimphi +',
      'DISLEXIA': 'Dislexia Qillqa',
      'TDAH': 'Hatun T\'oqoq',
      'ESPACIADO': 'Qillqa T\'aqay',
      'SATURACION': 'Llimphi Hunt\'ay',
      'IDIOMA': 'Simi',
      'NUEVO': 'Musuq',
      'BUSCAR': 'Maskhay...',
      'VER_DETALLES': 'Qhaway',
      'ACCIONES': 'Ruwaykuna',
      'ID': 'Kipu',
      'ESTADO': 'Kachkay',
      'MODELO': 'Rikch\'aq',
      'CANTIDAD': 'Hayk\'a',
      'PRECIO': 'Chanin',
      'CLIENTE': 'Rantiq',
      'FECHA': 'P\'unchaw',
      'TOTAL': 'Llapan',
      'GUARDAR': 'Waqaychay',
      'CANCELAR': 'Kutichiy',
      'CATEGORIA': 'T\'aqa',
      'RESUMEN': 'Tukuy Qawariy',
      'INGRESOS': 'Qullqi Tarisqa',
      'TOP_DISFRACES': 'Asmanta P\'achakuna',
      'ACTIVIDAD': 'Qhipa Ruwaykuna',
      'GESTIONA_DISFRACES': 'P\'achakuna aswan allin kamachiy.',
      'BUSCAR_PLACEHOLDER': 'Maskhay sutinpi...',
      'EXPORTAR': 'Excel apay',
      'NUEVA_CATEGORIA': 'Musuq T\'aqa',
      'NUEVO_DISFRAZ': 'Musuq P\'acha',
      'LISTA_MODELOS': 'Rikch\'aqkuna qhaway',
      'DESCRIPCION': 'Sut\'inchay',
      'STOCK': 'Waqaychasqa',
      'NO_RESULTADOS': 'Mana tarisqachu.',
      'DNI': 'DNI / Llaqta Runa Kipu',
      'NOMBRES': 'Suti y Ayllu',
      'CELULAR': 'Karuyari',
      'DIRECCION': 'Tiyay',
      'NUEVO_CLIENTE': 'Musuq Rantiq',
      'METODO_PAGO': 'Qullqi Qusay',
      'FECHA_ALQUILER': 'Mañakuy P\'unchaw',
      'FECHA_DEVOLUCION': 'Kutichiy P\'unchaw',
      'NUEVO_ALQUILER': 'Musuq Mañakuy'
    }
  };

  constructor() {
    // Load saved preferences if any
    const savedLang = localStorage.getItem('appLang');
    if (savedLang) {
      this.currentLanguage.set(savedLang);
    }
    
    // Load a11y states
    this.highContrast.set(localStorage.getItem('a11y_contrast') === 'true');
    this.dyslexiaFont.set(localStorage.getItem('a11y_dyslexia') === 'true');
    this.largeCursor.set(localStorage.getItem('a11y_cursor') === 'true');
    this.textSpacing.set(localStorage.getItem('a11y_spacing') === 'true');
    this.saturation.set(localStorage.getItem('a11y_saturation') === 'true');

    this.applyFilters();
  }

  setLanguage(lang: string) {
    this.currentLanguage.set(lang);
    localStorage.setItem('appLang', lang);
  }

  translate(key: string): string {
    const lang = this.currentLanguage();
    return this.translations[lang]?.[key] || key;
  }

  toggleHighContrast() {
    this.highContrast.update(v => !v);
    localStorage.setItem('a11y_contrast', this.highContrast().toString());
    this.applyFilters();
  }

  toggleDyslexia() {
    this.dyslexiaFont.update(v => !v);
    localStorage.setItem('a11y_dyslexia', this.dyslexiaFont().toString());
    this.applyFilters();
  }

  toggleLargeCursor() {
    this.largeCursor.update(v => !v);
    localStorage.setItem('a11y_cursor', this.largeCursor().toString());
    this.applyFilters();
  }

  toggleSpacing() {
    this.textSpacing.update(v => !v);
    localStorage.setItem('a11y_spacing', this.textSpacing().toString());
    this.applyFilters();
  }

  toggleSaturation() {
    this.saturation.update(v => !v);
    localStorage.setItem('a11y_saturation', this.saturation().toString());
    this.applyFilters();
  }

  private applyFilters() {
    const body = document.body;
    
    if (this.highContrast()) body.classList.add('a11y-high-contrast');
    else body.classList.remove('a11y-high-contrast');

    if (this.dyslexiaFont()) body.classList.add('a11y-dyslexia');
    else body.classList.remove('a11y-dyslexia');

    if (this.largeCursor()) body.classList.add('a11y-large-cursor');
    else body.classList.remove('a11y-large-cursor');

    if (this.textSpacing()) body.classList.add('a11y-spacing');
    else body.classList.remove('a11y-spacing');

    if (this.saturation()) body.classList.add('a11y-saturation');
    else body.classList.remove('a11y-saturation');
  }
}
