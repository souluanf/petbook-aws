# 🐾 Petbook – Integração com AWS, Observabilidade e Arquitetura Moderna


[![Java 21](https://img.shields.io/badge/Java-21-red)](https://openjdk.org/projects/jdk/21/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.5-6DB33F?logo=spring)](https://spring.io/projects/spring-boot)
[![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

---

## 📘 Sobre o projeto

O **Petbook** é uma prova de conceito desenvolvida para demonstrar integrações modernas com a AWS usando Java e Spring Boot, com foco em mensageria assíncrona, rastreamento de eventos e observabilidade completa. A aplicação simula um backend de sistema de gestão de pets, ideal para estudos, testes arquiteturais e apresentações técnicas.

A infraestrutura local é toda provisionada com Docker e LocalStack, permitindo explorar diversos serviços da AWS sem custos, em ambiente de desenvolvimento.

---

## ⚙️ Tecnologias e Ferramentas

### Backend
- **Java 21**, **Spring Boot 3.4.5**
- Spring Web, Validation, Scheduling
- AWS SDK v2 (DynamoDB, S3, SNS, SQS, Secrets Manager)
- Spring Cloud AWS (`io.awspring.cloud`)
- RestClient (HTTP client do Spring 6)
- MapStruct, Lombok

### Observabilidade
- Prometheus (coleta de métricas)
- Grafana (dashboards)
- Loki + Promtail (centralização de logs)
- Tempo (tracing distribuído)
- Spring Actuator
- JaCoCo (análise de cobertura)

### Integrações externas
- OpenCep (consulta de endereço)
- Mailhog (simulação de SMTP)

### Infraestrutura
- Docker e Docker Compose
- LocalStack (simulação local da AWS)
- Health checks e bootstrap automático de recursos (tópicos, filas, buckets)

---

## 🧪 Funcionalidades implementadas

- Cadastro e persistência de pets no DynamoDB
- Disparo e consumo de eventos via SNS + SQS
- Upload e leitura de imagens no S3
- Consulta de CEP via OpenCep
- Envio de e-mails simulados com Mailhog
- Métricas em tempo real com Prometheus e Grafana
- Logs centralizados com Loki
- Tracing completo com Grafana Tempo

---

## 🧩 Funcionalidades futuras e melhorias planejadas

Este projeto segue em evolução. Abaixo, as tecnologias e refatorações previstas para as próximas versões:

### Integrações e melhorias técnicas
- Keycloak para autenticação e controle de acesso (RBAC)
- Resilience4j para tolerância a falhas (circuit breaker, retry, etc.)
- Redis como cache distribuído
- PostgreSQL como banco relacional
- Testcontainers para testes de integração com containers reais
- GitHub Actions para CI/CD
- SonarCloud para análise contínua de qualidade
- Testes de API com Rest-Assured + JUnit + Mockito
- Integração com Cucumber e Gherkin (avaliando uso de BDD)
- WireMock para simulação de serviços externos

### Refatorações planejadas
- Reorganização da estrutura de pacotes
- Separação clara por domínios e camadas
- Modularização para maior escalabilidade
- Aumento da cobertura de testes e validações

---

## ▶️ Executando localmente

### Pré-requisitos

- Docker e Docker Compose instalados

### Subindo a infraestrutura

```bash
cp .env.example .env
```

```bash
docker-compose up -d
```

---

## 📎 Documentação da API

Acesse a interface do Swagger para explorar os endpoints disponíveis:

- [http://localhost:8080/petbook/swagger-ui](http://localhost:8080/petbook/swagger-ui)

---

## 🌐 Interfaces e Serviços

| Serviço         | Endereço                                                                             |
|-----------------|--------------------------------------------------------------------------------------|
| Swagger UI      | [http://localhost:8080/petbook/swagger-ui](http://localhost:8080/petbook/swagger-ui) |
| Mailhog         | [http://localhost:8025](http://localhost:8025)                                       |
| Prometheus      | [http://localhost:9090](http://localhost:9090)                                       |
| Grafana         | [http://localhost:3000](http://localhost:3000) (login: `admin` / `admin`)            |
| Tempo (Tracing) | [http://localhost:3200](http://localhost:3200)                                       |
| Loki (Logs)     | [http://localhost:3100](http://localhost:3100)                                       |

---

## 👤 Autor

**Luan Fernandes**

- [LinkedIn](https://linkedin.com/in/souluanf)
- [GitHub](https://github.com/souluanf)
- [luanfernandes.dev](https://luanfernandes.dev)

---

## 📄 Licença

Este projeto é open-source, licenciado sob a licença [MIT](LICENSE).