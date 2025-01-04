# Projeto de API para Gerenciamento de Produtos 📦

Este projeto é uma **API Rest para Gerenciamento de Produtos** cujo objetivo principal é permitir operações CRUD em produtos, além de oferecer funcionalidades avançadas como filtragem, paginação, relatórios e controle de acesso por usuários. 

### 🚀 Tecnologias Utilizadas

- Java 21
- Spring Boot 3.4.0
- MySQL
- Docker
- Swagger (OpenAPI)
- Envio de Email (MailTrap)
- JWT para Autenticação
- Heroku (Deploy em Produção)

### 🛠️ Funcionalidades

1. **CRUD de Produtos**: Adicionar, visualizar, editar e excluir produtos.
2. **CRUD de Usuários**: Adicionar, visualizar, editar e excluir contas.
3. **Listagem Avançada**: Paginação, Ordenação/Filtros múltiplos.
4. **Controle de Acesso**: Dois níveis: Administrador e Estoquista.
5. **Auditoria**: Registro de alterações nos produtos com histórico detalhado.
6. **Relatórios**: Geração de relatórios em **CSV** ou **XLSX**.
7. **Resumo Financeiro**: Cálculo de custos e valores de venda de produtos.
8. **Documentação**: API documentada com Swagger.
9. **Teste Unitários**: Cobertura para funcionalidades críticas.
    
### 🚨 Observações Importantes

- Foi realizado uma integração com o serviço de e-mail para que seja enviado atualizações do sistema, todavia, a versão gratuita do MailTrap só permite que seja enviado para o meu e-mail pessoal, assim, é um pouco dificil de comprovar essa funcionalizade para testadores externos. 

---

### 🐳 Como Rodar Localmente com Docker

1. Certifique-se de ter o **Docker** instalado em sua máquina.
2. Clone este repositório:
   ```bash
   git clone https://github.com/seu-repositorio/projeto-api-gerenciamento-produtos.git
   ```
3. Acesse o diretório do projeto:
   ```bash
   cd projeto-api-gerenciamento-produtos
   ```
4. Suba o ambiente com Docker Compose:
   ```bash
   docker-compose up
   ```
   Isso irá:
   - Inicializar o **MySQL**.
   - Subir a aplicação na porta `8080`.

5. Acesse a aplicação:
   - **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

### 🌐 Acesso ao Deploy em Produção

O projeto também está hospedado no **Heroku**. Você pode acessá-lo no link:

👉 **[Link Heroku](https://rita-product-management-763d144bcf95.herokuapp.com/api/v1/swagger-ui/index.html?urls.primaryName=public)**

---

### 🧪 Testes

Para rodar os testes de funcionalidades utilize o primeiro usuário criado de cada tipo:

```bash
ADMIN: {
  "username" : "admin",
  "password" : "Admin@123"
}

ESTOQUISTA: {
  "username" : "stockist",
  "password" : "Stockist@123"
}
```

---

### 🔧 Requisitos para Configuração Manual

Caso não queira usar Docker, você pode configurar o ambiente manualmente:

1. **Banco de Dados**:
   - Instale o MySQL.
   - Crie um banco de dados chamado `products-management`.
   - Execute o script SQL disponível no repositório (`scripts/schema.sql`).

2. **Configuração do Projeto**:
   - Atualize o arquivo `application.properties` com as credenciais do banco.

3. **Rodando o Projeto**:
   - Compile o projeto:
     ```bash
     ./mvnw clean install
     ```
   - Inicie a aplicação:
     ```bash
     ./mvnw spring-boot:run
     ```

### ✨ Desenvolvido por Rita Ferreira 💻

Tempo de desenvolvimento contabilizado pelo app [WakaTime](https://wakatime.com/@018bed01-1668-43dc-aef1-b064cc5ec137/projects/cmhnjibeay?start=2024-12-29&end=2025-01-04) integrado à IDEA Intellij.
