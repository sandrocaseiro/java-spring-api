# java-spring-api

## Usage

### Unit Tests
```bash
mvn test
```

### Integration Tests
```bash
mvn integration-test -DskipUnitTests
```

To run using test configurations (Mocks and pré-populated H2 database)

```bash
mvn spring-boot:run -Ptest
```

And to debug it:
```bash
mvn spring-boot:run -Dspring-boot.run.fork=false -Ptest
```
