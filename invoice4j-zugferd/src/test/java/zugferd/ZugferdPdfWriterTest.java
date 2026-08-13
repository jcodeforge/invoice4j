package zugferd;

import factory.TestInvoiceFactory;
import io.github.jcodeforge.invoice4jbase.calculation.InvoiceCalculator;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdPdfWriter;
import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdProfile;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.Assert.*;

public class ZugferdPdfWriterTest {

    private ZugferdPdfWriter SUT;

    @Before
    public void setUp() {
        Invoice invoice = new InvoiceCalculator().calculate(TestInvoiceFactory.createCompleteInvoice());

        SUT = ZugferdPdfWriter.builder()
                .invoice(invoice)
                .profile(ZugferdProfile.EXTENDED)
                .build();
    }

    @Test
    public void shouldWriteZugferdPdf() throws Exception {
        File inputPdf = createInputPdf();
        File outputPdf = createOutputPdf();

        SUT.write(inputPdf, outputPdf);

        assertTrue(outputPdf.exists());
        assertTrue(outputPdf.length() > 0);

        assertTrue(inputPdf.delete());
        assertTrue(outputPdf.delete());
    }

    @Test
    public void shouldEmbedFacturXXmlWithCorrectMetadata() throws Exception {
        File inputPdf = createInputPdf();
        File outputPdf = createOutputPdf();

        SUT.write(inputPdf, outputPdf);

        try (PDDocument document = Loader.loadPDF(outputPdf)) {
            PDDocumentNameDictionary names =
                    document.getDocumentCatalog().getNames();

            assertNotNull(names);

            PDEmbeddedFilesNameTreeNode embeddedFiles = names.getEmbeddedFiles();
            assertNotNull(embeddedFiles);

            Map<String, PDComplexFileSpecification> files = embeddedFiles.getNames();

            assertNotNull(files);
            assertTrue(files.containsKey("factur-x.xml"));

            PDComplexFileSpecification fileSpec = files.get("factur-x.xml");

            assertEquals("Alternative", fileSpec.getCOSObject().getNameAsString(COSName.AF_RELATIONSHIP));

            PDEmbeddedFile embeddedFile = fileSpec.getEmbeddedFile();

            assertNotNull(embeddedFile);
            assertEquals("application/xml", embeddedFile.getSubtype());
            assertTrue(embeddedFile.getSize() > 0);
        }

        assertTrue(inputPdf.delete());
        assertTrue(outputPdf.delete());
    }

    @Test
    public void shouldAddAssociatedFileToCatalog() throws Exception {
        File inputPdf = createInputPdf();
        File outputPdf = createOutputPdf();

        SUT.write(inputPdf, outputPdf);

        try (PDDocument document = Loader.loadPDF(outputPdf)) {
            COSArray af = (COSArray) document.getDocumentCatalog()
                    .getCOSObject()
                    .getDictionaryObject(COSName.AF);

            assertNotNull(af);
            assertEquals(1, af.size());

            COSDictionary fileSpecification = (COSDictionary) af.getObject(0);

            assertEquals("Alternative", fileSpecification.getNameAsString(COSName.AF_RELATIONSHIP));
        }

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
}