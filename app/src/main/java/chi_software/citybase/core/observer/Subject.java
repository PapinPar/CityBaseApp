package chi_software.citybase.core.observer;

/**
 * Created by Papin on 08.11.2016.
 */

public interface Subject <O extends Subscriber> {
    void Subscribe (O observer);

    void UnSubscribe (O observer);

    boolean IsSubscribe (O observer);

    void notifySuccessSubscribers (int eventId, Object object);

    void notifyErrorSubscribers (int eventId, Object object);

}