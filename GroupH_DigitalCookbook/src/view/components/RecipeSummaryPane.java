package view.components;

import util.SceneManager;
import view.majorView.ViewRecipePageView;
import entities.Recipe;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.TextAlignment;

/**
 * Recipe summary pane for search page
 */
public class RecipeSummaryPane extends AnchorPane {
    public Recipe recipe;
    public ImageView imageView;
    public VBox vBox;
    public Text recipeName;
    public Font font1;
    public Font font2;
    public Text stepsText;

    /**
     * Constructor
     * @param _recipe The underlying recipe
     */
    public RecipeSummaryPane(Recipe _recipe) {
        recipe = _recipe;
        setPrefHeight(126.0);
        setPrefWidth(600.0);
        initializeUI();
        setStyle("-fx-border-color: BLACK;");
        setOnMouseClicked(mouseEvent -> {
            Scene s = new Scene(new ViewRecipePageView(recipe));
            SceneManager.getInstance().registerScene(s);
        });
    }

    /**
     * Initialize UI
     */
    private void initializeUI() {

        imageView = new ImageView();
        imageView.setFitHeight(163.0);
        imageView.setFitWidth(146.0);
        imageView.setLayoutX(6.0);
        imageView.setPickOnBounds(true);
        try{
            Image img = new Image("file:" + recipe.getImgURL());
            imageView.setImage(img);
        }catch (Exception e){
        }

        vBox = new VBox();
        vBox.setLayoutX(159.0);
        vBox.setPrefHeight(163.0);
        vBox.setPrefWidth(442.0);

        recipeName = new Text(recipe.getName());
        recipeName.setStrokeType(StrokeType.OUTSIDE);
        recipeName.setStrokeWidth(0.0);
        recipeName.setTextAlignment(TextAlignment.JUSTIFY);
        recipeName.setWrappingWidth(440.240234375);
        font1 = Font.font("Bradley Hand ITC", 30.0);
        recipeName.setFont(font1);

        int endIndex = Math.min(30, recipe.getSteps().length());
        stepsText = new Text(recipe.getSteps().substring(0, endIndex));
        stepsText.setOpacity(0.32);
        stepsText.setStrokeType(StrokeType.OUTSIDE);
        stepsText.setStrokeWidth(0.0);
        stepsText.setTextAlignment(TextAlignment.JUSTIFY);
        stepsText.setWrappingWidth(440.400390625);
        font2 = Font.font("Times New Roman Bold Italic", 20.0);
        stepsText.setFont(font2);

        vBox.getChildren().addAll(recipeName, stepsText);
        getChildren().addAll(imageView,vBox);
    }
}
