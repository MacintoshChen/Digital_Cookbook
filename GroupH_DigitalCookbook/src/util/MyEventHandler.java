package util;

import constant.UpdateEventBase;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import view.majorView.HomePageView;
import view.majorView.ManagePageView;
import view.majorView.SearchPageView;

/**
 * Event handler to deal with my custom event
 */
public class MyEventHandler implements EventHandler<UpdateEventBase> {
    /**
     * Inner class to achieve single instance
     */
    private static class SingletonInstance{
        private static final MyEventHandler INSTANCE = new MyEventHandler();
    }

    /**
     * Get instance
     * @return Single instance
     */
    public static MyEventHandler getInstance(){
        return SingletonInstance.INSTANCE;
    }

    /**
     * Deal with Event
     * @param updateEventBase Event
     */
    @Override
    public void handle(UpdateEventBase updateEventBase) {
        updateEventBase.invokeHandler(getInstance());
    }

    /**
     * Deals with manage page
     */
    public void onEventManage(){
        (
                (ManagePageView)
                        (
                                (
                                        (HomePageView) SceneManager.getInstance().getSceneStack().peek().getRoot()
                                ).tabPane.getTabs().get(1).getContent()
                        )
        ).searchTextField.fireEvent(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.BACK_SPACE, false, false, false, false));
        
        (
                (ManagePageView)
                        (
                                (
                                        (HomePageView) SceneManager.getInstance().getSceneStack().peek().getRoot()
                                ).tabPane.getTabs().get(1).getContent()
                        )
        ).updateFirstPage();
    }

    /**
     * Deals with search page
     */
    public void onEventSearch(){
        (
                (SearchPageView)
                        (
                                (
                                        (HomePageView) SceneManager.getInstance().getSceneStack().peek().getRoot()
                                ).tabPane.getTabs().get(0).getContent()
                        )
        ).searchTextField.fireEvent(new KeyEvent(KeyEvent.KEY_RELEASED, "", "", KeyCode.BACK_SPACE, false, false, false, false));
    }
}
