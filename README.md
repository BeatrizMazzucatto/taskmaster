# TASKMASTER - API de Gerenciamento de Tarefas

Este projeto implementa uma API RESTful robusta, escalável e de fácil manutenção para gerenciamento de tarefas, seguindo as melhores práticas de arquitetura em camadas, DTOs, validação de dados, tratamento centralizado de exceções e documentação automatizada com Spring Boot.

## 📋 Descrição

O TASKMASTER é uma API RESTful completa que permite criar, atualizar, excluir, listar e filtrar tarefas. A aplicação foi desenvolvida seguindo uma arquitetura em camadas (Controller-Service-Repository), utilizando DTOs para transferência de dados, validações robustas, tratamento centralizado de exceções e documentação automática com Swagger/OpenAPI.

## 🗂️ Estrutura do Projeto

```
Taskmaster/
├── src/main/java/com/taskmaster/
│   ├── TaskmasterApplication.java
│   ├── controller/
│   │   └── TaskController.java
│   ├── dto/
│   │   └── TaskDTO.java
│   ├── model/
│   │   └── Task.java
│   ├── repository/
│   │   └── TaskRepository.java
│   ├── service/
│   │   └── TaskService.java
│   ├── exception/
│   │   ├── ErrorResponse.java
│   │   ├── GlobalExceptionHandler.java
│   │   └── ResourceNotFoundException.java
│   └── config/
│       └── SwaggerConfig.java
│
├── src/main/resources/
│   └── application.properties
│
├── src/test/java/com/taskmaster/
│   ├── controller/
│   │   └── TaskControllerTest.java
│   └── service/
│       └── TaskServiceTest.java
│
├── pom.xml
└── README.md
```

## 🎯 Funcionalidades Implementadas

### Épico 1: Gerenciamento de Tarefas

#### História 1.1: Criar uma nova tarefa
**Objetivo:** Permitir a criação de novas tarefas com validação completa dos dados.

**Conceitos abordados:**
- Validação de campos obrigatórios com Jakarta Bean Validation
- Validação de regras de negócio (data limite não pode ser no passado)
- Tratamento de erros de validação
- Geração automática de ID único

**Implementação:**
- POST `/tasks` retorna **201 Created**
- Validação com `@Valid`, `@NotBlank`, `@NotNull`, `@Size`
- Validação de data limite no Service
- Mensagens de erro claras e padronizadas

#### História 1.2: Atualizar uma tarefa existente
**Objetivo:** Permitir a atualização completa dos dados de uma tarefa existente.

**Conceitos abordados:**
- Validação de existência de recurso
- Atualização completa de entidade
- Tratamento de recursos não encontrados

**Implementação:**
- PUT `/tasks/{id}` retorna **200 OK**
- Retorna **404 Not Found** se tarefa não existir
- Validação completa dos dados atualizados

#### História 1.3: Excluir uma tarefa
**Objetivo:** Permitir a exclusão permanente de tarefas do sistema.

**Conceitos abordados:**
- Validação de existência antes de excluir
- Resposta apropriada (204 No Content)
- Tratamento de recursos não encontrados

**Implementação:**
- DELETE `/tasks/{id}` retorna **204 No Content**
- Retorna **404 Not Found** se tarefa não existir
- Validação de existência antes da exclusão

### Épico 2: Consulta e Organização de Tarefas

#### História 2.1: Listar tarefas com paginação e ordenação
**Objetivo:** Implementar listagem eficiente de tarefas com paginação e ordenação.

**Conceitos abordados:**
- Interface `Pageable` do Spring Data JPA
- Tipo `Page<T>` para resultados paginados
- Parâmetros de paginação na URL (`page`, `size`, `sort`)
- Metadados de paginação na resposta
- Ordenação dinâmica por múltiplos campos

**Implementação:**
- GET `/tasks` retorna **200 OK** com `Page<Task>`
- Parâmetros: `page` (padrão: 0), `size` (padrão: 10), `sort` (padrão: dataLimite,asc)
- Metadados incluídos: número da página, total de elementos, total de páginas

**Exemplo de uso:**
```
GET /tasks?page=0&size=10&sort=dataLimite,asc
GET /tasks?page=1&size=5&sort=titulo,desc
```

#### História 2.2: Filtrar tarefas por categoria
**Objetivo:** Permitir filtrar tarefas por categoria específica.

**Conceitos abordados:**
- Filtro por parâmetro query opcional
- Consultas customizadas no Repository
- Retorno de lista filtrada

**Implementação:**
- GET `/tasks?categoria=Trabalho` retorna **200 OK** com `List<Task>`
- Retorna apenas tarefas da categoria especificada
- Retorna lista vazia se não houver tarefas
- Endpoint alternativo: GET `/tasks/filtrar?categoria=Trabalho`

**Exemplo de uso:**
```
GET /tasks?categoria=Trabalho
GET /tasks?categoria=Estudo
GET /tasks/filtrar?categoria=Pessoal
```

## 🏗 Arquitetura

A aplicação segue uma arquitetura em camadas bem definida:

```
┌─────────────────────────────────────┐
│      Controller (TaskController)    │  ← Camada de apresentação (HTTP)
│  - Recebe requisições HTTP          │
│  - Valida entrada (@Valid)          │
│  - Retorna ResponseEntity           │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      Service (TaskService)          │  ← Camada de negócio
│  - Lógica de negócio                │
│  - Validações de regras             │
│  - Conversão DTO ↔ Entity           │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│   Repository (TaskRepository)       │  ← Camada de persistência
│  - Acesso ao banco de dados         │
│  - Consultas JPA                    │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      Model (Task)                   │  ← Entidade de domínio
│  - Representação da tabela          │
└─────────────────────────────────────┘
```

### Componentes Principais

- **TaskController**: Gerencia requisições HTTP e retorna respostas adequadas
- **TaskService**: Contém toda a lógica de negócio e orquestração
- **TaskRepository**: Interface JPA para acesso aos dados
- **Task**: Entidade JPA representando a tabela de tarefas
- **TaskDTO**: Objeto de transferência de dados com validações
- **GlobalExceptionHandler**: Tratamento centralizado de exceções
- **SwaggerConfig**: Configuração da documentação OpenAPI

## 🛠 Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Spring Boot 3.2.0** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **H2 Database** - Banco de dados em memória (desenvolvimento)
- **Jakarta Bean Validation** - Validação de dados
- **SpringDoc OpenAPI** - Documentação Swagger
- **Maven** - Gerenciamento de dependências
- **Mockito** - Framework de testes com mocks
- **JUnit 5** - Framework de testes

## 🚀 Como Executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior

### Executando a Aplicação

```bash
# Navegue até o diretório do projeto
cd Taskmaster

# Compile o projeto
mvn clean compile

# Execute a aplicação
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

### Acessando a Documentação Swagger

Após iniciar a aplicação, acesse:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs (JSON)**: http://localhost:8080/api-docs

### Acessando o Console H2

- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:taskmasterdb`
  - Username: `sa`
  - Password: (deixe em branco)

## 📡 Endpoints da API

| Método | Endpoint | Descrição | Status de Sucesso |
|--------|----------|-----------|-------------------|
| POST | `/tasks` | Criar uma nova tarefa | 201 Created |
| GET | `/tasks` | Listar tarefas (com paginação e ordenação) | 200 OK |
| GET | `/tasks?categoria=Trabalho` | Filtrar tarefas por categoria | 200 OK |
| GET | `/tasks/filtrar?categoria=Trabalho` | Filtrar tarefas (endpoint alternativo) | 200 OK |
| GET | `/tasks/{id}` | Buscar tarefa por ID | 200 OK |
| PUT | `/tasks/{id}` | Atualizar uma tarefa | 200 OK |
| DELETE | `/tasks/{id}` | Excluir uma tarefa | 204 No Content |

### Parâmetros de Paginação e Ordenação

Os endpoints de listagem suportam parâmetros de paginação:

- `page`: Número da página (começa em 0) - padrão: 0
- `size`: Tamanho da página - padrão: 10
- `sort`: Campo para ordenação (ex: `dataLimite,asc` ou `titulo,desc`) - padrão: `dataLimite,asc`

**Exemplos:**
```
GET /tasks?page=0&size=5&sort=dataLimite,asc
GET /tasks?page=1&size=10&sort=titulo,desc
GET /tasks?categoria=Trabalho&page=0&size=5
```

## 📝 Exemplos de Uso

### Criar uma Tarefa (POST)

```bash
curl -X POST "http://localhost:8080/tasks" \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Implementar funcionalidade X",
    "descricao": "Desenvolver a nova funcionalidade conforme especificação",
    "categoria": "Trabalho",
    "dataLimite": "2024-12-31"
  }'
```

**Resposta (201 Created):**
```json
{
  "id": 1,
  "titulo": "Implementar funcionalidade X",
  "descricao": "Desenvolver a nova funcionalidade conforme especificação",
  "categoria": "Trabalho",
  "dataLimite": "2024-12-31"
}
```

### Listar Tarefas com Paginação (GET)

```bash
curl -X GET "http://localhost:8080/tasks?page=0&size=10&sort=dataLimite,asc"
```

**Resposta (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "titulo": "Implementar funcionalidade X",
      "descricao": "Desenvolver a nova funcionalidade conforme especificação",
      "categoria": "Trabalho",
      "dataLimite": "2024-12-31"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": true,
      "unsorted": false
    }
  },
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "size": 10,
  "number": 0
}
```

### Filtrar por Categoria (GET)

```bash
curl -X GET "http://localhost:8080/tasks?categoria=Trabalho"
```

**Resposta (200 OK):**
```json
[
  {
    "id": 1,
    "titulo": "Implementar funcionalidade X",
    "descricao": "Desenvolver a nova funcionalidade conforme especificação",
    "categoria": "Trabalho",
    "dataLimite": "2024-12-31"
  }
]
```

### Buscar Tarefa por ID (GET)

```bash
curl -X GET "http://localhost:8080/tasks/1"
```

### Atualizar uma Tarefa (PUT)

```bash
curl -X PUT "http://localhost:8080/tasks/1" \
  -H "Content-Type: application/json" \
  -d '{
    "titulo": "Implementar funcionalidade X - Atualizado",
    "descricao": "Descrição atualizada",
    "categoria": "Trabalho",
    "dataLimite": "2024-12-31"
  }'
```

### Excluir uma Tarefa (DELETE)

```bash
curl -X DELETE "http://localhost:8080/tasks/1"
```

## ✅ Validações Implementadas

### Validações de Sintaxe (DTO)

- `titulo`: Obrigatório (`@NotBlank`), máximo 200 caracteres (`@Size`)
- `descricao`: Opcional, máximo 1000 caracteres (`@Size`)
- `categoria`: Obrigatória (`@NotBlank`), máximo 50 caracteres (`@Size`)
- `dataLimite`: Obrigatória (`@NotNull`), formato `LocalDate`

### Validações de Negócio (Service)

- **Data limite não pode ser no passado**: Implementada no método `validarDataLimite()` do `TaskService`
- **Tarefa deve existir**: Validação antes de atualizar ou excluir tarefas

## 🔒 Tratamento de Erros

A API retorna respostas de erro padronizadas através do `@RestControllerAdvice`:

### 400 Bad Request - Validação
```json
{
  "erro": "VALIDATION_ERROR",
  "mensagem": "O título é obrigatório"
}
```

### 404 Not Found - Recurso não encontrado
```json
{
  "erro": "NOT_FOUND",
  "mensagem": "Tarefa não encontrada com ID: 999"
}
```

### 400 Bad Request - Regra de negócio
```json
{
  "erro": "BAD_REQUEST",
  "mensagem": "A data limite não pode ser no passado"
}
```

### 500 Internal Server Error - Erro genérico
```json
{
  "erro": "INTERNAL_SERVER_ERROR",
  "mensagem": "Ocorreu um erro interno no servidor. Tente novamente mais tarde."
}
```

## 🧪 Testes

### Executando Testes

```bash
mvn test
```

### Estrutura de Testes

- **Testes Unitários** (`TaskServiceTest`): Focam na camada de serviço com mocks do repositório usando Mockito
- **Testes de Integração** (`TaskControllerTest`): Testam os endpoints de ponta a ponta com MockMvc

## 🔍 Conceitos Aprendidos

### Arquitetura em Camadas
- **Controller**: Camada de apresentação, recebe requisições HTTP
- **Service**: Camada de negócio, contém a lógica de aplicação
- **Repository**: Camada de persistência, acesso aos dados
- **DTO**: Objetos de transferência de dados, separação de responsabilidades

### DTOs (Data Transfer Objects)
- Separação entre entidades de domínio e objetos de transferência
- Segurança: controle de dados expostos na API
- Validação de dados na camada de apresentação
- Conversão entre Entity e DTO nos Services

### Injeção de Dependência via Construtor
- Dependências explícitas e testáveis
- Facilita a criação de testes unitários
- Garante que objetos sejam criados em estado válido

### Validação de Dados
- **Validação de Sintaxe**: Jakarta Bean Validation (`@NotBlank`, `@NotNull`, `@Size`)
- **Validação de Negócio**: Regras complexas implementadas na camada de serviço

### Tratamento Centralizado de Exceções
- `@RestControllerAdvice` para tratamento global
- Respostas de erro padronizadas
- Códigos de status HTTP apropriados

### Paginação e Ordenação
- Interface `Pageable` do Spring Data JPA
- Tipo `Page<T>` para resultados paginados com metadados
- Parâmetros de paginação na URL
- Ordenação dinâmica por múltiplos campos

### Documentação com Swagger
- SpringDoc OpenAPI para documentação automática
- Anotações Swagger (`@Tag`, `@Operation`, `@ApiResponse`, `@Parameter`)
- Interface Swagger UI interativa
- Configuração personalizada da documentação

## 🏛 Princípios e Boas Práticas Aplicadas

1. ✅ **Arquitetura em Camadas**: Separação clara de responsabilidades
2. ✅ **DTOs**: Isolamento da estrutura interna do modelo
3. ✅ **Injeção de Dependência via Construtor**: Dependências explícitas e testáveis
4. ✅ **Validação de Dados**: Jakarta Bean Validation + regras de negócio
5. ✅ **Tratamento Centralizado de Exceções**: `@RestControllerAdvice`
6. ✅ **Documentação Swagger**: Documentação automática e interativa
7. ✅ **Código Limpo**: Nomenclatura clara, métodos focados, baixo acoplamento
8. ✅ **Testes Automatizados**: Cobertura de testes unitários e de integração

## 📖 Referências

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Data JPA - Pagination](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repositories.query-methods.query-creation)
- [SpringDoc OpenAPI](https://springdoc.org/)
- [Jakarta Bean Validation](https://beanvalidation.org/)
- [RESTful API Best Practices](https://restfulapi.net/)

## 👤 Autor

Ana Layslla - https://www.linkedin.com/in/ana-layslla/ & Beatriz Mazzucatto - www.linkedin.com/in/beatriz-mazzucatto-seabra

---

**Instituto Federal de Educação, Ciência e Tecnologia de São Paulo, Câmpus Guarulhos.**  
**APIs e Microsserviços - Prof. Giovani.**
