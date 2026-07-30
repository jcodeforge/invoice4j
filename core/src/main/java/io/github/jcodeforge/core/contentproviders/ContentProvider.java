package io.github.jcodeforge.core.contentproviders;

import java.util.List;

/**
 * Instances of this interface are Data Access Objects (Repositories)
 * These are non-static wrappers around SQLiteWrapper and represents all CRUD operations
 */
public interface ContentProvider {

    List<SQLiteContentValues> query(String uri, String selection);
    int insert(String uri, SQLiteContentValues contentValues);
    int update(String uri, SQLiteContentValues contentValues);
    int delete(String uri, String selection);
}
