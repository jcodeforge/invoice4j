package io.github.jcodeforge.invoice4jzugferd.cii;

public final class CiiSerializationContext {

    private final CiiInvoiceWriterOptions options;

    public CiiSerializationContext(CiiInvoiceWriterOptions options) {
        this.options = options;
    }

    public CiiProfile getProfile() {
        return options.getProfile();
    }

    public boolean isPrettyPrint() {
        return options.isPrettyPrint();
    }
}
