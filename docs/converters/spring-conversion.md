---
description: An argument converter using Spring Framework conversion service
hide:
  - toc
---

# `@SpringConversion`

`@SpringConversion` is an annotation that converts arguments using the Spring Framework
[`ConversionService`](https://docs.spring.io/spring-framework/reference/core/validation/convert.html).

The converter uses Spring's
[`DefaultConversionService`](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/core/convert/support/DefaultConversionService.html),
which provides a wide range of built-in converters for common types including:

* Primitives and their wrappers
* Collections (List, Set, Map, etc.)
* Arrays
* Enums
* Common value types (UUID, Currency, Locale, etc.)
* And many more standard conversions

The following examples demonstrate some of the supported conversions.

| Source Type     | Target Declaration                       | Example                                                   |
|-----------------|------------------------------------------|-----------------------------------------------------------|
| `#!java String` | `#!java @SpringConversion List<Integer>` | `#!java "123, 456"` → `#!java List.of(123, 456)`          |
| `#!java List`   | `#!java @SpringConversion List<Integer>` | `#!java List.of("123", "456")` → `#!java List.of(123, 456)` |

For a complete list of supported conversions, refer to the [Spring Framework documentation](https://docs.spring.io/spring-framework/reference/core/validation/convert.html).
