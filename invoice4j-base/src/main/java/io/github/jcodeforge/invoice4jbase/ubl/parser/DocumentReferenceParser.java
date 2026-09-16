package io.github.jcodeforge.invoice4jbase.ubl.parser;

import io.github.jcodeforge.invoice4jbase.ubl.UblDateFormats;
import io.github.jcodeforge.invoice4jbase.xml.XmlParser;
import io.github.jcodeforge.invoice4jbase.datamodels.enums.DocumentTypeCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.DocumentReference;
import io.github.jcodeforge.invoice4jbase.xml.XmlReader;

public final class DocumentReferenceParser implements XmlParser<DocumentReference> {

    @Override
    public DocumentReference parse(XmlReader reader, String basePath) {
        if (!reader.exists(basePath)) {
            return null;
        }

        return DocumentReference.builder()
                .id(reader.readString(
                        basePath + "/cbc:ID"))
                .typeCode(readTypeCode(reader, basePath))
                .name(reader.readString(
                        basePath + "/cbc:DocumentDescription"))
                .issueDate(reader.readDate(basePath + "/cbc:IssueDate", UblDateFormats.DATE))
                .build();
    }

    private DocumentTypeCode readTypeCode(XmlReader reader, String basePath) {
        String code = reader.readString(basePath + "/cbc:DocumentTypeCode");

        return code == null ? null : DocumentTypeCode.fromCode(code);
    }
}