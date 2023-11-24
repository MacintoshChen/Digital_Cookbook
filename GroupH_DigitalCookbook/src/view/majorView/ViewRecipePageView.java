package view.majorView;

import entities.Recipe;
import util.SceneManager;
import util.Utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

/**
 * View recipe view
 */
public class ViewRecipePageView extends AnchorPane {
    public Text titleText;
    public TextField serveAmountField;
    public Text serveAmountLabel;
    public Text ingredientsLabel;
    public ImageView imageView;
    public Text stepsLabel;
    public MediaView mediaView;
    public TextArea ingredientsTextArea;
    public TextArea stepsTextArea;
    public Button exitButton;
    private final Recipe recipe;

    /**
     * Constructor
     * @param _recipe Underlying recipe
     */
    public ViewRecipePageView(Recipe _recipe) {
        recipe = _recipe;
        setPrefHeight(886.0);
        setPrefWidth(1123.0);
        setStyle("-fx-background-image: url('file:Background/backgroundView.png');");
        initializeUI();
    }

    /**
     * Set ingredients according to current serve amount
     * @return The string of ingredients text field
     * @throws Exception Parse error
     */
    private String setIngredientsTextArea() throws Exception{
        double ratio;
        StringBuffer s = new StringBuffer();
        var ingredients = recipe.getIngredients();
        int inputNumber = Integer.parseInt(serveAmountField.getText());
        ratio = (double) inputNumber / recipe.getPeopleNumber();
        for(var i : ingredients.keySet()){
            s.append(i.getName())
                    .append(" ")
                    .append(ingredients.get(i)*ratio)
                    .append(i.getUnit())
                    .append(", \n");
        }
        if(inputNumber<=0){
            throw new Exception("");
        }
        return s.substring(0, s.length()-3)+".";
    }

    private void initializeUI() {
        titleText = new Text(recipe.getName());
        titleText.setStyle("-fx-fill: WHITE");
        titleText.setLayoutX(378.0);
        titleText.setLayoutY(65.0);
        titleText.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        titleText.setStrokeWidth(0.0);
        titleText.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        titleText.setWrappingWidth(366.240234375);
        titleText.setFont(Font.font("Bodoni MT Black", 31.0));

        serveAmountField = new TextField();
        serveAmountField.setLayoutX(970.0);
        serveAmountField.setLayoutY(43.0);
        serveAmountField.setPrefHeight(23.0);
        serveAmountField.setPrefWidth(75.0);
        serveAmountField.setText(String.valueOf(recipe.getPeopleNumber()));
        serveAmountField.setOnKeyReleased(keyEvent -> {
            if(serveAmountField.getText().equals("")){
                ingredientsTextArea.setText("");
            }else {
                try {
                    String text = setIngredientsTextArea();
                    ingredientsTextArea.setText(text);
                } catch (Exception e) {
                    Utils.showAlert(Alert.AlertType.ERROR, e.getMessage()+"It should be a positive integer!");
                    serveAmountField.setText("");
                    ingredientsTextArea.setText("");
                }
            }
        });

        serveAmountLabel = new Text("Serve Amount:");
        serveAmountLabel.setLayoutX(846.0);
        serveAmountLabel.setLayoutY(61.0);
        serveAmountLabel.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        serveAmountLabel.setStrokeWidth(0.0);
        serveAmountLabel.setFont(Font.font(16.0));
        serveAmountLabel.setStyle("-fx-fill: WHITE");

        ingredientsLabel = new Text("Ingredients:");
        ingredientsLabel.setLayoutX(36.0);
        ingredientsLabel.setLayoutY(181.0);
        ingredientsLabel.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        ingredientsLabel.setStrokeWidth(0.0);
        ingredientsLabel.setFont(Font.font(16.0));
        ingredientsLabel.setStyle("-fx-fill: WHITE");

        imageView = new ImageView();
        imageView.setFitHeight(146.0);
        imageView.setFitWidth(160.0);
        imageView.setLayoutX(126.0);
        imageView.setLayoutY(14.0);
        imageView.setPickOnBounds(true);
        try{
            Image img = new Image("file:" + recipe.getImgURL());
            imageView.setImage(img);
        }catch (Exception e){
           // System.out.println("exception");
        }

        stepsLabel = new Text("Steps:");
        stepsLabel.setLayoutX(591.0);
        stepsLabel.setLayoutY(180.0);
        stepsLabel.setStrokeType(javafx.scene.shape.StrokeType.OUTSIDE);
        stepsLabel.setStrokeWidth(0.0);
        stepsLabel.setFont(Font.font(16.0));
        stepsLabel.setStyle("-fx-fill: WHITE");

        mediaView = new MediaView();
        mediaView.setFitHeight(450.0);
        mediaView.setFitWidth(800.0);
        mediaView.setLayoutX(161.0);
        mediaView.setLayoutY(416.0);
        mediaView.setPreserveRatio(false);
        try{
            if((!recipe.getVideoURL().equals("")) || (recipe.getVideoURL() != null)) {
                File file = new File(recipe.getVideoURL());
                String url = file.toURI().toString();
                Media media = new Media(url);
                MediaPlayer player = new MediaPlayer(media);
                mediaView.setMediaPlayer(player);
                player.setCycleCount(MediaPlayer.INDEFINITE);
                player.play();
                System.out.println("play");
            }
        }catch (Exception e){
        	System.out.println("error");
        }

        ingredientsTextArea = new TextArea();
        ingredientsTextArea.setEditable(false);
        ingredientsTextArea.setLayoutX(36.0);
        ingredientsTextArea.setLayoutY(197.0);
        ingredientsTextArea.setPrefHeight(202.0);
        ingredientsTextArea.setPrefWidth(496.0);
        ingredientsTextArea.setWrapText(true);
        ingredientsTextArea.setFont(new Font(16));
        try{
            ingredientsTextArea.setText(setIngredientsTextArea());
        }catch (Exception e){
            Utils.showAlert(Alert.AlertType.ERROR, "please input a number");
        }

        stepsTextArea = new TextArea();
        stepsTextArea.setEditable(false);
        stepsTextArea.setLayoutX(591.0);
        stepsTextArea.setLayoutY(197.0);
        stepsTextArea.setPrefHeight(202.0);
        stepsTextArea.setPrefWidth(496.0);
        stepsTextArea.setWrapText(true);
        stepsTextArea.setFont(new Font(16));
        stepsTextArea.setText(recipe.getSteps());

        exitButton = new Button("back");
        exitButton.setLayoutX(36);
        exitButton.setLayoutY(65);
        exitButton.setOnAction(actionEvent -> {
            if(mediaView.getMediaPlayer() != null){
                mediaView.getMediaPlayer().stop();
            }
            SceneManager.getInstance().closeScene();
        });

        getChildren().addAll(titleText, serveAmountField, serveAmountLabel, ingredientsLabel, imageView, stepsLabel,
                mediaView, ingredientsTextArea, stepsTextArea, exitButton);
    }
}
