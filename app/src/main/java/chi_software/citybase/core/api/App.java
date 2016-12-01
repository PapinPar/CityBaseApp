package chi_software.citybase.core.api;

import java.util.concurrent.Executor;

/**
 * Created by Papin on 08.11.2016.
 */

public interface App {
    Net getNet();
    Executor getmExecutor ();
}
