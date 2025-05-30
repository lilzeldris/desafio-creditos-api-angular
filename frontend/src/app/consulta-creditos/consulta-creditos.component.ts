import { Component } from '@angular/core';
import { CreditoService } from '../services/credito.service';

interface Credito {
  numeroCredito: string;
  numeroNfse: string;
  dataConstituicao: string;
  valorIssqn: number;
  tipoCredito: string;
  simplesNacional: boolean;
  aliquota: number;
  valorFaturado: number;
  valorDeducao: number;
  baseCalculo: number;
}

@Component({
  selector: 'app-consulta-creditos',
  template: `
    <div class="container">
      <mat-card>
        <mat-card-content>
          <div class="search-form">
            <mat-form-field>
              <mat-label>Número da NFS-e ou Crédito</mat-label>
              <input matInput [(ngModel)]="numeroConsulta" placeholder="Digite o número">
            </mat-form-field>
            <button mat-raised-button color="primary" (click)="consultar()">Consultar</button>
          </div>

          <table mat-table [dataSource]="creditos" *ngIf="creditos.length > 0">
            <ng-container matColumnDef="numeroCredito">
              <th mat-header-cell *matHeaderCellDef>Número do Crédito</th>
              <td mat-cell *matCellDef="let credito">{{credito.numeroCredito}}</td>
            </ng-container>

            <ng-container matColumnDef="numeroNfse">
              <th mat-header-cell *matHeaderCellDef>Número da NFS-e</th>
              <td mat-cell *matCellDef="let credito">{{credito.numeroNfse}}</td>
            </ng-container>

            <ng-container matColumnDef="dataConstituicao">
              <th mat-header-cell *matHeaderCellDef>Data de Constituição</th>
              <td mat-cell *matCellDef="let credito">{{credito.dataConstituicao | date:'dd/MM/yyyy'}}</td>
            </ng-container>

            <ng-container matColumnDef="valorIssqn">
              <th mat-header-cell *matHeaderCellDef>Valor ISSQN</th>
              <td mat-cell *matCellDef="let credito">{{credito.valorIssqn | currency:'BRL'}}</td>
            </ng-container>

            <ng-container matColumnDef="tipoCredito">
              <th mat-header-cell *matHeaderCellDef>Tipo do Crédito</th>
              <td mat-cell *matCellDef="let credito">{{credito.tipoCredito}}</td>
            </ng-container>

            <tr mat-header-row *matHeaderRowDef="displayedColumns"></tr>
            <tr mat-row *matRowDef="let row; columns: displayedColumns;"></tr>
          </table>
        </mat-card-content>
      </mat-card>
    </div>
  `,
  styles: [`
    .container {
      padding: 20px;
      max-width: 1200px;
      margin: 0 auto;
    }
    .search-form {
      display: flex;
      gap: 20px;
      align-items: center;
      margin-bottom: 20px;
    }
    mat-form-field {
      flex: 1;
    }
    table {
      width: 100%;
    }
  `]
})
export class ConsultaCreditosComponent {
  numeroConsulta = '';
  creditos: Credito[] = [];
  displayedColumns = ['numeroCredito', 'numeroNfse', 'dataConstituicao', 'valorIssqn', 'tipoCredito'];

  constructor(private creditoService: CreditoService) {}

  consultar() {
    if (!this.numeroConsulta) return;

    if (this.numeroConsulta.length === 6) {
      this.creditoService.consultarPorNumeroCredito(this.numeroConsulta)
        .subscribe(credito => {
          this.creditos = [credito];
        });
    } else {
      this.creditoService.consultarPorNumeroNfse(this.numeroConsulta)
        .subscribe(creditos => {
          this.creditos = creditos;
        });
    }
  }
} 