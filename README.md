# 🧪 TESTE TÉCNICO DESTAXA

## 🚀 RESOLUÇÃO DO DESAFIO TÉCNICO

### Arquitetura proposta:

- ✅ **API REST em Spring Boot**, responsável por enviar pedidos de autorização de pagamento.
- ✅ **Servidor Socket (Autorizador)**, simula uma adquirente e responde (ou não) com base nas regras do protocolo ISO8583.
- ✅ Comunicação assíncrona via TCP/IP entre a API e o Autorizador.
- ✅ Foco em testar concorrência e estrutura do código.

## 🔹 Módulo 1: `autorizador` (Java puro)

### 🧠 Responsabilidades:
- Escutar conexões TCP usando `ServerSocket` na porta `8583`.
- Ler mensagens no formato simulado ISO8583:  
  `0200|valor|...`
- Aplicar as seguintes regras de resposta:
  - `0210|000|Autorizado` → valor **par**
  - `0210|051|Negado` → valor **ímpar**
  - **Sem resposta (timeout)** → valor **maior que 1000**
- Tratar múltiplas conexões simultâneas usando `ExecutorService` (Thread Pool).

### ⚙️ Tecnologias e Requisitos:
- Linguagem: **Java 17**
- Sem frameworks externos (sem Spring)
- Dependências no `pom.xml`: **nenhuma**

### 📄 Arquivos principais:
- `AutorizadorApplication.java`: inicia o servidor socket.
- `ServerSocketHandler.java`: escuta conexões e distribui para threads.
- `ClientHandler.java`: processa cada requisição e aplica as regras de resposta.

---

## 🔹 Módulo 2: `api-pagamento` (Spring Boot)

### 🧠 Funcionalidades:
- Expor endpoint:  
  `POST /authorization`
- Receber JSON com dados do pagamento.
- Converter dados em uma mensagem ISO8583 simulada.
- Abrir conexão socket com o autorizador.
- Enviar a mensagem e aguardar resposta (ou timeout).
- Retornar resposta formatada em JSON contendo:  
  `response_code`, `authorization_code`, `transaction_date`, etc.

### ⚙️ Tecnologias e Dependências:
- Framework: **Spring Boot**
- Dependências:
  - `spring-boot-starter-web`
  - `jackson`
  - (Opcional) `lombok`

### 📄 Arquivos principais:
- `PagamentoController.java`: expõe o endpoint da API.
- `PagamentoService.java`: implementa a lógica de comunicação com o autorizador.
- `PagamentoRequest.java` e `PagamentoResponse.java`: DTOs para request/response.

## 🔁 Comunicação entre os módulos

1. A **API** cria um `Socket` para `localhost:8583`.
2. Envia a mensagem formatada com os dados da transação.
3. Lê a resposta ou aguarda até o timeout.
4. Retorna um **JSON** ao cliente com as informações da transação.

## ✅ Objetivo alcançado

A solução simula com fidelidade a comunicação entre um sistema de pagamento e um autorizador externo, aplicando regras de negócio com concorrência, arquitetura em camadas e boas práticas de código.

---

