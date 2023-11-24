package view.majorView;

import constant.UpdateEventBase;
import controller.CreateRecipePageController;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.RecipeModel;
import util.MyEventHandler;
import view.components.TitlePane;

/**
 * Create recipe view
 */
public class CreateRecipePageView extends AnchorPane {
    RecipeModel recipeModel = new RecipeModel();
    CreateRecipePageController createRecipePageController = new CreateRecipePageController(this, recipeModel);

    public TextField recipeTitle;
    public TextField serveAmountField;
    public Text serveAmountText;
    public Text ingredientsText;
    public ScrollPane ingredientsScrollPane;
    public VBox ingredientsVBox;
    public ImageView imageView;
    public Text stepsText;
    public TextArea stepsTextArea;
    public Button addIngredientButton;
    public Button deleteAllButton;
    public Button addVideoButton;
    public Text urlText;
    public Button confirmButton;
    public Button cancelButton;

    /**
     * Constructor
     */
    CreateRecipePageView(){
        setPrefHeight(684.0);
        setPrefWidth(1123.0);
        setStyle("-fx-background-image: url('file:Background/background.png');");
        initializeUI();
    }

    /**
     * Initialize UI
     */
    private void initializeUI() {
        recipeTitle = new TextField();
        recipeTitle.setLayoutX(360.0);
        recipeTitle.setLayoutY(65.0);
        recipeTitle.setPromptText("RecipeName");
        recipeTitle.setFont(new Font("Bodoni MT Black", 31.0));

        serveAmountField = new TextField();
        serveAmountField.setLayoutX(970.0);
        serveAmountField.setLayoutY(43.0);
        serveAmountField.setPrefHeight(23.0);
        serveAmountField.setPrefWidth(75.0);

        serveAmountText = new Text();
        serveAmountText.setLayoutX(846.0);
        serveAmountText.setLayoutY(61.0);
        serveAmountText.setStrokeWidth(0.0);
        serveAmountText.setText("Serve Amount:");
        serveAmountText.setFont(new Font(16.0));
        serveAmountText.setStyle("-fx-fill: WHITE");

        ingredientsText = new Text();
        ingredientsText.setLayoutX(36.0);
        ingredientsText.setLayoutY(181.0);
        ingredientsText.setStrokeWidth(0.0);
        ingredientsText.setText("Ingredients:");
        ingredientsText.setFont(new Font(16.0));

        ingredientsVBox = new VBox();
        ingredientsVBox.getChildren().add(new TitlePane(ingredientsVBox));
        ingredientsScrollPane = new ScrollPane(ingredientsVBox);
        ingredientsScrollPane.setLayoutX(35.0);
        ingredientsScrollPane.setLayoutY(197.0);
        ingredientsScrollPane.setPrefHeight(202.0);
        ingredientsScrollPane.setPrefWidth(520.0);

        imageView = new ImageView();
        imageView.setFitHeight(146.0);
        imageView.setFitWidth(160.0);
        imageView.setLayoutX(36.0);
        imageView.setLayoutY(14.0);
        imageView.setPickOnBounds(true);
        imageView.setImage(new Image("file:img/uploadImg.jpg"));
        imageView.setOnMouseClicked(createRecipePageController);

        stepsText = new Text();
        stepsText.setLayoutX(591.0);
        stepsText.setLayoutY(180.0);
        stepsText.setStrokeWidth(0.0);
        stepsText.setText("Steps:");
        stepsText.setFont(new Font(16.0));

        stepsTextArea = new TextArea();
        stepsTextArea.setLayoutX(591.0);
        stepsTextArea.setLayoutY(197.0);
        stepsTextArea.setWrapText(true);

        addIngredientButton = new Button();
        addIngredientButton.setLayoutX(35.0);
        addIngredientButton.setLayoutY(423.0);
        addIngredientButton.setText("Add Ingredient");
        addIngredientButton.setOnAction(createRecipePageController);

        deleteAllButton = new Button();
        deleteAllButton.setLayoutX(159.0);
        deleteAllButton.setLayoutY(423.0);
        deleteAllButton.setText("Delete All");
        deleteAllButton.setOnAction(createRecipePageController);

        addVideoButton = new Button();
        addVideoButton.setLayoutX(706.0);
        addVideoButton.setLayoutY(553.0);
        addVideoButton.setText("Add Video");
        addVideoButton.setOnAction(createRecipePageController);

        urlText = new Text();
        urlText.setLayoutX(447.0);
        urlText.setLayoutY(572.0);
        urlText.setStrokeWidth(0.0);
        urlText.setText("null");
        urlText.setWrappingWidth(228.38037109375);
        urlText.setFont(new Font(19.0));

        confirmButton = new Button();
        confirmButton.setLayoutX(487.0);
        confirmButton.setLayoutY(623.0);
        confirmButton.setText("Confirm");
        confirmButton.setOnAction(createRecipePageController);
        confirmButton.addEventHandler(UpdateEventBase.MY_EVENT_TYPE_BASE, MyEventHandler.getInstance());

        cancelButton = new Button();
        cancelButton.setLayoutX(569.0);
        cancelButton.setLayoutY(623.0);
        cancelButton.setText("Clear");
        cancelButton.setOnAction(createRecipePageController);


        getChildren().addAll(
                recipeTitle,
                serveAmountField,
                serveAmountText,
                ingredientsText,
                ingredientsScrollPane,
                imageView,
                stepsText,
                stepsTextArea,
                addIngredientButton,
                deleteAllButton,
                addVideoButton,
                urlText,
                confirmButton,
                cancelButton
        );
    }
}
