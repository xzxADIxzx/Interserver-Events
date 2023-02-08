package interserver;

import interserver.listeners.EventListener;

public class Interserver {

    public static void load(EventListener listener) {
        listener.subscribe(Interserver::sendEvent);
    }

    public static void sendEvent(Object object) {}
}
