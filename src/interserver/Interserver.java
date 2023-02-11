package interserver;

import arc.net.Client;
import arc.net.Connection;
import arc.net.NetListener;
import arc.net.Server;
import interserver.listeners.EventListener;

public class Interserver {

    public static Client client = new Client(8192, 8192, new EventSerializer());
    public static Server server = new Server(32768, 8192, new EventSerializer());

    public static void load(EventListener listener) {
        listener.subscribe(Interserver::sendEvent);

        // TODO many clients or custom connections?
        client.addListener(new NetListener() {
            @Override
            public void received(Connection connection, Object object) {}
        });

        server.addListener(new NetListener() {
            @Override
            public void received(Connection connection, Object object) {
                if (object != null) listener.received(object);
            }
        });
    }

    public static void sendEvent(Object object) {}
}
