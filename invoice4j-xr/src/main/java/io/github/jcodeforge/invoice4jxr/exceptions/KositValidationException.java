package io.github.jcodeforge.invoice4jxr.exceptions;

import io.github.jcodeforge.invoice4jxr.validation.ValidationMessage;
import io.github.jcodeforge.invoice4jxr.validation.ValidationResult;

public final class KositValidationException extends RuntimeException {

    private final ValidationResult validationResult;

    public KositValidationException(ValidationResult validationResult) {
        super(createMessage(validationResult));
        this.validationResult = validationResult;
    }

    public ValidationResult getValidationResult() {
        return validationResult;
    }

    private static String createMessage(ValidationResult result) {
        StringBuilder message = new StringBuilder("XRechnung validation failed.");

        for (ValidationMessage validationMessage : result.getMessages()) {
            message.append(System.lineSeparator())
                    .append(validationMessage.getSeverity())
                    .append(": ")
                    .append(validationMessage.getMessage());
        }

        return message.toString();
    }
}
