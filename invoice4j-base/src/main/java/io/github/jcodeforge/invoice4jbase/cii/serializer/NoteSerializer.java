package io.github.jcodeforge.invoice4jbase.cii.serializer;

import io.github.jcodeforge.invoice4jbase.datamodels.pojos.Note;
import io.github.jcodeforge.invoice4jbase.xml.XmlNamespaces;
import io.github.jcodeforge.invoice4jbase.xml.XmlWriter;

public final class NoteSerializer implements XmlSerializer<Note> {

    @Override
    public void serialize(XmlWriter writer, Note note) {
        if (note == null) {
            return;
        }

        writer.startElement(XmlNamespaces.RAM, "IncludedNote");
        writer.writeElement(XmlNamespaces.RAM, "Content", note.getText());

        if (note.getSubjectCode() != null) {
            writer.writeElement(XmlNamespaces.RAM, "SubjectCode", note.getSubjectCode());
        }

        writer.endElement();
    }
}