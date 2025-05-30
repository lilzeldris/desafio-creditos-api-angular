import { Component } from '@angular/core';
import { CreditoService } from '../services/credito.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

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
    <div class="container mt-4">
      <div class="row">
        <div class="col-md-8 offset-md-2">
          <div class="card">
            <div class="card-header">
              <h3 class="text-center">Consulta de Créditos</h3>
            </div>
            <div class="card-body">
              <form [formGroup]="consultaForm" (ngSubmit)="consultar()">
                <div class="mb-3">
                  <label class="form-label">Tipo de Consulta</label>
                  <select class="form-select" formControlName="tipoConsulta">
                    <option value="nfse">NFS-e</option>
                    <option value="credito">Número do Crédito</option>
                  </select>
                </div>
                <div class="mb-3">
                  <label class="form-label">Número</label>
                  <input type="text" class="form-control" formControlName="numero" 
                         [placeholder]="consultaForm.get('tipoConsulta')?.value === 'nfse' ? 'Número da NFS-e' : 'Número do Crédito'">
                  <div class="text-danger" *ngIf="consultaForm.get('numero')?.errors?.['required'] && consultaForm.get('numero')?.touched">
                    Campo obrigatório
                  </div>
                </div>
                <button type="submit" class="btn btn-primary w-100" [disabled]="consultaForm.invalid || loading">
                  <span *ngIf="loading" class="spinner-border spinner-border-sm me-2"></span>
                  Consultar
                </button>
              </form>
            </div>
          </div>

          <div class="alert alert-danger mt-3" *ngIf="erro">
            {{ erro }}
          </div>

          <div class="card mt-4" *ngIf="creditos.length > 0">
            <div class="card-header">
              <h4>Resultados</h4>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-striped">
                  <thead>
                    <tr>
                      <th>Nº Crédito</th>
                      <th>Nº NFS-e</th>
                      <th>Data</th>
                      <th>Valor ISSQN</th>
                      <th>Tipo</th>
                      <th>Simples</th>
                      <th>Alíquota</th>
                      <th>Valor Faturado</th>
                      <th>Valor Dedução</th>
                      <th>Base Cálculo</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr *ngFor="let credito of creditos">
                      <td>{{ credito.numeroCredito }}</td>
                      <td>{{ credito.numeroNfse }}</td>
                      <td>{{ credito.dataConstituicao | date:'dd/MM/yyyy' }}</td>
                      <td>{{ credito.valorIssqn | currency:'BRL' }}</td>
                      <td>{{ credito.tipoCredito }}</td>
                      <td>{{ credito.simplesNacional ? 'Sim' : 'Não' }}</td>
                      <td>{{ credito.aliquota }}%</td>
                      <td>{{ credito.valorFaturado | currency:'BRL' }}</td>
                      <td>{{ credito.valorDeducao | currency:'BRL' }}</td>
                      <td>{{ credito.baseCalculo | currency:'BRL' }}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .table-responsive {
      max-height: 500px;
      overflow-y: auto;
    }
    .table th {
      position: sticky;
      top: 0;
      background: white;
      z-index: 1;
    }
  `]
})
export class ConsultaCreditosComponent {
  consultaForm: FormGroup;
  creditos: Credito[] = [];
  loading = false;
  erro: string | null = null;

  constructor(
    private creditoService: CreditoService,
    private fb: FormBuilder
  ) {
    this.consultaForm = this.fb.group({
      tipoConsulta: ['nfse', Validators.required],
      numero: ['', Validators.required]
    });
  }

  consultar() {
    if (this.consultaForm.invalid) return;

    this.loading = true;
    this.erro = null;
    this.creditos = [];

    const tipo = this.consultaForm.get('tipoConsulta')?.value;
    const numero = this.consultaForm.get('numero')?.value;

    if (tipo === 'nfse') {
      this.creditoService.consultarPorNumeroNfse(numero)
        .subscribe({
          next: (result) => {
            this.creditos = result;
            this.loading = false;
          },
          error: (error) => {
            this.erro = 'Erro ao consultar créditos. Tente novamente.';
            this.loading = false;
            console.error('Erro:', error);
          }
        });
    } else {
      this.creditoService.consultarPorNumeroCredito(numero)
        .subscribe({
          next: (result) => {
            this.creditos = [result];
            this.loading = false;
          },
          error: (error) => {
            this.erro = 'Erro ao consultar crédito. Tente novamente.';
            this.loading = false;
            console.error('Erro:', error);
          }
        });
    }
  }
} 