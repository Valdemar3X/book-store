![Book Store](assets/book-store.png)
[Watch the demo video](assets/demo.mp4)
📝 Project description:
This application was implemented for the operation of an online bookstore. The primary goal of the project is to provide users with a user-friendly platform to browse, select, and purchase books while offering administrators the tools necessary for efficient store management.

🎯 Features:
Role Division:
1. Customer (User)
   Sign up and sign in to the store.
   Browse all book categories or select by category.
   View detailed information about each book.
   Add or remove books from their shopping cart.
   View the contents of the shopping cart.
   Purchase all the books in the cart.
   View past orders and order details.
   View each book from a specific order in detail.
2. Manager (Admin)
   Add and remove books from the store.
   Update book details, ensuring accurate and current information.
   Create and delete book categories for effective organization.
   Update category details as needed.
   Update order status to reflect stages such as "pending" or "delivered."
   📂 File Structure:
   .
   ├── .github/workflows        
   ├── .mvn/wrapper             
   ├── src                      # Source files
   │   ├── main
   │   │   ├── java
   │   │   │   └── project
   │   │   │       └── mybookshop
   │   │   │           ├── config          # Configuration classes
   │   │   │           ├── controller      # Request handling controllers
   │   │   │           ├── dto             # Data Transfer Objects
   │   │   │           ├── exceptions      # Custom exception classes
   │   │   │           ├── mapper          # Mapping between entities and DTOs
   │   │   │           ├── model           # Entity classes representing database tables
   │   │   │           ├── repository      # Repository interfaces for database interactions
   │   │   │           ├── security        # Security configurations and components
   │   │   │           ├── service         # Business logic and service classes
   │   │   │           ├── validation      # Custom validation logic and annotations
   │   │   │           └── MyBookShopApplication.java  # Main application class
   │   │   ├── resources
   │   │   │   └── db
   │   │   │       └── changelog
   └── changes                
   │   │   │           └── db.changelog-master.yaml   # Liquibase changelog file
   │   │   │       └── application.properties  # Configuration properties for the application
   │   ├── test
   │   │   └── java
   │   │       └── project.mybookshop      # Test cases for my project  
   │   │           ├── config          
   │   │           ├── controller      
   │   │           ├── service      
   │   │           ├── repository
   │   │           ├── util
   │   │           └── MyBookShopApplication.java  
   │   │── resources
   │   │       └── database            # Database query files for tests
   │   │   └── application.properties  # Configuration properties for test cases
   │   │               
   ├── .env                     # Environment configuration file
   ├── .gitignore               
   ├── Dockerfile               # Docker configuration file
   ├── README.md                # Project documentation
   ├── checkstyle.xml           # Checkstyle configuration file
   ├── docker-compose.yml       # Docker Compose configuration file
   ├── mvnw                     
   ├── mvnw.cmd                 
   ├── pom.xml                  
   💻 Technologies:
   Java v.17
   Spring Boot v.3.2.3
   Spring Data JPA v.3.2.3
   Maven v.4.0.0
   Lombok v.1.18.30
   MapStruct 1.5.5.Final
   Docker v.25.0.3
   Swagger
   MySql v.8.3.0
   H2 Database v.2.2.224
   Spring Security v.3.2.3
   Mockito Test
   Postman
   Liquibase v.4.24.0
   JWT (JSON Web Token) v.0.11.5
   ⚙ Installation
   To use Online Book Shop application locally, follow these steps:

Prerequisites:
Make sure you have the following installed on your machine:

Java Development Kit (JDK) - Version 17 or higher
Maven - A software project management and comprehension tool
Clone the Repository:
HTTPS:

git clone https://github.com/Valdemar3X/book-store
Configure Database:
Open src/main/resources/application.properties file and update following properties with your database connection details:

spring.datasource.url=jdbc:mysql://localhost:3306/YOUR_DATABASE_NAME
spring.datasource.username=USERNAME
spring.datasource.password=PASSWORD
Run the Application!
The application will start and you can access it by navigating to http://localhost:8080/
