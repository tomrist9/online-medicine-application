# 💊 Online Medicine Application

A **microservices-based backend system** for an online pharmacy, built with Java and Spring Boot following **Clean Architecture**, **Hexagonal Architecture**, and **Domain-Driven Design (DDD)** principles. This project was developed as part of the Udemy course [*Microservices: Clean Architecture, DDD, SAGA, Outbox & Kafka*](https://www.udemy.com/course/microservices-clean-architecture-ddd-saga-outbox-kafka/).

## 🚀 Features

- ✅ Microservices-based backend (Order, Pharmacy, Customer, Payment)
- ✅ Event-driven communication using **Apache Kafka**
- ✅ Domain-driven business logic using **DDD principles**
- ✅ **Clean Architecture** and **Hexagonal Architecture** structure
- ✅ **SAGA** pattern for distributed transaction management
- ✅ **Outbox Pattern** for reliable message delivery
- ✅ **CQRS Pattern** in selected use cases
- ✅ PostgreSQL for data persistence
- ✅ Docker + Docker Compose for containerization
- ✅ Kubernetes deployment support

## 🧱 Microservices Included

| Service   | Description |
|-----------|-------------|
| **Order Service** | Handles medicine orders, integrates with payment and pharmacy |
| **Customer Service** | Manages customer data and credit history |
| **Pharmacy Service** | Stores medicine and pharmacy inventory |
| **Payment Service** | Processes payments, uses SAGA and Outbox patterns |

## 📦 Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **Spring Kafka**
- **PostgreSQL**
- **Kafka (Confluent Platform)**
- **Docker & Docker Compose**
- **Kubernetes (Local & GKE-ready)**

## ⚙️ Architecture Patterns

- 🧼 **Clean Architecture**
- 🛠️ **Hexagonal Architecture (Ports & Adapters)**
- 🧠 **Domain-Driven Design (DDD)**
- 🔁 **SAGA Pattern**
- 📤 **Outbox Pattern**
- ⚔️ **CQRS Pattern**

## 📂 Project Structure

```bash
online-medicine-application/
├── customer-service/
├── pharmacy-service/
├── order-service/
├── payment-service/
├── common/                # Shared libraries and DTOs
├── docker-compose.yml
├── k8s/                   # Kubernetes manifests
└── README.md
## 🧪 How to Run

### Clone the Repository

```bash
git clone https://github.com/tomrist9/online-medicine-application.git
cd online-medicine-application

bash
Copy
Edit
docker-compose up -d
Run Microservices
Each microservice can be run independently from its module using your IDE or via:

bash
Copy
Edit
./gradlew :order-service:bootRun
Test Kafka Events
Kafka UI is available at:
📍 http://localhost:8081


🛠️ Still in Progress
Planned improvements:

✅ Unit & Integration tests with JUnit and Testcontainers

✅ CI/CD setup

✅ API documentation with Swagger

✅ Kubernetes Helm chart

✅ Observability stack (Prometheus, Grafana, Tempo)


Tomris Teymurlu
 ✉️tomristt9@gmail.com

⭐ If you find this project helpful, please give it a star and share feedback. Contributions are welcome!
