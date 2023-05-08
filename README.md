# Insurance Campaign Service

## Tech Stack
- Java 11+
- Maven 3.6+
- Spring-Boot 2+
- H2 in-memory Database
- Swagger Doc
- Dockerized Project
- Slf4j Logging


## How Can We Run the Project
To Run with Docker

Firstly, need to build Dockerfile
```
$ docker build --tag=insurance-campaign-service:latest .
```
Then, need to run docker image
```
$ docker run -p8080:8080 insurance-campaign-service:latest
```


## Swagger Documentation and H2 Database
- Swagger -> http://localhost:8080/swagger-ui.html
- H2 Database -> http://localhost:8080/h2-console

H2 Info & Credentials
```
JDBC URL -> jdbc:h2:mem:insurance
username -> sa
password -> password
```