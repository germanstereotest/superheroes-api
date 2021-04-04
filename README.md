# superheroes-api
Repositorio destinado a la resolución del desafío técnico W2M

## Api Documentation
http://localhost:8080/swagger-ui.html

## Spring Security integration
This project does not have a User administration API.
Instead of I create two users: user/user and admin/admin

## Gradle command
./gradlew build && java -jar build/libs/superhero-api.jar

## Docker command
docker build --build-arg JAR_FILE=build/libs/*.jar -t superhero-api  . -f Dockerfile
docker run --publish 8080:8080 superhero-api

## Postman Integration Test
I included a postman collection with a complete integration test of superhero-api into:
superhero-api.postman_collection.json