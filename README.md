# invoice4j

[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-23%2B-orange.svg)]()

**invoice4j** is a modular, open-source Java library for creating, reading, validating, and converting electronic invoices.

The project provides a shared invoice domain model and support for multiple e-invoicing standards, including **ZUGFeRD**, **Factur-X**, **XRechnung**, and **PEPPOL BIS**.

> **Status:** Early development

---

## Features

- Common invoice domain model
- Read electronic invoices
- Generate electronic invoices
- Validate invoice documents
- Convert between invoice formats
- Modular architecture
- Pure Java
- Maven Central distribution

---

## Modules

| Module | Description |
|---------|-------------|
| `core` | Shared utilities and infrastructure. |
| `invoice4j-base` | Common invoice domain model and value objects. |
| `invoice4j-zugferd` | ZUGFeRD / Factur-X support. |
| `invoice4j-xrechnung` | XRechnung support. |

---

## Installation

### Maven

```xml
<dependency>
    <groupId>io.github.scholzalex</groupId>
    <artifactId>invoice4j-zugferd</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## Example

```
Invoice invoice = Invoice.builder()
        .seller(...)
        .buyer(...)
        .build();

ZugferdWriter.write(invoice, outputFile);
```

---

## Roadmap

- [x] Shared invoice domain model
- [ ] ZUGFeRD reader
- [ ] ZUGFeRD writer
- [ ] XRechnung support
- [ ] PEPPOL BIS support
- [ ] Validation framework
- [ ] XML mapping
- [ ] Extensive documentation
- [ ] Maven Central release

---

## Contributing

Contributions, bug reports, feature requests, and discussions are welcome.

If you would like to contribute, please open an issue.

---

## License

This project is licensed under the Apache License 2.0. See the `LICENSE` file for details.

---

## Support

If **invoice4j** is useful to you, consider supporting its development.

<a href="https://paypal.me/juniorscholle">
  <img src="https://img.shields.io/badge/Donate-PayPal-00457C?logo=paypal&logoColor=white" alt="Donate with PayPal">
</a>

**PayPal:** https://paypal.me/juniorscholle