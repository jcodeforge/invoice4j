package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.LanguageCode;

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
            note.text = text;
            return this;
        }

        public Builder subjectCode(String subjectCode) {
            note.subjectCode = subjectCode;
            return this;
        }

        public Builder languageCode(LanguageCode languageCode) {
            note.languageCode = languageCode;
            return this;
        }

        public Note build() {
            return note;
        }
    }
}
