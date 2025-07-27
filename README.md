# JUnit Converters [![Maven Central](https://img.shields.io/maven-central/v/io.github.scordio/junit-converters?label=Maven%20Central)](https://mvnrepository.com/artifact/io.github.scordio/junit-converters) [![javadoc](https://javadoc.io/badge2/io.github.scordio/junit-converters/javadoc.svg)](https://javadoc.io/doc/io.github.scordio/junit-converters)

[![CI](https://github.com/scordio/junit-converters/actions/workflows/main.yml/badge.svg?branch=main)](https://github.com/scordio/junit-converters/actions/workflows/main.yml?query=branch%3Amain)

This project provides a collection of [JUnit Jupiter][]
[converters](https://docs.junit.org/current/user-guide/#writing-tests-parameterized-tests-argument-conversion-explicit)
for parameterized classes and tests.

| Annotation            | Description                                                       |
|-----------------------|-------------------------------------------------------------------|
| [`@Base64`](#@Base64) | Decodes Base64-encoded `String` instances into `byte[]` instances |

## Getting Started

### Compatibility

JUnit Converters is based on JUnit Jupiter 5 and requires Java 8 or higher.

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

## @Base64

`@Base64` is a JUnit composed annotation that decodes `String` instances into `byte[]` instances using the [Base64][]
encoding scheme.

The annotation provides an optional `encoding` attribute to configure the desired encoding scheme,
following [RFC 4648][] and [RFC 2045][]:

* `BASIC`: for the _Basic_ encoding scheme (default)
* `URL`: for the _URL and Filename Safe_ encoding scheme
* `MIME`: for the _MIME_ encoding scheme

| Source Type | Target Declaration                                                 | Example                                                            |
|-------------|--------------------------------------------------------------------|--------------------------------------------------------------------|
| `String`    | `@Base64 byte[] bytes` or `@Base64(encoding = BASIC) byte[] bytes` | `"PDw/Pz8+Pg=="` → `new byte[] { 60, 60, 63, 63, 63, 62, 62 }`     |
| `String`    | `@Base64(encoding = URL) byte[] bytes`                             | `"PDw_Pz8-Pg"` → `new byte[] { 60, 60, 63, 63, 63, 62, 62 }`       |
| `String`    | `@Base64(encoding = MIME) byte[] bytes`                            | `"PDw/Pz8+\r\nPg=="` → `new byte[] { 60, 60, 63, 63, 63, 62, 62 }` |

## License

JUnit Converters is released under version 2.0 of the [Apache License][].

[Apache License]: https://www.apache.org/licenses/LICENSE-2.0
[Base64]: https://en.wikipedia.org/wiki/Base64
[JUnit Jupiter]: https://github.com/junit-team/junit5
[RFC 2045]: http://www.ietf.org/rfc/rfc2045.txt
[RFC 4648]: http://www.ietf.org/rfc/rfc4648.txt
