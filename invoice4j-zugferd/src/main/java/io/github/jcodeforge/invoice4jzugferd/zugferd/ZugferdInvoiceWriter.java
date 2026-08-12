package io.github.jcodeforge.invoice4jzugferd.zugferd;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiInvoiceWriter;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiProfile;
import java.io.File;
import java.util.Objects;

public final class ZugferdInvoiceWriter {

    private final ZugferdProfile profile;

    private final CiiInvoiceWriter ciiWriter;

    private ZugferdInvoiceWriter(Builder builder) {
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
     * Writes an invoice as ZUGFeRD CII XML.
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
     * Serializes an invoice to ZUGFeRD CII XML.
     *
     * @param invoice invoice to serialize
     * @return generated XML
     */
    public String writeToString(Invoice invoice) {
        Objects.requireNonNull(invoice, "invoice must not be null");

        return ciiWriter.writeToString(invoice);
    }

    /**
     * Returns the configured ZUGFeRD profile.
     *
     * @return ZUGFeRD profile
     */
    public ZugferdProfile getProfile() {
        return profile;
    }

    private static CiiProfile toCiiProfile(ZugferdProfile profile) {
        Objects.requireNonNull(profile, "profile must not be null");

        return switch (profile) {
            case EN16931 -> CiiProfile.ZUGFERD_EN16931;
            case BASIC -> CiiProfile.ZUGFERD_BASIC;
            case BASIC_WL -> CiiProfile.ZUGFERD_BASIC_WL;
            case MINIMUM -> CiiProfile.ZUGFERD_MINIMUM;
            case EXTENDED -> throw new UnsupportedOperationException("ZUGFeRD EXTENDED profile is not implemented yet.");
        };
    }

    /**
     * Builder for {@link ZugferdInvoiceWriter}.
     */
    public static final class Builder {

        private ZugferdProfile profile = ZugferdProfile.EN16931;
        private boolean prettyPrint;

        /**
         * Sets the ZUGFeRD profile.
         *
         * @param profile target profile
         * @return this builder
         */
        public Builder profile(ZugferdProfile profile) {
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
        public ZugferdInvoiceWriter build() {
            return new ZugferdInvoiceWriter(this);
        }
    }
}