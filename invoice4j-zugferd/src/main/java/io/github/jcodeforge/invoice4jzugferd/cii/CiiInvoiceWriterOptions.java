package io.github.jcodeforge.invoice4jzugferd.cii;

public final class CiiInvoiceWriterOptions {

    private final CiiProfile profile;

    private final boolean prettyPrint;

    private CiiInvoiceWriterOptions(Builder builder) {
        this.prettyPrint = builder.prettyPrint;
        this.profile = builder.profile;
    }

    public CiiProfile getProfile() {
        return profile;
    }

    public boolean isPrettyPrint() {
        return prettyPrint;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private CiiProfile profile = CiiProfile.EN16931;
        private boolean prettyPrint;

        public Builder profile(CiiProfile profile) {
            this.profile = profile;
            return this;
        }

        public Builder prettyPrint(boolean prettyPrint) {
            this.prettyPrint = prettyPrint;
            return this;
        }

        public CiiInvoiceWriterOptions build() {
            return new CiiInvoiceWriterOptions(this);
        }
    }
}