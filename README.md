<h1 align="center">Genus Backend</h1>

<div align="center">

[![Average time to resolve an issue](http://isitmaintained.com/badge/resolution/BrenoSouza/genus-back.svg)](http://isitmaintained.com/project/BrenoSouza/genus-back "Average time to resolve an issue") [![Percentage of issues still open](http://isitmaintained.com/badge/open/BrenoSouza/genus-back.svg)](http://isitmaintained.com/project/BrenoSouza/genus-back "Percentage of issues still open")
</div>

### About
Genus is a web application for managing educational institutions.

### Requirements

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)

### Technologies
- Spring Boot Framework / Security / JPA
- Postgres
- GraphQl

### How to run
Execute the `main` method in the `br.edu.ufcg.genus.GenusApplication` class from your IDE. Or,

For local db configuration:
```shell
mvn spring-boot:run -Dspring-boot.run.profiles=local
```
For heroku postgres database:
```shell
mvn spring-boot:run -Dspring-boot.run.profiles=release
```
### Client Repository
- [Genus Client](https://github.com/Klynger/genus-client)

### Deploy 
- [Genus Back](https://genuss.herokuapp.com/graphql)
- [Genus Client](https://genus-app.herokuapp.com/)

