package io.github.jcodeforge.invoice4jxr.validation;

import de.kosit.validationtool.api.Check;
import de.kosit.validationtool.api.Configuration;
import de.kosit.validationtool.api.Input;
import de.kosit.validationtool.api.InputFactory;
import de.kosit.validationtool.api.Result;
import de.kosit.validationtool.impl.DefaultCheck;
import de.kosit.validationtool.impl.xml.ProcessorProvider;
import org.oclc.purl.dsdl.svrl.RichText;
import org.oclc.purl.dsdl.svrl.Text;
import org.w3c.dom.Element;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class KositValidator {

    private final Check validator;

    public KositValidator() {
        try {
            URL scenarios = getClass()
                    .getClassLoader()
                    .getResource("kosit/scenarios.xml");

            if (scenarios == null) {
                throw new IllegalStateException("KoSIT scenarios.xml not found.");
            }

            Configuration configuration = Configuration
                    .load(scenarios.toURI())
                    .build(ProcessorProvider.getProcessor());

            this.validator = new DefaultCheck(configuration);

        } catch (Exception e) {
            throw new IllegalStateException("Unable to initialize KoSIT XRechnung validator.", e);
        }
    }

    public ValidationResult validate(String xml) {
        Objects.requireNonNull(xml, "xml must not be null");

        try {
            Input input = InputFactory.read(new StreamSource(new StringReader(xml)));
            Result result = validator.checkInput(input);

            return toValidationResult(result);

        } catch (Exception e) {
            throw new IllegalStateException("Unable to validate XRechnung document.", e);
        }
    }

    private ValidationResult toValidationResult(Result result) {
        Objects.requireNonNull(result, "result must not be null");

        return new ValidationResult(result.isProcessingSuccessful() && result.isAcceptable(),
                readMessages(result));
    }

    private List<ValidationMessage> readMessages(Result result) {
        List<ValidationMessage> messages = new ArrayList<>();

        result.getFailedAsserts().forEach(assertion -> {
            String message = extractText(assertion.getText());

            messages.add(new ValidationMessage(ValidationSeverity.ERROR, assertion.getTest(),
                    assertion.getLocation(), message));
        });

        return messages;
    }

    private String extractText(Text text) {
        if (text == null) {
            return null;
        }

        StringBuilder result = new StringBuilder();
        appendText(text.getContent(), result);

        return result.toString().trim();
    }

    private void appendText(List<Object> content, StringBuilder result) {
        for (Object item : content) {
            if (item instanceof String string) {
                result.append(string);
                continue;
            }

            if (item instanceof RichText richText) {
                appendText(richText.getContent(), result);
                continue;
            }

            if (item instanceof Element element) {
                result.append(element.getTextContent());
            }
        }
    }
}