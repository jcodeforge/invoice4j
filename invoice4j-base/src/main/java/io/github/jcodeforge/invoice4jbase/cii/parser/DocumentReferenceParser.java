package io.github.jcodeforge.invoice4jbase.cii.parser;

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
                        basePath + "/ram:IssuerAssignedID"))
                .typeCode(readTypeCode(reader, basePath))
                .name(reader.readString(
                        basePath + "/ram:Name"))
                .issueDate(reader.readDate(
                        basePath
                                + "/ram:FormattedIssueDateTime"
                                + "/udt:DateTimeString"))
                .build();
    }

    private DocumentTypeCode readTypeCode(XmlReader reader, String basePath) {
        String code = reader.readString(basePath + "/ram:TypeCode");

        return code == null ? null : DocumentTypeCode.fromCode(code);
    }
}