# Overview

JUnit Converters is a collection of [JUnit Framework](https://junit.org/)
[converters](https://docs.junit.org/current/user-guide/#writing-tests-parameterized-tests-argument-conversion-explicit)
for parameterized classes and tests.

## Compatibility

JUnit Converters is based on the JUnit Framework 5 and requires Java 8 or higher.

## Getting Started

[![JUnit Converters](https://img.shields.io/maven-central/v/io.github.scordio/junit-converters?label=JUnit%20Converters&color=#4cae4f)](https://central.sonatype.com/artifact/io.github.scordio/junit-converters)

=== ":simple-apachemaven: Maven"

    ``` xml
    <dependency>
      <groupId>io.github.scordio</groupId>
      <artifactId>junit-converters</artifactId>
      <version>${junit-converters.version}</version>
      <scope>test</scope>
    </dependency>
    ```

=== ":simple-gradle: Gradle"

    ``` kotlin
    testImplementation("io.github.scordio:junit-converters:${junitConvertersVersion}")
    ```

For instructions on how to use it with the JUnit Framework, see the [Converters](converters/index.md) section.
