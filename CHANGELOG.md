# Changelog

All notable changes to this project will be documented in this file.

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
- Implement invoice calculation engine

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