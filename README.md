# Projeto CP2 — Azure Web App + Azure SQL

## 1. Sobre o projeto

Aplicação Web desenvolvida em **Java com Spring Boot**, criada para o 2º Checkpoint da disciplina de Aplicações e Banco em Nuvem.

A aplicação disponibiliza uma API REST para gerenciamento de **clientes e pedidos**, utilizando um banco de dados **Azure SQL Database** para persistência dos dados.

A aplicação foi publicada no **Azure App Service (Web App)** utilizando comandos da **Azure CLI**.

### Tecnologias utilizadas

* Java 21
* Spring Boot 4.0.8
* Maven
* Spring Data JPA
* Hibernate
* Microsoft SQL Server / Azure SQL Database
* Azure App Service
* Azure Application Insights
* Azure CLI
* Postman
* GitHub

---

# 2. Arquitetura

A aplicação utiliza a seguinte arquitetura:

```text
┌─────────────────┐
│     Postman     │
│   / Consumidor  │
└────────┬────────┘
         │ HTTP
         ▼
┌─────────────────────────┐
│    Azure Web App        │
│    Spring Boot / Java   │
│    Porta 8080           │
└────────┬────────────────┘
         │ JDBC
         ▼
┌─────────────────────────┐
│     Azure SQL Database  │
│       projeto-db        │
│                         │
│  ┌─────────┐ ┌────────┐ │
│  │ cliente │ │ pedido │ │
│  └─────────┘ └────────┘ │
└─────────────────────────┘
         
         │
         ▼
┌─────────────────────────┐
│   Application Insights  │
│   Monitoramento da API  │
└─────────────────────────┘
```

---

# 3. Modelo do banco de dados

O banco possui duas tabelas relacionadas:

### Cliente

Armazena os dados dos clientes.

| Campo      | Tipo         | Descrição         |
| ---------- | ------------ | ----------------- |
| id_cliente | BIGINT       | Chave primária    |
| nome       | VARCHAR(100) | Nome do cliente   |
| email      | VARCHAR(150) | E-mail do cliente |

### Pedido

Armazena os pedidos realizados pelos clientes.

| Campo       | Tipo          | Descrição         |
| ----------- | ------------- | ----------------- |
| id_pedido   | BIGINT        | Chave primária    |
| data_pedido | DATE          | Data do pedido    |
| valor_total | DECIMAL(10,2) | Valor total       |
| id_cliente  | BIGINT        | Chave estrangeira |

### Relacionamento

Um cliente pode possuir vários pedidos.

```text
CLIENTE
   │
   │ 1
   │
   │
   │ N
   ▼
PEDIDO
```

A tabela `pedido` possui a chave estrangeira:

```sql
FOREIGN KEY (id_cliente)
REFERENCES cliente(id_cliente)
```

O DDL completo do banco está disponível em:

```text
database/ddl.sql
```

---

# 4. Estrutura do projeto

```text
projeto/
│
├── src/
│   └── main/
│       ├── java/
│       │   └── br/com/fiap/projeto/
│       │       ├── controller/
│       │       ├── entity/
│       │       ├── repository/
│       │       └── service/
│       │
│       └── resources/
│           └── application.properties
│
├── database/
│   └── ddl.sql
│
├── azure/
│   ├── 01-resource-group.sh
│   ├── 02-sql-server.sh
│   ├── 03-sql-database.sh
│   ├── 04-firewall.sh
│   ├── 05-webapp.sh
│   └── 06-deploy.sh
│
├── postman/
│   └── projeto-api.json
│
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

---

# 5. Entidades

## Cliente

A entidade `Cliente` representa os clientes cadastrados na aplicação.

Principais atributos:

```text
idCliente
nome
email
```

Tabela correspondente:

```text
cliente
```

---

## Pedido

A entidade `Pedido` representa os pedidos realizados pelos clientes.

Principais atributos:

```text
idPedido
dataPedido
valorTotal
cliente
```

Tabela correspondente:

```text
pedido
```

O atributo `cliente` representa o relacionamento `ManyToOne` com a entidade `Cliente`.

---

# 6. Endpoints da API

## Clientes

### Criar cliente

```http
POST /clientes
```

Exemplo:

```json
{
  "nome": "Maria Silva",
  "email": "maria.silva@email.com"
}
```

---

### Listar clientes

```http
GET /clientes
```

---

### Buscar cliente por ID

```http
GET /clientes/{id}
```

Exemplo:

```http
GET /clientes/1
```

---

### Atualizar cliente

```http
PUT /clientes/{id}
```

Exemplo:

```http
PUT /clientes/1
```

Body:

```json
{
  "nome": "Maria Silva Santos",
  "email": "maria.santos@email.com"
}
```

---

### Excluir cliente

```http
DELETE /clientes/{id}
```

Exemplo:

```http
DELETE /clientes/1
```

---

# 7. Pedidos

### Criar pedido

```http
POST /pedidos
```

Exemplo:

```json
{
  "dataPedido": "2026-10-03",
  "valorTotal": 150.00,
  "cliente": {
    "idCliente": 1
  }
}
```

---

### Listar pedidos

```http
GET /pedidos
```

---

### Buscar pedido por ID

```http
GET /pedidos/{id}
```

Exemplo:

```http
GET /pedidos/1
```

---

### Atualizar pedido

```http
PUT /pedidos/{id}
```

Exemplo:

```http
PUT /pedidos/1
```

Body:

```json
{
  "dataPedido": "2026-10-05",
  "valorTotal": 200.00,
  "cliente": {
    "idCliente": 1
  }
}
```

---

### Excluir pedido

```http
DELETE /pedidos/{id}
```

Exemplo:

```http
DELETE /pedidos/1
```

---

# 8. Configuração local

## Pré-requisitos

Para executar o projeto localmente, é necessário possuir:

* Java 21
* Maven ou Maven Wrapper
* Git
* Acesso ao Azure SQL Database

---

## Executando o projeto

Clone o repositório:

```bash
git clone <URL_DO_REPOSITORIO>
```

Entre na pasta:

```bash
cd projeto
```

No Windows, execute:

```powershell
./mvnw.cmd spring-boot:run
```

A aplicação será iniciada na porta:

```text
8080
```

A API estará disponível em:

```text
http://localhost:8080
```

---

# 9. Configuração do Azure SQL

O banco utilizado pelo projeto está hospedado no Azure SQL Database.

Informações utilizadas:

```text
Resource Group:
rg-projeto-rm550509

SQL Server:
sql-projeto-rm550509

Database:
projeto-db

Região:
Brazil South (brazilsouth)
```

A aplicação utiliza o driver JDBC da Microsoft para realizar a conexão com o Azure SQL.



---

# 10. Criação dos recursos no Azure

Os recursos foram criados utilizando a Azure CLI.

## Login/verificação

```bash
az account show --query "{name:name,id:id}" -o table
```

---

## Variáveis

```bash
RESOURCE_GROUP="rg-projeto-rm550509"
LOCATION="brazilsouth"
SQL_SERVER="sql-projeto-rm550509"
DATABASE="projeto-db"
SQL_ADMIN="sqladmin"
```

---

## Resource Group

```bash
az group create \
  --name $RESOURCE_GROUP \
  --location $LOCATION
```

---

## SQL Server

```bash
az sql server create \
  --name $SQL_SERVER \
  --resource-group $RESOURCE_GROUP \
  --location $LOCATION \
  --admin-user $SQL_ADMIN \
  --admin-password "Cr05049655"
```

---

## Criar banco

```bash
az sql db create \
  --resource-group $RESOURCE_GROUP \
  --server $SQL_SERVER \
  --name $DATABASE \
  --edition Free
```


---

# 11. Configuração do Firewall

Para permitir o acesso ao banco, foi criada uma regra para serviços do Azure:

```bash
az sql server firewall-rule create \
  --resource-group $RESOURCE_GROUP \
  --server $SQL_SERVER \
  --name AllowAzureServices \
  --start-ip-address 0.0.0.0 \
  --end-ip-address 0.0.0.0
```

Em caso de IP da máquina ser utilizada durante o desenvolvimento:

```bash
az sql server firewall-rule create \
  --resource-group $RESOURCE_GROUP \
  --server $SQL_SERVER \
  --name AllowMyComputer \
  --start-ip-address SEU_IP \ 
  --end-ip-address SEU_IP
```

---

# 12. Criação do App Service Plan

Em continuação foi criado um App Service Plan Linux utilizando a Azure CLI em ordem a hospedar a aplicação Java Spring Boot.:

```bash
Passo a passo resumido

1. Definir o Web App e runtime

WEBAPP_NAME="projeto-rm550509"
RUNTIME="JAVA:21-java21"
PLAN_NAME="plan-projeto-rm550509"

2. Criar o App Service Plan

az appservice plan create \
  --resource-group $RESOURCE_GROUP \
  --name $PLAN_NAME \
  --sku F1 \
  --is-linux

A primeira tentativa apresentou erro temporário de capacidade, mas a segunda execução do mesmo comando funcionou.

3. Criar o Web App

az webapp create \
  --resource-group $RESOURCE_GROUP \
  --plan $PLAN_NAME \
  --name $WEBAPP_NAME \
  --runtime "$RUNTIME"

4. Criar o Application Insights

APPINSIGHTS_NAME="appi-projeto-rm550509"

az monitor app-insights component create \
  --app $APPINSIGHTS_NAME \
  --location $LOCATION \
  --resource-group $RESOURCE_GROUP \
  --application-type web

5. Definir a senha do SQL

read -s SQL_PASSWORD

6. Configurar a conexão do Spring Boot com o Azure SQL

SQL_URL="jdbc:sqlserver://sql-projeto-rm550509.database.windows.net:1433;databaseName=projeto-db;encrypt=true;trustServerCertificate=false;loginTimeout=30;"

az webapp config appsettings set \
  --resource-group $RESOURCE_GROUP \
  --name $WEBAPP_NAME \
  --settings \
  SPRING_DATASOURCE_URL="$SQL_URL" \
  SPRING_DATASOURCE_USERNAME="$SQL_ADMIN" \
  SPRING_DATASOURCE_PASSWORD="$SQL_PASSWORD" \
  SPRING_JPA_HIBERNATE_DDL_AUTO="update"

7. Obter a conexão do Application Insights

APPINSIGHTS_CONNECTION_STRING=$(az monitor app-insights component show \
  --app $APPINSIGHTS_NAME \
  --resource-group $RESOURCE_GROUP \
  --query connectionString \
  --output tsv)

8. Configurar o Application Insights no Web App

az webapp config appsettings set \
  --resource-group $RESOURCE_GROUP \
  --name $WEBAPP_NAME \
  --settings \
  APPLICATIONINSIGHTS_CONNECTION_STRING="$APPINSIGHTS_CONNECTION_STRING"

9. Fazer o deploy do JAR (fazer o UPLOAD do JAR do projeto ANTES)

ls -lh projeto-0.0.1-SNAPSHOT.jar

az webapp deploy \
  --resource-group $RESOURCE_GROUP \
  --name $WEBAPP_NAME \
  --src-path ./projeto-0.0.1-SNAPSHOT.jar \
  --type jar
```


Após a criação, o endereço público da aplicação foi este:

```text
http://projeto-rm550509.azurewebsites.net
```

---

# 13. Configuração da conexão com o banco

As configurações da aplicação foram adicionadas ao Azure Web App através da Azure CLI.

Exemplo:

```bash
az webapp config appsettings set \
  --resource-group rg-projeto-rm550509 \
  --name projeto-rm550509 \
  --settings \
    SPRING_DATASOURCE_URL="jdbc:sqlserver://sql-projeto-rm550509.database.windows.net:1433;databaseName=projeto-db;encrypt=true;trustServerCertificate=false;loginTimeout=30;" \
    SPRING_DATASOURCE_USERNAME="sqladmin" \
    SPRING_DATASOURCE_PASSWORD="Cr05049655" \
    SPRING_JPA_HIBERNATE_DDL_AUTO="update"
```

---

# 14. Application Insights

Foi criado um recurso do Azure Application Insights para monitoramento da aplicação.

Nome:

```text
appi-projeto-rm550509
```

Criação através da Azure CLI:

```bash
az monitor app-insights component create \
  --app appi-projeto-rm550509 \
  --location brazilsouth \
  --resource-group rg-projeto-rm550509 \
  --application-type web
```

A conexão do Application Insights foi configurada no Web App através da variável:

```text
APPLICATIONINSIGHTS_CONNECTION_STRING
```

Essa configuração permite acompanhar informações de execução e monitoramento da aplicação no Azure.

---

# 15. Build da aplicação

Antes do deploy, o projeto foi compilado utilizando o Maven Wrapper:

```powershell
./mvnw.cmd clean package -DskipTests
```

O arquivo `.jar` gerado fica dentro da pasta:

```text
target/
```

Exemplo:

```text
target/projeto-0.0.1-SNAPSHOT.jar
```

---

# 16. Deploy no Azure Web App

Após gerar o `.jar`, o arquivo pode ser enviado para o Azure Web App através da Azure CLI:

```bash
az webapp deploy \
  --resource-group rg-projeto-rm550509 \
  --name projeto-rm550509 \
  --src-path ./projeto-0.0.1-SNAPSHOT.jar \
  --type jar
```

Após o deploy, o Azure realiza a inicialização da aplicação.

---

# 18. Testando a aplicação publicada

A API publicada pode ser acessada através de:

```text
http://projeto-rm550509.azurewebsites.net
```

Exemplo:

```http
GET http://projeto-rm550509.azurewebsites.net/clientes
```

Resposta esperada:

```json
[
  {
    "idCliente": 1,
    "nome": "Cristian",
    "email": "cristian@email.com"
  }
]
```

---

# 19. Persistência dos dados

Os dados enviados pela API são persistidos no Azure SQL Database.

Para verificar os clientes:

```sql
SELECT * FROM cliente;
```

Para verificar os pedidos:

```sql
SELECT * FROM pedido;
```

Para verificar o relacionamento entre as tabelas:

```sql
SELECT
    p.id_pedido,
    p.data_pedido,
    p.valor_total,
    c.id_cliente,
    c.nome AS cliente,
    c.email
FROM pedido p
INNER JOIN cliente c
    ON p.id_cliente = c.id_cliente;
```

Essa consulta demonstra a relação entre `cliente` e `pedido` através da chave estrangeira `id_cliente`.

---

# 20. Operações CRUD

A API possui as seguintes operações:

| Operação           | Método | Endpoint         |
| ------------------ | ------ | ---------------- |
| Criar cliente      | POST   | `/clientes`      |
| Consultar clientes | GET    | `/clientes`      |
| Consultar cliente  | GET    | `/clientes/{id}` |
| Atualizar cliente  | PUT    | `/clientes/{id}` |
| Excluir cliente    | DELETE | `/clientes/{id}` |
| Criar pedido       | POST   | `/pedidos`       |
| Consultar pedidos  | GET    | `/pedidos`       |
| Consultar pedido   | GET    | `/pedidos/{id}`  |
| Atualizar pedido   | PUT    | `/pedidos/{id}`  |
| Excluir pedido     | DELETE | `/pedidos/{id}`  |

UMA SEQUENCIA RESUMIDA DO MEU TESTE USANDO O POSTMAN E VERIFICANDO NO BANCO DE DADOS:
```sql
## 1. Cliente — POST
URL: 
http://projeto-rm550509.azurewebsites.net/clientes

Body:
{
  "nome": "Maria Silva",
  "email": "maria.silva@email.com"
}

Verificar no banco:

SELECT * FROM cliente;


## 2. Cliente — GET

Método: GET

URL:
http://projeto-rm550509.azurewebsites.net/clientes


## 3. Cliente — GET por ID

Método: GET

URL:
http://projeto-rm550509.azurewebsites.net/clientes/1


## 4. Cliente — PUT

Método: PUT

URL:
http://projeto-rm550509.azurewebsites.net/clientes/1

Body:
{
  "nome": "Maria Silva Santos",
  "email": "maria.santos@email.com"
}

Verificar no banco:

SELECT * FROM cliente
WHERE id_cliente = 1;


## 5. Pedido — POST

Método: POST

URL:
http://projeto-rm550509.azurewebsites.net/pedidos

Body:
{
  "dataPedido": "2026-10-03",
  "valorTotal": 150.00,
  "cliente": {
    "idCliente": 1
  }
}

Verificar no banco:

SELECT * FROM pedido;

Verificar relacionamento:

SELECT
    p.id_pedido,
    p.data_pedido,
    p.valor_total,
    c.id_cliente,
    c.nome AS cliente,
    c.email
FROM pedido p
INNER JOIN cliente c
    ON p.id_cliente = c.id_cliente;


## 6. Pedido — GET

Método: GET

URL:
http://projeto-rm550509.azurewebsites.net/pedidos


## 7. Pedido — GET por ID

Método: GET

URL:
http://projeto-rm550509.azurewebsites.net/pedidos/1


## 8. Pedido — PUT

Método: PUT

URL:
http://projeto-rm550509.azurewebsites.net/pedidos/1

Body:
{
  "dataPedido": "2026-10-03",
  "valorTotal": 200.00,
  "cliente": {
    "idCliente": 1
  }
}

Verificar no banco:

SELECT * FROM pedido
WHERE id_pedido = 1;


## 9. Pedido — DELETE

Método: DELETE

URL:
http://projeto-rm550509.azurewebsites.net/pedidos/1

Verificar no banco:

SELECT * FROM pedido;


## 10. Cliente — DELETE

Método: DELETE

URL:
http://projeto-rm550509.azurewebsites.net/clientes/1

Verificar no banco:

SELECT * FROM cliente;


## Sequência dos testes

POST  /clientes
GET   /clientes
GET   /clientes/1
PUT   /clientes/1

POST  /pedidos
GET   /pedidos
GET   /pedidos/1
PUT   /pedidos/1
DELETE /pedidos/1

DELETE /clientes/1
```
--- 
# 21. Conclusão

O projeto demonstra uma aplicação Java Spring Boot publicada no Azure App Service, utilizando o Azure SQL Database como banco de dados relacional.

A solução possui duas tabelas relacionadas por chave estrangeira, operações completas de CRUD, persistência dos dados em ambiente cloud, deployment através da Azure CLI e monitoramento através do Application Insights.

