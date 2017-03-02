package chi_software.citybase.db.base;

import android.database.Cursor;
import android.database.SQLException;

/**
 * Created by Igor on 1.3.17
 */

public interface IRepository {

    /**
     * Executes "insert" sqlite statement with provided arguments.
     *
     * @param sqliteStatement sql request, which should be compiled as sqlite statement.
     * @param args            arguments which should be used in statement.
     * @return id of the created entry. id == -1 if insertion failed.
     * @throws SQLException if insert execution returns -1 response.
     */
    long create(String sqliteStatement, String... args) throws SQLException;

    /**
     * Executes "query" with provided selection params.
     *
     * @param tableName     name of the table, from where we should read data.
     * @param selection     selection condition
     * @param selectionArgs arguments which should be used in selection
     * @return cursor
     */
    Cursor read(String tableName, String selection, String... selectionArgs);

    /**
     * Executes "update" sqlite statement with provided arguments.
     *
     * @param sqliteStatement sql request, which should be compiled as sqlite statement.
     * @param args            arguments which should be used in statement.
     * @return amount of changed rows
     * @throws SQLException if update executing returns 0 response. (0 = no rows has been changed)
     */
    long update(String sqliteStatement, String... args) throws SQLException;

    /**
     * Executes "delete" sqlite statement with provided arguments.
     *
     * @param sqliteStatement sql request, which should be compiled as sqlite statement.
     * @param args            arguments which should be used in statement.
     * @return amount of deleted rows
     * @throws SQLException if update executing returns 0 response. (0 = no rows has been deleted)
     */
    long delete(String sqliteStatement, String... args);
}
