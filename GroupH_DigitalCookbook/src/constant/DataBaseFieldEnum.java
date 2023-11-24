package constant;


/**
 * Enum for operating the database
 */
public enum DataBaseFieldEnum {

    /**
     * Field of field used in search
     */
    ID("id"),
    NAME("name"),
    INGREDIENT_NAME("ingredient_name"),
    STEPS("steps"),

    ALL("*");


    /**
     * Field of name
     */
    String fieldName;

    /**
     * 1. constructor
     *
     * @param fieldName
     */
    private DataBaseFieldEnum(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return this.fieldName;
    }
}