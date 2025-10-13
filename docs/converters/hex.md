---
description: An argument converter to decode hexadecimal strings to byte arrays
hide:
  - toc
---

# `@Hex`

`@Hex` is an annotation that decodes [hexadecimal](https://en.wikipedia.org/wiki/Hexadecimal) `String` instances to
`byte[]` instances.

The following source types and target declarations are supported.

| Source Type     | Target Declaration   | Example                                                      |
|-----------------|----------------------|--------------------------------------------------------------|
| `#!java String` | `#!java @Hex byte[]` | `#!java "0A1B2C"` → `#!java new byte[] { 0x0A, 0x1B, 0x2C }` |
