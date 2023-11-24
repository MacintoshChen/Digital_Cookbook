package constant;

import javafx.event.Event;
import javafx.event.EventType;
import util.MyEventHandler;

/**
 * This class is the base class for all update custom events
 * An abstract class
 */
public abstract class UpdateEventBase extends Event {
    public static final EventType<UpdateEventBase> MY_EVENT_TYPE_BASE = new EventType<>(ANY);

    public UpdateEventBase(EventType<? extends Event> eventType){
        super(eventType);
    }

    public abstract void invokeHandler(MyEventHandler handler);
}
