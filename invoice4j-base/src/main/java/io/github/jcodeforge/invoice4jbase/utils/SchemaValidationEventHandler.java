package io.github.jcodeforge.invoice4jbase.utils;

import io.github.jcodeforge.invoice4jbase.exceptions.SchemaValidationException;
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
            throw new SchemaValidationException("Schema validation error: \nLine: " +
                    locator.getLineNumber() + ", " + event.getMessage());
        }

        return true;
    }
}
