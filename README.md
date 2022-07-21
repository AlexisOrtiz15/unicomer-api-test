
# UNICOMER API TEST.


#### Using JVM Options

When running the application using `java -jar` or from a IDE, a variable value
could be change using the Java Options as shown below:

`-Denvironment_develop=true`

#### Using Maven `spring-boot:run`

When running the application using Maven, Java Options can be pass down using the
`-Dspring-boot.run.jvmArguments` Maven Option, as shown below:
 
`-Dspring-boot.run.jvmArguments="-Denvironment_log_root_level=debug"`



### Adding new variables

Any property setup on `application.[yaml|properties]` file can be customize 
as shown on [Customizing varibles](###Customizing variables).

To do so, the value should be replace for a `${variable_name}` using snake case. A default value 
can be set like `${variable_name:default_value}`

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



