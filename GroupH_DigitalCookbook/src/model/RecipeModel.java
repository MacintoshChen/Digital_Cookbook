package model;

import view.components.IngredientAddPane;

import java.util.LinkedList;

/**
 * Model for all views that display information of a recipe
 */
public class RecipeModel {
    private String name;
    private String serveAmount;
    private LinkedList<IngredientAddPane> ingredients;
    private String steps;
    private String videoURL;
    private String imgURL;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServeAmount() {
        return serveAmount;
    }

    public void setServeAmount(String serveAmount) {
        this.serveAmount = serveAmount;
    }

    public LinkedList<IngredientAddPane> getIngredients() {
        return ingredients;
    }

    public void setIngredients(LinkedList<IngredientAddPane> ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
}
