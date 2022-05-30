package inne;

import java.util.LinkedList;
import java.util.List;

public class EventSubscriber {
    static List<EventBus> subscribers = new LinkedList<>();
    public static void publishEvent(){
        for (EventBus subscriber:
             subscribers) {
            subscriber.onEvent();
        }
    }
    public static void subscribe(EventBus subscriber){
        subscribers.add(subscriber);
    }
    public static void unsubscribe(EventBus subscriber){
        subscribers.remove(subscriber);
    }
}
