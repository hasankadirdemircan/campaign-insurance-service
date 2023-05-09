# Insurance Campaign Service

### Before Starting
> Note: In the project, the Auth mechanism is counted as existing. That's why a static jwt was created.

>JWT -> `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZW1haWwiOiJkZW1pcmNhbmhhc2Fua2FkaXJAZ21haWwuY29tIiwiaWF0IjoxNTE2MjM5MDIyfQ.gj0OOwdIFtyS3L32NQ2hSGP1c6MqZLFZbk8isg3tqpM`

> Since I'm using hibernate ddl auto in the project, the tables will be created automatically when the project is up.

## Tech Stack
- Java 11+
- Maven 3.6+
- Spring-Boot 2+
- H2 in-memory Database
- Swagger Doc
- Dockerized Project
- Slf4j Logging
- Custom Exceptions (@ControllerAdvice)
- Mockito -> Unit Test
- Mockmvc -> Integration Test

## How Can We Run the Project
#### To Run with Docker
- Clone Project
- Go to `campaign-insurance-service` folder.
- To Build Dockerfile and generate image
- Follow `docker build` command below.
```
$ docker build --tag=insurance-campaign-service:latest .
```
- Then, need to run docker image
```
$ docker run -p8080:8080 insurance-campaign-service:latest
```
#### To Run on Intellij IDEA
- Clone Project
- Open Project with Intellij IDEA
- Run `InsuranceApplication`

## Swagger Documentation and H2 Database
- Swagger -> http://localhost:8080/swagger-ui.html
- H2 Database -> http://localhost:8080/h2-console

H2 Info & Credentials
```
JDBC URL -> jdbc:h2:mem:insurance
username -> sa
password -> password
```

## Project Infrastructure
![swagger.png](docs%2Fswagger.png)
- Create a campaign
  
  - {campaignTitle}
    - Campaign Title must be between 10 and 50 characters
    - Only accept Turkish Characters and Numbers. Not allowed special characters.
  - {campaignDetail}
    - Campaign Detail must be between 20 and 200 characters
    - accept Turkish Characters, Numbers and special characters
  - When a campaign created the CampaignStatus can be
    - TSS, OSS, OTHER -> WAITING_FOR_APPROVAL
    - HAYAT_INSURANCE -> ACTIVE
  - When a campaign with the same title and description is entered in the same category.
  It should be marked as `REPETITIVE`, the status of duplicate postings cannot be updated.       

- Activate the campaign
  - The campaign that is in the `WAITING_FOR_APPROVAL` status becomes `ACTIVE` when approved.
- Deactivate the campaign
  - User declares `ACTIVE` status or `WAITING_FOR_APPROVAL` status `DEACTIVATE`.
  can do.
- Get Campaign with ID
  - Get Campaign  by campaignID
- Get All Campaigns
  - Get All Campaign
- Get History of the campaign
  - Get a campaign history to see all steps of the campaign
- Get statics of the campaigns
  - Get statics of the campaign such as how many ACTIVE,REPETITIVE are in Database.

## Project Bonus
- Swagger Documentation
- Logging of requests with more than 5 milliseconds uptime
- Containerizing the application
- Integration tests
- List all status changes of an ad over time(History of the campaign)
