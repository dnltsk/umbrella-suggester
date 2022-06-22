# Umbrella Suggester

Task Description: https://www.notion.so/Tenera-Backend-Coding-Challenge-f5a4c891e1094717b224d9d1ad268a88

## How to set up and run the application

I used [OpenJDK v17](https://openjdk.java.net/projects/jdk/17/)

```shell
./gradlew build
```

to run server
```shell
java -jar build/libs/*.jar --openweathermap.apiKey=${YOUR_API_KEY}
```

alternatively (to run server without using openweathermap api)
```shell
java -jar build/libs/*.jar --spring.profiles.active=mock
```

you can than use the following sample calls:
* current: http://localhost:8080/current?location=Berlin
* history: http://localhost:8080/history?location=Berlin

alternatively, just use the interactive Swagger UI
* http://localhost:8080/swagger-ui/index.html

# How did I approached it

1. used my [springboot-kotlin-starter](https://github.com/dnltsk/springboot-kotlin-starter) Repo as a starting point
2. checked out the Specs of the [OpenWeatherMap API](https://openweathermap.org/api) (especially Geocoding API and Current Weather Data)
3. used the JsonToKotlinClass IntelliJ Plugin to generate the OpenWeatherMap API data modells
4. added AssertJ and Mockito, Configured Jackson
5. implemented /current endpoint with mock data
6. implemented /history endpoint with mock data
7. implemented the real client
8. improved namings, mappings and test data
9. added SwaggerUI

# What would I improve in order to have production READY

2. testing of RequestMapper (skipped for time reasons)
3. reasonable error handling handling via `@ExceptionHandler`
1. add /actuator endpoints via micrometer
4. ~~CI via Github Action~~
5. dockerize as multi-stage build
6. CD (see below)
7. store and use apiKey as Secret
8. alerting, e.g. via Sentry
9. externalize cache, e.g. DynamoDB
10. implement monitoring via Prometheus and Grafana

# How would I deploy the application in the context of a multi-environment CI/CD pipeline

* setup infrastructure: a single ECS Cluster, ECR, CodePipeline via CDK
* setup stage-dependent (dev/prod): ECS Task, API Gateway, Route53 Domain via CDK
   * the Tasks could be configured to listen on specific tags on an ECR, e.g. `${stage}-next`, to trigger a new deployment
* setup a CodePipeline via CDK to deploy new build (`docker deploy`)
* usually the `/health`-endpoint is used on typical blue-green deployments and the logic behind can be customized to check if the `apiKey` is correct and so on
* To use real sample requests via HTTP one could implement a workflow like the following:
   * first, build and deploy on dev (via tag, e.g. `${timestamp}` and `dev-next`)
   * do some sample requests for health check on fresh deployed dev-version
   * if everything is fine add tag `dev-latest` to image and continue with prod (via tag, e.g. `prod-next`)
   * run some checks on fresh deployed prod-version and add `prod-latest` if everything was fine
   * if some of the health checks fail, one could easily move shift the `${stage}-next` to the existing `${stage}-latest` to trigger a rollback
* if some clients are known one could implement a CDCT (Consumer Driven Contract Test)-Process
* to simplify the contract testing one could use code generation from a versionized OpenAPI document ¯\\_(ツ)_/¯