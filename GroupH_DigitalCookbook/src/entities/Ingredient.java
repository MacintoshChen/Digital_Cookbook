package entities;

/**
 * Entity class of ingredient
 */
public class Ingredient {

    String name;


    String unit;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Ingredient(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

}
