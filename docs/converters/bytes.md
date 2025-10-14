---
description: An argument converter to convert strings or numbers into byte arrays
hide:
  - toc
---

# `@Bytes`

`@Bytes` is an annotation that converts `String` or number instances into `byte[]` instances.

## With Strings

When converting strings, the input instance is encoded into a sequence of bytes.

The `charset` attribute configures the charset to use for conversion.
If not specified, the JVM default charset is used.

The following source types and target declarations are supported.

| Source Type                    | Target Declaration                        | Example                                         |
|--------------------------------|-------------------------------------------|-------------------------------------------------|
| `#!java String`                | `#!java @Bytes byte[]`                    | `#!java "a"` → `#!java new byte[] { 97 }`       |
| `#!java String`                | `#!java @Bytes(charset = "UTF-8") byte[]` | `#!java "ä"` → `#!java new byte[] { -61, -92 }` |


## With Numbers

When converting numbers, the input instance is converted into a sequence of bytes using its binary representation.

The `order` attribute configures the byte order to use for conversion:

* `BIG_ENDIAN` (default): the bytes of a multibyte value are ordered from most significant to least significant
* `LITTLE_ENDIAN`: the bytes of a multibyte value are ordered from least significant to most significant

The following source types and target declarations are supported.

| Source Type                     | Target Declaration                            | Example                                                                                                         |
|---------------------------------|-----------------------------------------------|-----------------------------------------------------------------------------------------------------------------|
| `#!java byte`/`#!java Byte`     | `#!java @Bytes byte[]`                        | `#!java 0x12` → `#!java new byte[] { 0x12 }`                                                                    |
| `#!java short`/`#!java Short`   | `#!java @Bytes byte[]`                        | `#!java 0x1234` → `#!java new byte[] { 0x12, 0x34 }`                                                            |
| `#!java short`/`#!java Short`   | `#!java @Bytes(order = LITTLE_ENDIAN) byte[]` | `#!java 0x1234` → `#!java new byte[] { 0x34, 0x12 }`                                                            |
| `#!java int`/`#!java Integer`   | `#!java @Bytes byte[]`                        | `#!java 0x12345678` → `#!java new byte[] { 0x12, 0x34, 0x56, 0x78 }`                                            |
| `#!java int`/`#!java Integer`   | `#!java @Bytes(order = LITTLE_ENDIAN) byte[]` | `#!java 0x12345678` → `#!java new byte[] { 0x78, 0x56, 0x34, 0x12 }`                                            |
| `#!java long`/`#!java Long`     | `#!java @Bytes byte[]`                        | `#!java 0x123456780A1B2C3DL` → `#!java new byte[] { 0x12, 0x34, 0x56, 0x78, 0x0A, 0x1B, 0x2C, 0x3D }`           |
| `#!java long`/`#!java Long`     | `#!java @Bytes(order = LITTLE_ENDIAN) byte[]` | `#!java 0x123456780A1B2C3DL` → `#!java new byte[] { 0x3D, 0x2C, 0x1B, 0x0A, 0x78, 0x56, 0x34, 0x12 }`           |
| `#!java float`/`#!java Float`   | `#!java @Bytes byte[]`                        | `#!java (float) 0x12345678` → `#!java new byte[] { 0x4D, -0x6F, -0x5E, -0x4C }`                                 |
| `#!java float`/`#!java Float`   | `#!java @Bytes(order = LITTLE_ENDIAN) byte[]` | `#!java (float) 0x12345678` → `#!java new byte[] { -0x4C, -0x5E, -0x6F, 0x4D }`                                 |
| `#!java double`/`#!java Double` | `#!java @Bytes byte[]`                        | `#!java (double) 0x123456780A1B2C3DL` → `#!java new byte[] { 0x43, -0x4E, 0x34, 0x56, 0x78, 0x0A, 0x1B, 0x2C }` |
| `#!java double`/`#!java Double` | `#!java @Bytes(order = LITTLE_ENDIAN) byte[]` | `#!java (double) 0x123456780A1B2C3DL` → `#!java new byte[] { 0x2C, 0x1B, 0x0A, 0x78, 0x56, 0x34, -0x4E, 0x43 }` |
