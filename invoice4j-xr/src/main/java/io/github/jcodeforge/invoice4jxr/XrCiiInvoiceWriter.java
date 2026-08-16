package io.github.jcodeforge.invoice4jxr;

import io.github.jcodeforge.invoice4jbase.cii.CiiInvoiceWriter;
import io.github.jcodeforge.invoice4jbase.cii.CiiProfile;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.validation.Cii16BXsdValidator;
import java.io.File;
import java.util.Objects;

public final class XrCiiInvoiceWriter {

    private final XrProfile profile;

    private final CiiInvoiceWriter ciiWriter;

    private final boolean validate;

    private XrCiiInvoiceWriter(Builder builder) {
        this.profile = builder.profile;
        this.validate = builder.validate;

        this.ciiWriter = CiiInvoiceWriter.builder()
                .profile(toCiiProfile(profile))
                .prettyPrint(builder.prettyPrint)
                .build();
    }

    /**
     * Creates a new builder.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Writes an invoice as Xrechnung CII XML.
     *
     * @param invoice invoice to serialize
     * @param file destination file
     */
    public void writeToFile(Invoice invoice, File file) {
        Objects.requireNonNull(invoice, "invoice must not be null");
        Objects.requireNonNull(file, "file must not be null");

        if (validate) {
            validateInvoice(invoice);
        }

        String xml = ciiWriter.writeToString(invoice);

        if (validate) {
            validateXml(xml);
        }

        ciiWriter.writeToFile(xml, file);
    }

    /**
     * Serializes an invoice to Xrechnung CII XML.
     *
     * @param invoice invoice to serialize
     * @return generated XML
     */
    public String writeToString(Invoice invoice) {
        Objects.requireNonNull(invoice, "invoice must not be null");

        if (validate) {
            validateInvoice(invoice);
        }

        String xml = ciiWriter.writeToString(invoice);

        if (validate) {
            validateXml(xml);
        }

        return xml;
    }

    /**
     * Returns the configured Xrechnung profile.
     *
     * @return Xrechnung profile
     */
    public XrProfile getProfile() {
        return profile;
    }

    private static CiiProfile toCiiProfile(XrProfile profile) {
        Objects.requireNonNull(profile, "profile must not be null");

        return switch (profile) {
            case  XRECHNUNG -> CiiProfile.XRECHNUNG;
        };
    }

    // Todo add business logic validation here
    private void validateInvoice(Invoice invoice) {
    }

    private void validateXml(String xml) {
        switch (profile) {
            case XRECHNUNG -> new Cii16BXsdValidator().validate(xml);
        }
    }

    /**
     * Builder for {@link XrCiiInvoiceWriter}.
     */
    public static final class Builder {

        private XrProfile profile = XrProfile.XRECHNUNG;
        private boolean prettyPrint;
        private boolean validate = true;

        /**
         * Sets the Xrechnung profile.
         *
         * @param profile target profile
         * @return this builder
         */
        public Builder profile(XrProfile profile) {
            this.profile = Objects.requireNonNull(profile, "profile must not be null");
            return this;
        }

        /**
         * Enables or disables pretty printing.
         *
         * @param prettyPrint whether XML should be formatted
         * @return this builder
         */
        public Builder prettyPrint(boolean prettyPrint) {
            this.prettyPrint = prettyPrint;
            return this;
        }

        public Builder validate(boolean validate) {
            this.validate = validate;
            return this;
        }

        /**
         * Builds a new writer.
         *
         * @return configured writer
         */
        public XrCiiInvoiceWriter build() {
            return new XrCiiInvoiceWriter(this);
        }
    }
}