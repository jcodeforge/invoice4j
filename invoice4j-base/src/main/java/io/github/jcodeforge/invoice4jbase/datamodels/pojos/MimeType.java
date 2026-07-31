package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import java.util.Objects;

public class MimeType {

    public static final MimeType PDF = new MimeType("application/pdf");
    public static final MimeType XML = new MimeType("application/xml");
    public static final MimeType PNG = new MimeType("image/png");
    public static final MimeType JPEG = new MimeType("image/jpeg");
    public static final MimeType CSV = new MimeType("text/csv");
    public static final MimeType TEXT = new MimeType("text/plain");

    private final String value;

    private MimeType(String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static MimeType of(String value) {
        return new MimeType(value);
    }

    public String value() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof MimeType other
                && value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
