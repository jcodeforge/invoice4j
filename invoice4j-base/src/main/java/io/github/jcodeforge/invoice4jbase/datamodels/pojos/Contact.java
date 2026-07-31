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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
