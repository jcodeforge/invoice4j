package io.github.jcodeforge.invoice4jzugferd.zugferd;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public final class ZugferdPdfReader {

    private static final String XML_FILENAME = "factur-x.xml";

    private ZugferdPdfReader() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public String extractXml(File pdfFile) {
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

    public static final class Builder {

        public ZugferdPdfReader build() {
            return new ZugferdPdfReader();
        }
    }
}