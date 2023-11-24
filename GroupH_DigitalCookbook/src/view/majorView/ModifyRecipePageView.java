package view.majorView;

import constant.UpdateEventBase;
import controller.ModifyRecipePageController;
import entities.Ingredient;
import entities.Recipe;

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
import view.components.IngredientAddPane;
import view.components.TitlePane;

/**
 * Modify recipe view
 */
public class ModifyRecipePageView extends AnchorPane {

    RecipeModel recipeModel = new RecipeModel();
    ModifyRecipePageController modifyRecipePageController = new ModifyRecipePageController(this, recipeModel);

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
    public Button deleteButton;
    public Recipe recipe;

    /**
     * Constructor
     * @param r Underlying recipe
     */
    public ModifyRecipePageView(Recipe r) {
        recipe = r;
        // show info about R
        // later to be done
        setPrefHeight(684.0);
        setPrefWidth(1123.0);
        setStyle("-fx-background-image: url('file:Background/background.png')");
        initializeUI();
    }

    /**
     * Initialize UI
     */
    private void initializeUI() {
        recipeTitle = new TextField();
        recipeTitle.setLayoutX(360.0);
        recipeTitle.setLayoutY(65.0);
        recipeTitle.setText(recipe.getName());
        recipeTitle.setFont(new Font("Bodoni MT Black", 31.0));

        serveAmountField = new TextField();
        serveAmountField.setLayoutX(970.0);
        serveAmountField.setLayoutY(43.0);
        serveAmountField.setPrefHeight(23.0);
        serveAmountField.setPrefWidth(75.0);
        serveAmountField.setText(String.valueOf(recipe.getPeopleNumber()));


        serveAmountText = new Text();
        serveAmountText.setLayoutX(846.0);
        serveAmountText.setLayoutY(61.0);
        serveAmountText.setStrokeWidth(0.0);
        serveAmountText.setText("Serve Amount:");
        serveAmountText.setFont(new Font(16.0));

        ingredientsText = new Text();
        ingredientsText.setLayoutX(36.0);
        ingredientsText.setLayoutY(181.0);
        ingredientsText.setStrokeWidth(0.0);
        ingredientsText.setText("Ingredients:");
        ingredientsText.setFont(new Font(16.0));

        ingredientsVBox = new VBox();
        ingredientsScrollPane = new ScrollPane(ingredientsVBox);
        ingredientsVBox.getChildren().add(new TitlePane(ingredientsVBox));
        ingredientsScrollPane.setLayoutX(35.0);
        ingredientsScrollPane.setLayoutY(197.0);
        ingredientsScrollPane.setPrefHeight(202.0);
        ingredientsScrollPane.setPrefWidth(520.0);


        for (Ingredient ingredient:recipe.getIngredients().keySet()) {
           IngredientAddPane ingredientAddPane= new IngredientAddPane(ingredientsVBox);
           ingredientAddPane.setName(ingredient.getName());
           ingredientAddPane.setAmount(recipe.getIngredients().get(ingredient).toString());
           ingredientAddPane.setUnit(ingredient.getUnit());

           ingredientsVBox.getChildren().add(ingredientAddPane);
        }


        imageView = new ImageView();
        imageView.setFitHeight(146.0);
        imageView.setFitWidth(160.0);
        imageView.setLayoutX(36.0);
        imageView.setLayoutY(14.0);
        imageView.setPickOnBounds(true);
        imageView.setOnMouseClicked(modifyRecipePageController);
        try{
            Image img = new Image("file:" + recipe.getImgURL());
            imageView.setImage(img);
        }catch (Exception e){
        }finally{
            if(recipe.getImgURL() == null || recipe.getImgURL().equals("")){
                imageView.setImage(new Image("file:img/uploadImg.jpg"));
            }
        }

        stepsText = new Text();
        stepsText.setLayoutX(591.0);
        stepsText.setLayoutY(180.0);
        stepsText.setStrokeWidth(0.0);
        stepsText.setText("Steps:");
        stepsText.setFont(new Font(16.0));

        stepsTextArea = new TextArea();
        stepsTextArea.setText(recipe.getSteps());
        stepsTextArea.setWrapText(true);
        stepsTextArea.setLayoutX(591.0);
        stepsTextArea.setLayoutY(197.0);

        addIngredientButton = new Button();
        addIngredientButton.setLayoutX(35.0);
        addIngredientButton.setLayoutY(423.0);
        addIngredientButton.setText("Add Ingredient");
        addIngredientButton.setOnAction(modifyRecipePageController);

        deleteAllButton = new Button();
        deleteAllButton.setLayoutX(159.0);
        deleteAllButton.setLayoutY(423.0);
        deleteAllButton.setText("Delete All");
        deleteAllButton.setOnAction(modifyRecipePageController);

        addVideoButton = new Button();
        addVideoButton.setLayoutX(706.0);
        addVideoButton.setLayoutY(553.0);
        addVideoButton.setText("Add Video");
        addVideoButton.setOnAction(modifyRecipePageController);

        urlText = new Text();
        urlText.setLayoutX(447.0);
        urlText.setLayoutY(572.0);
        urlText.setStrokeWidth(0.0);
        urlText.setText(recipe.getVideoURL());
        urlText.setWrappingWidth(228.38037109375);
        urlText.setFont(new Font(19.0));

        confirmButton = new Button();
        confirmButton.setLayoutX(447.0);
        confirmButton.setLayoutY(623.0);
        confirmButton.setText("Confirm");
        confirmButton.setOnAction(modifyRecipePageController);
        confirmButton.addEventHandler(UpdateEventBase.MY_EVENT_TYPE_BASE, MyEventHandler.getInstance());

        cancelButton = new Button();
        cancelButton.setLayoutX(609.0);
        cancelButton.setLayoutY(623.0);
        cancelButton.setText("Back");
        cancelButton.setOnAction(modifyRecipePageController);
        cancelButton.addEventHandler(UpdateEventBase.MY_EVENT_TYPE_BASE, MyEventHandler.getInstance());

        deleteButton = new Button();
        deleteButton.setLayoutX(534.0);
        deleteButton.setLayoutY(623.0);
        deleteButton.setText("Delete");
        deleteButton.setOnAction(modifyRecipePageController);

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
                cancelButton,
                deleteButton
        );
    }
}
