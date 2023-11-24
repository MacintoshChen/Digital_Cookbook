package controller;

import constant.UpdateManageEvent;
import constant.UpdateSearchEvent;
import entities.Recipe;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import model.RecipeModel;
import util.DatabaseUtils;
import util.SceneManager;
import util.Utils;
import view.components.IngredientAddPane;
import view.components.TitlePane;
import view.majorView.CreateRecipePageView;

import java.io.File;
import java.util.LinkedList;

/**
 * Controller class for create recipe page
 * @param <T> The type of event
 */
public class CreateRecipePageController<T extends Event> implements EventHandler<T> {
    CreateRecipePageView createRecipePageView;
    RecipeModel recipeModel;

    /**
     * Constructor
     * @param view  View
     * @param model Model
     */
    public CreateRecipePageController(CreateRecipePageView view, RecipeModel model){
        createRecipePageView = view;
        recipeModel = model;
    }

    /**
     * Deals with the event
     * @param actionEvent Event
     */
    @Override
    public void handle(T actionEvent) {
        var source = actionEvent.getSource();
        if(actionEvent instanceof ActionEvent) {
            if (source == createRecipePageView.addIngredientButton) {
                createRecipePageView.ingredientsVBox
                        .getChildren()
                        .add(new IngredientAddPane(createRecipePageView.ingredientsVBox));
            } else if (source == createRecipePageView.deleteAllButton) {
                createRecipePageView.ingredientsVBox
                        .getChildren()
                        .clear();
                createRecipePageView.ingredientsVBox
                        .getChildren()
                        .add(new TitlePane(createRecipePageView.ingredientsVBox));
            } else if (source == createRecipePageView.cancelButton) {
                clear();
            } else if (source == createRecipePageView.addVideoButton) {
                java.io.File selectedFile = Utils.getSelectedFile();
                if (selectedFile != null) {
                    if(Utils.copyFile(selectedFile, "video")) {
                        createRecipePageView.urlText.setText("video" + File.separator + Utils.getFileNameFromURL(selectedFile.getAbsolutePath()));
                    }
                }
                Platform.runLater(() -> SceneManager.getInstance().getRootStage().show()); // Show the hidden Stage, weird that directly show the stage will throw a exception
            } else if (source == createRecipePageView.confirmButton) {
                updateModel(); // update model to store right data

                try {
                    Recipe recipe = Utils.extractRecipe(recipeModel);

                    if(Utils.showAlert(Alert.AlertType.CONFIRMATION, "confirm to create recipe")){
                        DatabaseUtils.getInstance().insertRecipe(recipe);
                        clear();
                        createRecipePageView.confirmButton.fireEvent(new UpdateSearchEvent());
                        createRecipePageView.confirmButton.fireEvent(new UpdateManageEvent());
                    }
                } catch (Exception e) {
                    errorHandle(e.getMessage());
                } // try to insert recipe

            }
        } else if (actionEvent instanceof MouseEvent) {
            if(actionEvent.getSource() == createRecipePageView.imageView){
                java.io.File selectedFile = Utils.getSelectedFile();
                if (selectedFile != null && Utils.isImage(selectedFile.getAbsolutePath())) {
                    if(Utils.copyFile(selectedFile, "img")) {
                        createRecipePageView.imageView.setImage(new Image("file:" + "img" + File.separator + selectedFile.getName()));
                    }else {
                        Utils.showAlert(Alert.AlertType.ERROR, "cannot insert image");
                    }
                } else if (selectedFile != null && !Utils.isImage(selectedFile.getAbsolutePath())) {
                    Utils.showAlert(Alert.AlertType.ERROR, "please insert a image");
                }
                Platform.runLater(() -> SceneManager.getInstance().getRootStage().show()); // Show the hidden Stage, weird that directly show the stage will throw a exception
            }
        }
    }

    /**
     * Reset view according to error message
     * @param errorMessage Message from the exception
     */
    private void errorHandle(String errorMessage) {
        switch (errorMessage){
            case "serve amount not a number", "serve amount must be a positive number", "serve amount cannot be zero" -> createRecipePageView.serveAmountField.setText("");
        }
        Utils.showAlert(Alert.AlertType.ERROR, errorMessage);
    }

    /**
     * Update model according to current display of view
     */
    private void updateModel() {
        if(createRecipePageView.imageView.getImage() != null && !createRecipePageView.imageView.getImage().getUrl().equals("file:img/uploadImg.jpg") ){
            String fileName = Utils.getFileNameFromURL(createRecipePageView.imageView.getImage().getUrl());
            recipeModel.setImgURL("img"+File.separator+fileName);
        }else{
            recipeModel.setImgURL("null");
        }
        recipeModel.setName(createRecipePageView.recipeTitle.getText());
        recipeModel.setServeAmount(createRecipePageView.serveAmountField.getText());
        recipeModel.setVideoURL(createRecipePageView.urlText.getText());
        recipeModel.setSteps(createRecipePageView.stepsTextArea.getText());
        LinkedList<IngredientAddPane> list = new LinkedList<>();
        for(int i = 1; i < createRecipePageView.ingredientsVBox.getChildren().size(); ++i){
            list.add((IngredientAddPane) createRecipePageView.ingredientsVBox.getChildren().get(i));
        }
        recipeModel.setIngredients(list);
    }

    /**
     * Reset view to initial state
     */
    private void clear(){
        createRecipePageView.ingredientsVBox.getChildren().clear();
        createRecipePageView.ingredientsVBox.getChildren().add(new TitlePane(createRecipePageView.ingredientsVBox));
        createRecipePageView.serveAmountField.clear();
        createRecipePageView.stepsTextArea.clear();
        createRecipePageView.urlText.setText("null");
        createRecipePageView.recipeTitle.setText("");
        createRecipePageView.imageView.setImage(new Image("file:img/uploadImg.jpg"));
    }
}
