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
import view.majorView.ModifyRecipePageView;

import java.io.File;
import java.util.LinkedList;

/**
 * Controller for modify recipe page
 * @param <T> Type of event
 */
public class ModifyRecipePageController<T extends Event> implements EventHandler<T> {
    ModifyRecipePageView modifyRecipePageView;
    RecipeModel recipeModel;

    /**
     * Constructor
     * @param view  View
     * @param model Model
     */
    public ModifyRecipePageController(ModifyRecipePageView view, RecipeModel model) {
        modifyRecipePageView = view;
        recipeModel = model;
    }

    /**
     * Handle the event
     * @param actionEvent Event
     */
    @Override
    public void handle(T actionEvent) {
        Object eventSource = actionEvent.getSource();
        if(actionEvent instanceof ActionEvent){
            if(eventSource == modifyRecipePageView.addIngredientButton){
                modifyRecipePageView.ingredientsVBox.getChildren().add(new IngredientAddPane(modifyRecipePageView.ingredientsVBox));
            }else if(eventSource == modifyRecipePageView.deleteAllButton){
                modifyRecipePageView.ingredientsVBox.getChildren().clear();
                modifyRecipePageView.ingredientsVBox.getChildren().add(new TitlePane(modifyRecipePageView.ingredientsVBox));
            }else if(eventSource == modifyRecipePageView.cancelButton){
                SceneManager.getInstance().closeScene();
                modifyRecipePageView.cancelButton.fireEvent(new UpdateManageEvent());
                modifyRecipePageView.cancelButton.fireEvent(new UpdateSearchEvent());
            }else if(eventSource == modifyRecipePageView.addVideoButton){
                File selectedFile = Utils.getSelectedFile();
                if (selectedFile != null) {
                    if(Utils.copyFile(selectedFile, "video")) {
                        modifyRecipePageView.urlText.setText("video" + File.separator + selectedFile.getName());
                    }
                }
                Platform.runLater(() -> SceneManager.getInstance().getRootStage().show()); // Show the hidden Stage, weird that directly show the stage will throw a exception
            }else if (eventSource == modifyRecipePageView.confirmButton) {
                updateModel(); // update model to store right data

                try {
                    Recipe recipe = Utils.extractRecipe(recipeModel);
                    modifyRecipePageView.recipe.setAttributes(recipe);

                    if(Utils.showAlert(Alert.AlertType.CONFIRMATION, "confirm to modify recipe")){
                        DatabaseUtils.getInstance().updateRecipe(modifyRecipePageView.recipe);
                        SceneManager.getInstance().closeScene();
                        modifyRecipePageView.confirmButton.fireEvent(new UpdateManageEvent());
                        modifyRecipePageView.confirmButton.fireEvent(new UpdateSearchEvent());
                    }

                } catch (Exception e) {
                    errorHandle(e.getMessage());
                } // try to insert recipe
            } else if(eventSource == modifyRecipePageView.deleteButton) {
                if(Utils.showAlert(Alert.AlertType.CONFIRMATION, "confirm to delete recipe")) {
                    DatabaseUtils.getInstance().deleteRecipe(modifyRecipePageView.recipe.getId());
                    SceneManager.getInstance().closeScene();
                    modifyRecipePageView.confirmButton.fireEvent(new UpdateManageEvent());
                    modifyRecipePageView.confirmButton.fireEvent(new UpdateSearchEvent());
                    Utils.showAlert(Alert.AlertType.INFORMATION, "successfully deleted from database");
                }
            }
        }else if(actionEvent instanceof MouseEvent){
            if(actionEvent.getSource() == modifyRecipePageView.imageView){
                File selectedFile = Utils.getSelectedFile();
                if (selectedFile != null && Utils.isImage(selectedFile.getAbsolutePath())) {
                    if(Utils.copyFile(selectedFile, "img")) {
                        Image img = new Image("file:" + "img" + File.separator + selectedFile.getName());
                        modifyRecipePageView.imageView.setImage(img);
                    }
                } else if(selectedFile != null && !Utils.isImage(selectedFile.getAbsolutePath())){
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
            case "serve amount not a number", "serve amount must be a positive number",  "serve amount cannot be zero" -> modifyRecipePageView.serveAmountField.setText("");
        }
        Utils.showAlert(Alert.AlertType.ERROR, errorMessage);
    }

    /**
     * Update model according to current display of view
     */
    private void updateModel() {
        if(modifyRecipePageView.imageView.getImage() != null){
            String fileName = Utils.getFileNameFromURL(modifyRecipePageView.imageView.getImage().getUrl());
            recipeModel.setImgURL("img"+File.separator+fileName);
        }else{
            recipeModel.setImgURL("null");
        }

        if(modifyRecipePageView.imageView.getImage().getUrl().equals("file:img/uploadImg.jpg")){
            recipeModel.setImgURL("null");
        }
        recipeModel.setName(modifyRecipePageView.recipeTitle.getText());
        recipeModel.setServeAmount(modifyRecipePageView.serveAmountField.getText());
        recipeModel.setVideoURL(modifyRecipePageView.urlText.getText());
        recipeModel.setSteps(modifyRecipePageView.stepsTextArea.getText());
        LinkedList<IngredientAddPane> list = new LinkedList<>();
        for(int i = 1; i < modifyRecipePageView.ingredientsVBox.getChildren().size(); ++i){
            list.add((IngredientAddPane) modifyRecipePageView.ingredientsVBox.getChildren().get(i));
        }
        recipeModel.setIngredients(list);
    }

    /**
     * Clear screen
     */
    private void clear(){
        modifyRecipePageView.ingredientsVBox.getChildren().clear();
        modifyRecipePageView.ingredientsVBox.getChildren().add(new TitlePane(modifyRecipePageView.ingredientsVBox));
        modifyRecipePageView.serveAmountField.setText("");
        modifyRecipePageView.stepsTextArea.clear();
    }

}
