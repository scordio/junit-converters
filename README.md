# JUnit Converters [![Maven Central](https://img.shields.io/maven-central/v/io.github.scordio/junit-converters?label=Maven%20Central)](https://mvnrepository.com/artifact/io.github.scordio/junit-converters) [![javadoc](https://javadoc.io/badge2/io.github.scordio/junit-converters/javadoc.svg)](https://javadoc.io/doc/io.github.scordio/junit-converters)

[![CI](https://github.com/scordio/junit-converters/actions/workflows/main.yml/badge.svg?branch=main)](https://github.com/scordio/junit-converters/actions/workflows/main.yml?query=branch%3Amain)

This project provides a collection of [JUnit Jupiter][]
[converters](https://docs.junit.org/current/user-guide/#writing-tests-parameterized-tests-argument-conversion-explicit)
for parameterized classes and tests.

| Annotation           | Description                                                       |
|----------------------|-------------------------------------------------------------------|
| [`@Base64`](#base64) | Decodes Base64 encoded `String` instances into `byte[]` instances |
| [`@Hex`](#hex)       | Decodes hexadecimal `String` instances into `byte[]` instances    |

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

`@Base64` decodes [Base64][] encoded `String` instances into `byte[]` instances.

The annotation's `encoding` attribute configures the desired encoding scheme, following [RFC 4648][] and [RFC 2045][]:

* `BASIC`: for the _Basic_ encoding scheme (default)
* `URL`: for the _URL and Filename Safe_ encoding scheme
* `MIME`: for the _MIME_ encoding scheme

The following source types and target declarations are supported.

| Source Type | Target Declaration                | Example                                    |
|-------------|-----------------------------------|--------------------------------------------|
| `String`    | `@Base64 byte[]`                  | `"Pz8/"` → `new byte[] { 63, 63, 63 }`     |
| `String`    | `@Base64(encoding = URL) byte[]`  | `"Pz8_"` → `new byte[] { 63, 63, 63 }`     |
| `String`    | `@Base64(encoding = MIME) byte[]` | `"Pz\r\n8/"` → `new byte[] { 63, 63, 63 }` |

## @Hex

`@Hex` decodes [hexadecimal][] `String` instances into `byte[]` instances.

The following source types and target declarations are supported.

| Source Type | Target Declaration | Example                                        |
|-------------|--------------------|------------------------------------------------|
| `String`    | `@Hex byte[]`      | `"0A1B2C"` → `new byte[] { 0x0A, 0x1B, 0x2C }` |

## License

JUnit Converters is released under version 2.0 of the [Apache License][].

[Apache License]: https://www.apache.org/licenses/LICENSE-2.0
[Base64]: https://en.wikipedia.org/wiki/Base64
[hexadecimal]: https://en.wikipedia.org/wiki/Hexadecimal
[JUnit Jupiter]: https://github.com/junit-team/junit-framework
[RFC 2045]: http://www.ietf.org/rfc/rfc2045.txt
[RFC 4648]: http://www.ietf.org/rfc/rfc4648.txt
