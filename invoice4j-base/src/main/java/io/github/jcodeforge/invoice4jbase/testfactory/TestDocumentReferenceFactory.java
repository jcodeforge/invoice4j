package io.github.jcodeforge.invoice4jbase.testfactory;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.DocumentTypeCode;
import io.github.jcodeforge.invoice4jbase.datamodels.pojos.DocumentReference;

public final class TestDocumentReferenceFactory {

    public static DocumentReference createBillingReference() {
        return DocumentReference.builder()
                .id("INV-2025-0001")
                .typeCode(DocumentTypeCode.COMMERCIAL_INVOICE)
                .description("Referenced previous invoice")
                .build();
    }

    public static DocumentReference createAdditionalDocument() {
        return DocumentReference.builder()
                .id("ATTACHMENT-001")
                .typeCode(DocumentTypeCode.RELATED_DOCUMENT)
                .description("Terms and Conditions")
                .build();
    }
}
