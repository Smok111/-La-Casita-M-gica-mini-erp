import { Pipe, PipeTransform } from '@angular/core';
import { AccessibilityService } from '../services/accessibility.service';

@Pipe({
  name: 'translate',
  standalone: true,
  pure: false // Allows the pipe to re-evaluate when language changes
})
export class TranslatePipe implements PipeTransform {
  constructor(private a11y: AccessibilityService) {}

  transform(value: string): string {
    return this.a11y.translate(value);
  }
}
