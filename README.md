# Contador Comitê - Sistema de Gestão Financeira para Comitê de Formatura

## Visão Geral

O Contador Comitê é uma aplicação full-stack desenvolvida para facilitar a gestão financeira de comitês de formatura. A plataforma permite o controle intuitivo de receitas e despesas, monitoramento de vendas diárias, acompanhamento de metas financeiras e geração de relatórios analíticos.

Desenvolvido com tecnologias modernas, o sistema oferece uma interface amigável para membros do setor financeiro registrarem vendas, acompanharem o progresso em relação às metas e gerenciarem o fluxo de caixa.

## Funcionalidades Implementadas

### Gestão de Capital
✅ Controle do capital total disponível  
✅ Registro de capital inicial para início do monitoramento  
✅ Operações de adição e subtração de valores do capital

### Gestão de Metas
✅ Definição de metas financeiras com valores, descrições e prazos  
✅ Acompanhamento do progresso em direção às metas  
✅ Monitoramento de status das metas (em andamento, concluída, etc.)

## Funcionalidades Planejadas

### Registro de Vendas
🔄 Contadores para diferentes produtos (refri copo, refri garrafa, picolé)  
🔄 Marcação de "dia de trote" com contadores especiais (bingo, cadeia do amor, correio elegante)  
🔄 Registro de vendas customizadas e exceções

### Registro de Despesas
🔄 Cadastro de despesas com insumos (picolés, fardos de refrigerante)  
🔄 Controle de despesas diversas

### Visualização e Análise
🔄 Calendário semanal até a data final de pagamento da formatura  
🔄 Gráficos de receitas e lucros por dia  
🔄 Sumário financeiro e acompanhamento de metas

## Arquitetura do Sistema

### Backend (Implementado)
- Java com Spring Boot (API REST)
- Banco de dados PostgreSQL
- Armazenamento em AWS RDS

### Frontend (Planejado)
- Vue.js com TypeScript
- Vuex para gerenciamento de estado
- Interface responsiva e intuitiva

## Modelo de Domínio
### Diagrama de Classes Atual
```mermaid
classDiagram
    %% Domain - Entidades do Domínio
    class Capital {
        -Long id
        -BigDecimal initialAmount
        -BigDecimal currentAmount
        -LocalDateTime createdAt
        -LocalDateTime updatedAt
        +INSTANCE_ID : Long
    }
    
    class Meta {
        -Long id
        -String description
        -BigDecimal goalValue
        -BigDecimal currentValue
        -LocalDate startDate
        -LocalDate endDate
        -MetaStatus status
        +INSTANCE_ID : Long
    }
    
    class Venda {
        -Long id
        -LocalDate date
        -ItemType itemType
        -Integer quantity
        -BigDecimal unitPrice
        -BigDecimal totalPrice
        -String notes
        -Boolean isTrote
    }
    
    class Despesa {
        -Long id
        -LocalDate date
        -String item
        -Integer quantity
        -BigDecimal unitCost
        -BigDecimal totalCost
        -String notes
    }
    
    class ItemType {
        <<enumeration>>
        REFRIGERANTE_COPO
        REFRIGERANTE_GARRAFA
        PICOLE
        OUTRO
    }
    
    class MetaStatus {
        <<enumeration>>
        ATIVA
        CONCLUIDA
        CANCELADA
    }
    
    class User {
        -Long id
        -String name
        -String email
        -String password
        -UserRole role
    }
    
    class UserRole {
        <<enumeration>>
        ADMIN
        USER
    }
    
    %% DTOs - Objetos de Transferência de Dados
    class CapitalDTO {
        -Long id
        -BigDecimal currentAmount
        -BigDecimal initialAmount
        +toEntity() Capital
        +fromEntity(Capital) CapitalDTO
    }
    
    class MetaDTO {
        -Long id
        -String description
        -BigDecimal goalValue
        -BigDecimal currentValue
        -LocalDate startDate
        -LocalDate endDate
        -MetaStatus status
        +toEntity() Meta
        +fromEntity(Meta) MetaDTO
    }
    
    class VendaDTO {
        -Long id
        -String date
        -ItemType itemType
        -Integer quantity
        -BigDecimal unitPrice
        -BigDecimal totalPrice
        -String notes
        -Boolean isHazing
        +toEntity() Venda
        +fromEntity(Venda) VendaDTO
    }
    
    class DespesaDTO {
        -Long id
        -LocalDate date
        -String item
        -Integer quantity
        -BigDecimal unitCost
        -BigDecimal totalCost
        -String notes
        +toEntity() Despesa
        +fromEntity(Despesa) DespesaDTO
    }
    
    %% Auth DTOs
    class AuthenticationDTO {
        -String email
        -String password
    }
    
    class LoginResponseDTO {
        -String token
    }
    
    class RegisterDTO {
        -String name
        -String email
        -String password
        -String role
    }
    
    %% Services
    class CapitalService {
        -CapitalRepository repository
        -MetaService metaService
        +getOrCreateCapital() Capital
        +updateInitialCapital(BigDecimal) Capital
        +addValue(BigDecimal) Capital
        +subtractValue(BigDecimal) Capital
    }
    
    class MetaService {
        -MetaRepository repository
        +getOrCreateMeta() Meta
        +updateMeta(Meta) Meta
        +addValue(BigDecimal) Meta
        +subtractValue(BigDecimal) Meta
    }
    
    class VendaService {
        -VendaRepository repository
        -CapitalService capitalService
        +createOrUpdateVenda(Venda) Venda
        +getVendas() List~Venda~
        +getVendasByDate(LocalDate) List~Venda~
        +deleteVenda(Long) void
    }
    
    class DespesaService {
        -DespesaRepository repository
        -CapitalService capitalService
        +createOrUpdateDespesa(Despesa) Despesa
        +getDespesas() List~Despesa~
        +getDespesasByDate(LocalDate) Optional~List~Despesa~~
        +deleteDespesa(Long) void
    }
    
    %% Controllers
    class CapitalController {
        -CapitalService service
        +getCapital() ResponseEntity~CapitalDTO~
        +updateCapitalInicial(CapitalDTO) ResponseEntity~CapitalDTO~
        +addCapital(BigDecimal) ResponseEntity~CapitalDTO~
        +subtractCapital(BigDecimal) ResponseEntity~CapitalDTO~
    }
    
    class MetaController {
        -MetaService service
        +getMeta() ResponseEntity~MetaDTO~
    }
    
    class VendaController {
        -VendaService service
        +createVenda(VendaDTO) ResponseEntity~VendaDTO~
        +getVendas() ResponseEntity~List~VendaDTO~~
        +getVendasByDate(LocalDate) ResponseEntity~List~VendaDTO~~
        +updateVenda(Long, VendaDTO) ResponseEntity~VendaDTO~
        +deleteVenda(Long) ResponseEntity~Void~
    }
    
    class DespesaController {
        -DespesaService service
        +createDespesa(DespesaDTO) ResponseEntity~DespesaDTO~
        +getDespesas() ResponseEntity~List~DespesaDTO~~
        +getDespesasByDate(LocalDate) ResponseEntity~List~DespesaDTO~~
        +updateDespesa(Long, DespesaDTO) ResponseEntity~DespesaDTO~
        +deleteDespesa(Long) ResponseEntity~Void~
    }
    
    class AuthenticationController {
        -UserRepository repository
        -AuthenticationManager authManager
        -TokenService service
        +login(AuthenticationDTO) ResponseEntity~LoginResponseDTO~
        +register(RegisterDTO) ResponseEntity~Void~
    }
    
    %% Relações
    Venda --> ItemType
    Meta --> MetaStatus
    User --> UserRole
    
    Capital ..> CapitalDTO : converte para
    Meta ..> MetaDTO : converte para
    Venda ..> VendaDTO : converte para
    Despesa ..> DespesaDTO : converte para
    
    CapitalService --> Capital : manipula
    MetaService --> Meta : manipula
    VendaService --> Venda : manipula
    DespesaService --> Despesa : manipula
    
    CapitalController --> CapitalService : usa
    MetaController --> MetaService : usa
    VendaController --> VendaService : usa
    DespesaController --> DespesaService : usa
    
    VendaService --> CapitalService : usa
    DespesaService --> CapitalService : usa
    CapitalService --> MetaService : usa
    
    AuthenticationController --> User : manipula
```

### Diagrama da Arquitetura do Sistema
```mermaid
classDiagram
    %% Camadas da Arquitetura
    namespace Apresentação {
        class CapitalController
        class MetaController
        class VendaController
        class DespesaController
        class AuthenticationController
    }
    
    namespace Aplicação {
        class CapitalService
        class MetaService
        class VendaService
        class DespesaService
        class TokenService
    }
    
    namespace Domínio {
        class Capital
        class Meta
        class Venda
        class Despesa
        class User
        class ItemType
        class MetaStatus
        class UserRole
    }
    
    namespace Infraestrutura {
        class CapitalRepository
        class MetaRepository
        class VendaRepository
        class DespesaRepository
        class UserRepository
    }
    
    namespace DTOs {
        class CapitalDTO
        class MetaDTO
        class VendaDTO
        class DespesaDTO
        class AuthenticationDTO
        class LoginResponseDTO
        class RegisterDTO
    }
    
    %% Relações entre camadas
    Apresentação --> Aplicação : usa
    Aplicação --> Domínio : manipula
    Aplicação --> Infraestrutura : usa
    Domínio ..> DTOs : converte para
```

### Fluxo de Dados do Sistema
```mermaid
flowchart TB
    Cliente[Cliente Frontend] --> Controllers
    subgraph Backend
        Controllers --> DTOs
        DTOs --> Services
        Services --> Entities[Entidades de Domínio]
        Services --> Repositories
        Repositories --> Database[(Database PostgreSQL)]
    end
    Controllers --> Cliente
```

## API Endpoints Implementados

### Capital
- `GET /api/capital` - Obtém informações do capital atual
- `PUT /api/capital/initial` - Atualiza o valor do capital inicial
- `PUT /api/capital/current/add` - Adiciona valor ao capital atual
- `PUT /api/capital/current/subtract` - Subtrai valor do capital atual

### Vendas
- `GET /api/vendas` - Obtém todas as vendas
- `GET /api/vendas/{date}` - Obtém vendas por data (formato dd-MM-yyyy)
- `POST /api/vendas` - Registra uma nova venda
- `PUT /api/vendas/{id}` - Atualiza uma venda existente
- `DELETE /api/vendas/{id}` - Remove uma venda

### Despesas
- `GET /api/despesas` - Obtém todas as despesas
- `GET /api/despesas/{date}` - Obtém despesas por data (formato dd-MM-yyyy)
- `POST /api/despesas` - Registra uma nova despesa
- `PUT /api/despesas/{id}` - Atualiza uma despesa existente
- `DELETE /api/despesas/{id}` - Remove uma despesa

### Meta
- `GET /api/meta` - Obtém informações da meta atual

### Autenticação
- `POST /api/auth/login` - Autentica um usuário
- `POST /api/auth/register` - Registra um novo usuário

## Configuração e Execução

### Pré-requisitos
- Java 17+
- PostgreSQL
- Maven

### Configuração
1. Clone o repositório
2. Configure as credenciais do banco de dados em `application.properties`
3. Execute o backend com `mvn spring-boot:run`

## Próximos Passos
- Implementação de módulos de venda e despesa
- Desenvolvimento do frontend em Vue.js
- Implementação de autenticação e autorização
- Desenvolvimento de dashboards analíticos
- Implementação de relatórios exportáveis

## Sobre o Projeto

Este projeto foi desenvolvido para auxiliar o comitê de formatura do terceiro ano na gestão financeira, com o objetivo principal de arrecadar fundos suficientes para custear a colação de grau de aproximadamente 100 alunos, que representa um custo estimado de R$ 120,00 por aluno (no primeiro lote, sujeito a aumentos nos lotes seguintes).

Projeto em desenvolvimento ativo. Contribuições são bem-vindas!
