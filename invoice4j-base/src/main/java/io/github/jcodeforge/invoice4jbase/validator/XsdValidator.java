package io.github.jcodeforge.invoice4jbase.validator;

import java.io.File;
import java.io.InputStream;

public interface XsdValidator {

    void validate(File xml);
    void validate(InputStream inputStream);
}
