package chi_software.citybase.db.base;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;

import chi_software.citybase.db.DBHelper;
import chi_software.citybase.db.Post.PostTable;

/**
 * Created by Igor on 1.3.17
 */

public class BaseRepository implements IRepository {

    private static final String ERROR_INSERT_FAILED = "Insert failed";
    private static final String ERROR_UPDATE_FAILED = "0 rows has been changed";
    private static final String ERROR_DELETE_FAILED = "Delete failed";

    private final DBHelper dbHelper;

    public BaseRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    protected DBHelper getDbHelper() {
        return dbHelper;
    }

    @Override
    public long create(String sqliteStatement, String... args) throws SQLException {
        final SQLiteDatabase db = getDbHelper().getWritableDatabase();
        final SQLiteStatement statement = db.compileStatement(sqliteStatement);
        statement.bindAllArgsAsStrings(args);
        final long id = statement.executeInsert();
        if (id == -1) {
            throw new SQLiteException(ERROR_INSERT_FAILED);
        }
        return id;
    }

    @Override
    public Cursor read(String tableName, String selection, String... selectionArgs) {
        final SQLiteDatabase db = getDbHelper().getReadableDatabase();
        return db.query(PostTable.TABLE_NAME, null, selection, selectionArgs, null, null, null);
    }

    @Override
    public long update(String sqliteStatement, String... args) {
        final int changedAmount = executeUpdateDelete(sqliteStatement, args);
        if (changedAmount == 0) {
            throw new SQLiteException(ERROR_UPDATE_FAILED);
        }
        return changedAmount;
    }

    @Override
    public long delete(String sqliteStatement, String... args) {
        final int deletedAmount = executeUpdateDelete(sqliteStatement, args);
        if (deletedAmount == 0) {
            throw new SQLiteException(ERROR_DELETE_FAILED);
        }
        return deletedAmount;
    }

    private int executeUpdateDelete(String sqliteStatement, String... args) {
        final SQLiteDatabase db = getDbHelper().getWritableDatabase();
        final SQLiteStatement statement = db.compileStatement(sqliteStatement);
        statement.bindAllArgsAsStrings(args);
        return statement.executeUpdateDelete();
    }
}
