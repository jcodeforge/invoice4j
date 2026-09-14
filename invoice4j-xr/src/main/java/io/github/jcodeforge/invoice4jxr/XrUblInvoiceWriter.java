package io.github.jcodeforge.invoice4jxr;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.ubl.UblInvoiceWriter;
import io.github.jcodeforge.invoice4jbase.ubl.UblProfile;
import io.github.jcodeforge.invoice4jxr.exceptions.KositValidationException;
import io.github.jcodeforge.invoice4jxr.validation.KositValidator;
import io.github.jcodeforge.invoice4jxr.validation.ValidationResult;
import java.io.File;
import java.util.Objects;

public final class XrUblInvoiceWriter {

    private final XrProfile profile;

    private final UblInvoiceWriter ublWriter;

    private final KositValidator kositValidator = new KositValidator();

    private final boolean validate;

    private XrUblInvoiceWriter(Builder builder) {
        this.profile = builder.profile;
        this.validate = builder.validate;

        this.ublWriter = UblInvoiceWriter.builder()
                .profile(toUblProfile(builder.profile))
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
     * Writes an invoice as XRechnung UBL XML.
     *
     * @param invoice invoice to serialize
     * @param file destination file
     */
    public void writeToFile(Invoice invoice, File file) {
        Objects.requireNonNull(invoice, "invoice must not be null");
        Objects.requireNonNull(file, "file must not be null");

        String xml = ublWriter.writeToString(invoice);

        if (validate) {
            validateXml(xml);
            validateInvoice(xml);
        }

        ublWriter.writeToFile(xml, file);
    }

    /**
     * Serializes an invoice to XRechnung UBL XML.
     *
     * @param invoice invoice to serialize
     * @return generated XML
     */
    public String writeToString(Invoice invoice) {
        Objects.requireNonNull(invoice, "invoice must not be null");

        String xml = ublWriter.writeToString(invoice);

        if (validate) {
            validateXml(xml);
            validateInvoice(xml);
        }

        return xml;
    }

    /**
     * Returns the configured XRechnung profile.
     *
     * @return XRechnung profile
     */
    public XrProfile getProfile() {
        return profile;
    }

    private static UblProfile toUblProfile(XrProfile profile) {
        Objects.requireNonNull(profile, "profile must not be null");

        return switch (profile) {
            case  XRECHNUNG -> UblProfile.XRECHNUNG;
        };
    }

    private void validateInvoice(String xml) {
        ValidationResult result = kositValidator.validate(xml);

        if (!result.isValid()) {
            throw new KositValidationException(result);
        }
    }

    private void validateXml(String xml) {
        // todo
    }

    /**
     * Builder for {@link XrUblInvoiceWriter}.
     */
    public static final class Builder {

        private XrProfile profile = XrProfile.XRECHNUNG;

        private boolean prettyPrint;

        private boolean validate = true;

        /**
         * Sets the XRechnung profile.
         *
         * @param profile target profile
         * @return this builder
         */
        public Builder profile(XrProfile profile) {
            this.profile = Objects.requireNonNull(
                    profile,
                    "profile must not be null"
            );
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
         * Enables or disables validation.
         *
         * @param validate whether generated XML should be validated
         * @return this builder
         */
        public Builder validate(boolean validate) {
            this.validate = validate;
            return this;
        }

        /**
         * Builds a new writer.
         *
         * @return configured writer
         */
        public XrUblInvoiceWriter build() {
            return new XrUblInvoiceWriter(this);
        }
    }
}