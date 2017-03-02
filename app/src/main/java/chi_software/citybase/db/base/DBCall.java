package chi_software.citybase.db.base;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executors;

/**
 * Created by Igor on 2.3.17
 */

public abstract class DBCall<RESPONSE> {

    /**
     * Executes {@link #call()} in background thread and returns result to user in the main thread.
     *
     * @param dbCallback callback, which is used for providing result of the request to user.
     */
    public void execute(final IDBCallback<RESPONSE> dbCallback) {
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                start(dbCallback);
            }
        });
    }

    private void start(IDBCallback<RESPONSE> dbCallback) {
        try {
            final RESPONSE response = call();
            sendResultToUIThread(true, response, dbCallback);
        } catch (Exception c) {
            sendResultToUIThread(false, null, dbCallback);
        }
    }

    private void sendResultToUIThread(final boolean isSuccessful, final RESPONSE response,
                                      final IDBCallback<RESPONSE> dbCallback) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                dbCallback.onCompleted(isSuccessful, response);
            }
        });
    }

    /**
     * Override this method to implement request, which should be executed in background thread.
     *
     * @return response object for DB call.
     * @throws Exception if something failed during execution.
     */
    protected abstract RESPONSE call() throws Exception;
}
