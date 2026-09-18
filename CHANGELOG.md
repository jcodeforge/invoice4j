# Changelog

All notable changes to this project will be documented in this file.

---

## [0.5.0] - 2026-09-18

### Added

#### XRechnung UBL Support

- XRechnung UBL writer
- XRechnung UBL reader
- Automatic UBL/CII format detection
- Unified `XrInvoiceReader` API
- UBL 2.1 XSD validation
- KoSIT XRechnung validation for UBL
- Genericode code list validation
- Validation enabled by default
- Optional validation disabling via the builder API

#### Testing

- Valid XRechnung UBL integration tests
- Invalid XRechnung UBL integration tests
- Real XRechnung UBL test document
- XRechnung UBL read/write round-trip tests
- Automatic UBL/CII reader tests

#### Examples

- XRechnung UBL example demonstrating:
  - Invoice creation
  - Invoice calculation
  - XRechnung UBL serialization
  - Automatic XRechnung format detection
  - XRechnung deserialization
  - Read/write round trip

#### Documentation

- Added comprehensive Javadocs for the public XRechnung API
- Updated README with XRechnung UBL support
- Updated installation and usage documentation

---

## [0.4.0] - 2026-08-16

### Added

#### XRechnung CII Support

- XRechnung CII writer
- XRechnung CII reader
- Automatic XRechnung profile detection
- CII 16B XSD validation
- KoSIT XRechnung validation
- Integrated validation into XRechnung reading and writing
- Validation enabled by default
- Optional validation disabling via the builder API

#### Testing

- Valid XRechnung integration tests
- Invalid XRechnung integration tests
- Official/KoSIT XRechnung test documents
- XRechnung CII read/write round-trip tests

#### Examples

- XRechnung CII example demonstrating:
  - Invoice creation
  - Invoice calculation
  - XRechnung CII serialization
  - XRechnung CII deserialization
  - Read/write round trip

---

## [0.3.0] - 2026-08-13

### Added

#### ZUGFeRD Support

- Support for all ZUGFeRD profiles:
  - MINIMUM
  - BASIC WL
  - BASIC
  - EN 16931
  - EXTENDED
- ZUGFeRD CII XML reading and writing
- Automatic ZUGFeRD profile detection
- ZUGFeRD validation
- XSD validation
- High-level `ZugferdPdfWriter` API
- Hybrid ZUGFeRD PDF document creation
- Embedded `factur-x.xml` associated file
- ZUGFeRD XMP metadata
- High-level `ZugferdPdfReader` API
- Reading ZUGFeRD PDFs back into `Invoice` objects
- Expanded document type and unit code support

#### Testing

- XML round-trip tests
- PDF round-trip tests
- Real ZUGFeRD sample documents for interoperability testing
- Comprehensive reader, writer, validation, and PDF test coverage

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

#### Core Domain Model

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