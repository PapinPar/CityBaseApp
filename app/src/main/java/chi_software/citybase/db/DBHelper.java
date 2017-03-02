package chi_software.citybase.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import chi_software.citybase.BuildConfig;
import chi_software.citybase.db.Post.PostTable;


public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, BuildConfig.DATABASE_NAME, null, BuildConfig.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PostTable.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PostTable.SQL_DROP_TABLE);
        onCreate(db);
    }
}