import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AccessibilityMenuComponent } from './components/accessibility-menu/accessibility-menu.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, AccessibilityMenuComponent],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('MagiHouse-miniERP-frontend');
}
