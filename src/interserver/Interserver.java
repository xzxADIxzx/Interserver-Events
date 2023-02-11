package interserver;

import arc.net.Client;
import arc.net.Server;
import interserver.listeners.EventListener;

public class Interserver {

    public static Client client = new Client(8192, 8192, new EventSerializer());
    public static Server server = new Server(32768, 8192, new EventSerializer());

    public static void load(EventListener listener) {
        listener.subscribe(Interserver::sendEvent);
    }

    public static void sendEvent(Object object) {}
}
