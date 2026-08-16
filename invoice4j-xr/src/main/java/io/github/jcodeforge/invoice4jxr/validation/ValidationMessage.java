package io.github.jcodeforge.invoice4jxr.validation;

import java.util.Objects;

public final class ValidationMessage {

    private final ValidationSeverity severity;
    private final String message;
    private final String ruleId;
    private final String location;

    public ValidationMessage(ValidationSeverity severity, String message, String ruleId, String location) {
        this.severity = Objects.requireNonNull(severity, "severity must not be null");
        this.message = Objects.requireNonNull(message, "message must not be null");
        this.ruleId = ruleId;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public String getRuleId() {
        return ruleId;
    }

    public String getMessage() {
        return message;
    }

    public ValidationSeverity getSeverity() {
        return severity;
    }
}
