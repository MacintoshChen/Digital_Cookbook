package model;

import constant.DataBaseFieldEnum;
import entities.Recipe;
import util.DatabaseUtils;

import java.util.List;

/**
 * Model for search page
 */
public class SearchPageModel {
    List<Recipe> searchResult;

    public SearchPageModel(){
        searchResult = DatabaseUtils.getInstance().selectRecipe(DataBaseFieldEnum.NAME, ""); // First time select all the recipes
    }

    public List<Recipe> getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(List<Recipe> searchResult) {
        this.searchResult = searchResult;
    }
}
