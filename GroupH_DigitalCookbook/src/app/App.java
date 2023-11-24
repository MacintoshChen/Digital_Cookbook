package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import util.SceneManager;

/**
 * The application class of our app
 */
public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        SceneManager.getInstance().getRootStage().setOnCloseRequest(windowEvent -> {
            SceneManager.getInstance().close();
        });
        Platform.setImplicitExit(false);
    }
}


