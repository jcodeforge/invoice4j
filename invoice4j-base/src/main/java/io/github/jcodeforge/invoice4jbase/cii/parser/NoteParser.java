package io.github.jcodeforge.invoice4jbase.cii.parser;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Note;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class NoteParser implements XmlParser<Note> {

    @Override
    public Note parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return Note.builder()
                .text(reader.readString(
                        basePath + "/ram:Content"))
                .subjectCode(reader.readString(
                        basePath + "/ram:SubjectCode"))
                .build();
    }
}