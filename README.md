# 🏦 NexaBank ATM - Java Spring Boot Banking System

A full-stack ATM simulation built with **Java Spring Boot** and a modern dark-themed UI.

---

## 📋 Features
- ✅ Create Bank Account
- ✅ Secure PIN Login
- ✅ Check Balance
- ✅ Deposit Money
- ✅ Withdraw Money
- ✅ Fund Transfer between accounts
- ✅ Transaction History (last 10 transactions)
- ✅ 3 Pre-loaded Demo Accounts

---

## 🛠️ Tech Stack
| Layer      | Tech                      |
|------------|---------------------------|
| Backend    | Java 17 + Spring Boot 3.2 |
| Frontend   | HTML + CSS + Vanilla JS   |
| Storage    | In-Memory (HashMap)       |
| Build Tool | Maven                     |

---

## 🚀 How to Run Locally

### Prerequisites
- Java 17+ installed → [Download](https://adoptium.net/)
- Maven installed → [Download](https://maven.apache.org/download.cgi)

### Steps
```bash
# 1. Navigate to the project folder
cd banking-system

# 2. Build the project
mvn clean install

# 3. Run the application
mvn spring-boot:run
```

### Then open your browser:
```
http://localhost:8080
```

---

## 🎯 Demo Accounts (pre-loaded)

| Name         | Account No | PIN  | Balance    |
|--------------|------------|------|------------|
| Uday Sharma  | 10010001   | 1234 | ₹50,000.00 |
| Priya Patel  | 10010002   | 5678 | ₹25,000.00 |
| Rohan Mehta  | 10010003   | 9999 | ₹10,000.00 |

---

## 🌐 Hosting on Render

Link- https://nexabank-17wp.onrender.com/

---

## 📁 Project Structure
```
banking-system/
├── pom.xml                          ← Maven build config
└── src/main/
    ├── java/com/bank/
    │   ├── BankingApplication.java  ← Entry point
    │   ├── model/
    │   │   ├── Account.java         ← Account model
    │   │   └── Transaction.java     ← Transaction model
    │   ├── service/
    │   │   └── BankingService.java  ← Business logic
    │   └── controller/
    │       └── BankingController.java ← REST API
    └── resources/
        ├── application.properties
        └── static/
            └── index.html           ← Frontend UI
```

---

## 🔗 API Endpoints

| Method | Endpoint                          | Description        |
|--------|-----------------------------------|--------------------|
| POST   | /api/account/create               | Create account     |
| POST   | /api/account/login                | Login              |
| GET    | /api/account/balance/{accNo}      | Check balance      |
| POST   | /api/transaction/deposit          | Deposit money      |
| POST   | /api/transaction/withdraw         | Withdraw money     |
| POST   | /api/transaction/transfer         | Transfer funds     |
| GET    | /api/transaction/history/{accNo}  | Transaction history|
