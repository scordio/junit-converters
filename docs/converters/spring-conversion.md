---
description: An argument converter using Spring Framework type conversion
---

# `@SpringConversion`

`@SpringConversion` is an annotation that converts instances using the
[type conversion](https://docs.spring.io/spring-framework/reference/core/validation/convert.html)
provided by the Spring Framework.

The converter delegates the conversion to the
[`DefaultConversionService`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/convert/support/DefaultConversionService.html),
which provides a wide range of built-in converters for common Java types, including:

* Primitives and their wrappers
* Arrays
* Collections (`List`, `Set`, `Map`, etc.)
* Enums
* Common value types (`UUID`, `Currency`, `Locale`, etc.)

The following source types and target declarations demonstrate some of the possible conversions.

| Source Type           | Target Declaration                       | Example                                                     |
|-----------------------|------------------------------------------|-------------------------------------------------------------|
| `#!java String`       | `#!java @SpringConversion List<Integer>` | `#!java "123, 456"` → `#!java List.of(123, 456)`            |
| `#!java List<String>` | `#!java @SpringConversion List<Integer>` | `#!java List.of("123", "456")` → `#!java List.of(123, 456)` |

For a complete list of supported conversions, refer to the
[`org.springframework.core.convert.support`](https://github.com/spring-projects/spring-framework/tree/main/spring-core/src/main/java/org/springframework/core/convert/support)
package content.

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

However, such an explicit dependency is generally not required when writing tests for a Spring application.
