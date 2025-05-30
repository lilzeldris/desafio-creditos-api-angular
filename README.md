# API de Consulta de Créditos

API RESTful desenvolvida com Spring Boot para consulta de créditos constituídos, com frontend em Angular para exibição dos dados.

## Tecnologias Utilizadas

### Backend
- Java 11
- Spring Boot 2.7.0
- Spring Data JPA
- Hibernate
- PostgreSQL
- Kafka
- JUnit 5
- Mockito
- Docker
- Docker Compose

### Frontend
- Angular 15
- TypeScript
- Bootstrap 5
- NGINX

## Pré-requisitos

- Docker
- Docker Compose

## Como Executar

1. Clone o repositório:
```bash
git clone https://github.com/lilzeldris/desafio-creditos-api-angular.git
```

2. Navegue até o diretório do projeto:
```bash
cd desafio-creditos-api-angular
```

3. Execute o projeto com Docker Compose:
```bash
docker-compose up -d --build
```

O sistema estará disponível em:
- Frontend: http://localhost
- API: http://localhost:8080

## Endpoints da API

### Consultar créditos por número da NFS-e
```
GET /api/creditos/{numeroNfse}
```

Exemplo de resposta:
```json
[
  {
    "numeroCredito": "123456",
    "numeroNfse": "7891011",
    "dataConstituicao": "2024-02-25",
    "valorIssqn": 1500.75,
    "tipoCredito": "ISSQN",
    "simplesNacional": true,
    "aliquota": 5.0,
    "valorFaturado": 30000.00,
    "valorDeducao": 5000.00,
    "baseCalculo": 25000.00
  }
]
```

### Consultar crédito por número do crédito
```
GET /api/creditos/credito/{numeroCredito}
```

Exemplo de resposta:
```json
{
  "numeroCredito": "123456",
  "numeroNfse": "7891011",
  "dataConstituicao": "2024-02-25",
  "valorIssqn": 1500.75,
  "tipoCredito": "ISSQN",
  "simplesNacional": true,
  "aliquota": 5.0,
  "valorFaturado": 30000.00,
  "valorDeducao": 5000.00,
  "baseCalculo": 25000.00
}
```

## Funcionalidades do Frontend

- Consulta de créditos por número da NFS-e
- Consulta de crédito por número do crédito
- Exibição dos resultados em tabela responsiva
- Interface adaptada para dispositivos móveis
- Validação de formulários
- Feedback visual durante carregamento
- Tratamento de erros

## Padrões de Projeto Utilizados

- MVC (Model-View-Controller)
- Repository
- Service Layer
- Dependency Injection
- Factory
- Singleton

## Testes

O projeto inclui testes unitários e de integração:

```bash
./mvnw test
```

Cobertura de testes:
- Testes unitários com JUnit 5
- Testes de integração
- Mocks com Mockito
- Testes do serviço de notificação Kafka

## Mensageria

A aplicação utiliza Kafka para registrar eventos de consulta:
- Tópico: `consultas-creditos`
- Formato da mensagem: JSON
- Campos: numeroNfse, tipoConsulta, timestamp, status

## Estrutura do Projeto

### Backend
- `src/main/java/com/desafio/creditosapi/`
  - `controller/` - Controladores REST
  - `model/` - Entidades JPA
  - `repository/` - Repositórios JPA
  - `service/` - Lógica de negócio
  - `config/` - Configurações
  - `exception/` - Tratamento de exceções

### Frontend
- `frontend/src/app/`
  - `components/` - Componentes Angular
  - `services/` - Serviços HTTP
  - `consulta-creditos/` - Componente de consulta

## Banco de Dados

- PostgreSQL 13
- Scripts de inicialização em `src/main/resources/schema.sql`
- Dados de exemplo incluídos

## Containers Docker

- `backend`: API Spring Boot
- `frontend`: Frontend Angular com NGINX
- `postgres`: Banco de dados PostgreSQL
- `zookeeper`: Gerenciador do Kafka
- `kafka`: Message broker

## Critérios de Qualidade

- Código limpo e organizado
- Princípios SOLID aplicados
- DRY (Don't Repeat Yourself)
- KISS (Keep It Simple, Stupid)
- Testes automatizados
- Documentação clara
- Commits organizados 