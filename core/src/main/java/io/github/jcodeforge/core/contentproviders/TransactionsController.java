package io.github.jcodeforge.core.contentproviders;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionsController {

    private final SQLiteConnectionPool mSQLiteConnectionPool;

    private Connection mConnection = null;

    public TransactionsController(SQLiteConnectionPool sqLiteConnectionPool) {
        mSQLiteConnectionPool = sqLiteConnectionPool;
    }

    /**
     * When you connect to an SQLite database, the default mode is auto-commit.
     * It means that every query you issue to the SQLite database is automatically committed.
     * <p>
     * To disable the auto-commit mode, you use the setAutoCommit() method of the Connection object
     */
    public void beginTransaction() {
        try {
            mConnection = mSQLiteConnectionPool.getConnection();
            mConnection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * In case a failure occurs in the middle of the transaction, you can always use the rollback()
     * method to roll back the transaction.
     */
    public void rollbackTransaction() {
        try {
            if (mConnection != null) {
                mConnection.rollback();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * To commit work, you use the commit method of the Connection object.
     */
    public void endTransaction() {
        try {
            if (mConnection != null) {
                mConnection.commit();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
