import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccessibilityService } from '../../services/accessibility.service';
import { TranslatePipe } from '../../pipes/translate.pipe';

@Component({
  selector: 'app-accessibility-menu',
  standalone: true,
  imports: [CommonModule, TranslatePipe],
  templateUrl: './accessibility-menu.component.html',
  styleUrls: ['./accessibility-menu.component.css']
})
export class AccessibilityMenuComponent {
  isOpen = false;

  constructor(public a11y: AccessibilityService) {}

  toggleMenu() {
    this.isOpen = !this.isOpen;
  }

  setLanguage(lang: string) {
    this.a11y.setLanguage(lang);
  }
}
