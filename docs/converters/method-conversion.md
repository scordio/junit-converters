---
description: An argument converter using a conversion method
---

# `@MethodConversion`

`@MethodConversion` is an annotation that converts instances using a conversion method defined either in the test class or in an external class.

Conversion methods in the test class must be `static` unless the test class is annotated with
[`@TestInstance(Lifecycle.PER_CLASS)`](https://docs.junit.org/6.0.3/writing-tests/test-instance-lifecycle.html);
conversion methods in external classes must always be `static`.
