package chi_software.citybase.db.Post;

import android.database.Cursor;

import chi_software.citybase.db.DBHelper;
import chi_software.citybase.db.DBModelConverter;
import chi_software.citybase.db.base.BaseRepository;
import chi_software.citybase.db.base.DBCall;

import static android.R.attr.id;

/**
 * Created by user on 02.03.2017.
 */

public class PostRepository extends BaseRepository implements IPostRepository {

    public PostRepository(DBHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public DBCall<Long> create(final PostModel postModel) {
        return new DBCall<Long>() {
            @Override
            protected Long call() throws Exception {
                return create(PostTable.SQL_INSERT_ENTRY, String.valueOf(postModel.getId()), postModel.getPrice(), postModel.getComment());
            }
        };
    }

    @Override
    public DBCall<PostModel> readById(final long id) {
        return new DBCall<PostModel>() {
            @Override
            protected PostModel call() {
                Cursor postCursor = null;
                PostModel postModel = null;
                try {
                    postCursor = read(PostTable.TABLE_NAME, PostTable._ID + " = ?", String.valueOf(id));
                    postCursor.moveToFirst();
                    postModel = DBModelConverter.toPostModel(postCursor);
                } finally {
                    if (postCursor != null) {
                        postCursor.close();
                    }
                }
                return postModel;
            }
        };
    }

    @Override
    public DBCall<Long> delete(PostModel postModel) {
        return new DBCall<Long>() {
            @Override
            protected Long call() throws Exception {
                return delete(PostTable.SQL_DELETE_ENTRY, String.valueOf(id));
            }
        };
    }

    @Override
    public DBCall<Long> update(final PostModel postModel) {
        return new DBCall<Long>() {
            @Override
            protected Long call() throws Exception {
                return update(PostTable.SQL_UPDATE_ENTRY, postModel.getPrice(), String.valueOf(postModel.getId()), postModel.getComment());
            }
        };
    }
}
