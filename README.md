# Validator

### Sonar checks:
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=DaniilKornilov_java-project-78&branch=main&metric=alert_status)](https://sonarcloud.io/summary/overall?id=DaniilKornilov_java-project-78&branch=main)
[![Code Coverage](https://sonarcloud.io/api/project_badges/measure?project=DaniilKornilov_java-project-78&branch=main&metric=coverage)](https://sonarcloud.io/summary/overall?id=DaniilKornilov_java-project-78&branch=main)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=DaniilKornilov_java-project-78&branch=main&metric=bugs)](https://sonarcloud.io/summary/overall?id=DaniilKornilov_java-project-78&branch=main)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=DaniilKornilov_java-project-78&branch=main&metric=vulnerabilities)](https://sonarcloud.io/summary/overall?id=DaniilKornilov_java-project-78&branch=main)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=DaniilKornilov_java-project-78&branch=main&metric=code_smells)](https://sonarcloud.io/summary/overall?id=DaniilKornilov_java-project-78&branch=main)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=DaniilKornilov_java-project-78&branch=main&metric=sqale_rating)](https://sonarcloud.io/summary/overall?id=DaniilKornilov_java-project-78&branch=main)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=DaniilKornilov_java-project-78&branch=main&metric=reliability_rating)](https://sonarcloud.io/summary/overall?id=DaniilKornilov_java-project-78&branch=main)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=DaniilKornilov_java-project-78&branch=main&metric=security_rating)](https://sonarcloud.io/summary/overall?id=DaniilKornilov_java-project-78&branch=main)

### Hexlet tests and linter status:
[![Actions Status](https://github.com/DaniilKornilov/java-project-78/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/DaniilKornilov/java-project-78/actions)

## Demo:
### String schema examples
```java
Validator validator = new Validator();
validator.string().isValid(null); // true (no rules)
validator.string().required().isValid(null); // false
validator.string().required().isValid("hexlet"); // true
validator.string().contains("wh").isValid("what does the fox say"); // true
validator.string().minLength(5).isValid("Hexlet"); // true
```

### Number schema examples
```java
Validator validator = new Validator();
validator.number().isValid(null); // true (no rules)
validator.number().required().isValid(null); // false
validator.number().positive().isValid(5); // true
validator.number().range(1, 10).isValid(0); // false
```

### Map schema examples
```java
Validator validator = new Validator();
validator.map().isValid(null); // true (no rules)
validator.map().required().isValid(null); // false
validator.map().sizeof(2).isValid(Map.of(1, 2, 3, 4)); // true

Map<String, BaseSchema<String>> schemas = new HashMap<>();
schemas.put("firstName", validator.string().required());
schemas.put("lastName", validator.string().required().minLength(2));
Map<String, String> human1 = Map.of("firstName", "John", "lastName", "Smith");
Map<String, String> human2 = Map.of("firstName", "John", "lastName", "B");
validator.map().shape(schemas).isValid(human1); // true
validator.map().shape(schemas).isValid(human2); // false
```
