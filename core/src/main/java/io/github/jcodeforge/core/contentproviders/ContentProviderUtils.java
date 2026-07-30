package io.github.jcodeforge.core.contentproviders;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ContentProviderUtils {

    public static List<SQLiteContentValues> executeQuery(Connection conn, String sql)
            throws SQLException {

        Statement stmt = null;
        List<SQLiteContentValues> result = new ArrayList<>();

        try {
            if (conn != null) {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                result = resultSetToContentValues(rs);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }

            if (stmt != null) {
                stmt.close();
            }
        }

        return result;
    }

    public static int executeUpdate(Connection conn, PreparedStatement pStmt)
            throws SQLException {

        int returnCode;
        try {
            try {
                returnCode = pStmt.executeUpdate();
            } catch (SQLException e) {
                // Todo anything here ??
                throw new SQLException(e);
            }
        } finally {
            pStmt.close();
            conn.close();
        }

        return returnCode;
    }

    private static List<SQLiteContentValues> resultSetToContentValues(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();
        int columns = metaData.getColumnCount();

        List<SQLiteContentValues> result = new ArrayList<>();

        while (rs.next()) {
            SQLiteContentValues contentValues = new SQLiteContentValues(columns);
            for(int i = 1; i <= columns; ++i) {
                contentValues.put(metaData.getColumnName(i), rs.getObject(i));
            }
            result.add(contentValues);
        }

        return result;
    }

    public static String generateInsert(String tableName, String[] headers, String[] values) {
        StringBuilder query = new StringBuilder("INSERT INTO ");
        query.append(tableName).append(" (");

        for (int i = 0; i < headers.length; i++) {
            query.append(headers[i]);
            if (i < headers.length - 1) query.append(", ");
        }

        query.append(") VALUES (");

        for (int i = 0; i < values.length; i++) {
            query.append("'").append(values[i]).append("'");
            if (i < values.length - 1) query.append(", ");
        }

        query.append(");");
        return query.toString();
    }

    public static String generateUpdate(String tableName, String[] headers, String[] values,
                                        String idColumn, String idValue) {
        StringBuilder query = new StringBuilder("UPDATE ");
        query.append(tableName).append(" SET ");

        for (int i = 0; i < headers.length; i++) {
            query.append(headers[i]).append(" = '").append(values[i]).append("'");
            if (i < headers.length - 1) query.append(", ");
        }

        query.append(" WHERE ").append(idColumn).append(" = '").append(idValue).append("';");
        return query.toString();
    }

    public static String generateDelete(String tableName, String idColumn, String idValue) {
        return "DELETE FROM " + tableName + " WHERE " + idColumn + " = '" + idValue + "';";
    }

    private static List<String[]> readCsv(String filePath) {
        List<String[]> records = new ArrayList<>();
        try (BufferedReader br =
                     new BufferedReader(new InputStreamReader(new FileInputStream(filePath),
                             StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                records.add(values);
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }

        return records;
    }

    public static String readCsvAsJson(String filePath) {
        List<String[]> csvContent = readCsv(filePath);
        String[] headers = csvContent.getFirst();

        csvContent.removeFirst();

        StringBuilder builder = new StringBuilder("[");
        for (String[] columns : csvContent) {
            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < columns.length; i++) {
                map.put(headers[i], columns[i]);
            }

            builder.append(new Gson().toJson(map,
                    new TypeToken<HashMap<String, String>>() {}.getType())).append(",");
        }

        builder.append("]");

        String s = builder.toString();
        // Remove last comma (separator)
        int lastIndex = s.lastIndexOf(",");

        return s.substring(0, lastIndex) + s.substring(lastIndex + 1);
    }
}
