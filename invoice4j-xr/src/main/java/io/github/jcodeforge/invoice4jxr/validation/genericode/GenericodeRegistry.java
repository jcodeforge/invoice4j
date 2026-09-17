package io.github.jcodeforge.invoice4jxr.validation.genericode;

public final class GenericodeRegistry {

    private final GenericodeCodeList codeList;

    private GenericodeRegistry(GenericodeCodeList codeList) {
        this.codeList = codeList;
    }

    public static GenericodeRegistry fromResource(String resource) {
        return new GenericodeRegistry(new GenericodeReader().readResource(resource));
    }

    public boolean contains(String value) {
        return codeList.contains(value);
    }

    public GenericodeCodeList getCodeList() {
        return codeList;
    }
}