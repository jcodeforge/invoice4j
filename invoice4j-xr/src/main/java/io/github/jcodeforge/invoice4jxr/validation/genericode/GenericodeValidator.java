package io.github.jcodeforge.invoice4jxr.validation.genericode;

public final class GenericodeValidator {

    private final GenericodeRegistry registry;

    public GenericodeValidator(GenericodeRegistry registry) {
        this.registry = registry;
    }

    public boolean isValid(String value) {
        return registry.contains(value);
    }
}