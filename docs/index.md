# Overview

JUnit Converters is a collection of ready-to-use argument converters designed to streamline parameterized testing in the
[JUnit Framework](https://junit.org/).

It uses the framework's
[explicit argument conversion](https://docs.junit.org/current/user-guide/#writing-tests-parameterized-tests-argument-conversion-explicit)
mechanism to provide converters for common data types and patterns.
This simplifies the process of transforming input arguments into the types required by the test, allowing you to write
clear test cases without clutter.

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

## Converters

The following converters are available:

* [`@Base64`](converters/base64.md): decodes Base64 encoded instances into `byte[]` instances
* [`@Bytes`](converters/bytes.md): converts `String` instances into `byte[]` instances
* [`@Hex`](converters/hex.md): decodes hexadecimal `String` instances into `byte[]` instances
