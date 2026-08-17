package io.github.jcodeforge.invoice4jzugferd.pdf;

import io.github.jcodeforge.invoice4jbase.utils.Invoice4jDateTimeUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;
import java.io.IOException;
import java.io.InputStream;
import java.util.GregorianCalendar;
import java.util.Objects;

public final class ZugferdPdfFileHandler {

    private static final float PDF_VERSION = 1.7f;

    private static final String SRGB_PROFILE = "/pdf/sRGB.icc";

    /**
     * Prepares the supplied PDF document as a PDF/A-3b document.
     *
     * <p>
     * The document is modified in-place and is not saved by this method.
     *
     * @param document PDF document to prepare
     */
    public void prepare(PDDocument document) {
        Objects.requireNonNull(document, "document must not be null");

        try {
            prepareDocumentInformation(document);
            addOutputIntent(document);

            document.setVersion(PDF_VERSION);

        } catch (IOException e) {
            throw new PdfA3Exception("Unable to prepare PDF as PDF/A-3.", e);
        }
    }

    private void prepareDocumentInformation(PDDocument document) {
        PDDocumentInformation source = document.getDocumentInformation();
        PDDocumentInformation information = new PDDocumentInformation();

        information.setProducer(source.getProducer() != null ? source.getProducer() : "invoice4j");

        if (source.getTitle() != null) {
            information.setTitle(source.getTitle());
        }

        if (source.getAuthor() != null) {
            information.setAuthor(source.getAuthor());
        }

        if (source.getSubject() != null) {
            information.setSubject(source.getSubject());
        }

        if (source.getKeywords() != null) {
            information.setKeywords(source.getKeywords());
        }

        if (source.getCreator() != null) {
            information.setCreator(source.getCreator());
        }

        GregorianCalendar creationDate =
                Invoice4jDateTimeUtils.toGregorianCalendar(source.getCreationDate());

        if (creationDate != null) {
            information.setCreationDate(creationDate);
        }

        GregorianCalendar modificationDate =
                Invoice4jDateTimeUtils.toGregorianCalendar(source.getModificationDate());

        if (modificationDate != null) {
            information.setModificationDate(modificationDate);
        }

        document.setDocumentInformation(information);
    }

    private void addOutputIntent(PDDocument document) throws IOException {
        PDDocumentCatalog catalog = document.getDocumentCatalog();

        if (!catalog.getOutputIntents().isEmpty()) {
            return;
        }

        try (InputStream colorProfile = openColorProfile()) {
            PDOutputIntent outputIntent = new PDOutputIntent(document, colorProfile);
            outputIntent.setInfo("sRGB IEC61966-2.1");
            outputIntent.setOutputCondition("sRGB IEC61966-2.1");
            outputIntent.setOutputConditionIdentifier("sRGB IEC61966-2.1");
            outputIntent.setRegistryName("http://www.color.org");
            catalog.addOutputIntent(outputIntent);
        }
    }

    private InputStream openColorProfile() {
        InputStream input = ZugferdPdfFileHandler.class.getResourceAsStream(SRGB_PROFILE);

        if (input == null) {
            throw new PdfA3Exception("Unable to load embedded sRGB ICC profile.");
        }

        return input;
    }
}