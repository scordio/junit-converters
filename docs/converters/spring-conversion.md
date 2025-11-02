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

The converter delegates the conversion to the
[`DefaultConversionService`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/convert/support/DefaultConversionService.html),
which provides a wide range of built-in converters for common Java types, including:

* Primitives and their wrappers
* Arrays
* Collections (`List`, `Set`, `Map`, etc.)
* Enums
* Common value types (`UUID`, `Currency`, `Locale`, etc.)

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

## Examples

The following sections demonstrate some of the possible conversions. For a complete list of supported conversions,
refer to the Spring Framework reference documentation.

### Array → Array

| Source Type       | Target Declaration               | Example                                                                  |
|-------------------|----------------------------------|--------------------------------------------------------------------------|
| `#!java String[]` | `#!java @SpringConversion int[]` | `#!java new String[] { "123", "456" }` → `#!java new int[] { 123, 456 }` |


### Array → Collection

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

### Collection → Collection

| Source Type           | Target Declaration                       | Example                                                     |
|-----------------------|------------------------------------------|-------------------------------------------------------------|
| `#!java List<String>` | `#!java @SpringConversion List<Integer>` | `#!java List.of("123", "456")` → `#!java List.of(123, 456)` |

### Map → Map

| Source Type                  | Target Declaration                              | Example                                                                       |
|------------------------------|-------------------------------------------------|-------------------------------------------------------------------------------|
| `#!java Map<String, String>` | `#!java @SpringConversion Map<Integer, Double>` | `#!java Map.of("1", "123", "2", "456")` → `#!java Map.of(1, 123.0, 2, 456.0)` |

### String → Collection

| Source Type           | Target Declaration                       | Example                                                             |
|-----------------------|------------------------------------------|---------------------------------------------------------------------|
| `#!java String`       | `#!java @SpringConversion List<Integer>` | `#!java "123, 456"` → `#!java List.of(123, 456)`                    |
