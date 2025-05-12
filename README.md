# calculator-api

Este projeto consiste em um microsserviço de calculadora desenvolvido em **Java com Spring Boot**, com foco em boas práticas de desenvolvimento, testes automatizados, logging, e conteinerização com Docker.

## \:gear: Funcionalidades

* Operações matemáticas básicas: soma, subtração, multiplicação e divisão
* Persistência de histórico de cálculos em banco de dados (PostgreSQL)
* Exceções tratadas com mensagens claras e apropriadas
* Testes unitários e de integração implementados
* Logs com configuração via `logback`

## \:rocket: Tecnologias Utilizadas

* Java 21
* Spring Boot 3
* Gradle
* PostgreSQL
* Docker / Docker Compose
* JUnit 5 e Mockito

## \:package: Estrutura do Projeto

```
calculator/
├── src/
│   ├── main/java/com/paulo/calculator/
│   │   ├── controllers/
│   │   ├── services/
│   │   ├── repositories/
│   │   ├── dtos/
│   │   ├── entities/
│   │   └── enums/
│   ├── test/java/com/paulo/calculator/
│       ├── integration/
│       └── services/
├── resources/
│   ├── application.yml
│   └── logback-spring.xml
├── build.gradle.kts
├── docker-compose.yml
└── Dockerfile
```

## \:runner: Como Executar o Projeto

### Usando Docker (recomendado)

```bash
docker-compose up --build
```

A aplicação estará acessível em: `http://localhost:8080`

### Sem Docker

1. Configure o PostgreSQL localmente
2. Altere o `application.yml` com suas credenciais
3. Execute:

```bash
./gradlew bootRun
```

## \:test\_tube: Executando os Testes

```bash
./gradlew test
```

## \:triangular\_ruler: Exemplos de Uso (via Postman ou cURL)

### Requisição

```json
POST /calculator
{
  "operand1": 10,
  "operand2": 5,
  "operation": "SUBTRACTION"
}
```

### Resposta

```json
{
  "result": 5.0
}
```

## \:memo: Autor

Paulo Roberto Barbosa de França

`p.barbosa088@gmail.com`
