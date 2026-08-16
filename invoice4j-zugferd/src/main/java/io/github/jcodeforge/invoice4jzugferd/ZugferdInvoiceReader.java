package io.github.jcodeforge.invoice4jzugferd;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.cii.CiiInvoiceReader;
import io.github.jcodeforge.invoice4jbase.exceptions.DeserializationException;
import io.github.jcodeforge.invoice4jzugferd.validation.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

public class ZugferdInvoiceReader {

    private final CiiInvoiceReader ciiReader;

    private final ZugferdProfileDetector profileDetector = new ZugferdProfileDetector();

    private final boolean validate;

    private ZugferdInvoiceReader(Builder builder) {
        this.validate = builder.validate;
        this.ciiReader = CiiInvoiceReader.builder().build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private boolean validate = true;

        /**
         * Enables or disables ZUGFeRD validation.
         *
         * @param validate whether the XML should be validated
         * @return this builder
         */
        public Builder validate(boolean validate) {
            this.validate = validate;
            return this;
        }

        public ZugferdInvoiceReader build() {
            return new ZugferdInvoiceReader(this);
        }
    }

    /**
     * Reads a ZUGFeRD invoice from an XML input stream.
     *
     * @param inputStream the ZUGFeRD CII XML input stream
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

            throw new DeserializationException("Unable to read ZUGFeRD invoice from input stream.", e);
        }
    }

    /**
     * Reads a ZUGFeRD invoice from a file.
     *
     * @param file the ZUGFeRD XML file
     * @return the parsed invoice
     */
    public Invoice readFromFile(File file) {
        Objects.requireNonNull(file, "file must not be null");

        try {
            String xml = Files.readString(file.toPath(), StandardCharsets.UTF_8);

            return readFromString(xml);

        } catch (IOException e) {
            throw new DeserializationException("Unable to read ZUGFeRD invoice from file.", e);
        }
    }

    /**
     * Reads a ZUGFeRD invoice from an XML string.
     *
     * @param xml the ZUGFeRD CII XML document
     * @return the parsed invoice
     */
    public Invoice readFromString(String xml) {
        Objects.requireNonNull(xml, "xml must not be null");

        if (xml.isBlank()) {
            throw new DeserializationException("XML document must not be empty.");
        }

        ZugferdProfile profile = detectProfile(xml);

        if (validate) {
            validateXml(profile, xml);
        }

        return ciiReader.readFromString(xml);
    }

    public ZugferdProfile detectProfile(String xml) {
        Objects.requireNonNull(xml, "xml must not be null");

        return profileDetector.detect(xml);
    }

    private void validateXml(ZugferdProfile profile, String xml) {
        switch (profile) {
            case BASIC -> new ZugferdBasicXsdValidator().validate(xml);
            case BASIC_WL -> new ZugferdBasicwlXsdValidator().validate(xml);
            case MINIMUM -> new ZugferdMinimumXsdValidator().validate(xml);
            case EN16931 -> new ZugferdEn16931XsdValidator().validate(xml);
            case EXTENDED -> new ZugferdExtendedXsdValidator().validate(xml);
        }
    }
}
