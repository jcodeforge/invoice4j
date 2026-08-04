# invoice4j

[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](LICENSE)
[![Java](https://img.shields.io/badge/Java-23%2B-orange.svg)]()

**invoice4j** is a modular, open-source Java library for creating, reading, validating, and converting electronic invoices.

The project provides a shared invoice domain model and support for multiple e-invoicing standards, including **ZUGFeRD**, **Factur-X**, **XRechnung**, and **PEPPOL BIS**.

> **Status:** Early development

---

## Features

- EN 16931 compliant domain model
- ZUGFeRD / Factur-X support
- XRechnung support
- Built-in invoice calculation engine
- XML validation
- Pure Java, no external services

---

## Supported Standards
- UN/CEFACT CII
- ZUGFeRD
- XRechnung
- UBL (planned)
- PEPPOL (planned)

---

## Invoice Calculation Engine

`invoice4j` contains a built-in calculation engine that automatically derives invoice totals according to EN 16931.

### Features

- Calculates invoice line totals (BT-131)
- Supports fixed and percentage discounts
- Supports line and document level allowances/charges
- Calculates VAT breakdowns (BG-23)
- Calculates invoice monetary summation (BG-22)
- Centralized monetary arithmetic and rounding
- Validates calculation consistency

---

## Installation

*todo*

---

## Quick Start

*todo*

---

## Examples

*todo*
```
Invoice invoice = Invoice.builder()
    .invoiceNumber("INV-1")
    .seller(...)
    .buyer(...)
    .addLine(...)
    .build();

Invoice calculated = InvoiceCalculator.calculate(invoice);
        
```

---

## Running Tests

Clone the repository and execute:

```bash
mvn test
```

To run the complete verification including integration tests:

```bash
mvn verify
```

The test suite covers:

- Domain model validation
- Invoice line calculations
- VAT breakdown calculations
- Monetary summation calculations
- Complete invoice calculations

---

## Roadmap

| Version   | Milestone                               | Status |
|-----------|-----------------------------------------|--------|
| v0.1.0    | Core Model - Invoice calculation engine | In Progress |
| v0.2.0    | CII XML                                 | Planned |
| v0.3.0    | ZUGFeRD                                 | Planned |
| v0.4.0    | XRechnung                               | Planned |
| v1.0.0    | Public Release                          | Planned |
| v1.1.0    | PEPPOL Support                          | Planned |
| v1.2.0    | Additional European e-Invoice Formats   | Planned |
| v1.3.0    | Streaming APIs                          | Planned |
| v1.4.0    | Digital Signatures (XAdES/PAdES)        | Planned |

See the **GitHub Milestones** for the detailed implementation plan and progress.

---

### Maven

```xml
<dependency>
    <groupId>io.github.scholzalex</groupId>
    <artifactId>invoice4j-zugferd</artifactId>
    <version>1.0.0</version>
</dependency>
```

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

Development of invoice4j requires time for implementing new features, improving documentation, maintaining standards compatibility, and providing support.


<a href="https://paypal.me/juniorscholle">
  <img src="https://img.shields.io/badge/Donate-PayPal-00457C?logo=paypal&logoColor=white" alt="Donate with PayPal">
</a>

**PayPal:** https://paypal.me/juniorscholle  

## Early Development Notice

invoice4j is currently in an early development stage.

The project is published at this stage to gather feedback from developers and users working with electronic invoices such as ZUGFeRD and XRechnung.

The goal is to find out whether invoice4j solves real-world problems and whether the architecture, API design, and supported standards meet the needs of the community.  

At this stage:
- The API may still change.
- Some features are not implemented yet.
- Documentation is actively being improved.
- Production usage is not recommended until a stable release is available

Feedback is highly appreciated:
- Feature requests
- Missing invoice fields or standards
- API design suggestions
- Real-world use cases
- Bug reports

If you work with electronic invoices and have requirements or suggestions, please open an issue or start a discussion.

Thank you for helping shape **invoice4j**.