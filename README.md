# UNICOMER API TEST.

## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
- [Maven 3](https://maven.apache.org)

## Deploying the application to Heroku

[The easiest way to deploy the sample application to Herokuis to use the Github](https://dev.to/kristijanfistrek/deploy-springboot-app-to-heroku-with-github-27ij)

[Test the application deployed on heroku](https://unicomer-api-test.herokuapp.com/swagger-ui/index.html)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `de.codecentric.springbootsample.Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```

#### Using JVM Options

When running the application using `java -jar` or from a IDE, a variable value
could be change using the Java Options as shown below:

`-Denvironment_develop=true`

#### Using Maven `spring-boot:run`

When running the application using Maven, Java Options can be pass down using the
`-Dspring-boot.run.jvmArguments` Maven Option, as shown below:
 
`-Dspring-boot.run.jvmArguments="-Denvironment_log_root_level=debug"`

### Flyway

Flyway has been setup to handle incremental changes over the database, the incremental scripts should
be placed on `src/main/resources/db` and must follow the naming convention as: 

```
V{version}__{description}
    where:
        -version: has the structure of major version and minor version. i.e 1_0
        -description a snake case short description of the new version
```

Sometimes there are some scripts that are meant to be executed only on an specific 
environment, there fore the `db` follows the next filesystem distribution:

-`[database_abrevation]/common/ddl`: For DDL scripts that will be executed regarless the environment
-`[database_abrevation]/common/dml`: For DML scripts that will be executed regarless the environment
-`[database_abrevation]/[development|staging|production]`: For DML scripts that will be executed based on the name of the environment.
