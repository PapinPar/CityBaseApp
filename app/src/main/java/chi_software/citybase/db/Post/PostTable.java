package chi_software.citybase.db.Post;

import android.provider.BaseColumns;

/**
 * Created by user on 02.03.2017.
 */

public class PostTable implements BaseColumns {
    public static final String TABLE_NAME = "post";
    public static final String COLUMN_POST_ID = "post_id";
    public static final String COLUMN_POST_PRICE = "post_price";
    public static final String COLUMN_COMMENT = "comment";


    public final static String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY, " +
            COLUMN_POST_ID + " TEXT NOT NULL, " +
            COLUMN_POST_PRICE + " TEXT NOT NULL, " +
            COLUMN_COMMENT + " TEXT);";

    public final static String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    final static String SQL_INSERT_ENTRY = "INSERT INTO " + TABLE_NAME + " (" +
            COLUMN_POST_ID + ", " +
            COLUMN_POST_PRICE + ", " +
            COLUMN_COMMENT + ") " +
            "VALUES " + "(?, ?, ?)";

    final static String SQL_UPDATE_ENTRY = "UPDATE " + TABLE_NAME + " " +
            "SET " +
            COLUMN_POST_ID + " = ?, " +
            COLUMN_POST_PRICE + " = ?, " +
            COLUMN_COMMENT + " = ? " +
            "WHERE " +
            _ID + " = ?";


    final static String SQL_DELETE_ENTRY = "DELETE FROM " + TABLE_NAME + " " +
            "WHERE " +
            _ID + " = ?";
}
