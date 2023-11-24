package view.components;

import entities.Recipe;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import util.SceneManager;
import view.majorView.ModifyRecipePageView;

/**
 * Summary pane for each recipe for manage view
 */
public class ManageRecipeSummaryPane extends AnchorPane {
    public Text text;
    public ImageView imageView;
    public Recipe recipe;

    /**
     * Constructor
     * @param _recipe The underlying recipe
     */
    public ManageRecipeSummaryPane(Recipe _recipe){
        recipe = _recipe;
        setStyle("-fx-background-color: WHITE; -fx-border-color: BLACK");

        text = new Text(_recipe.getName());
        text.setLayoutY(175);
        text.setStrokeWidth(0);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setWrappingWidth(256);
        text.setFont(new Font(29));

        imageView = new ImageView();
        imageView.setFitHeight(140);
        imageView.setFitWidth(140);
        imageView.setLayoutX(58);
        imageView.setPickOnBounds(true);
        imageView.setPreserveRatio(true);
        try{
            Image img = new Image("file:" + recipe.getImgURL());
            imageView.setImage(img);
        }catch (Exception e){
        }

        getChildren().addAll(text, imageView);

        setOnMouseClicked(mouseEvent -> {
            Scene s = new Scene(new ModifyRecipePageView(recipe));
            SceneManager.getInstance().registerScene(s);
        });
    }
}
