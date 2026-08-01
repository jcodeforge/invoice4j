package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

import io.github.jcodeforge.invoice4jbase.datamodels.enums.TaxIdentifierType;

public class TaxIdentifier {

    private String value;

    private TaxIdentifierType type;

    public String getValue() {
        return value;
    }

    public TaxIdentifierType getType() {
        return type;
    }
}
