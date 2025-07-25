# JUnit Converters [![Maven Central](https://img.shields.io/maven-central/v/io.github.scordio/junit-converters?label=Maven%20Central)](https://mvnrepository.com/artifact/io.github.scordio/junit-converters) [![javadoc](https://javadoc.io/badge2/io.github.scordio/junit-converters/javadoc.svg)](https://javadoc.io/doc/io.github.scordio/junit-converters)

[![CI](https://github.com/scordio/junit-converters/actions/workflows/main.yml/badge.svg?branch=main)](https://github.com/scordio/junit-converters/actions/workflows/main.yml?query=branch%3Amain)

This project provides a collection of [JUnit Jupiter][]
[`ArgumentConverter`](https://docs.junit.org/current/api/org.junit.jupiter.params/org/junit/jupiter/params/converter/ArgumentConverter.html)
implementations.

## Compatibility

JUnit Converters is based on JUnit Jupiter 5 and requires Java 8 or higher.

## Getting Started

### Maven

```xml
<dependency>
  <groupId>io.github.scordio</groupId>
  <artifactId>junit-converters</artifactId>
  <version>${junit-converters.version}</version>
  <scope>test</scope>
</dependency>
```

### Gradle

```kotlin
testImplementation("io.github.scordio:junit-converters:${junitConvertersVersion}")
```

## Converters

## License

Jimfs JUnit Jupiter is released under version 2.0 of the [Apache License][].

[Apache License]: https://www.apache.org/licenses/LICENSE-2.0
[Jimfs]: https://github.com/google/jimfs
[JUnit Jupiter]: https://github.com/junit-team/junit5
