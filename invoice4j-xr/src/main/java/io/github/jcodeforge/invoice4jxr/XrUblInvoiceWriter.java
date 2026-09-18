package io.github.jcodeforge.invoice4jxr;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Invoice;
import io.github.jcodeforge.invoice4jbase.ubl.UblInvoiceWriter;
import io.github.jcodeforge.invoice4jbase.ubl.UblProfile;
import io.github.jcodeforge.invoice4jxr.exceptions.KositValidationException;
import io.github.jcodeforge.invoice4jxr.validation.KositValidator;
import io.github.jcodeforge.invoice4jxr.validation.Ubl21XsdValidator;
import io.github.jcodeforge.invoice4jxr.validation.ValidationResult;
import io.github.jcodeforge.invoice4jxr.validation.genericode.XrGenericodeValidator;

import java.io.File;
import java.util.Objects;

/**
 * Writer for XRechnung invoices using the UBL syntax.
 *
 * <p>The writer serializes an {@link Invoice} to an XRechnung
 * UBL 2.1 XML document.</p>
 *
 * <p>Validation is enabled by default. Generated XML is validated
 * against the UBL 2.1 XML schema and the KoSIT XRechnung validation
 * rules. Present code list values are additionally checked against
 * the supported XRechnung Genericode lists.</p>
 *
 * <p>Example:</p>
 *
 * <pre>{@code
 * XrUblInvoiceWriter writer = XrUblInvoiceWriter.builder()
 *         .prettyPrint(true)
 *         .build();
 *
 * writer.writeToFile(
 *         invoice,
 *         new File("xrechnung.xml")
 * );
 * }</pre>
 */
public final class XrUblInvoiceWriter {

    private final XrProfile profile;

    private final UblInvoiceWriter ublWriter;

    private final KositValidator kositValidator = new KositValidator();

    private final XrGenericodeValidator genericodeValidator = new XrGenericodeValidator();

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
     * Writes an invoice as XRechnung UBL XML to a file.
     *
     * <p>The generated XML is validated before it is written when
     * validation is enabled.</p>
     *
     * @param invoice invoice to serialize
     * @param file destination file
     * @throws NullPointerException if {@code invoice} or {@code file}
     *                              is {@code null}
     * @throws KositValidationException if KoSIT validation fails
     * @throws IllegalArgumentException if a present code list value
     *                                  is invalid
     */
    public void writeToFile(Invoice invoice, File file) {
        Objects.requireNonNull(invoice, "invoice must not be null");
        Objects.requireNonNull(file, "file must not be null");

        String xml = ublWriter.writeToString(invoice);

        if (validate) {
            validate(invoice, xml);
        }

        ublWriter.writeToFile(xml, file);
    }

    /**
     * Serializes an invoice to XRechnung UBL XML.
     *
     * <p>The generated XML is validated before it is returned when
     * validation is enabled.</p>
     *
     * @param invoice invoice to serialize
     * @return generated XRechnung UBL XML
     * @throws NullPointerException if {@code invoice} is {@code null}
     * @throws KositValidationException if KoSIT validation fails
     * @throws IllegalArgumentException if a present code list value
     *                                  is invalid
     */
    public String writeToString(Invoice invoice) {
        Objects.requireNonNull(invoice, "invoice must not be null");

        String xml = ublWriter.writeToString(invoice);

        if (validate) {
            validate(invoice, xml);
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

    private void validate(Invoice invoice, String xml) {
        validateXml(xml);
        validateInvoice(xml);
        validateGenericode(invoice);
    }

    private void validateInvoice(String xml) {
        ValidationResult result = kositValidator.validate(xml);

        if (!result.isValid()) {
            throw new KositValidationException(result);
        }
    }

    private void validateXml(String xml) {
        switch (profile) {
            case XRECHNUNG -> new Ubl21XsdValidator().validate(xml);
        }
    }

    private void validateGenericode(Invoice invoice) {
        genericodeValidator.validate(invoice);
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
         * Enables or disables XRechnung validation.
         *
         * <p>Validation is enabled by default. When enabled, generated
         * XML is validated against the UBL 2.1 XML schema, the KoSIT
         * XRechnung validation rules, and the supported Genericode
         * code lists.</p>
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