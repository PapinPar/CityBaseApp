package chi_software.citybase.core.api;

import chi_software.citybase.core.observer.Subscriber;

/**
 * Created by Papin on 08.11.2016.
 */
public interface NetSubscriber extends Subscriber {

    void onNetRequestDone(@Net.NetEvent int eventId, Object NetObjects);

    void onNetRequestFail(@Net.NetEvent int eventId, Object NetObjects);
}