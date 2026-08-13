package zugferd;

import factory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.zugferd.*;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public final class ZugferdPdfReaderTest {

    private final ZugferdPdfReader SUT = ZugferdPdfReader.builder().build();

    @Test
    public void shouldReadInvoiceFromZugferdPdf() throws Exception {
        File inputPdf = createInputPdf();
        File outputPdf = createOutputPdf();

        Invoice invoice = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        ZugferdPdfWriter.builder()
                .invoice(invoice)
                .profile(ZugferdProfile.EXTENDED)
                .build()
                .write(inputPdf, outputPdf);

        Invoice readInvoice = SUT.read(outputPdf);

        assertNotNull(readInvoice);
        assertEquals(invoice.getInvoiceNumber(), readInvoice.getInvoiceNumber());
        assertTrue(inputPdf.delete());
        assertTrue(outputPdf.delete());
    }

    private File createInputPdf() throws IOException {
        File file = File.createTempFile("zugferd-input-", ".pdf");

        try (PDDocument document = new PDDocument()) {
            document.addPage(new PDPage());
            document.save(file);
        }

        return file;
    }

    private File createOutputPdf() throws IOException {
        return File.createTempFile("zugferd-output-", ".pdf");
    }

    @Test(expected = ZugferdPdfException.class)
    public void shouldRejectPdfWithoutEmbeddedFiles() throws Exception {
        File inputPdf = createInputPdf();

        try {
            SUT.read(inputPdf);
        } finally {
            assertTrue(inputPdf.delete());
        }
    }

    @Test(expected = ZugferdPdfException.class)
    public void shouldRejectPdfWithoutFacturXXml() throws Exception {
        File inputPdf = createInputPdf();
        File outputPdf = createOutputPdf();

        try {
            // Add an embedded file with a different name.
            embedOtherFile(inputPdf, outputPdf);

            SUT.read(outputPdf);

        } finally {
            assertTrue(inputPdf.delete());
            assertTrue(outputPdf.delete());
        }
    }

    @Test(expected = ZugferdPdfException.class)
    public void shouldRejectFacturXXmlWithoutEmbeddedData() throws Exception {
        File inputPdf = createInputPdf();
        File outputPdf = createOutputPdf();

        try {
            createEmptyFacturXAttachment(inputPdf, outputPdf);

            SUT.read(outputPdf);

        } finally {
            assertTrue(inputPdf.delete());
            assertTrue(outputPdf.delete());
        }
    }

    @Test(expected = ZugferdPdfException.class)
    public void shouldRejectInvalidPdf() throws Exception {
        File invalidPdf = createInputPdf();
        try {
            Files.writeString(invalidPdf.toPath(), "This is not a PDF");

            SUT.read(invalidPdf);

        } finally {
            invalidPdf.delete();
        }
    }

    private void embedOtherFile(File inputPdf, File outputPdf) throws IOException {
        try (PDDocument document = Loader.loadPDF(inputPdf)) {

            PDDocumentNameDictionary names = document.getDocumentCatalog().getNames();

            if (names == null) {
                names = new PDDocumentNameDictionary(document.getDocumentCatalog());
                document.getDocumentCatalog().setNames(names);
            }

            PDEmbeddedFilesNameTreeNode embeddedFiles = new PDEmbeddedFilesNameTreeNode();

            names.setEmbeddedFiles(embeddedFiles);

            PDComplexFileSpecification fileSpec = new PDComplexFileSpecification();

            fileSpec.setFile("other.xml");

            byte[] data = "<test/>".getBytes(StandardCharsets.UTF_8);

            PDEmbeddedFile embeddedFile = new PDEmbeddedFile(document, new ByteArrayInputStream(data));

            embeddedFile.setSubtype("application/xml");
            embeddedFile.setSize(data.length);

            fileSpec.setEmbeddedFile(embeddedFile);

            Map<String, PDComplexFileSpecification> files = new HashMap<>();

            files.put("other.xml", fileSpec);

            embeddedFiles.setNames(files);

            document.save(outputPdf);
        }
    }

    @Test
    public void shouldRoundTripInvoiceThroughPdf() throws Exception {
        Invoice original = new InvoiceCalculator()
                .calculate(TestInvoiceFactory.createCompleteInvoice());

        File inputPdf = createInputPdf();
        File outputPdf = createOutputPdf();

        try {
            ZugferdPdfWriter.builder()
                    .invoice(original)
                    .profile(ZugferdProfile.EXTENDED)
                    .build()
                    .write(inputPdf, outputPdf);

            Invoice parsed = SUT.read(outputPdf);

            assertNotNull(parsed);

            // Verify important invoice data survived the PDF round trip
            assertEquals(original.getInvoiceNumber(), parsed.getInvoiceNumber());
            assertEquals(original.getIssueDate(), parsed.getIssueDate());
            assertEquals(original.getCurrency().getCode(), parsed.getCurrency().getCode());
            assertEquals(original.getLines().size(), parsed.getLines().size());

        } finally {
            assertTrue(inputPdf.delete());
            assertTrue(outputPdf.delete());
        }
    }

    private void createEmptyFacturXAttachment(
            File inputPdf,
            File outputPdf) throws IOException {

        try (PDDocument document = Loader.loadPDF(inputPdf)) {
            PDDocumentNameDictionary names = new PDDocumentNameDictionary(document.getDocumentCatalog());

            document.getDocumentCatalog().setNames(names);

            PDEmbeddedFilesNameTreeNode embeddedFiles = new PDEmbeddedFilesNameTreeNode();

            names.setEmbeddedFiles(embeddedFiles);

            PDComplexFileSpecification fileSpec = new PDComplexFileSpecification();

            fileSpec.setFile("factur-x.xml");

            Map<String, PDComplexFileSpecification> files = new HashMap<>();

            files.put("factur-x.xml", fileSpec);

            embeddedFiles.setNames(files);

            document.save(outputPdf);
        }
    }

    @Test
    public void shouldReadExtendedSamplePdf() throws Exception {
        File pdf = new File(
                getClass()
                        .getResource("/zugferd/Extended/X11_01_Kostenrechnung_fx.pdf")
                        .toURI()
        );

        Invoice invoice = SUT.read(pdf);

        assertNotNull(invoice);
        assertNotNull(invoice.getInvoiceNumber());
        assertNotNull(invoice.getIssueDate());
        assertNotNull(invoice.getCurrency());
    }

    @Test
    public void shouldReadBasicSamplePdf() throws Exception {
        File pdf = new File(
                getClass()
                        .getResource("/zugferd/basic/B01_01_Einfach_fx.pdf")
                        .toURI()
        );

        Invoice invoice = SUT.read(pdf);

        assertNotNull(invoice);
        assertNotNull(invoice.getInvoiceNumber());
        assertNotNull(invoice.getIssueDate());
        assertNotNull(invoice.getCurrency());
    }

    @Test
    public void shouldReadBasicwlSamplePdf() throws Exception {
        File pdf = new File(
                getClass()
                        .getResource("/zugferd/basicwl/BASIC-WL_Einfach_fx.pdf")
                        .toURI()
        );

        Invoice invoice = SUT.read(pdf);

        assertNotNull(invoice);
        assertNotNull(invoice.getInvoiceNumber());
        assertNotNull(invoice.getIssueDate());
        assertNotNull(invoice.getCurrency());
    }

    @Test
    public void shouldReadEn16931SamplePdf() throws Exception {
        File pdf = new File(
                getClass()
                        .getResource("/zugferd/en16931/E05_01_Einfach_fx.pdf")
                        .toURI()
        );

        Invoice invoice = SUT.read(pdf);

        assertNotNull(invoice);
        assertNotNull(invoice.getInvoiceNumber());
        assertNotNull(invoice.getIssueDate());
        assertNotNull(invoice.getCurrency());
    }

    @Test
    public void shouldReadMinimumSamplePdf() throws Exception {
        File pdf = new File(
                getClass()
                        .getResource("/zugferd/minimum/MINIMUM_Rechnung_fx.pdf")
                        .toURI()
        );

        Invoice invoice = SUT.read(pdf);

        assertNotNull(invoice);
        assertNotNull(invoice.getInvoiceNumber());
        assertNotNull(invoice.getIssueDate());
        assertNotNull(invoice.getCurrency());
    }
}
