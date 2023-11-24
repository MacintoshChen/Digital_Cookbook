package constant;

import javafx.event.EventType;
import util.MyEventHandler;

/**
 * Event for updating search page
 */
public class UpdateSearchEvent extends UpdateEventBase{
    public static final EventType<UpdateEventBase> UPDATE_SEARCH = new EventType<>(MY_EVENT_TYPE_BASE, "update search");

    /**
     * Constructor
     */
    public UpdateSearchEvent(){
        super(UPDATE_SEARCH);
    }

    /**
     * Call event handler
     * @param handler Event handler
     */
    @Override
    public void invokeHandler(MyEventHandler handler){
        handler.onEventSearch();
    }
}
