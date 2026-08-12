package zugferd;

import io.github.jcodeforge.invoice4jzugferd.zugferd.ZugferdPdfWriter;
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
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.Assert.*;

public class ZugferdPdfWriterTest {

    private final ZugferdPdfWriter SUT = ZugferdPdfWriter.builder().build();

    @Test
    public void shouldEmbedXml() throws Exception {
        File inputPdf = createInputPdf();
        File outputPdf = createOutputPdf();

        String xml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <test>Invoice XML</test>
                """;

        SUT.embedXml(inputPdf, xml, outputPdf);

        assertTrue(outputPdf.exists());
        assertTrue(outputPdf.length() > 0);

        inputPdf.delete();
        outputPdf.delete();
    }

    @Test
    public void shouldEmbedFacturXXmlWithCorrectMetadata() throws Exception {
        File inputPdf = createInputPdf();
        File outputPdf = createOutputPdf();

        String xml = """
            <?xml version="1.0" encoding="UTF-8"?>
            <invoice>
                <id>INV-001</id>
            </invoice>
            """;

        SUT.embedXml(inputPdf, xml, outputPdf);

        try (PDDocument document = Loader.loadPDF(outputPdf)) {
            PDDocumentNameDictionary names = document.getDocumentCatalog().getNames();

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
            assertEquals(xml.getBytes(StandardCharsets.UTF_8).length, embeddedFile.getSize());
        }

        inputPdf.delete();
        outputPdf.delete();
    }

    @Test
    public void shouldAddAssociatedFileToCatalog() throws Exception {
        File inputPdf = createInputPdf();
        File outputPdf = createOutputPdf();

        String xml = "<invoice><id>INV-001</id></invoice>";

        SUT.embedXml(inputPdf, xml, outputPdf);

        try (PDDocument document = Loader.loadPDF(outputPdf)) {
            COSArray af = (COSArray) document.getDocumentCatalog()
                    .getCOSObject()
                    .getDictionaryObject(COSName.AF);

            assertNotNull(af);
            assertEquals(1, af.size());

            COSDictionary fileSpecification = (COSDictionary) af.getObject(0);

            assertEquals("Alternative", fileSpecification.getNameAsString(COSName.AF_RELATIONSHIP));
        }

        inputPdf.delete();
        outputPdf.delete();
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