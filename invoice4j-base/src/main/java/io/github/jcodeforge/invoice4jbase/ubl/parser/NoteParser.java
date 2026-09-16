package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
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
                        basePath))
                .build();
    }
}