package io.github.jcodeforge.invoice4jbase.validator;

import io.github.jcodeforge.invoice4jbase.exceptions.XsdValidationException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.SAXException;

public final class CiiEn16931XsdValidator implements XsdValidator {

    private static final String SCHEMA_LOCATION = "/xsd/en16931/Factur-X_1.09_EN16931.xsd";

    private final Schema schema;

    public CiiEn16931XsdValidator() {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // Prevent loading schemas from arbitrary external locations.
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            // local imports only
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "file");

            URL schemaUrl = Objects.requireNonNull(getClass().getResource(SCHEMA_LOCATION),
                    "Unable to locate schema: " + SCHEMA_LOCATION);

            this.schema = factory.newSchema(schemaUrl);

        } catch (Exception e) {
            throw new IllegalStateException("Unable to load CII EN16931 XML schema.", e);
        }
    }

    @Override
    public void validate(File file) {
        Objects.requireNonNull(file, "file must not be null");

        try {
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file));

        } catch (SAXException e) {
            throw new XsdValidationException("XML document is not valid according to the CII EN16931 schema.", e);

        } catch (Exception e) {
            throw new XsdValidationException("Unable to validate XML document.", e);
        }
    }

    @Override
    public void validate(InputStream inputStream) {
        Objects.requireNonNull(inputStream, "inputStream must not be null");

        try {
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(inputStream));

        } catch (SAXException e) {
            throw new XsdValidationException("XML document is not valid according to the CII EN16931 schema.", e);

        } catch (Exception e) {
            throw new XsdValidationException("Unable to validate XML document.", e);
        }
    }

    @Override
    public void validate(String xml) {
        Objects.requireNonNull(xml, "xml must not be null");

        try (InputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8))) {
            validate(inputStream);

        } catch (Exception e) {
            throw new XsdValidationException("Unable to validate XML document.", e);
        }
    }
}