package io.github.jcodeforge.invoice4jbase.datamodels.pojos;

public class Identifier {
    /**
     * Identifier scheme (e.g. GLN, DUNS, VAT, LEITWEG_ID).
     */
    private String scheme;
    /**
     * Identifier value.
     */
    private String value;


    public Identifier(String scheme, String value) {
        this.scheme = scheme;
        this.value = value;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
