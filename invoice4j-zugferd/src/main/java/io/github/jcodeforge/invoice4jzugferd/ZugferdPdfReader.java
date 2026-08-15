package io.github.jcodeforge.invoice4jzugferd;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public final class ZugferdPdfReader {

    private static final String XML_FILENAME = "factur-x.xml";

    private static final String FACTUR_X_NAMESPACE =
            "urn:factur-x:pdfa:CrossIndustryDocument:invoice:1p0#";

    private final ZugferdInvoiceReader xmlReader;

    private final boolean validate;

    private ZugferdPdfReader(Builder builder) {
        this.validate = builder.validate;
        this.xmlReader = ZugferdInvoiceReader.builder()
                .validate(validate)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public Invoice read(File pdfFile) {
        Objects.requireNonNull(pdfFile, "pdfFile must not be null");

        String xml = extractXml(pdfFile);

        // todo validate metadata ??

        // The embedded XML is validated by ZugferdInvoiceReader.
        return xmlReader.readFromString(xml);
    }

    /**
     * Extracts the embedded ZUGFeRD / Factur-X XML document.
     *
     * @param pdfFile the ZUGFeRD PDF
     * @return the embedded CII XML
     * @throws ZugferdPdfException if the XML cannot be extracted
     */
    private String extractXml(File pdfFile) {
        Objects.requireNonNull(pdfFile, "Pdf file must not be null");

        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            PDEmbeddedFile embeddedFile = getEmbeddedFile(document);

            if (embeddedFile == null) {
                throw new ZugferdPdfException("Embedded " + XML_FILENAME + " has no file data.");
            }

            try (InputStream inputStream = embeddedFile.createInputStream()) {
                return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            }

        } catch (IOException e) {
            throw new ZugferdPdfException("Unable to extract ZUGFeRD XML from PDF.", e);
        }
    }

    /**
     * Reads a value from the Factur-X XMP metadata.
     *
     * This method is currently private because PDF metadata
     * is an implementation detail of the high-level reader API.
     */
    private String readMetadataValue(File pdfFile, String elementName) {
        try (PDDocument document = Loader.loadPDF(pdfFile)) {
            if (document.getDocumentCatalog().getMetadata() == null) {
                throw new DeserializationException("PDF does not contain XMP metadata.");
            }

            byte[] metadata =
                    document.getDocumentCatalog()
                            .getMetadata()
                            .toByteArray();

            Document xmp = parseXmp(metadata);

            Node node = xmp.getElementsByTagNameNS(FACTUR_X_NAMESPACE, elementName).item(0);

            if (node == null) {
                return null;
            }

            String value = node.getTextContent();

            return value == null || value.isBlank() ? null : value.trim();

        } catch (Exception e) {
            throw new DeserializationException("Unable to read ZUGFeRD PDF metadata.", e);
        }
    }

    /**
     * Returns the embedded Factur-X XML file.
     */
    private PDEmbeddedFile getEmbeddedFile(PDDocument document) throws IOException {
        PDDocumentNameDictionary names = document.getDocumentCatalog().getNames();

        if (names == null) {
            throw new ZugferdPdfException("PDF does not contain embedded files.");
        }

        PDEmbeddedFilesNameTreeNode embeddedFiles = names.getEmbeddedFiles();

        if (embeddedFiles == null) {
            throw new ZugferdPdfException("PDF does not contain embedded files.");
        }

        Map<String, PDComplexFileSpecification> files = embeddedFiles.getNames();

        if (files == null || !files.containsKey(XML_FILENAME)) {
            throw new ZugferdPdfException("PDF does not contain " + XML_FILENAME + ".");
        }

        PDComplexFileSpecification fileSpec = files.get(XML_FILENAME);

        return fileSpec.getEmbeddedFile();
    }

    private Document parseXmp(byte[] metadata) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

        factory.setFeature(
                "http://apache.org/xml/features/disallow-doctype-decl",
                true
        );

        factory.setFeature(
                "http://xml.org/sax/features/external-general-entities",
                false
        );

        factory.setFeature(
                "http://xml.org/sax/features/external-parameter-entities",
                false
        );

        factory.setXIncludeAware(false);
        factory.setExpandEntityReferences(false);

        return factory.newDocumentBuilder()
                .parse(new InputSource(new StringReader(new String(metadata, StandardCharsets.UTF_8))));
    }

    public static final class Builder {

        private boolean validate = true;

        public Builder validate(boolean validate) {
            this.validate = validate;
            return this;
        }

        public ZugferdPdfReader build() {
            return new ZugferdPdfReader(this);
        }
    }
}