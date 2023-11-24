package controller;

import constant.DataBaseFieldEnum;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import model.SearchPageModel;
import util.DatabaseUtils;
import view.majorView.SearchPageView;

/**
 * Controller of search page
 */
public class SearchPageController implements EventHandler<KeyEvent> {
    SearchPageView searchPageView;
    SearchPageModel searchPageModel;

    /**
     * Constructor
     * @param view  View
     * @param model Model
     */
    public SearchPageController(SearchPageView view, SearchPageModel model) {
        searchPageView = view;
        searchPageModel = model;
    }

    @Override
    public void handle(KeyEvent actionEvent) {
        var source = actionEvent.getSource();
        if(source == searchPageView.searchTextField){
            searchPageModel.setSearchResult(
                    DatabaseUtils.getInstance().selectRecipe(
                            DataBaseFieldEnum.NAME,
                            searchPageView.searchTextField.getText()
                    )
            );
        }
        searchPageView.update();
    }
}
