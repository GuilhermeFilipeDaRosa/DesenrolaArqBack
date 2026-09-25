# DesenrolaArq — Backend

Backend da plataforma **DesenrolaArq**, uma aplicação de automação de processos de recrutamento e análise de currículos.

O sistema permite que o recrutador cadastre vagas com critérios específicos, receba currículos dos candidatos e, futuramente, utilize recursos de **OCR e Inteligência Artificial** para extrair informações, analisar a compatibilidade do candidato com a vaga e automatizar etapas do processo seletivo.

## 🚀 Tecnologias

* **Java 21**
* **Spring Boot 4**
* **Spring Web**
* **Spring Data JPA**
* **Hibernate**
* **PostgreSQL**
* **Maven**
* **REST API**
* **Git / GitHub**

## 🏗️ Arquitetura

O projeto utiliza uma arquitetura baseada em camadas, separando as responsabilidades da aplicação:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

### Principais responsabilidades

* **Controller** — recebe as requisições HTTP e retorna as respostas da API.
* **Service** — concentra as regras de negócio.
* **Repository** — realiza a comunicação com o banco de dados através do JPA/Hibernate.
* **Entity** — representa as entidades persistidas no banco.

## 📂 Estrutura do projeto

```text
src/
└── main/
    ├── java/
    │   └── br/
    │       └── com/
    │           └── desenrolaarq/
    │               ├── controller/
    │               ├── service/
    │               ├── repository/
    │               ├── entity/
    │               └── DesenrolaArqApplication.java
    │
    └── resources/
        └── application.properties
```

## ⚙️ Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

* Java 21 ou superior
* Maven
* PostgreSQL
* Git

## 🗄️ Banco de dados

O projeto utiliza **PostgreSQL**.

Crie um banco de dados:

```sql
CREATE DATABASE desenrolaarq;
```

Configure as informações de conexão no arquivo:

```text
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/desenrolaarq
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> **Importante:** não envie senhas, tokens ou outras credenciais para o GitHub. Utilize variáveis de ambiente para informações sensíveis.

## ▶️ Executando o projeto

Clone o repositório:

```bash
git clone https://github.com/GuilhermeFilipeDaRosa/DesenrolaArq.git
```

Entre na pasta do projeto:

```bash
cd DesenrolaArq
```

Execute utilizando o Maven:

```bash
mvn spring-boot:run
```

Ou, no Windows:

```bash
mvnw.cmd spring-boot:run
```

Por padrão, a API estará disponível em:

```text
http://localhost:8081
```

## 🔌 API REST

### Vagas

#### Listar vagas

```http
GET /api/vagas
```

Retorna todas as vagas cadastradas.

#### Buscar vaga

```http
GET /api/vagas/{id}
```

Exemplo:

```http
GET /api/vagas/1
```

#### Criar vaga

```http
POST /api/vagas
```

Exemplo de requisição:

```json
{
  "titulo": "Desenvolvedor Java",
  "descricao": "Desenvolvimento e manutenção de aplicações backend.",
  "criterios": "Java, Spring Boot, PostgreSQL e APIs REST",
  "instrucaoIa": "Avalie a experiência do candidato com Java e desenvolvimento backend.",
  "notaCorte": 7.5,
  "status": "ATIVA"
}
```

#### Atualizar vaga

```http
PUT /api/vagas/{id}
```

Permite atualizar informações da vaga, como:

* título;
* descrição;
* critérios;
* instrução para análise;
* nota de corte;
* status.

Os status disponíveis são:

```text
ATIVA
INATIVA
```

#### Enviar currículo

```http
POST /api/vagas/{vagaId}/curriculos
```

A requisição deve utilizar `multipart/form-data`.

Campo esperado:

```text
arquivo
```

Exemplo:

```text
POST /api/vagas/1/curriculos
Content-Type: multipart/form-data
```

O currículo enviado será associado à vaga correspondente.

## 📄 Fluxo de análise de currículo

O fluxo planejado para a plataforma é:

```text
Recrutador
    ↓
Cadastro da vaga
    ↓
Definição dos critérios
    ↓
Upload do currículo
    ↓
Cadastro do candidato
    ↓
OCR
    ↓
Extração das informações
    ↓
Análise por IA
    ↓
Pontuação do candidato
    ↓
Comparação com nota de corte
    ↓
Notificação ao recrutador
```

## 🤖 Inteligência Artificial

A plataforma foi projetada para utilizar Inteligência Artificial na análise dos currículos.

A vaga possui uma instrução específica para a IA:

```text
instrucaoIa
```

Além disso, existe uma nota de corte:

```text
notaCorte
```

Exemplo:

```text
Nota de corte: 7.5
```

A análise poderá retornar uma estrutura padronizada contendo informações como:

```json
{
  "nota": 8.5,
  "aderencia": true,
  "justificativa": "Candidato possui experiência compatível com os principais critérios da vaga."
}
```

## 🔎 OCR

Uma das funcionalidades planejadas é a utilização de **OCR (Optical Character Recognition)** para extrair automaticamente informações dos currículos enviados em PDF.

Entre os dados que poderão ser identificados estão:

* nome;
* e-mail;
* telefone;
* endereço;
* formação;
* experiências profissionais;
* tecnologias;
* certificações.

Essas informações serão utilizadas para realizar o cadastro automático do candidato e posteriormente alimentar o processo de análise.

## 🔐 Segurança

Informações sensíveis, como:

* senhas;
* chaves de API;
* tokens;
* credenciais de banco;

não devem ser armazenadas diretamente no código-fonte ou versionadas no Git.

A aplicação deve utilizar variáveis de ambiente ou mecanismos equivalentes para essas informações.

## 🧪 Testes

Para executar os testes:

```bash
mvn test
```

Ou:

```bash
mvnw.cmd test
```

## 🔄 Integração com o Frontend

O backend fornece uma API REST consumida pelo frontend do DesenrolaArq.

Arquitetura atual:

```text
┌──────────────────────┐
│   React + TypeScript │
│       Frontend       │
└──────────┬───────────┘
           │
           │ HTTP / REST
           ↓
┌──────────────────────┐
│     Spring Boot      │
│       Backend        │
└──────────┬───────────┘
           │
           │ JPA / Hibernate
           ↓
┌──────────────────────┐
│      PostgreSQL      │
└──────────────────────┘
```

## 🎯 Objetivo do projeto

O **DesenrolaArq** está sendo desenvolvido como um projeto de portfólio com foco em:

* desenvolvimento de APIs REST;
* Java e Spring Boot;
* persistência de dados;
* integração entre frontend e backend;
* processamento de documentos;
* OCR;
* Inteligência Artificial;
* automação de processos;
* boas práticas de desenvolvimento de software.

O projeto também busca demonstrar a aplicação prática de tecnologias utilizadas em sistemas corporativos.

## 👨‍💻 Autor

**Guilherme Filipe da Rosa**

Desenvolvedor Full Stack com experiência em desenvolvimento Backend, Frontend e Mobile.

Tecnologias de interesse:

```text
Java • Spring Boot • PHP • JavaScript • TypeScript
React • Vue.js • Kotlin • SQL • REST APIs
```

---

### Status do projeto

🚧 **Em desenvolvimento**

Novas funcionalidades estão sendo implementadas gradualmente, incluindo processamento de currículos, OCR, análise por IA e automação do processo seletivo.
