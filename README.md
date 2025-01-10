# Product Management API Project 📦

This project is a **REST API for Product Management** designed to handle CRUD operations for products, alongside advanced features such as filtering, pagination, reporting, and user access control.

### 🚀 Technologies Used

- Java 21
- Spring Boot 3.4.0
- MySQL
- Docker
- Swagger (OpenAPI)
- Email Sending (MailTrap)
- JWT for Authentication
- Heroku (Production Deployment)

### 🛠️ Features

1. **Product CRUD**: Add, view, edit, and delete products.
2. **User CRUD**: Add, view, edit, and delete user accounts.
3. **Advanced Listing**: Pagination, sorting, and multiple filters.
4. **Access Control**: Two levels: Administrator and Stockist.
5. **Auditing**: Track product changes with detailed history.
6. **Reports**: Generate reports in **CSV** or **XLSX** format.
7. **Financial Summary**: Calculate product costs and sales values.
8. **Documentation**: Fully documented API using Swagger.
9. **Unit Testing**: Coverage for critical functionalities.

### 🚨 Important Notes

- The system integrates with an email service to send system updates. However, the free version of MailTrap only allows emails to be sent to my personal email. This makes it challenging for external testers to validate this feature.

---

### 🐳 How to Run Locally with Docker

1. Ensure **Docker** is installed on your machine.
2. Clone this repository:
   ```bash
   git clone https://github.com/your-repo/product-management-api.git
   ```
3. Navigate to the project directory:
   ```bash
   cd product-management-api
   ```
4. Start the environment with Docker Compose:
   ```bash
   docker-compose up
   ```
   This will:
   - Initialize **MySQL**.
   - Launch the application on port `8080`.

5. Access the application:
   - **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

### 🌐 Production Deployment Access

The project is also hosted on **Heroku**. You can access it via the link below:

👉 **[Heroku Link](https://rita-product-management-763d144bcf95.herokuapp.com/api/v1/swagger-ui/index.html?urls.primaryName=public)**

---

### 🧪 Testing

To run the functionality tests, use the first created user of each type:

```bash
ADMIN: {
  "username": "admin",
  "password": "Admin@123"
}

STOCKIST: {
  "username": "stockist",
  "password": "Stockist@123"
}
```

---

### 🔧 Manual Configuration Requirements

If you prefer not to use Docker, you can manually set up the environment:

1. **Database**:
   - Install MySQL.
   - Create a database named `products-management`.
   - Execute the SQL script available in the repository (`scripts/schema.sql`).

2. **Project Configuration**:
   - Update the `application.properties` file with your database credentials.

3. **Running the Project**:
   - Compile the project:
     ```bash
     ./mvnw clean install
     ```
   - Start the application:
     ```bash
     ./mvnw spring-boot:run
     ```

### Improvements for the Project

1. **Implement Annotations for Auditing**:  
   Use annotations like `@CreatedBy`, `@CreatedDate`, `@LastModifiedBy`, and `@LastModifiedDate` to automate the tracking of entity metadata.

2. **Restrict Product Creation to Admin**:  
   Ensure that only administrators can add new products, as stockists should not have permission to modify certain fields.

3. **Access Rule Enforcement for Stockists**:  
   Guarantee restricted access for stockists to limited data fields in the report endpoint.

4. **User Name Limitation per LGPD**:  
   Limit the exposure of user names to comply with LGPD (Brazilian General Data Protection Law) regulations.

5. **Handle Jackson Time Serialization/Deserialization Issues**:  
   Resolve serialization and deserialization issues caused by `JacksonTime`.

6. **Simplify GetProductListUseCase Logic**:  
   After resolving the `JacksonTime` issue, remove the need for mapping the `Product` object in the customizable method within the `GetProductListUseCase` class.

7. **Fix Dependency Vulnerabilities**:  
   Address four reported vulnerabilities in project dependencies to improve security.

8. **Expand Unit Test Coverage**:  
   Add more unit tests beyond the primary scope of the project to ensure better coverage and reliability.

9. **Implement Containerized Tests**:  
   Use container-based environments for testing (e.g., using Docker) to better replicate production setups.

10. **Add Controller Tests**:  
    Write and implement tests for all controller endpoints to ensure the correctness of API behavior.

These enhancements will improve the robustness, security, and maintainability of the project while aligning it with best practices.

### ✨ Developed by Rita Ferreira 💻

Development time tracked using the [WakaTime](https://wakatime.com/@018bed01-1668-43dc-aef1-b064cc5ec137/projects/cmhnjibeay?start=2024-12-29&end=2025-01-04) app integrated with IntelliJ IDEA.