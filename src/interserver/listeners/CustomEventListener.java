package interserver.listeners;

public class CustomEventListener extends EventListener {

    public void run(Object event) {
        subscriptions.each(sub -> sub.get(event));
    }
}
