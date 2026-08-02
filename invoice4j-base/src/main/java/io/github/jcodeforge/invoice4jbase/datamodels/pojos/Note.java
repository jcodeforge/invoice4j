package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.LanguageCode;
import io.github.jcodeforge.invoice4jbase.exceptions.InvoiceValidationException;

/**
 * BG-1
 * Invoice note.
 *
 * Additional textual information provided on the invoice.
 */
public class Note {

    /**
     * BT-22
     * Invoice note text.
     */
    private String text;

    /**
     * BT-21
     * Invoice note subject code.
     *
     * Optional code identifying the purpose of the note.
     */
    private String subjectCode;

    /**
     * Language identifier.
     *
     * Example:
     * en, de
     */
    private LanguageCode languageCode;

    private Note() {
    }

    public String getText() {
        return text;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public LanguageCode getLanguageCode() {
        return languageCode;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Note note;

        private Builder() {
            this.note = new Note();
        }

        public Builder text(String text) {
            note.text = text == null ? null : text.trim();
            return this;
        }

        public Builder subjectCode(String subjectCode) {
            note.subjectCode = subjectCode == null ? null : subjectCode.trim();
            return this;
        }

        public Builder languageCode(LanguageCode languageCode) {
            note.languageCode = languageCode;
            return this;
        }

        public Note build() {
            if (note.text == null || note.text.isBlank()) {
                throw new InvoiceValidationException("BT-22 Invoice note text is required.");
            }
            if (note.subjectCode != null && note.subjectCode.isBlank()) {
                throw new InvoiceValidationException("BT-21 Invoice note subject code must not be blank.");
            }

            return note;
        }
    }
}
