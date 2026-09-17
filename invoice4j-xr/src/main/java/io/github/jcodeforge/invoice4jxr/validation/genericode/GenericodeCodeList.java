package io.github.jcodeforge.invoice4jxr.validation.genericode;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class GenericodeCodeList {

    private final String shortName;
    private final String longName;
    private final String version;
    private final String canonicalUri;
    private final String canonicalVersionUri;
    private final String codeColumn;
    private final Set<String> values;

    public GenericodeCodeList(String shortName, String longName, String version, String canonicalUri,
                              String canonicalVersionUri, String codeColumn, List<String> values) {

        this.shortName = shortName;
        this.longName = longName;
        this.version = version;
        this.canonicalUri = canonicalUri;
        this.canonicalVersionUri = canonicalVersionUri;
        this.codeColumn = codeColumn;
        this.values = Collections.unmodifiableSet(new LinkedHashSet<>(values));
    }

    public String getShortName() {
        return shortName;
    }

    public String getLongName() {
        return longName;
    }

    public String getVersion() {
        return version;
    }

    public String getCanonicalUri() {
        return canonicalUri;
    }

    public String getCanonicalVersionUri() {
        return canonicalVersionUri;
    }

    public String getCodeColumn() {
        return codeColumn;
    }

    public Set<String> getValues() {
        return values;
    }

    public boolean contains(String value) {
        return value != null && values.contains(value);
    }
}