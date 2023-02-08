package interserver.listeners;

import arc.struct.Seq;

public abstract class EventListener {

    public Seq<Subscription> subscriptions = new Seq<>();

    /** Subscribes to an event listener that will invoke {@link Subscription#get(Object)} when it hears an event. */
    public void subscribe(Subscription subscription) {
        subscriptions.add(subscription);
    }

    /**
     * Unsubscribes from the event listener.
     * 
     * @param subscription to be canceled.
     * @return Whether there was such a subscription.
     */
    public boolean unsubscribe(Subscription subscription) {
        return subscriptions.remove(subscription);
    }

    /** Subscribe to an event listener. It will fire every time the listener hears an event. */
    public static interface Subscription {
        public void get(Object object);
    }
}
