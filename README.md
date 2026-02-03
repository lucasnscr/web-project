# PreventivaDB - Sistema de Manutenção Preventiva

Sistema CRUD completo para gestão de manutenção preventiva de plataformas elevatórias, desenvolvido com **Java Spring Boot 3.x** no backend e **React + TypeScript** no frontend, com design moderno estilo SaaS.

![PreventivaDB Dashboard](https://img.shields.io/badge/Status-Funcionando-brightgreen)
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-green)
![React](https://img.shields.io/badge/React-19-blue)
![TypeScript](https://img.shields.io/badge/TypeScript-5.7-blue)

## 📋 Visão Geral

O PreventivaDB é um sistema completo de gestão de manutenção preventiva de plataformas elevatórias, permitindo:

- **Visualização** de todos os itens de manutenção com estatísticas em tempo real
- **Busca** por modelo, fabricante, item ou part number
- **Filtros avançados** por fabricante e modelo
- **CRUD completo** (Criar, Ler, Atualizar, Deletar)
- **Ordenação** por qualquer coluna
- **Interface responsiva** e moderna estilo SaaS
- **Intervalos de manutenção** por horas (250h, 500h, 750h, 1000h)

## 🏗️ Arquitetura

```
plataformas-crud/
├── backend/                    # API REST Spring Boot
│   ├── src/main/java/
│   │   └── com/plataformas/crud/
│   │       ├── config/         # Configurações (CORS, DataLoader)
│   │       ├── controller/     # Controllers REST
│   │       ├── model/          # Entidades JPA
│   │       ├── repository/     # Repositórios Spring Data
│   │       └── service/        # Camada de serviço
│   └── pom.xml
│
└── frontend/                   # SPA React + TypeScript
    ├── src/
    │   ├── components/         # Componentes React
    │   ├── services/           # Serviços de API
    │   └── types/              # Tipos TypeScript
    └── package.json
```

## 🛠️ Tecnologias

### Backend
- **Java 21** - Última versão LTS
- **Spring Boot 3.4.2** - Framework web
- **Spring Data JPA** - Persistência de dados
- **H2 Database** - Banco de dados em arquivo (persistente)
- **Lombok** - Redução de boilerplate
- **Maven** - Gerenciamento de dependências

### Frontend
- **React 19** - Biblioteca UI
- **TypeScript 5.7** - Tipagem estática
- **Vite 7** - Build tool
- **TailwindCSS 4** - Estilização
- **Axios** - Cliente HTTP
- **Lucide React** - Ícones

## 📊 Modelo de Dados

| Campo | Tipo | Descrição |
|-------|------|-----------|
| id | Long | Identificador único |
| modelo | String | Modelo da plataforma |
| fabricante | String | Fabricante (JLG, GENIE, SKYJACK, HAULOTTE) |
| itemDescricao | String | Descrição do item de manutenção |
| partNumber | String | Part Number do fabricante |
| codSap | String | Código SAP |
| qtd250h | String | Quantidade para 250 horas |
| qtd500h | String | Quantidade para 500 horas |
| qtd750h | String | Quantidade para 750 horas |
| qtd1000h | String | Quantidade para 1000 horas |
| horasMo250h | String | Horas de mão de obra para 250h |
| horasMo500h | String | Horas de mão de obra para 500h |
| horasMo750h | String | Horas de mão de obra para 750h |
| horasMo1000h | String | Horas de mão de obra para 1000h |

## 🚀 Como Executar

### Pré-requisitos
- Java 21+
- Maven 3.8+
- Node.js 18+
- pnpm (ou npm/yarn)

### Backend

```bash
# Navegar para o diretório do backend
cd backend

# Compilar e executar
mvn spring-boot:run
```

O backend estará disponível em `http://localhost:8080`

### Frontend

```bash
# Navegar para o diretório do frontend
cd frontend

# Instalar dependências
pnpm install

# Executar em modo desenvolvimento
pnpm dev
```

O frontend estará disponível em `http://localhost:5173`

## 📡 API Endpoints

### Preventivas

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/preventivas` | Lista todas as preventivas |
| GET | `/api/preventivas/{id}` | Busca preventiva por ID |
| POST | `/api/preventivas` | Cria nova preventiva |
| PUT | `/api/preventivas/{id}` | Atualiza preventiva |
| DELETE | `/api/preventivas/{id}` | Remove preventiva |
| GET | `/api/preventivas/search?q={termo}` | Busca por termo |
| GET | `/api/preventivas/filter` | Filtra com múltiplos parâmetros |
| GET | `/api/preventivas/stats` | Estatísticas do catálogo |
| GET | `/api/preventivas/fabricantes` | Lista fabricantes únicos |
| GET | `/api/preventivas/modelos` | Lista modelos únicos |
| GET | `/api/preventivas/itens` | Lista itens únicos |

### Exemplos de Requisições

```bash
# Listar todas as preventivas
curl http://localhost:8080/api/preventivas

# Buscar por termo
curl "http://localhost:8080/api/preventivas/search?q=GENIE"

# Filtrar por fabricante e modelo
curl "http://localhost:8080/api/preventivas/filter?fabricante=JLG&modelo=450AJ"

# Obter estatísticas
curl http://localhost:8080/api/preventivas/stats
```

### Exemplo de Resposta

```json
{
  "id": 1,
  "modelo": "Serie ES",
  "fabricante": "JLG",
  "itemDescricao": "Filtro Hidráulico (retorno)",
  "partNumber": "7023576",
  "codSap": "3004091",
  "qtd250h": "1",
  "qtd500h": "1",
  "qtd750h": "1",
  "qtd1000h": "1",
  "horasMo250h": "4",
  "horasMo500h": "4",
  "horasMo750h": "4",
  "horasMo1000h": "5"
}
```

## 🎨 Funcionalidades do Frontend

### Dashboard
- Cards com estatísticas em tempo real
- Total de registros, modelos, fabricantes e itens de manutenção
- Design moderno com gradientes

### Tabela de Dados
- Exibição de todos os registros de manutenção
- Colunas para intervalos de horas (250h, 500h, 750h, 1000h)
- Ordenação por qualquer coluna (clique no cabeçalho)
- Badges coloridos por fabricante
- Ações de editar e excluir

### Filtros
- Busca textual em tempo real
- Filtro por fabricante
- Filtro por modelo
- Limpar busca com um clique

### Modal de Cadastro/Edição
- Formulário completo com todos os campos
- Seleção de fabricante com opções dinâmicas
- Campos para quantidades por intervalo de horas
- Feedback visual de carregamento

### Confirmação de Exclusão
- Diálogo de confirmação antes de excluir
- Feedback de sucesso/erro via toast

## 🗃️ Dados Iniciais

O sistema vem pré-carregado com **298 registros** de manutenção preventiva:

### Estatísticas
- **36 modelos** de plataformas
- **4 fabricantes**: JLG, GENIE, SKYJACK, HAULOTTE
- **39 tipos** de itens de manutenção

### Fabricantes e Modelos

| Fabricante | Modelos |
|------------|---------|
| **JLG** | Serie ES, Serie LE (4069), Serie 10RS, 450AJ Deutz, 450AJ Perkins, E450AJ, 860SJ, 1200SJP, 1350SJP |
| **GENIE** | Serie GS DC, GS 2668 RT Perkins, Z-45/25 RT, Z-34/22 RT, Z-45/25 J DC, S-65TRAX, SX-180 |
| **SKYJACK** | SJ8841RT, SJ6832RT, SJ9250RT, SJIII 3219/3226/4626/4632/4732, SJ45 AJ+, SJ85 AJ |
| **HAULOTTE** | C10N, C14N, STAR8, STAR10, HA15IP, HA16SPX, HA18SPX, HA20, HA26, HA16RTJ |

### Itens de Manutenção
- Filtro Hidráulico (retorno/carga)
- Filtro de óleo motor
- Filtro de ar (primário/secundário)
- Filtro de combustível
- Filtro separador
- Óleo hidráulico
- Óleo de motor
- Óleo para HUB
- Água de bateria
- Kit de manutenção preventiva

## 🔧 Configuração

### Backend (application.properties)

```properties
# Servidor
server.port=8080

# H2 Database (arquivo persistente)
spring.datasource.url=jdbc:h2:file:./data/plataformas
spring.datasource.driverClassName=org.h2.Driver
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

### Frontend (vite.config.ts)

```typescript
export default defineConfig({
  plugins: [react(), tailwindcss()],
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
})
```

## 🗄️ Console H2 (Banco de Dados)

Acesse `http://localhost:8080/h2-console` com:
- **JDBC URL**: `jdbc:h2:file:./data/plataformas`
- **User**: `sa`
- **Password**: (vazio)

## 📝 Licença

Este projeto foi desenvolvido para fins educacionais e demonstrativos.

## 👨‍💻 Autor

Desenvolvido com ❤️ usando as melhores práticas de desenvolvimento web moderno.
