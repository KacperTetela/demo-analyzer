# Counter-Strike 2 Dem Analyzer

**Status:** 🚧 In development / Active

Dem Analyzer is a comprehensive, modular web application designed for parsing, analyzing, and visualizing Counter-Strike 2 demo files (`.dem`). 

The system leverages a distributed architecture. It utilizes a Python-based microservice with the [AWPY](https://awpy.readthedocs.io/en/latest/index.html) library to extract detailed in-game events, which are then processed by a robust Java Spring Boot backend and visualized in a modern React frontend.

## 📸 Screenshots

Here is a glimpse of the application's interface:

### Authentication (Login & Registration)
<p align="center">
  

  <img width="886" height="423" alt="image" src="https://github.com/user-attachments/assets/c667e06f-cec1-434c-b98f-fa41cd399d22" width="45%" alt="Login Screen" />
  <img width="886" height="423" alt="image" src="https://github.com/user-attachments/assets/cae99f13-c9a3-4f40-905c-3d17fb930360" width="45%" alt="Registration Screen" />
</p>
<p align="center"><em>Secure authentication powered by Spring Security and dual JWT (Access & Refresh tokens).</em></p>

### User Account Panel
<img width="886" height="423" alt="image" src="https://github.com/user-attachments/assets/909fab20-78cd-4b15-977d-ceefc44f023c" />

*Manage your credentials securely, including email and password updates.*

### Main Menu & Uploading Demos
![Upload Menu](docs/images/upload.png)
*Intuitive Drag & Drop interface for uploading `.dem` files directly to the parser.*

### Demo History
![Demo History](docs/images/history.png)
*Personalized history panel tracking all previously analyzed matches.*

### Analyzed Demo Dashboard
![Analyzed Demo](docs/images/dashboard.png)
*Advanced match dashboard presenting crucial e-sports metrics such as Entry Kills, Trade Kills, Clutches, KAST, and ADR to evaluate tactical roles and player performance.*

## ✨ Features

- **Advanced Match Analytics:** Extracts high-level metrics (First kill detection, 1vX Clutch detection, Trade kills, KAST, ADR) that go beyond the standard CS2 scoreboard.
- **Secure Authentication:** Dual-token JWT system (Access + Refresh tokens) combined with a server-side token blacklist.
- **User Management:** Registration, login, and secure credential updates.
- **Match History:** Dedicated, isolated history for each user to review past games.
- **Modular DDD Architecture:** Built strictly using Domain-Driven Design (DDD) and Hexagonal Architecture (Ports and Adapters) for high maintainability and scalability.
- **Hybrid Database Approach:** Complex match statistics are stored as `JSONB` documents in PostgreSQL to drastically optimize read performance.

## 🛠️ Technologies

**Backend (Core API):**
- Java 17+
- Spring Boot 3 & Spring Security
- Spring Data JPA
- PostgreSQL (Relational + JSONB structures)
- Maven

**Parser Microservice:**
- Python 3+
- FastAPI
- [AWPY](https://awpy.readthedocs.io/en/latest/index.html) (CS2 parser)

**Frontend:**
- React (Single Page Application)
- JavaScript, HTML, CSS

**Infrastructure & Deployment:**
- Docker & Docker Compose
- Coolify (CI/CD)

---

**Note:** This project is actively developed. Functionality is being continuously expanded and tested. Feel free to explore the code or open issues!
