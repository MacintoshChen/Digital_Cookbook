package util;

import entities.Ingredient;
import entities.Recipe;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.RecipeModel;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for some functionality
 * All static methods
 */
public class Utils {

    /**
     * From model extracts the underlying Recipes
     *
     * @param model The CreateRecipePageModel needed to be parsed
     * @return The parsed model
     * @throws Exception The wrong data of the model, also contains reason in the exception message
     */
    public static Recipe extractRecipe(RecipeModel model) throws Exception {
        String name;
        int serveAmount;
        Map<Ingredient, Float> ingredients = new HashMap<>();
        String steps;
        String videoUrl = null;
        String imgUrl = null;

        name = model.getName();
        if (name.equals("")) {
            throw new Exception("name not null");
        }

        steps = model.getSteps();

        if (!model.getVideoURL().equals("null")) {
            videoUrl = model.getVideoURL();
        }

        if (!model.getImgURL().equals("null")) {
            imgUrl = model.getImgURL();
        }

        try {
            serveAmount = Integer.parseInt(model.getServeAmount());
        } catch (Exception e) {
            throw new Exception("serve amount not a number");
        }
        if (serveAmount < 0) {
            throw new Exception("serve amount must be a positive number");
        }
        if (serveAmount == 0) {
            throw new Exception("serve amount cannot be zero");
        }


        if (model.getIngredients().size() != 0) {
            Map<String, Integer> s = new HashMap<>();
            for(var v : model.getIngredients()){
                if(!s.containsKey(v.textField1.getText() + v.textField3.getText())){
                    s.put(v.textField1.getText() + v.textField3.getText(), 1);
                }else{
                    v.textField1.setText("");
                    v.textField2.setText("");
                    v.textField3.setText("");
                    throw new Exception("Cannot contain two same Ingredients");
                }
            }
            
            for (var v : model.getIngredients()) {
                if (v.textField1.getText().equals("") || v.textField2.getText().equals("") || v.textField3.getText().equals("")) {
                    throw new Exception("ingredient attributes not null");
                } else {
                    Ingredient ing = new Ingredient(v.textField1.getText(), v.textField3.getText());
                    float amount;
                    try {
                        amount = Float.parseFloat(v.textField2.getText());
                    } catch (Exception e) {
                        v.textField2.setText("");
                        throw new Exception("ingredient amount not a number");
                    }

                    if (amount < 0) {
                        v.textField2.setText("");
                        throw new Exception("ingredient amount not a negative number");
                    } else if (amount == 0) {
                        throw new Exception("ingredient amount cannot be zero");
                    }
                    ingredients.put(ing, amount);
                }
            }
        } else {
            throw new Exception("ingredients not null");
        }

        return new Recipe(name, serveAmount, ingredients, steps, videoUrl, imgUrl);
    }

    /**
     * Show new alert window
     *
     * @param alertType Type of alert
     * @param message   Message on the new window
     */
    public static boolean showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType, message);
        alert.showAndWait();
        return alert.getResult() == ButtonType.OK;
    }

    /**
     * To let user choose file
     *
     * @return The selected file
     */
    public static File getSelectedFile() {
        Stage fileChoose = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Video");
        SceneManager.getInstance().getRootStage().hide();

        return fileChooser.showOpenDialog(fileChoose);
    }

    /**
     * Copy source file to target path
     *
     * @param sourceFile          Source file
     * @param targetDirectoryPath Target path
     */
    public static boolean copyFile(File sourceFile, String targetDirectoryPath) {
        File targetDirectory = new File(targetDirectoryPath);

        if (!sourceFile.exists()) {
            System.out.println("source file not existing");
            return false;
        }

        if (!targetDirectory.exists()) {
            if (targetDirectory.mkdirs()) {
                System.out.println("Create directory");
            } else {
                System.out.println("Cannot create directory");
                return false;
            }
        }

        if (targetDirectoryPath.equals("video")) {
            String fileName = sourceFile.getName();
            String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (!fileExtension.equals("mp4")) {
                showAlert(Alert.AlertType.ERROR, "Please Upload a .mp4 Video");
                System.out.println("Please Upload a .mp4 Video");
                return false;
            }
            long fileSize = sourceFile.length();
            long maxSize = 25 * 1024 * 1024; // 25MB in bytes
            if (fileSize>maxSize){
                showAlert(Alert.AlertType.ERROR, "Please Upload a Video <25MB");
                System.out.println("Please Upload a Video <25MB");
                return false;
            }
        }

        String sourceFileName = sourceFile.getName();

        // path of target file
        String targetFilePath = targetDirectoryPath + File.separator + sourceFileName;

        try {
            Files.copy(Path.of(sourceFile.getAbsolutePath()), Path.of(targetFilePath), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("copy succeeded");
            return true;
        } catch (IOException e) {
            System.out.println("copy failed" + e.getMessage());
            return false;
        }
    }

    /**
     * Get file name from url
     *
     * @param url Url of the file
     * @return File name
     */
    public static String getFileNameFromURL(String url) {
        String fileName = "";
        try {
            File file = new File(url);
            fileName = file.getName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }

    /**
     * If an address points to an image
     * @param url Url of the file
     * @return Whether the file is an image
     */
    public static boolean isImage(String url){
        String fileName;
        try {
            File file = new File(url);
            fileName = file.getName();
            var s = fileName.split("\\.");
            StringBuilder res = new StringBuilder();
            for(int i = 0; i < s[s.length-1].length(); ++i){
                var c = s[s.length-1].charAt(i);
                if(Character.isUpperCase(c)){
                    res.append(Character.toLowerCase(c));
                }else {
                    res.append(c);
                }
            }

            switch (res.toString()){
                case "jpg","jpeg","png","gif","bmp","wbmp","tiff","tif","ico" -> {
                    return true;
                }
                default -> {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }
}
