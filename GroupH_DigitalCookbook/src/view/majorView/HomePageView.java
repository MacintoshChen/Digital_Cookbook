package view.majorView;

import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

/**
 * Home page
 */
public class HomePageView extends Pane {
    public TabPane tabPane;
    public Tab searchTab;
    public Tab manageRecipeTab;
    public Tab addRecipeTab;

    /**
     * Constructor
     */
    public HomePageView() {
        setPrefHeight(684.0);
        setPrefWidth(1123.0);
        initializeUI();
    }

    /**
     * Initialize UI
     */
    private void initializeUI() {
        tabPane = new TabPane();
        tabPane.setSide(Side.TOP);
        tabPane.setPrefHeight(684.0);
        tabPane.setPrefWidth(1123.0);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        searchTab = new Tab();
        searchTab.setClosable(false);
        searchTab.setText("Search");
        searchTab.setContent(new SearchPageView());

        manageRecipeTab = new Tab();
        manageRecipeTab.setClosable(false);
        manageRecipeTab.setText("Manage Recipe");
        manageRecipeTab.setContent(new ManagePageView());

        addRecipeTab = new Tab();
        addRecipeTab.setClosable(false);
        addRecipeTab.setText("Add Recipe");
        //addRecipeTab.setContent(new CreateRecipePageView());
        addRecipeTab.setContent(new CreateRecipePageView());
        tabPane.getTabs().addAll(searchTab, manageRecipeTab, addRecipeTab);

        getChildren().add(tabPane);
    }
}
