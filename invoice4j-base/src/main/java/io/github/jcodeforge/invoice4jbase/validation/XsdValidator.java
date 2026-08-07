package io.github.jcodeforge.invoice4jbase.validation;

import java.io.File;
import java.io.InputStream;

public interface XsdValidator {

    void validate(File xml);
    void validate(InputStream inputStream);
    void validate(String xml);
}
