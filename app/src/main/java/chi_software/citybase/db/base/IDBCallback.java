package chi_software.citybase.db.base;

import android.support.annotation.Nullable;

/**
 * Created by Igor on 2.3.17
 */

public interface IDBCallback<RESPONSE> {

    /**
     * This method should be called, when operation with db is completed successfully or failed.
     *
     * @param isSuccessful flag, which shows if request execution completed successfully.
     *                     true == success, false == fail.
     * @param response it is a response object. Response should be null if isSuccessful == false.
     */
    void onCompleted(boolean isSuccessful, @Nullable RESPONSE response);
}
