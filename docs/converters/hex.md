---
description: An argument converter to decode hexadecimal strings into byte arrays
hide:
  - toc
---

# `@Hex`

`@Hex` is an annotation that decodes [hexadecimal](https://en.wikipedia.org/wiki/Hexadecimal) `String` instances into `byte[]` instances:

``` java
--8<--
HexDemo.java:import
HexDemo.java:test
--8<--
```

The input strings are treated as case-insensitive and can be prefixed by `0x`.

The following source types and target declarations are supported.

| Source Type     | Target Declaration   | Example                                                                                                                |
|-----------------|----------------------|------------------------------------------------------------------------------------------------------------------------|
| `#!java String` | `#!java @Hex byte[]` | `#!java "0A1B2C"`/`#!java "0a1b2c"`/`#!java "0x0A1B2C"`/`#!java "0x0a1b2c"` → `#!java new byte[] { 0x0A, 0x1B, 0x2C }` |
