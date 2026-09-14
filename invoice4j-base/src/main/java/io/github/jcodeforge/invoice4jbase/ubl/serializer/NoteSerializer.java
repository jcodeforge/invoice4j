package io.github.jcodeforge.invoice4jbase.ubl.serializer;

import io.github.jcodeforge.invoice4jbase.XmlSerializer;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Note;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class NoteSerializer implements XmlSerializer<Note> {

    @Override
    public void serialize(XmlWriter writer, Note note) {
        if (note == null) {
            return;
        }

        writer.writeElement(
                "cbc",
                XmlNamespaces.UBL_CBC,
                "Note",
                note.getText()
        );
    }
}