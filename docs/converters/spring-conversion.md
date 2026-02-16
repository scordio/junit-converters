---
description: An argument converter using Spring Framework type conversion
---

# `@SpringConversion`

`@SpringConversion` is an annotation that converts instances using the
[type conversion](https://docs.spring.io/spring-framework/reference/core/validation/convert.html)
provided by the Spring Framework:

``` java
--8<--
SpringConversionDemo.java:import
SpringConversionDemo.java:test
--8<--
```

The Spring conversion service provides a wide range of built-in converters for common Java types, including:

* Primitives and their wrappers
* Arrays
* Collections (`List`, `Set`, `Map`, etc.)
* Enums
* Common value types (`UUID`, `Currency`, `Locale`, etc.)

In addition, the Spring
[format annotations](https://docs.spring.io/spring-framework/reference/core/validation/format.html#format-annotations-api)
are also supported:

* `@NumberFormat` for formatting `Number` values such as `Double` and `Long`
* `@DurationFormat` for formatting `java.time.Duration` values in ISO-8601 and simplified styles
* `@DateTimeFormat` for formatting values such as `java.util.Date`, `java.util.Calendar`, and `Long` (for millisecond
  timestamps) as well as JSR-310 `java.time` types

## Requirements

The annotation requires `spring-core` available in the test classpath:

=== ":simple-apachemaven: Maven"

    ``` xml
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-core</artifactId>
      <version>${spring-framework.version}</version>
      <scope>test</scope>
    </dependency>
    ```

=== ":simple-gradle: Gradle"

    ``` kotlin
    testImplementation("org.springframework:spring-core:${springFrameworkVersion}")
    ```

However, declaring this dependency is generally not required in a Spring application.

To use the Spring format annotations, `spring-context` must also be available:

=== ":simple-apachemaven: Maven"

    ``` xml
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>${spring-framework.version}</version>
      <scope>test</scope>
    </dependency>
    ```

=== ":simple-gradle: Gradle"

    ``` kotlin
    testImplementation("org.springframework:spring-context:${springFrameworkVersion}")
    ```

## Examples

The following sections demonstrate some of the possible conversions. For a complete list of supported conversions,
refer to the Spring Framework reference documentation.

### Array → Array

| Source Type       | Target Declaration               | Example                                                                  |
|-------------------|----------------------------------|--------------------------------------------------------------------------|
| `#!java String[]` | `#!java @SpringConversion int[]` | `#!java new String[] { "123", "456" }` → `#!java new int[] { 123, 456 }` |

### Array → List

| Source Type           | Target Declaration                       | Example                                                             |
|-----------------------|------------------------------------------|---------------------------------------------------------------------|
| `#!java int[]`        | `#!java @SpringConversion List<Integer>` | `#!java new int[] { 123, 456 }` → `#!java List.of(123, 456)`        |
| `#!java Integer[]`    | `#!java @SpringConversion List<Integer>` | `#!java new Integer[] { 123, 456 }` → `#!java List.of(123, 456)`    |
| `#!java String[]`     | `#!java @SpringConversion List<Integer>` | `#!java new String[] { "123", "456" }` → `#!java List.of(123, 456)` |

### Array → Object

| Source Type        | Target Declaration             | Example                                               |
|--------------------|--------------------------------|-------------------------------------------------------|
| `#!java int[]`     | `#!java @SpringConversion int` | `#!java new int[] { 123, 456 }` → `#!java 123`        |
| `#!java Integer[]` | `#!java @SpringConversion int` | `#!java new Integer[] { 123, 456 }` → `#!java 123`    |
| `#!java String[]`  | `#!java @SpringConversion int` | `#!java new String[] { "123", "456" }` → `#!java 123` |

### Array → String

| Source Type        | Target Declaration                | Example                                                     |
|--------------------|-----------------------------------|-------------------------------------------------------------|
| `#!java int[]`     | `#!java @SpringConversion String` | `#!java new int[] { 123, 456 }` → `#!java "123,456"`        |
| `#!java Integer[]` | `#!java @SpringConversion String` | `#!java new Integer[] { 123, 456 }` → `#!java "123,456"`    |
| `#!java String[]`  | `#!java @SpringConversion String` | `#!java new String[] { "123", "456" }` → `#!java "123,456"` |

### List → List

| Source Type           | Target Declaration                       | Example                                                     |
|-----------------------|------------------------------------------|-------------------------------------------------------------|
| `#!java List<String>` | `#!java @SpringConversion List<Integer>` | `#!java List.of("123", "456")` → `#!java List.of(123, 456)` |

### Map → Map

| Source Type                  | Target Declaration                              | Example                                                                           |
|------------------------------|-------------------------------------------------|-----------------------------------------------------------------------------------|
| `#!java Map<String, String>` | `#!java @SpringConversion Map<Integer, Double>` | `#!java Map.of("1", "123.4", "2", "234.5")` → `#!java Map.of(1, 123.4, 2, 234.5)` |

### String → Double

| Source Type     | Target Declaration                                               | Example                        |
|-----------------|------------------------------------------------------------------|--------------------------------|
| `#!java String` | `#!java @SpringConversion @NumberFormat(style = PERCENT) double` | `#!java "42%"` → `#!java 0.42` |

### String → Duration

| Source Type     | Target Declaration                                                  | Example                                          |
|-----------------|---------------------------------------------------------------------|--------------------------------------------------|
| `#!java String` | `#!java @SpringConversion @DurationFormat(style = SIMPLE) Duration` | `#!java "42ms"` → `#!java Duration.ofMillis(42)` |

### String → List

| Source Type           | Target Declaration                       | Example                                                             |
|-----------------------|------------------------------------------|---------------------------------------------------------------------|
| `#!java String`       | `#!java @SpringConversion List<Integer>` | `#!java "123, 456"` → `#!java List.of(123, 456)`                    |

### String → LocalDate

| Source Type     | Target Declaration                   | Example                                          |
|-----------------|--------------------------------------|--------------------------------------------------|
| `#!java String` | `#!java @SpringConversion LocalDate` | `#!java "1970-01-01"` → `#!java LocalDate.EPOCH` |

### String → LocalDateTime

| Source Type     | Target Declaration                       | Example                                                                                         |
|-----------------|------------------------------------------|-------------------------------------------------------------------------------------------------|
| `#!java String` | `#!java @SpringConversion LocalDateTime` | `#!java "1970-01-01T00:00:00"` → `#!java LocalDateTime.of(LocalDate.EPOCH, LocalTime.MIDNIGHT)` |
