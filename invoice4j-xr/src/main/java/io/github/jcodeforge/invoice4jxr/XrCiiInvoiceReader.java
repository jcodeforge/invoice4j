package io.github.jcodeforge.invoice4jxr;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.cii.CiiInvoiceReader;
import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import io.github.jcodeforge.invoice4jbase.validation.Cii16BXsdValidator;
import io.github.jcodeforge.invoice4jxr.exceptions.KositValidationException;
import io.github.jcodeforge.invoice4jxr.validation.KositValidator;
import io.github.jcodeforge.invoice4jxr.validation.ValidationResult;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

public class XrCiiInvoiceReader {

    private final CiiInvoiceReader ciiReader;

    private final XrProfileDetector profileDetector = new XrProfileDetector();

    private final KositValidator kositValidator = new KositValidator();

    private final boolean validate;

    private XrCiiInvoiceReader(Builder builder) {
        this.validate = builder.validate;
        this.ciiReader = CiiInvoiceReader.builder().build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private boolean validate = true;

        /**
         * Enables or disables XRechnung XML validation.
         *
         * @param validate whether the XML should be validated against the
         *                 XRechnung CII schema
         * @return this builder
         */
        public Builder validate(boolean validate) {
            this.validate = validate;
            return this;
        }

        public XrCiiInvoiceReader build() {
            return new XrCiiInvoiceReader(this);
        }
    }

    /**
     * Reads a Xrechnung invoice from an XML input stream.
     *
     * @param inputStream the Xrechnung CII XML input stream
     * @return the parsed invoice
     * @throws DeserializationException if the invoice cannot be read
     */
    public Invoice read(InputStream inputStream) {
        Objects.requireNonNull(inputStream, "inputStream must not be null");
        try {
            String xml = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            return readFromString(xml);

        } catch (Exception e) {
            if (e instanceof DeserializationException) {
                throw (DeserializationException) e;
            }

            throw new DeserializationException("Unable to read Xrechnung invoice from input stream.", e);
        }
    }

    /**
     * Reads a Xrechnung invoice from a file.
     *
     * @param file the Xrechnung XML file
     * @return the parsed invoice
     */
    public Invoice readFromFile(File file) {
        Objects.requireNonNull(file, "file must not be null");

        try {
            String xml = Files.readString(file.toPath(), StandardCharsets.UTF_8);

            return readFromString(xml);

        } catch (IOException e) {
            throw new DeserializationException("Unable to read Xrechnung invoice from file.", e);
        }
    }

    /**
     * Reads a Xrechnung invoice from an XML string.
     *
     * @param xml the Xrechnung CII XML document
     * @return the parsed invoice
     */
    public Invoice readFromString(String xml) {
        Objects.requireNonNull(xml, "xml must not be null");

        if (xml.isBlank()) {
            throw new DeserializationException("XML document must not be empty.");
        }

        XrProfile profile = detectProfile(xml);

        if (validate) {
            validateXml(profile, xml);
            validateInvoice(xml);
        }

        return ciiReader.readFromString(xml);
    }

    public XrProfile detectProfile(String xml) {
        Objects.requireNonNull(xml, "xml must not be null");

        return profileDetector.detect(xml);
    }

    private void validateXml(XrProfile profile, String xml) {
        switch (profile) {
            case XRECHNUNG -> new Cii16BXsdValidator().validate(xml);
        }
    }

    private void validateInvoice(String xml) {
        ValidationResult result = kositValidator.validate(xml);

        if (!result.isValid()) {
            throw new KositValidationException(result);
        }
    }
}

