package util;

import java.util.UUID;

/**
 * The utility class of generating the unique ID using the UUID tool
 */
public class UUIDUtils {
    /**
     * Get an unique UUID String for Recipe ID
     *
     * @return Recipe ID
     */
    public static String getUUID(){

        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
