package io.github.jcodeforge.invoice4jzugferd.zugferd;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
import org.apache.pdfbox.pdmodel.common.filespecification.PDComplexFileSpecification;
import org.apache.pdfbox.pdmodel.common.filespecification.PDEmbeddedFile;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ZugferdPdfWriter {

    private ZugferdPdfWriter() {
    }

    public void embedXml(File inputPdf, String xml, File outputPdf) {
        Objects.requireNonNull(inputPdf, "Input Pdf must not be null");
        Objects.requireNonNull(xml, "Xml must not be null");
        Objects.requireNonNull(outputPdf, "Output Pdf must not be null");

        try (PDDocument document = Loader.loadPDF(inputPdf)) {

            byte[] xmlBytes = xml.getBytes(StandardCharsets.UTF_8);

            PDComplexFileSpecification fileSpec = new PDComplexFileSpecification();
            fileSpec.setFile("factur-x.xml");

            fileSpec.getCOSObject().setName(
                    COSName.AF_RELATIONSHIP,
                    "Alternative"
            );

            PDEmbeddedFile embeddedFile = new PDEmbeddedFile(document, new ByteArrayInputStream(xmlBytes));
            embeddedFile.setSubtype("application/xml");
            embeddedFile.setSize(xmlBytes.length);

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

            Map<String, PDComplexFileSpecification> files =
                    embeddedFiles.getNames();

            if (files == null) {
                files = new HashMap<>();
            }

            files.put("factur-x.xml", fileSpec);

            embeddedFiles.setNames(files);

            COSArray afArray = (COSArray) document.getDocumentCatalog()
                    .getCOSObject().getDictionaryObject(COSName.AF);

            if (afArray == null) {
                afArray = new COSArray();

                document.getDocumentCatalog()
                        .getCOSObject()
                        .setItem(COSName.AF, afArray);
            }

            afArray.add(fileSpec.getCOSObject());

            document.save(outputPdf);

        } catch (IOException e) {
            throw new ZugferdPdfException("Unable to embed ZUGFeRD XML into PDF.", e);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        public ZugferdPdfWriter build() {
            return new ZugferdPdfWriter();
        }
    }
}
