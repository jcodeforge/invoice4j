package io.github.jcodeforge.invoice4jzugferd.cii;

/**
 * Configuration options for a {@link io.github.jcodeforge.invoice4jzugferd.CiiInvoiceWriter}.
 *
 * <p>Instances of this class are immutable and are created using the
 * {@link Builder}. If no profile is specified, the
 * {@link CiiProfile#EN16931} profile is used by default.</p>
 */
public final class CiiInvoiceWriterOptions {

    private final CiiProfile profile;

    private final boolean prettyPrint;

    private CiiInvoiceWriterOptions(Builder builder) {
        this.prettyPrint = builder.prettyPrint;
        this.profile = builder.profile;
    }

    /**
     * Returns the CII profile used during serialization.
     *
     * @return the configured CII profile
     */
    public CiiProfile getProfile() {
        return profile;
    }

    /**
     * Returns whether generated XML should be formatted with indentation.
     *
     * @return {@code true} if pretty printing is enabled; {@code false} otherwise
     */
    public boolean isPrettyPrint() {
        return prettyPrint;
    }

    /**
     * Creates a new builder for configuring writer options.
     *
     * @return a new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for creating immutable {@link CiiInvoiceWriterOptions} instances.
     */
    public static final class Builder {

        /**
         * The target CII profile.
         *
         * <p>Defaults to {@link CiiProfile#EN16931}.</p>
         */
        private CiiProfile profile = CiiProfile.EN16931;

        /**
         * Whether generated XML should be pretty printed.
         */
        private boolean prettyPrint;

        /**
         * Sets the CII profile used during serialization.
         *
         * @param profile the target profile
         * @return this builder
         */
        public Builder profile(CiiProfile profile) {
            this.profile = profile;
            return this;
        }

        /**
         * Enables or disables pretty printed XML output.
         *
         * @param prettyPrint {@code true} to enable formatted XML output
         * @return this builder
         */
        public Builder prettyPrint(boolean prettyPrint) {
            this.prettyPrint = prettyPrint;
            return this;
        }

        /**
         * Builds a new immutable {@link CiiInvoiceWriterOptions} instance.
         *
         * @return the configured writer options
         */
        public CiiInvoiceWriterOptions build() {
            return new CiiInvoiceWriterOptions(this);
        }
    }
}