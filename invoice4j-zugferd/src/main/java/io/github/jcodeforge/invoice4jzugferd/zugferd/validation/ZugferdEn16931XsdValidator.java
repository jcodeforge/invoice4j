package io.github.jcodeforge.invoice4jzugferd.zugferd.validation;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import io.github.jcodeforge.invoice4jbase.validation.XsdValidator;
import io.github.jcodeforge.invoice4jzugferd.cii.CiiEn16931XsdValidator;

/**
 * Validates ZUGFeRD / Factur-X EN 16931 CII XML documents
 * against the Factur-X EN 16931 XML schema.
 *
 * <p>The actual XSD validation is delegated to
 * {@link CiiEn16931XsdValidator}.
 */
public final class ZugferdEn16931XsdValidator implements XsdValidator {

    private final CiiEn16931XsdValidator ciiValidator = new CiiEn16931XsdValidator();

    /**
     * Validates a ZUGFeRD EN 16931 XML file.
     *
     * @param file the XML file
     * @throws NullPointerException if {@code file} is {@code null}
     */
    @Override
    public void validate(File file) {
        Objects.requireNonNull(file, "file must not be null");
        ciiValidator.validate(file);
    }

    /**
     * Validates a ZUGFeRD EN 16931 XML input stream.
     *
     * @param inputStream the XML input stream
     * @throws NullPointerException if {@code inputStream} is {@code null}
     */
    @Override
    public void validate(InputStream inputStream) {
        Objects.requireNonNull(inputStream, "inputStream must not be null");
        ciiValidator.validate(inputStream);
    }

    /**
     * Validates a ZUGFeRD EN 16931 XML string.
     *
     * @param xml the XML document
     * @throws NullPointerException if {@code xml} is {@code null}
     */
    @Override
    public void validate(String xml) {
        Objects.requireNonNull(xml, "xml must not be null");
        ciiValidator.validate(xml);
    }
}