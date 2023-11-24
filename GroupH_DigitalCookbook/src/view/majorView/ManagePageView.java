package view.majorView;

import controller.ManagePageController;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.SearchPageModel;
import view.components.ManageRecipeSummaryPane;

/**
 * Manage page view
 */
public class ManagePageView extends AnchorPane {
    SearchPageModel searchPageModel = new SearchPageModel();
    ManagePageController managePageController = new ManagePageController(this,searchPageModel);
    public TextField searchTextField;
    public Text titleText;
    public GridPane gridPane;
    public Button nextButton;
    public Button previousButton;

    private int currentPage = 1;
    private int recipeNumPerPage = 4;

    /**
     * Constructor
     */
    public ManagePageView() {
        setPrefHeight(684.0);
        setPrefWidth(1123.0);
        setStyle("-fx-background-image: url('file:Background/background.png');");
        initializeUI();
        updateFirstPage();
    }

    /**
     * Initialize UI
     */
    private void initializeUI() {
        searchTextField = new TextField();
        searchTextField.setLayoutX(404.0);
        searchTextField.setLayoutY(122.0);
        searchTextField.setPrefHeight(23.0);
        searchTextField.setPrefWidth(341.0);
        searchTextField.setPromptText("Search Recipe To Edit");
        searchTextField.setOnKeyReleased(managePageController);

        titleText = new Text("Tasty MCL");
        titleText.setLayoutX(445.0);
        titleText.setLayoutY(91.0);
        titleText.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        titleText.setStrokeWidth(0.0);
        titleText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        titleText.setFont(new Font("Snap ITC", 37.0));

        gridPane = new GridPane();
        gridPane.setLayoutX(306.0);
        gridPane.setLayoutY(190.0);
        gridPane.setPrefHeight(368.0);
        gridPane.setPrefWidth(512.0);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        column1.setMinWidth(10.0);
        column1.setPrefWidth(256.0);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHgrow(javafx.scene.layout.Priority.SOMETIMES);
        column2.setMinWidth(10.0);
        column2.setPrefWidth(256.0);

        RowConstraints row1 = new RowConstraints();
        row1.setMaxHeight(184.0);
        row1.setMinHeight(184.0);
        row1.setPrefHeight(184.0);
        row1.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        RowConstraints row2 = new RowConstraints();
        row2.setMaxHeight(184.0);
        row2.setMinHeight(184.0);
        row2.setPrefHeight(184.0);
        row2.setVgrow(javafx.scene.layout.Priority.SOMETIMES);

        gridPane.getColumnConstraints().addAll(column1, column2);
        gridPane.getRowConstraints().addAll(row1, row2);

        nextButton = new Button(">");
        nextButton.setLayoutX(562.0);
        nextButton.setLayoutY(609.0);
        nextButton.setMnemonicParsing(false);
        nextButton.setOnAction(managePageController);

        previousButton = new Button("<");
        previousButton.setLayoutX(535.0);
        previousButton.setLayoutY(609.0);
        previousButton.setMnemonicParsing(false);
        previousButton.setOnAction(managePageController);

        getChildren().addAll(searchTextField, titleText, gridPane, nextButton, previousButton);
    }

    /**
     * Change page
     * Circular array
     * @param changedPage Indicates whether the previous or next page
     */
    public void updatePage(int changedPage) {
        gridPane.getChildren().clear();

        int totalRecipes = searchPageModel.getSearchResult().size();
        if (totalRecipes == 0)
            return;

        int totalPages = (int) Math.ceil((double) totalRecipes / recipeNumPerPage);

        currentPage = (currentPage - 1 + totalPages + changedPage) % totalPages + 1;
        //currentPage = Math.max(1, Math.min(currentPage, totalPages));

        int startIndex = (currentPage - 1) * recipeNumPerPage;
        int endIndex = Math.min(startIndex + recipeNumPerPage, totalRecipes);

        int rowIndex = 0;
        int columnIndex = 0;

        for (int i = startIndex; i < endIndex; i++) {
            gridPane.add(new ManageRecipeSummaryPane(searchPageModel.getSearchResult().get(i)), columnIndex, rowIndex);
            columnIndex++;
            if (columnIndex == 2) {
                columnIndex = 0;
                rowIndex++;
            }

        }
    }


    public void updateFirstPage() {
        gridPane.getChildren().clear();
        int totalRecipes = searchPageModel.getSearchResult().size();
        currentPage = 1;
        if (totalRecipes == 0)
            return;

        int endIndex = Math.min(recipeNumPerPage, totalRecipes);
        int rowIndex = 0;
        int columnIndex = 0;

        for (int i = 0; i < endIndex; i++) {
            gridPane.add(new ManageRecipeSummaryPane(searchPageModel.getSearchResult().get(i)), columnIndex, rowIndex);
            columnIndex++;
            if (columnIndex == 2) {
                columnIndex = 0;
                rowIndex++;
            }

        }
    }
}
