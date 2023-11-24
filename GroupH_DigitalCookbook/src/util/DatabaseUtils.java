package util;

import constant.DataBaseFieldEnum;
import entities.Ingredient;
import entities.Recipe;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The class for all database method, include Insert, Select, Delete and Update
 */
public class DatabaseUtils {
    private final String DB_URL = "jdbc:mysql://localhost:3306/cookbookdb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "20011018";
    private final String DB_DRIVERNAME = "com.mysql.cj.jdbc.Driver";
    private Connection connection;


    /**
     * Connect the database
     */
    private DatabaseUtils(){
        try{
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Make this class a Single Instance
     */
    private static class SingleInstance{
        private static final DatabaseUtils INSTANCE = new DatabaseUtils();
    }

    /**
     * Return the only single instance
     * @return The only single instance
     */
    public static DatabaseUtils getInstance(){
        return SingleInstance.INSTANCE;
    }



    /**
     * Execute sql file to generate the cookbook database structure and date to current host's local database
     * @param scriptFileName The sql file's name
     */
    public void executeScript(String scriptFileName) {
        try {
            Class clazz = Class.forName(DB_DRIVERNAME);
            Driver driver = (Driver) clazz.newInstance();
            DriverManager.registerDriver(driver);

            Statement statement = connection.createStatement();

            InputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "\\sources\\sqlFiles\\" + scriptFileName);

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            StringBuilder script = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                script.append(line);
                if (line.endsWith(";")) {
                    statement.execute(script.toString());
                    script.setLength(0);
                }
            }

            reader.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Insert a new Recipe Object into database
     *
     * @param recipe The recipe object
     * @return Show whether the insert success
     */
    public boolean insertRecipe(Recipe recipe) {
        boolean success = false;
        int insertedRecipeRows = 0;
        int insertedRelationRows = 0;
        String insertRecipeTableQuery = "INSERT INTO tbl_recipe (id,name,steps,people_number,video_URL,img_URL) VALUES (?,?,?,?,?,?)";
        String insertIngredientTableQuery = "INSERT INTO tbl_ingredient (name,unit) VALUES (?,?)";
        String insertRelationTableQuery = "INSERT INTO tbl_recipe_ingredient_relation (recipe_id,ingredient_name,ingredient_unit,ingredient_quantity) VALUES (?,?,?,?)";


        try  {
            // insert into tbl_recipe table
            PreparedStatement insertRecipeStatement = connection.prepareStatement(insertRecipeTableQuery);
            insertRecipeStatement.setString(1, recipe.getId());
            insertRecipeStatement.setString(2, recipe.getName());
            insertRecipeStatement.setString(3, recipe.getSteps());
            insertRecipeStatement.setInt(4, recipe.getPeopleNumber());
            insertRecipeStatement.setString(5, recipe.getVideoURL());
            insertRecipeStatement.setString(6, recipe.getImgURL());
            insertedRecipeRows++;
            insertRecipeStatement.executeUpdate();
            insertRecipeStatement.close();

            // insert into tbl_recipe_ingredient_relation table
            PreparedStatement insertRelationStatement = connection.prepareStatement(insertRelationTableQuery);
            for (Ingredient ingredient : recipe.getIngredients().keySet()) {
                insertRelationStatement.setString(1, recipe.getId());
                insertRelationStatement.setString(2, ingredient.getName());
                insertRelationStatement.setString(3, ingredient.getUnit());
                insertRelationStatement.setFloat(4, recipe.getIngredients().get(ingredient));
                insertRelationStatement.executeUpdate();
                insertedRelationRows++;
            }

            insertRelationStatement.close();


            // insert into tbl_ingredient table
            PreparedStatement insertIngredientStatement = connection.prepareStatement(insertIngredientTableQuery);
            for (Ingredient ingredient : recipe.getIngredients().keySet()) {
                // check if the ingredient already in the table
                String checkDuplicateQuery = "SELECT * FROM tbl_ingredient WHERE name = ? AND unit=?";
                PreparedStatement checkDuplicateStatement = connection.prepareStatement(checkDuplicateQuery);
                checkDuplicateStatement.setString(1, ingredient.getName());
                checkDuplicateStatement.setString(2, ingredient.getUnit());
                ResultSet resultSet = checkDuplicateStatement.executeQuery();

                if (!resultSet.next()) {
                    // If not, insert it into the table
                    insertIngredientStatement.setString(1, ingredient.getName());
                    insertIngredientStatement.setString(2, ingredient.getUnit());
                    insertIngredientStatement.executeUpdate();
                }
            }
            insertIngredientStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        success = insertedRecipeRows == 1 && insertedRelationRows == recipe.getIngredients().size();
        return success;
    }


    /**
     * Search recipe list based on field and criteria
     *
     * @param dataBaseFieldEnum Based database field
     * @param searchCriteria    The search criteria
     * @return Qualifying recipe list
     */
    public List<Recipe> selectRecipe(DataBaseFieldEnum dataBaseFieldEnum, String searchCriteria) {
        List<Recipe> recipeList = new ArrayList<>();
        String selectRecipeTableQuery = "";

        //Based on different search condition use different sql search query
        switch (dataBaseFieldEnum) {
            case ALL:
                selectRecipeTableQuery = "SELECT * FROM tbl_recipe";
                break;
            case ID:
                selectRecipeTableQuery = "SELECT * FROM tbl_recipe WHERE id = ?";
                break;
            case NAME:
                selectRecipeTableQuery = "SELECT * FROM tbl_recipe WHERE name LIKE ?";
                break;
            case INGREDIENT_NAME:
                selectRecipeTableQuery = "SELECT DISTINCT r.* FROM tbl_recipe r " +
                        "INNER JOIN tbl_recipe_ingredient_relation ri ON r.id = ri.recipe_id " +
//                        "INNER JOIN tbl_ingredient i ON ri.ingredient_name = i.name AND ri.ingredient_unit = i.unit" +
                        "WHERE ri.ingredient_name LIKE ?";
                break;
            case STEPS:
                selectRecipeTableQuery = "SELECT * FROM tbl_recipe WHERE steps LIKE ?";
                break;
            default:
                break;
        }


        try {
            PreparedStatement selectRecipeStatement = connection.prepareStatement(selectRecipeTableQuery);
            if (dataBaseFieldEnum != DataBaseFieldEnum.ALL)
                selectRecipeStatement.setString(1, "%" + searchCriteria + "%");
            ResultSet recipeResultSet = selectRecipeStatement.executeQuery();

            while (recipeResultSet.next()) {
                Recipe recipe = new Recipe(recipeResultSet.getString("id"),
                        recipeResultSet.getString("name"),
                        recipeResultSet.getInt("serve_amount"),
                        getIngredientsForRecipe(recipeResultSet.getString("id")),
                        recipeResultSet.getString("steps"),
                        recipeResultSet.getString("video_URL"),
                        recipeResultSet.getString("img_URL"));
                recipeList.add(recipe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recipeList;
    }


    /**
     * Search for the related ingredient map based on recipe ID
     *
     * @param recipeId Recipe ID
     * @return Recipe related ingredient map, with ingredient object and quantity
     */
    private Map<Ingredient, Float> getIngredientsForRecipe(String recipeId) {
        Map<Ingredient, Float> ingredients = new HashMap<>();

        //For searching the recipe related ingredients
        String selectRelationTableQuery = "SELECT * FROM tbl_recipe_ingredient_relation WHERE recipe_id = ?";
//        String selectRelationTableQuery = "SELECT i.*, ri.ingredient_quantity FROM tbl_ingredient i " +
//                "INNER JOIN tbl_recipe_ingredient_relation ri ON i.name = ri.ingredient_name " +
//                "WHERE ri.recipe_id = ?"

        try {
            PreparedStatement statement = connection.prepareStatement(selectRelationTableQuery);
            statement.setString(1, recipeId);
            ResultSet resultSet = statement.executeQuery();

            // 处理查询结果
            while (resultSet.next()) {
                // Create Ingredient Object
                Ingredient ingredient = new Ingredient(resultSet.getString("ingredient_name"), resultSet.getString("ingredient_unit"));
                // Get Ingredient Quantity
                float quantity = resultSet.getFloat("ingredient_quantity");
                // Put into Map
                ingredients.put(ingredient, quantity);
            }

            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredients;
    }


    /**
     * Update the recipe related fields in database
     *
     * @param recipe The updated recipe object
     * @return Show whether the update success
     */
    public boolean updateRecipe(Recipe recipe) {
        boolean success = false;
        boolean recipeIsUpdated = false;
        int insertedRelationRows = 0;
        String updateInRecipeTableQuery = "UPDATE tbl_recipe SET name = ?, steps = ?, people_number = ?, video_URL = ?,img_URL=? WHERE id = ?";
        String deleteInRelationTableQuery = "DELETE FROM tbl_recipe_ingredient_relation WHERE recipe_id = ?";
        String insertRelationTableQuery = "INSERT INTO tbl_recipe_ingredient_relation (recipe_id,ingredient_name,ingredient_unit,ingredient_quantity) VALUES (?,?,?,?)";
        String insertIngredientTableQuery = "INSERT INTO tbl_ingredient (name,unit) VALUES (?,?)";

        try {
            //update recipe table
            PreparedStatement recipeStatement = connection.prepareStatement(updateInRecipeTableQuery);
            recipeStatement.setString(1, recipe.getName());
            recipeStatement.setString(2, recipe.getSteps());
            recipeStatement.setInt(3, recipe.getPeopleNumber());
            recipeStatement.setString(4, recipe.getVideoURL());
            recipeStatement.setString(5, recipe.getImgURL());
            recipeStatement.setString(6, recipe.getId());
            recipeStatement.executeUpdate();
            recipeIsUpdated = true;
            recipeStatement.close();

            //delete all related relations in relation table
            PreparedStatement deleteRelationStatement = connection.prepareStatement(deleteInRelationTableQuery);
            deleteRelationStatement.setString(1, recipe.getId());
            deleteRelationStatement.executeUpdate();
            deleteRelationStatement.close();

            // insert new relations into tbl_recipe_ingredient_relation table
            PreparedStatement insertRelationStatement = connection.prepareStatement(insertRelationTableQuery);
            for (Ingredient ingredient : recipe.getIngredients().keySet()) {
                insertRelationStatement.setString(1, recipe.getId());
                insertRelationStatement.setString(2, ingredient.getName());
                insertRelationStatement.setString(3, ingredient.getUnit());
                insertRelationStatement.setFloat(4, recipe.getIngredients().get(ingredient));
                insertRelationStatement.executeUpdate();
                insertedRelationRows++;
            }
            insertRelationStatement.close();

            //insert new ingredients into ingredient table
            PreparedStatement insertIngredientStatement = connection.prepareStatement(insertIngredientTableQuery);
            for (Ingredient ingredient : recipe.getIngredients().keySet()) {
                // check if the ingredient already in the table
                String checkDuplicateQuery = "SELECT * FROM tbl_ingredient WHERE name = ? AND unit=?";
                PreparedStatement checkDuplicateStatement = connection.prepareStatement(checkDuplicateQuery);
                checkDuplicateStatement.setString(1, ingredient.getName());
                checkDuplicateStatement.setString(2, ingredient.getUnit());
                ResultSet resultSet = checkDuplicateStatement.executeQuery();

                if (!resultSet.next()) {
                    // If not, insert it into the table
                    insertIngredientStatement.setString(1, ingredient.getName());
                    insertIngredientStatement.setString(2, ingredient.getUnit());
                    insertIngredientStatement.executeUpdate();
                }
            }
            insertIngredientStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        success = recipeIsUpdated && insertedRelationRows == recipe.getIngredients().size();
        return success;
    }


    /**
     * Delete recipe record and related relations record in recipe table and relation table based on recipe id
     *
     * @param recipeId Deleted recipe's id
     * @return Boolean to show whether delete is success
     */
    public boolean deleteRecipe(String recipeId) {
        boolean success = false;
        int relationNums = 0;
        int rowsAffectedInRecipe = 0;
        int rowsAffectedInRelation = 0;

        String deleteRecipeTableQuery = "DELETE FROM tbl_recipe WHERE id = ?";
        String countRelationQuery = "SELECT COUNT(*) FROM tbl_recipe_ingredient_relation WHERE recipe_id = ?";
        String deleteInRelationTableQuery = "DELETE FROM tbl_recipe_ingredient_relation WHERE recipe_id = ?";
        try {
            //delete recipe table
            PreparedStatement deleteRecipeStatement = connection.prepareStatement(deleteRecipeTableQuery);
            deleteRecipeStatement.setString(1, recipeId);
            rowsAffectedInRecipe = deleteRecipeStatement.executeUpdate();
            deleteRecipeStatement.close();

            //get the number of recipe relations
            PreparedStatement countRelationStatement = connection.prepareStatement(countRelationQuery);
            countRelationStatement.setString(1, recipeId);
            ResultSet resultSet = countRelationStatement.executeQuery();
            if (resultSet.next()) {
                relationNums = resultSet.getInt(1);
            }
            countRelationStatement.close();

            //delete recipe relations
            PreparedStatement deleteRelationStatement = connection.prepareStatement(deleteInRelationTableQuery);
            deleteRelationStatement.setString(1, recipeId);
            rowsAffectedInRelation = deleteRelationStatement.executeUpdate();
            deleteRelationStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        success = rowsAffectedInRecipe == 1 && rowsAffectedInRelation == relationNums;
        return success;
    }

}
