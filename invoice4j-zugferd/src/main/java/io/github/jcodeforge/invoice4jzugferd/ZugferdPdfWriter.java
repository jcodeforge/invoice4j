package io.github.jcodeforge.invoice4jzugferd;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.pdf.ZugferdPdfFileHandler;
import io.github.jcodeforge.invoice4jzugferd.pdf.ZugferdXmpMetadata;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ZugferdPdfWriter {

    private final ZugferdPdfFileHandler pdfFileHandler = new ZugferdPdfFileHandler();

    private final ZugferdXmpMetadata xmpMetadata = new ZugferdXmpMetadata();

    private final Invoice invoice;

    private final ZugferdProfile profile;

    private final ZugferdInvoiceWriter xmlWriter;

    private ZugferdPdfWriter(Builder builder) {
        this.invoice = builder.invoice;
        this.profile = builder.profile;

        this.xmlWriter = ZugferdInvoiceWriter.builder()
                .profile(profile)
                .prettyPrint(builder.prettyPrintXml)
                .validate(builder.validate)
                .build();
    }

    public void write(File inputPdf, File outputPdf) {
        Objects.requireNonNull(inputPdf, "Input Pdf must not be null");
        Objects.requireNonNull(outputPdf, "Output Pdf must not be null");

        String xml = xmlWriter.writeToString(invoice);

        try (PDDocument document = Loader.loadPDF(inputPdf)) {
            /*
             * Prepare the source PDF as PDF/A-3.
             */
            pdfFileHandler.prepare(document);

            /*
             * Embed the ZUGFeRD CII XML.
             */
            embedInvoiceXml(document, xml);

            /*
             * TODO:
             *
             * Merge ZUGFeRD/Factur-X XMP metadata with
             * the PDF/A-3 XMP metadata created by PdfA3Converter.
             */
            addZugferdMetadata(document);

            document.save(outputPdf);

        } catch (IOException e) {
            throw new ZugferdPdfException("Unable to create ZUGFeRD PDF.", e);
        }
    }

    private void addZugferdMetadata(PDDocument document) {
        String metadata = xmpMetadata.create(invoice, profile);
        PDMetadata pdMetadata = new PDMetadata(document);

        try {
            pdMetadata.importXMPMetadata(metadata.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new ZugferdPdfException("Unable to add ZUGFeRD XMP metadata.", e);
        }

        document.getDocumentCatalog().setMetadata(pdMetadata);
    }

    private void embedInvoiceXml(PDDocument document, String xml) throws IOException {
        byte[] xmlBytes = xml.getBytes(StandardCharsets.UTF_8);

        PDComplexFileSpecification fileSpec = new PDComplexFileSpecification();

        fileSpec.setFile("factur-x.xml");
        fileSpec.setFileUnicode("factur-x.xml");

        fileSpec.getCOSObject().setName(COSName.AF_RELATIONSHIP, "Alternative");

        PDEmbeddedFile embeddedFile = new PDEmbeddedFile(document,
                new ByteArrayInputStream(xmlBytes));

        embeddedFile.setSubtype("text/xml");
        embeddedFile.setSize(xmlBytes.length);

        GregorianCalendar now = new GregorianCalendar();
        embeddedFile.setCreationDate(now);
        embeddedFile.setModDate(now);

        fileSpec.setEmbeddedFile(embeddedFile);

        PDDocumentNameDictionary names = document.getDocumentCatalog().getNames();

        if (names == null) {
            names = new PDDocumentNameDictionary(document.getDocumentCatalog());
            document.getDocumentCatalog().setNames(names);
        }

        PDEmbeddedFilesNameTreeNode embeddedFiles = names.getEmbeddedFiles();

        if (embeddedFiles == null) {
            embeddedFiles = new PDEmbeddedFilesNameTreeNode();
            names.setEmbeddedFiles(embeddedFiles);
        }

        Map<String, PDComplexFileSpecification> files = embeddedFiles.getNames();

        if (files == null) {
            files = new HashMap<>();
        }

        files.put("factur-x.xml", fileSpec);

        embeddedFiles.setNames(files);

        COSArray afArray = (COSArray) document.getDocumentCatalog().getCOSObject().getDictionaryObject(COSName.AF);

        if (afArray == null) {
            afArray = new COSArray();

            document.getDocumentCatalog()
                    .getCOSObject()
                    .setItem(COSName.AF, afArray);
        }

        afArray.add(fileSpec.getCOSObject());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Invoice invoice;
        private ZugferdProfile profile;
        private boolean prettyPrintXml;
        private boolean validate = true;

        public Builder invoice(Invoice invoice) {
            this.invoice = Objects.requireNonNull(invoice, "invoice must not be null");
            return this;
        }

        public Builder profile(ZugferdProfile profile) {
            this.profile = Objects.requireNonNull(profile, "profile must not be null");
            return this;
        }

        public Builder prettyPrintXml(boolean prettyPrint) {
            this.prettyPrintXml = prettyPrint;
            return this;
        }

        public Builder validate(boolean validate) {
            this.validate = validate;
            return this;
        }

        public ZugferdPdfWriter build() {
            if (invoice == null) {
                throw new IllegalStateException("invoice must be configured");
            }

            if (profile == null) {
                throw new IllegalStateException("profile must be configured");
            }

            return new ZugferdPdfWriter(this);
        }
    }
}
