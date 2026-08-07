# Changelog

All notable changes to this project will be documented in this file.

---

## [0.2.0] - 2026-08-07

### Added

#### CII Serialization

- Initial EN 16931 Cross Industry Invoice (CII) writer
- Builder-based `CiiInvoiceWriter` API
- Support for EN 16931 profile selection
- Pretty-print XML output
- Complete serializer architecture for all supported invoice components

#### CII Deserialization

- Initial EN 16931 Cross Industry Invoice (CII) reader
- Builder-based `CiiInvoiceReader` API
- Complete XML parser architecture
- Secure namespace-aware XML parsing
- Round-trip serialization/deserialization support

#### XML Infrastructure

- `XmlWriter` for namespace-aware XML generation
- `XmlReader` with XPath helper methods
- XML reader and writer factories
- Namespace context implementation
- Secure XML processing (XXE protection)

#### Examples

- QuickStart example demonstrating:
  - Invoice creation
  - Invoice calculation
  - XML serialization
  - XML deserialization

#### Testing

- Round-trip serialization/deserialization tests
- Complete invoice parsing tests
- Minimal invoice parsing tests
- Invalid XML handling tests

---

## [0.1.0] - 2026-08-04

### Added

#### Core domain model

- Immutable invoice domain model
- Builder pattern for all domain objects
- Comprehensive validation using `InvoiceValidationException`
- Complete set of EN 16931 core value objects
- Enumerations for standardized code lists

#### Calculations

- Invoice calculation engine

#### Validation

- Mandatory business term validation
- Numeric range validation
- Currency consistency validation
- VAT consistency validation
- Builder validation for all domain objects

#### Testing

- Comprehensive unit tests for:
  - Core domain model
  - Builders
  - Validation
  - Calculation engine