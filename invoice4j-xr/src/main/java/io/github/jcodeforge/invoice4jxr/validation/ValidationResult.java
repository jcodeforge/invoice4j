package io.github.jcodeforge.invoice4jxr.validation;

import java.util.List;
import java.util.Objects;

public final class ValidationResult {

    private final boolean valid;
    private final List<ValidationMessage> messages;

    public ValidationResult(boolean valid, List<ValidationMessage> messages) {
        this.valid = valid;
        this.messages = List.copyOf(Objects.requireNonNull(messages, "messages must not be null"));
    }

    public boolean isValid() {
        return valid;
    }

    public List<ValidationMessage> getMessages() {
        return messages;
    }
}
