package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.DocumentTypeCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.DocumentReference;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import org.junit.Test;
import java.net.URI;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class DocumentReferenceTest {

    @Test
    public void shouldCreateDocumentReference() throws Exception {
        DocumentReference reference = CalculationUtils.createDocumentReference();

        assertEquals("PO-12345", reference.getId());
        assertEquals(DocumentTypeCode.COMMERCIAL_INVOICE, reference.getTypeCode());
        assertEquals("Purchase Order", reference.getName());
        assertEquals("Customer purchase order", reference.getDescription());
        assertEquals(LocalDate.of(2026, 1, 1), reference.getIssueDate());
        assertEquals(new URI("https://example.com/order.pdf"), reference.getUri());
        assertNotNull(reference.getAttachment());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingId() {
        DocumentReference.builder()
                .typeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankId() {
        DocumentReference.builder()
                .id("   ")
                .typeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankName() {
        DocumentReference.builder()
                .id("PO-12345")
                .typeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .name("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankDescription() {
        DocumentReference.builder()
                .id("PO-12345")
                .typeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .description("   ")
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectFutureIssueDate() {
        DocumentReference.builder()
                .id("PO-12345")
                .typeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .issueDate(LocalDate.now().plusDays(1))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectRelativeUri() throws Exception {
        DocumentReference.builder()
                .id("PO-12345")
                .typeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .uri(new URI("order.pdf"))
                .build();
    }

    @Test
    public void shouldAllowAbsoluteUri() throws Exception {
        DocumentReference reference = DocumentReference.builder()
                .id("PO-12345")
                .typeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .uri(new URI("https://example.com/order.pdf"))
                .build();

        assertEquals(
                new URI("https://example.com/order.pdf"),
                reference.getUri());
    }

    @Test
    public void shouldAllowOnlyRequiredFields() {
        DocumentReference reference = DocumentReference.builder()
                .id("PO-12345")
                .typeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .build();

        assertEquals("PO-12345", reference.getId());
        assertEquals(DocumentTypeCode.COMMERCIAL_INVOICE, reference.getTypeCode());
        assertNull(reference.getName());
        assertNull(reference.getDescription());
        assertNull(reference.getIssueDate());
        assertNull(reference.getUri());
        assertNull(reference.getAttachment());
    }

    @Test
    public void shouldAllowAttachment() {
        DocumentReference reference = DocumentReference.builder()
                .id("PO-12345")
                .typeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .attachment(CalculationUtils.createAttachment())
                .build();

        assertNotNull(reference.getAttachment());
    }
}