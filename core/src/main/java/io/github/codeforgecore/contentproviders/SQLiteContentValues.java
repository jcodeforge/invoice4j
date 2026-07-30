package io.github.codeforgecore.contentproviders;

import java.util.AbstractMap;
import java.util.HashMap;

public class SQLiteContentValues {

    private final AbstractMap<String, Object> mSimpleEntries;

    public SQLiteContentValues(int initialCapacity) {
        mSimpleEntries = new HashMap<>(initialCapacity);
    }

    public void put(String key, Object o) {
        mSimpleEntries.put(key, o);
    }

    public Object get(String key) {
        return mSimpleEntries.get(key);
    }
}
