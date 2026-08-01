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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public LanguageCode getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(LanguageCode languageCode) {
        this.languageCode = languageCode;
    }
}
