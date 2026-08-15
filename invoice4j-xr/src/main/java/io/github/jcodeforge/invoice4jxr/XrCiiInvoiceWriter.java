package io.github.jcodeforge.invoice4jxr;

import io.github.jcodeforge.invoice4jbase.cii.CiiInvoiceWriter;
import io.github.jcodeforge.invoice4jbase.cii.CiiProfile;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import java.io.File;
import java.util.Objects;

public final class XrCiiInvoiceWriter {

    private final XrProfile profile;

    private final CiiInvoiceWriter ciiWriter;

    private XrCiiInvoiceWriter(Builder builder) {
        this.profile = builder.profile;

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

        ciiWriter.writeToFile(invoice, file);
    }

    /**
     * Serializes an invoice to Xrechnung CII XML.
     *
     * @param invoice invoice to serialize
     * @return generated XML
     */
    public String writeToString(Invoice invoice) {
        Objects.requireNonNull(invoice, "invoice must not be null");

        return ciiWriter.writeToString(invoice);
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

    /**
     * Builder for {@link XrCiiInvoiceWriter}.
     */
    public static final class Builder {

        private XrProfile profile = XrProfile.XRECHNUNG;
        private boolean prettyPrint;

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