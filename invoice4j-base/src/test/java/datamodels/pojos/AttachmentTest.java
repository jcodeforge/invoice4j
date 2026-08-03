package datamodels.pojos;

import calculation.CalculationUtils;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Attachment;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.MimeType;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import org.junit.Test;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

public class AttachmentTest {

    @Test
    public void shouldCreateEmbeddedAttachment() {
        Attachment attachment = CalculationUtils.createAttachment();

        assertEquals("invoice.pdf", attachment.getFileName());
        assertEquals(MimeType.PDF, attachment.getMimeType());
        assertNotNull(attachment.getContent());
        assertNull(attachment.getUri());
    }

    @Test
    public void shouldCreateUriAttachment() throws Exception {
        Attachment attachment = Attachment.builder()
                .fileName("invoice.pdf")
                .mimeType(MimeType.PDF)
                .uri(new URI("https://example.com/invoice.pdf"))
                .build();

        assertEquals("invoice.pdf", attachment.getFileName());
        assertEquals(MimeType.PDF, attachment.getMimeType());
        assertNull(attachment.getContent());
        assertEquals(
                new URI("https://example.com/invoice.pdf"),
                attachment.getUri());
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingFileName() {
        Attachment.builder()
                .mimeType(MimeType.PDF)
                .content("abc".getBytes(StandardCharsets.UTF_8))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectBlankFileName() {
        Attachment.builder()
                .fileName("   ")
                .mimeType(MimeType.PDF)
                .content("abc".getBytes(StandardCharsets.UTF_8))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingMimeType() {
        Attachment.builder()
                .fileName("invoice.pdf")
                .content("abc".getBytes(StandardCharsets.UTF_8))
                .build();
    }

    @Test(expected = InvoiceValidationException.class)
    public void shouldRejectMissingContentAndUri() {
        Attachment.builder()
                .fileName("invoice.pdf")
                .mimeType(MimeType.PDF)
                .build();
    }
}