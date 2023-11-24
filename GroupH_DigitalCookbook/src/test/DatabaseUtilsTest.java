package test;

import entities.Ingredient;
import entities.Recipe;
import util.DatabaseUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Junit test for DatabaseUtils
 */
class DatabaseUtilsTest {

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @org.junit.jupiter.api.Test
    void selectRecipe() {
    }


    @org.junit.jupiter.api.Test
    void insertRecipe(){
        Ingredient ing1=new Ingredient("salt","g");
        Ingredient ing2=new Ingredient("sugar","g");
        Ingredient ing3=new Ingredient("meat","g");
        Ingredient ing4=new Ingredient("fish","g");
        Ingredient ing5=new Ingredient("green vegetable","g");
        Ingredient ing6=new Ingredient("soy sauce","ml");
        Ingredient ing7=new Ingredient("water","ml");

        Map<Ingredient,Float> ingMap1=new HashMap<>();
        ingMap1.put(ing1,100.0f);
        ingMap1.put(ing2,50.0f);
        ingMap1.put(ing3,250.0f);

        Map<Ingredient,Float> ingMap2=new HashMap<>();
        ingMap1.put(ing1,20.5f);
        ingMap1.put(ing2,50.5f);
        ingMap1.put(ing3,250.0f);
        ingMap1.put(ing4,75.0f);
        ingMap1.put(ing5,50.0f);
        ingMap1.put(ing6,250.0f);


        Recipe recipe1=new Recipe("Red-cooked Pork",2,ingMap1,"put all ingredients into the pot and cook","///","///");
        Recipe recipe2=new Recipe("Sweet-sour Pork",1,ingMap1,"put all ingredients into the pot and cook","///","///");

        DatabaseUtils.getInstance().insertRecipe(recipe1);
        DatabaseUtils.getInstance().insertRecipe(recipe2);
    }
}