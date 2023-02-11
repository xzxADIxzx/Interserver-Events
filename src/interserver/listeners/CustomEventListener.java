package interserver.listeners;

public class CustomEventListener extends EventListener {

    public void fire(Object event) {
        subscriptions.each(sub -> sub.get(event));
    }

    public void received(Object event) {
        fire(event);
    }
}
