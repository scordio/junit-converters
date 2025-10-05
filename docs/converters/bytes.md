---
hide:
  - toc
---

# `@Bytes`

`@Bytes` converts `String` instances into `byte[]` instances.

The annotation's `charset` attribute configures the charset to use for conversion.
When not specified, the JVM default charset is used.

The following source types and target declarations are supported.

| Source Type     | Target Declaration                        | Example                                         |
|-----------------|-------------------------------------------|-------------------------------------------------|
| `#!java String` | `#!java @Bytes byte[]`                    | `#!java "a"` → `#!java new byte[] { 97 }`       |
| `#!java String` | `#!java @Bytes(charset = "UTF-8") byte[]` | `#!java "ä"` → `#!java new byte[] { -61, -92 }` |
