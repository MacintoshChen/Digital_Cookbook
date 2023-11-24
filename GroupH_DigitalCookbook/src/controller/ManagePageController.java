package controller;

import constant.DataBaseFieldEnum;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import model.SearchPageModel;
import util.DatabaseUtils;
import view.majorView.ManagePageView;
import javafx.event.ActionEvent;


/**
 * The Controller Class to handle all events on the Manage Recipe Page
 * @param <T>
 */
public class ManagePageController <T extends Event> implements EventHandler<T> {
    ManagePageView managePageView;
    SearchPageModel searchPageModel;

    /**
     * Construction
     * @param view  View
     * @param model Model
     */
    public ManagePageController(ManagePageView view, SearchPageModel model){
        managePageView = view;
        searchPageModel = model;
    }


    /**
     * Based on different kinds of actionEvent to handle different events
     * @param actionEvent
     */
    @Override
    public void handle(T actionEvent) {
        Object eventSource = actionEvent.getSource();
        //If the actionEvent is KeyEvent, which means the user is entering search criteria in the Text Field
        if (actionEvent instanceof KeyEvent){
            if (eventSource==managePageView.searchTextField){
                searchPageModel.setSearchResult(
                        DatabaseUtils.getInstance().selectRecipe(
                                DataBaseFieldEnum.NAME,
                                managePageView.searchTextField.getText()
                        )
                );
                managePageView.updateFirstPage();
            }
        }
        // If the actionEvent is ActionEvent, which means the user is click the buttons
        else if (actionEvent instanceof ActionEvent) {
            if (eventSource==managePageView.nextButton){
                searchPageModel.setSearchResult(
                        DatabaseUtils.getInstance().selectRecipe(
                                DataBaseFieldEnum.NAME,
                                managePageView.searchTextField.getText()
                        )
                );
                managePageView.updatePage(1);
            } else if (eventSource==managePageView.previousButton) {
                searchPageModel.setSearchResult(
                        DatabaseUtils.getInstance().selectRecipe(
                                DataBaseFieldEnum.NAME,
                                managePageView.searchTextField.getText()
                        )
                );
                managePageView.updatePage(-1);
            }
        }
    }
}
