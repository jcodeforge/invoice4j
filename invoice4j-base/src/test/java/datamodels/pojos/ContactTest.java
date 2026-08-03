package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Contact;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContactTest {

    @Test
    public void shouldCreateContact() {
        Contact contact = CalculationUtils.createContact();

        assertEquals("Max Mustermann", contact.getName());
        assertEquals("+49 351 1234567", contact.getTelephone());
        assertEquals("max.mustermann@jcodeforge.io", contact.getEmail());
        assertEquals("Accounting", contact.getDepartment());
        assertEquals("Primary contact", contact.getNote());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectEmptyContact() {
        Contact.builder()
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankName() {
        Contact.builder()
                .name("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankTelephone() {
        Contact.builder()
                .telephone("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankEmail() {
        Contact.builder()
                .email("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankDepartment() {
        Contact.builder()
                .department("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankNote() {
        Contact.builder()
                .note("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectEmailWithoutAtSign() {
        Contact.builder()
                .email("max.mustermann.jcodeforge.io")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectEmailStartingWithAtSign() {
        Contact.builder()
                .email("@jcodeforge.io")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectEmailEndingWithAtSign() {
        Contact.builder()
                .email("max.mustermann@")
                .build();
    }

    @Test
    public void shouldAllowNameOnly() {
        Contact contact = Contact.builder()
                .name("Max Mustermann")
                .build();

        assertEquals("Max Mustermann", contact.getName());
        assertNull(contact.getTelephone());
        assertNull(contact.getEmail());
    }

    @Test
    public void shouldAllowTelephoneOnly() {
        Contact contact = Contact.builder()
                .telephone("+49 351 1234567")
                .build();

        assertEquals("+49 351 1234567", contact.getTelephone());
        assertNull(contact.getName());
        assertNull(contact.getEmail());
    }

    @Test
    public void shouldAllowEmailOnly() {
        Contact contact = Contact.builder()
                .email("max.mustermann@jcodeforge.io")
                .build();

        assertEquals("max.mustermann@jcodeforge.io", contact.getEmail());
        assertNull(contact.getName());
        assertNull(contact.getTelephone());
    }

    @Test
    public void shouldAllowDepartmentOnly() {
        Contact contact = Contact.builder()
                .department("Accounting")
                .build();

        assertEquals("Accounting", contact.getDepartment());
    }

    @Test
    public void shouldAllowNoteOnly() {
        Contact contact = Contact.builder()
                .note("Primary contact")
                .build();

        assertEquals("Primary contact", contact.getNote());
    }
}