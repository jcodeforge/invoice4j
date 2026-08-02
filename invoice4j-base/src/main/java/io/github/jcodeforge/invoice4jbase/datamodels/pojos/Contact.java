package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;

/**
 * Contact information.
 *
 * Used by Party objects such as Seller, Buyer, Payee, and ShipTo.
 */
public class Contact {

    /**
     * Contact person name.
     *
     * BT-41 / BT-56
     */
    private String name;

    /**
     * Contact telephone number.
     *
     * BT-42 / BT-57
     */
    private String telephone;

    /**
     * Contact email address.
     *
     * BT-43 / BT-58
     */
    private String email;

    /**
     * Department or contact group.
     */
    private String department;

    /**
     * Additional contact information.
     */
    private String note;

    private Contact() {
    }

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getNote() {
        return note;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Contact contact;

        private Builder() {
            this.contact = new Contact();
        }

        public Builder name(String name) {
            contact.name = name == null ? null : name.trim();
            return this;
        }

        public Builder telephone(String telephone) {
            contact.telephone = telephone == null ? null : telephone.trim();
            return this;
        }

        public Builder email(String email) {
            contact.email = email == null ? null : email.trim();
            return this;
        }

        public Builder department(String department) {
            contact.department = department == null ? null : department.trim();
            return this;
        }

        public Builder note(String note) {
            contact.note = note == null ? null : note.trim();
            return this;
        }

        public Contact build() {
            if (contact.name == null && contact.telephone == null && contact.email == null && contact.department == null
                    && contact.note == null) {
                throw new InvoiceValidationException("Contact must contain at least one value.");
            }
            if (contact.name != null && contact.name.isBlank()) {
                throw new InvoiceValidationException("BT-41 / BT-56 Contact name must not be blank.");
            }
            if (contact.telephone != null && contact.telephone.isBlank()) {
                throw new InvoiceValidationException("BT-42 / BT-57 Contact telephone must not be blank.");
            }
            if (contact.email != null && contact.email.isBlank()) {
                throw new InvoiceValidationException("BT-43 / BT-58 Contact email address must not be blank.");
            }
            if (contact.department != null && contact.department.isBlank()) {
                throw new InvoiceValidationException("Contact department must not be blank.");
            }
            if (contact.note != null && contact.note.isBlank()) {
                throw new InvoiceValidationException("Contact note must not be blank.");
            }
            if (contact.email != null && !contact.email.contains("@")) {
                throw new InvoiceValidationException("BT-43 / BT-58 Contact email address is invalid.");
            }
            if (contact.email != null && (contact.email.startsWith("@") || contact.email.endsWith("@"))) {
                throw new InvoiceValidationException("BT-43 / BT-58 Contact email address is invalid.");
            }

            return contact;
        }
    }
}
