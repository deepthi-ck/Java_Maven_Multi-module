# Java Maven Multi-module

Spring Boot **Maven multi-module** project for **Java 8 only**.

Layout inspired by [lydtechconsulting/ctf-example-multi-module](https://github.com/lydtechconsulting/ctf-example-multi-module)
(parent POM + connected modules) with service modules in the spirit of
[nihadamirov/spring-boot-microservices](https://github.com/nihadamirov/spring-boot-microservices) —
implemented as **Java 8** + Spring Boot **2.7** (not a copy of those repositories).

## Requirements

- JDK **8** (see `.sdkmanrc`)
- Maven Wrapper (`./mvnw` / `mvnw.cmd`)

## Project structure

```
.
├── pom.xml                 Parent aggregator
├── common/                 Shared Java library
├── product-service/        Spring Boot REST API (port 8082)
├── order-service/          Spring Boot REST API (port 8083)
├── config/                 Checkstyle / PMD / SpotBugs
├── documentation/
├── scripts/ck|git|tools/
└── mvnw / mvnw.cmd
```

## Building from source

```bash
./mvnw clean test
./mvnw -pl product-service spring-boot:run
./mvnw -pl order-service spring-boot:run
```

Windows:

```bat
mvnw.cmd clean test
```

## Quality tools

See `documentation/TOOLS.md`. All of: CK, CPD, Checkstyle, Git, JaCoCo,
OWASP-Dependency-Check, PIT, PMD, SpotBugs, Static-DU-JaCoCo-composite, diff-cover.

```bash
bash scripts/tools/run_tools.sh
```

## License

Apache License 2.0 — see `LICENSE.txt`.