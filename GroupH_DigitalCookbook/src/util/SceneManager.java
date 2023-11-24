package util;

import view.majorView.HomePageView;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

/**
 * Tools to manage scene switch
 * Also single instance
 */
public class SceneManager {
    private Stage rootStage;

    private Stack<Scene> sceneStack;

    public Stack<Scene> getSceneStack() {
        return sceneStack;
    }

    /**
     * Constructor
     * @param _stage Stage of javafx application
     */
    private SceneManager(Stage _stage) {
        sceneStack = new Stack<>();
        rootStage = _stage;
        registerScene(new Scene(new HomePageView()));
        rootStage.show();
        rootStage.setResizable(false);
    }

    /**
     * Inner class for single instance
     */
    private static class SingletonInstance{
        private static final SceneManager INSTANCE = new SceneManager(new Stage());
    }

    /**
     * Get single instance
     * @return Single instance
     */
    public static SceneManager getInstance(){
        return SingletonInstance.INSTANCE;
    }

    /**
     * To open a new scene
     * @param s The scene need to be open
     */
    public void registerScene(Scene s){
        sceneStack.push(s);
        rootStage.setScene(sceneStack.peek());
    }

    /**
     * Used to close current top scene,
     * If this is the last scene,
     * Whole Application will be closed
     */
    public void closeScene(){
        sceneStack.pop();
        if(!sceneStack.isEmpty()) {
            rootStage.setScene(sceneStack.peek());
        }else {
            Platform.exit();
        }
    }

    /**
     * Used to close the whole Application
     */
    public void close(){
        Platform.exit();
    }

    public Stage getRootStage() {
        return rootStage;
    }


}
