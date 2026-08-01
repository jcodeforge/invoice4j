package io.github.jcodeforge.invoice4jbase.utils;

import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;
import jakarta.xml.bind.ValidationEvent;
import jakarta.xml.bind.ValidationEventHandler;
import jakarta.xml.bind.ValidationEventLocator;
import jakarta.xml.bind.helpers.DefaultValidationEventHandler;

public class SchemaValidationEventHandler extends DefaultValidationEventHandler
        implements ValidationEventHandler {

    @Override
    public boolean handleEvent(ValidationEvent event) {
        ValidationEventLocator locator = event.getLocator();
        if (locator != null) {
            throw new InvoiceValidationException("Schema validation error: \nLine: " +
                    locator.getLineNumber() + ", " + event.getMessage());
        }

        return true;
    }
}
