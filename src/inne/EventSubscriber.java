package inne;

import java.util.LinkedList;
import java.util.List;
//Wykonali Kacper Filipiak i Igor Arciszewski 13.06.2022r.
//Wzorzxec obserwatora w celu ulatwienia odswierzania UI
public class EventSubscriber {
    static List<EventBus> subscribers = new LinkedList<>();

    public static void publishEvent() {
        for (EventBus subscriber :
                subscribers) {
            subscriber.onEvent();
        }
    }

    public static void subscribe(EventBus subscriber) {
        subscribers.add(subscriber);
    }

    public static void unsubscribe(EventBus subscriber) {
        subscribers.remove(subscriber);
    }
}
