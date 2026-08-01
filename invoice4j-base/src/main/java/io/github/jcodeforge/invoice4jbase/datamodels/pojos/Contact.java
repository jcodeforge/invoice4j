package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

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
            contact.name = name;
            return this;
        }

        public Builder telephone(String telephone) {
            contact.telephone = telephone;
            return this;
        }

        public Builder email(String email) {
            contact.email = email;
            return this;
        }

        public Builder department(String department) {
            contact.department = department;
            return this;
        }

        public Builder note(String note) {
            contact.note = note;
            return this;
        }

        public Contact build() {
            return contact;
        }
    }
}
