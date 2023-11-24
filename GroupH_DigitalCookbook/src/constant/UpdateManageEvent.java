package constant;

import javafx.event.EventType;
import util.MyEventHandler;

/**
 * Event for update manage page
 */
public class UpdateManageEvent extends UpdateEventBase{
    public static final EventType<UpdateEventBase> UPDATE_SEARCH = new EventType<>(MY_EVENT_TYPE_BASE, "update manage");

    /**
     * Constructor
     */
    public UpdateManageEvent(){
        super(UPDATE_SEARCH);
    }

    /**
     * Call the event handler
     * @param handler Event handler
     */
    @Override
    public void invokeHandler(MyEventHandler handler){
        handler.onEventManage();
    }
}
