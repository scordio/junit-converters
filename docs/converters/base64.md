---
description: Decodes Base64 encoded instances to byte arrays
hide:
  - toc
---

# `@Base64`

`@Base64` decodes [Base64](https://en.wikipedia.org/wiki/Base64) encoded instances of type `byte[]` or `String` to
`byte[]` instances.

The annotation's `encoding` attribute configures the desired encoding scheme:

* `BASIC` (default): for the _Basic_ encoding scheme
* `URL`: for the _URL and Filename Safe_ encoding scheme
* `MIME`: for the _MIME_ encoding scheme

The following source types and target declarations are supported.

| Source Type     | Target Declaration                       | Example                                                                              |
|-----------------|------------------------------------------|--------------------------------------------------------------------------------------|
| `#!java byte[]` | `#!java @Base64 byte[]`                  | `#!java new byte[] { 80, 122, 56, 47 }` → `#!java new byte[] { 63, 63, 63 }`         |
| `#!java byte[]` | `#!java @Base64(encoding = URL) byte[]`  | `#!java new byte[] { 80, 122, 56, 95 }` → `#!java new byte[] { 63, 63, 63 }`         |
| `#!java byte[]` | `#!java @Base64(encoding = MIME) byte[]` | `#!java new byte[] { 80, 122, 13, 10, 56, 47 }` → `#!java new byte[] { 63, 63, 63 }` |
| `#!java String` | `#!java @Base64 byte[]`                  | `#!java "Pz8/"` → `#!java new byte[] { 63, 63, 63 }`                                 |
| `#!java String` | `#!java @Base64(encoding = URL) byte[]`  | `#!java "Pz8_"` → `#!java new byte[] { 63, 63, 63 }`                                 |
| `#!java String` | `#!java @Base64(encoding = MIME) byte[]` | `#!java "Pz\r\n8/"` → `#!java new byte[] { 63, 63, 63 }`                             |
