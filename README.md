# Dairy-Farm-Management-System

A comprehensive and high-performance desktop management suite designed to modernize dairy farm operations. This system replaces traditional paper-based tracking with a secure, digital platform that provides real-time visibility into animal health, production metrics, and financial performance.

## 💻 Tech Stack
![Java 18](https://img.shields.io/badge/java-18-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-18-blue?style=for-the-badge&logo=java&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white)

## ✨ Features
- **Dashboard Analytics**: Real-time visualization of farm statistics, including animal counts, today's sales, and earnings trends.
- **Animal Management**: Complete tracking of livestock (Cows, Bulls, Calves) including breed details, routines, and physical monitoring.
- **Health & Monitoring**: Track animal health scores, breathing, weight, and vaccination schedules to ensure optimal productivity.
- **Milk Production**: Record milk collection sessions (morning/evening) and manage bulk milk sales.
- **Financial Tracking**: Comprehensive management of purchases (feed, medicine, equipment) and animal/milk sales with client/supplier logs.
- **Employee & User Management**: Secure HR module to manage staff details, salaries, and system access levels.
- **Reporting & Export**: Generate professional reports in PDF and Excel formats for sales, animal inventories, and employee lists.

## ⚙️ Installation

### Prerequisites
- **JDK 18** or higher
- **Maven** 3.8+
- **MySQL 8.0**

### Standard Setup
1. **Database**: Import the `dairyfarm.sql` file into your MySQL server.
2. **Configuration**: Set your database credentials as environment variables (`DB_USER`, `DB_PASSWORD`) or ensure your local MySQL has `root` with no password.
3. **Build**:
   ```bash
   mvn clean package
   ```
4. **Run**:
   ```bash
   java -jar target/Dairy_Farm_Management_System-1.0-SNAPSHOT.jar
   ```

### Docker Setup (Fastest)
Run the entire stack (App + DB) with one command:
```bash
docker-compose up --build
```

## 👨🏻‍💻 Contributors
* ABDELLATIF LAGHJAJ
* OMAR LAMINE
* MARYAM BOUCHHAR
* NAIMA ADARDOUR
