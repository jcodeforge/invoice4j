package io.github.jcodeforge.invoice4jxr;

import io.github.jcodeforge.invoice4jbase.cii.CiiInvoiceWriter;
import io.github.jcodeforge.invoice4jbase.cii.CiiProfile;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.validation.Cii16BXsdValidator;
import io.github.jcodeforge.invoice4jxr.exceptions.KositValidationException;
import io.github.jcodeforge.invoice4jxr.validation.KositValidator;
import io.github.jcodeforge.invoice4jxr.validation.ValidationResult;
import java.io.File;
import java.util.Objects;


/**
 * Writer for XRechnung invoices using the CII syntax.
 *
 * <p>The writer serializes an {@link Invoice} to an XRechnung
 * Cross Industry Invoice (CII) XML document.</p>
 *
 * <p>Validation is enabled by default. Generated XML is validated
 * against the CII 16B XML schema and the KoSIT XRechnung validation
 * rules.</p>
 *
 * <p>Example:</p>
 *
 * <pre>{@code
 * XrCiiInvoiceWriter writer = XrCiiInvoiceWriter.builder()
 *         .prettyPrint(true)
 *         .build();
 *
 * writer.writeToFile(
 *         invoice,
 *         new File("xrechnung.xml")
 * );
 * }</pre>
 */
public final class XrCiiInvoiceWriter {

    private final XrProfile profile;

    private final CiiInvoiceWriter ciiWriter;

    private final KositValidator kositValidator = new KositValidator();

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
     * Writes an invoice as XRechnung CII XML to a file.
     *
     * <p>The generated XML is validated before it is written when
     * validation is enabled.</p>
     *
     * @param invoice invoice to serialize
     * @param file destination file
     * @throws NullPointerException if {@code invoice} or {@code file}
     *                              is {@code null}
     * @throws KositValidationException if KoSIT validation fails
     */
    public void writeToFile(Invoice invoice, File file) {
        Objects.requireNonNull(invoice, "invoice must not be null");
        Objects.requireNonNull(file, "file must not be null");

        String xml = ciiWriter.writeToString(invoice);

        if (validate) {
            validateXml(xml);
            validateInvoice(xml);
        }

        ciiWriter.writeToFile(xml, file);
    }

    /**
     * Serializes an invoice to XRechnung CII XML.
     *
     * <p>The generated XML is validated before it is returned when
     * validation is enabled.</p>
     *
     * @param invoice invoice to serialize
     * @return generated XRechnung CII XML
     * @throws NullPointerException if {@code invoice} is {@code null}
     * @throws KositValidationException if KoSIT validation fails
     */
    public String writeToString(Invoice invoice) {
        Objects.requireNonNull(invoice, "invoice must not be null");

        String xml = ciiWriter.writeToString(invoice);

        if (validate) {
            validateXml(xml);
            validateInvoice(xml);
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

    private void validateInvoice(String xml) {
        ValidationResult result = kositValidator.validate(xml);

        if (!result.isValid()) {
            throw new KositValidationException(result);
        }
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

        /**
         * Enables or disables XRechnung validation.
         *
         * <p>Validation is enabled by default. When enabled, generated
         * XML is validated against the CII 16B XML schema and the
         * KoSIT XRechnung validation rules.</p>
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
        public XrCiiInvoiceWriter build() {
            return new XrCiiInvoiceWriter(this);
        }
    }
}