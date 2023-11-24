package entities;

import util.UUIDUtils;

import java.util.Map;

/**
 * Entity class of Recipe
 */
public class Recipe {
    String id;
    String name;
    int peopleNumber;
    Map<Ingredient, Float> ingredients;
    String steps;
    String videoURL;
    String imgURL;

    /**
     * Constructor for creating a new recipe to put into database
     *
     * @param name         Recipe name
     * @param peopleNumber Recipe peopleNumber
     * @param ingredients  Recipe related ingredients
     * @param steps        Recipe Steps
     * @param videoURL     Recipe Video URL
     */
    public Recipe(String name, int peopleNumber, Map<Ingredient, Float> ingredients, String steps, String videoURL, String imgURL) {
        this.id = UUIDUtils.getUUID();
        this.name = name;
        this.peopleNumber = peopleNumber;
        this.ingredients = ingredients;
        this.steps = steps;
        this.videoURL = videoURL;
        this.imgURL = imgURL;
    }


    /**
     * Constructor for get a recipe object from database
     *
     * @param id           Recipe id
     * @param name         Recipe name
     * @param peopleNumber Recipe peopleNumber
     * @param ingredients  Recipe related ingredients
     * @param steps        Recipe Steps
     * @param videoURL     Recipe Video URL
     */
    public Recipe(String id, String name, int peopleNumber, Map<Ingredient, Float> ingredients, String steps, String videoURL, String imageURL) {
        this.id = id;
        this.name = name;
        this.peopleNumber = peopleNumber;
        this.ingredients = ingredients;
        this.steps = steps;
        this.videoURL = videoURL;
        this.imgURL = imageURL;
    }

    public void setAttributes(Recipe recipe) {
        name = recipe.getName();
        peopleNumber = recipe.getPeopleNumber();
        ingredients = recipe.getIngredients();
        steps = recipe.getSteps();
        videoURL = recipe.getVideoURL();
        imgURL = recipe.getImgURL();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(int peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public Map<Ingredient, Float> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<Ingredient, Float> ingredients) {
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

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getImgURL() {
        return imgURL;
    }

}
