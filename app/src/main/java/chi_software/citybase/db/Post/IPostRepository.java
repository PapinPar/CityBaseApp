package chi_software.citybase.db.Post;

import chi_software.citybase.db.base.DBCall;
import chi_software.citybase.db.base.IRepository;

/**
 * Created by user on 02.03.2017.
 */

public interface IPostRepository extends IRepository {
    DBCall<Long> create(PostModel postModel);

    DBCall<PostModel> readById(long id);

    DBCall<Long> delete(PostModel postModel);

    DBCall<Long> update(PostModel postModel);
}
