package chi_software.citybase.db;

import android.database.Cursor;

import chi_software.citybase.db.Post.PostModel;
import chi_software.citybase.db.Post.PostTable;

/**
 * Created by user on 02.03.2017.
 */

public class DBModelConverter {
    public static PostModel toPostModel(Cursor cursor) {
        final PostModel postModel = new PostModel();
        postModel.setId(cursor.getLong(cursor.getColumnIndex(PostTable.COLUMN_POST_ID)));
        postModel.setComment(cursor.getString(cursor.getColumnIndex(PostTable.COLUMN_COMMENT)));
        postModel.setPrice(cursor.getString(cursor.getColumnIndex(PostTable.COLUMN_POST_PRICE)));
        return postModel;
    }
}
