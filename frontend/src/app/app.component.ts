import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <mat-toolbar color="primary">
      <span>Sistema de Consulta de Créditos</span>
    </mat-toolbar>
    <app-consulta-creditos></app-consulta-creditos>
  `,
  styles: [`
    :host {
      display: block;
      height: 100vh;
    }
    mat-toolbar {
      margin-bottom: 20px;
    }
  `]
})
export class AppComponent {
  title = 'Sistema de Consulta de Créditos';
} 