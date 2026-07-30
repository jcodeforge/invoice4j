package io.github.jcodeforge.core.contentproviders;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.jcodeforge.core.Constants;
import io.github.jcodeforge.core.utils.BuildConfig;
import io.github.jcodeforge.core.utils.SecurityUtils;
import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLiteConnectionPool {

    private static final int MIN_IDLE_CONNECTION_POOL_SIZE = 10;
    private static final int MAX_CONNECTION_POOL_SIZE = 15;

    private static final int CONNECTION_TIMEOUT_MS = 35000;
    private static final int MAX_CONNECTION_LIFETIME_MS = 30000;

    private static final String APP_DATABASE_DRIVER = "jdbc:sqlite:";

    private HikariDataSource mDataSource = null;

    public SQLiteConnectionPool(String url, String pwd) {
        File dbFile = new File(url);
        if (!dbFile.exists()) {
            dbFile.getParentFile().mkdirs();
        }

        String jdbcUrl;
        String encodedKey;
        if (!BuildConfig.DEBUG) {
            jdbcUrl = APP_DATABASE_DRIVER + url;
            encodedKey = SecurityUtils.encodeStringAsCredential(pwd);
        } else {
            jdbcUrl = APP_DATABASE_DRIVER + Constants.APP_USER_DIR + "cache" + File.separator +
                    "test-" + getLastPathComponent(url);
            encodedKey = "";
        }

        mDataSource = new HikariDataSource(getConfig(jdbcUrl, encodedKey));
    }

    private HikariConfig getConfig(String url, String pwd) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setPassword(pwd);
        config.setMinimumIdle(MIN_IDLE_CONNECTION_POOL_SIZE);
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        config.setMaximumPoolSize(MAX_CONNECTION_POOL_SIZE);
        config.setConnectionTimeout(CONNECTION_TIMEOUT_MS);
        config.setMaxLifetime(MAX_CONNECTION_LIFETIME_MS);
        config.setConnectionInitSql("PRAGMA foreign_keys = ON");

        return config;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            if (mDataSource != null) {
                connection = mDataSource.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace(System.err);
        }

        return connection;
    }

    private String getLastPathComponent(String filePath) {
        return Paths.get(filePath).getFileName().toString();
    }
}
