package io.github.jcodeforge.invoice4jzugferd.validation;

import io.github.jcodeforge.invoice4jbase.exceptions.XsdValidationException;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.Objects;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import io.github.jcodeforge.invoice4jbase.validation.XsdValidator;
import org.xml.sax.SAXException;

public final class ZugferdExtendedXsdValidator implements XsdValidator {

    private static final String SCHEMA_LOCATION = "/xsd/extended/Factur-X_1.09_EXTENDED.xsd";

    private final Schema schema;

    public ZugferdExtendedXsdValidator() {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            // Prevent loading external DTDs.
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            // BASIC XSD imports its accompanying local schemas.
            factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "file");

            URL schemaUrl = Objects.requireNonNull(getClass().getResource(SCHEMA_LOCATION),
                    "Unable to locate schema: " + SCHEMA_LOCATION);

            this.schema = factory.newSchema(schemaUrl);

        } catch (Exception e) {
            throw new IllegalStateException("Unable to load ZUGFeRD EXTENDED XML schema.", e);
        }
    }

    @Override
    public void validate(File file) {
        Objects.requireNonNull(file, "file must not be null");

        try {
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file));

        } catch (SAXException e) {
            throw new XsdValidationException("XML document is not valid according to the ZUGFeRD EXTENDED schema.", e);

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
            throw new XsdValidationException("XML document is not valid according to the ZUGFeRD EXTENDED schema.", e);

        } catch (Exception e) {
            throw new XsdValidationException("Unable to validate XML document.", e);
        }
    }

    @Override
    public void validate(String xml) {
        Objects.requireNonNull(xml, "xml must not be null");

        try {
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(xml)));

        } catch (SAXException e) {
            throw new XsdValidationException("XML document is not valid according to the ZUGFeRD EXTENDED schema.", e);

        } catch (Exception e) {
            throw new XsdValidationException("Unable to validate XML document.", e);
        }
    }
}