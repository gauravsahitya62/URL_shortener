# URL Shortener - Spring Boot Application

A modern, full-featured URL shortener application built with Spring Boot. Transform long URLs into short, shareable links with click tracking and statistics.

## ✨ Features

- **URL Shortening**: Convert long URLs into short, manageable links
- **Click Tracking**: Monitor how many times each shortened URL has been accessed
- **Statistics**: View creation date and click count for each shortened URL
- **Modern UI**: Beautiful, responsive web interface
- **REST API**: Full RESTful API for programmatic access
- **In-Memory Database**: Uses H2 database for easy setup and demo

## 🚀 Quick Start

### Prerequisites

- Java 17 or higher
- Maven 3.6+ (or use Maven Wrapper)

### Installation & Running

1. **Clone the repository**
   ```bash
   git clone https://github.com/gauravsahitya62/URL_shortener.git
   cd URL_shortener
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Web UI: http://localhost:8080
   - H2 Console: http://localhost:8080/h2-console
     - JDBC URL: `jdbc:h2:mem:urlshortener`
     - Username: `sa`
     - Password: (leave empty)

## 📖 API Documentation

### Shorten URL

**POST** `/api/shorten`

Request body:
```json
{
  "url": "https://example.com/very/long/url"
}
```

Response:
```json
{
  "shortUrl": "http://localhost:8080/abc1234",
  "originalUrl": "https://example.com/very/long/url"
}
```

### Get URL Statistics

**GET** `/api/stats/{shortCode}`

Response:
```json
{
  "shortUrl": "http://localhost:8080/abc1234",
  "originalUrl": "https://example.com/very/long/url",
  "shortCode": "abc1234",
  "createdAt": "2024-01-15T10:30:00",
  "clickCount": 42
}
```

### Redirect to Original URL

**GET** `/{shortCode}`

Redirects to the original URL and increments the click count.

## 🛠️ Technology Stack

- **Spring Boot 3.2.0**: Core framework
- **Spring Data JPA**: Database abstraction
- **H2 Database**: In-memory database
- **Maven**: Build tool
- **Java 17**: Programming language

## 📁 Project Structure

```
url-shortener/
├── src/
│   ├── main/
│   │   ├── java/com/urlshortener/
│   │   │   ├── UrlShortenerApplication.java
│   │   │   ├── controller/
│   │   │   │   ├── UrlShortenerController.java
│   │   │   │   └── RedirectController.java
│   │   │   ├── service/
│   │   │   │   └── UrlShortenerService.java
│   │   │   ├── repository/
│   │   │   │   └── UrlMappingRepository.java
│   │   │   ├── model/
│   │   │   │   └── UrlMapping.java
│   │   │   └── dto/
│   │   │       ├── UrlRequest.java
│   │   │       └── UrlResponse.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   │           └── index.html
│   └── test/
├── pom.xml
└── README.md
```

## 🔧 Configuration

Edit `src/main/resources/application.properties` to customize:

- **Server Port**: Change `server.port` (default: 8080)
- **Base URL**: Change `app.base-url` for production deployment
- **Database**: Switch from H2 to PostgreSQL/MySQL by updating dependencies and datasource configuration

## 🧪 Testing

Run tests with:
```bash
mvn test
```

## 📝 License

This project is open source and available for showcase purposes.

## 🤝 Contributing

Feel free to fork this project and submit pull requests for any improvements!

## 📧 Contact

For questions or suggestions, please open an issue in the repository.

---

**Built with ❤️ using Spring Boot**
