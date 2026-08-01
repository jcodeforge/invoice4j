# Module Overview

## invoice4j-base

The `invoice4j-base` module is the foundation of the project and contains all shared functionality used by other modules.

### Responsibilities

- Domain model
- Common interfaces
- Value objects
- Shared enums
- Builder API
- Utility classes
- Exceptions
- Common validation abstractions

This module is completely independent of any specific invoice format and can be used by all format implementations.

---

## invoice4j-zugferd

The `invoice4j-zugferd` module provides support for the ZUGFeRD and Factur-X standards.

### Responsibilities

- Read ZUGFeRD invoices
- Create ZUGFeRD invoices
- Parse XML
- Generate XML
- Map between XML and the invoice4j domain model
- Format-specific validation
- Support multiple ZUGFeRD versions

---

## invoice4j-xr

The `invoice4j-xr` module provides support for the XRechnung standard.

### Responsibilities

- Read XRechnung invoices
- Create XRechnung invoices
- Parse XML
- Generate XML
- Map between XML and the invoice4j domain model
- Format-specific validation
- Support multiple XRechnung versions

---

# Module Dependencies

The project follows a simple modular architecture.

- `invoice4j-base` has no dependencies on format modules.
- `invoice4j-zugferd` depends on `invoice4j-base`.
- `invoice4j-xr` depends on `invoice4j-base`.
- Format modules never depend on one another.
- Applications should interact with the format modules through the shared domain model provided by `invoice4j-base`.

This architecture keeps the project maintainable and allows new invoice standards to be added without affecting existing modules.