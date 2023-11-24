package view.majorView;

import controller.SearchPageController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.SearchPageModel;
import view.components.ClockPane;
import view.components.RecipeSummaryPane;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Search page view
 */
public class SearchPageView extends AnchorPane {
    SearchPageModel searchPageModel = new SearchPageModel();
    SearchPageController searchPageController = new SearchPageController(this, searchPageModel);
    public TextField searchTextField;
    public ScrollPane scrollPane;
    public VBox vBox;
    public Text titleText;
    private ClockPane clock = new ClockPane();

    /**
     * Constructor
     */
    public SearchPageView() {
        setStyle("-fx-background-image: url('file:Background/background.png');");
        initializeUI();
        update();
    }

    /**
     * Update view according to search result
     */
    public void update(){
        vBox.getChildren().clear();
        for(var r : searchPageModel.getSearchResult()){
            vBox.getChildren().add(new RecipeSummaryPane(r));
        }
        scrollPane.setVvalue(0);
    }

    private void initializeUI() {
        searchTextField = new TextField();
        searchTextField.setLayoutX(373.0);
        searchTextField.setLayoutY(176.0);
        searchTextField.setPrefHeight(23.0);
        searchTextField.setPrefWidth(377.0);
        searchTextField.setPromptText("Search Recipe");
        searchTextField.setFont(new Font("Times New Roman", 13.0));
        searchTextField.setOnKeyReleased(searchPageController);

        scrollPane = new ScrollPane();
        scrollPane.setLayoutX(261.0);
        scrollPane.setLayoutY(230.0);
        scrollPane.setPrefHeight(400.0);
        scrollPane.setPrefWidth(600.0);

        vBox = new VBox();
        vBox.setPrefHeight(400.0);
        vBox.setPrefWidth(600.0);
        scrollPane.setContent(vBox);

        titleText = new Text("Tasty MCL");
        titleText.setStyle("-fx-fill: BLACK");
        titleText.setLayoutX(410.0);
        titleText.setLayoutY(106.0);
        titleText.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        titleText.setStrokeWidth(0.0);
        titleText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        titleText.setFont(new Font("Snap ITC", 48.0));

        /*clock.setLayoutX(0.0);
        clock.setLayoutY(0.0);
        clock.setStyle("-fx-background-color: WHITE;");
        Timeline animation = new Timeline(
                new KeyFrame(
                        Duration.millis(1000),
                        actionEvent -> clock.setCurrentTime()
                )
        );
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();*/ // If need to add clock

        getChildren().addAll(searchTextField, scrollPane, titleText);
    }
}
