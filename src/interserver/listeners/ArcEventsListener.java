package interserver.listeners;

import arc.Events;

public class ArcEventsListener extends EventListener {

    public void listen(Class<?> type) {
        Events.on(type, event -> subscriptions.each(sub -> sub.get(event)));
    }
}
