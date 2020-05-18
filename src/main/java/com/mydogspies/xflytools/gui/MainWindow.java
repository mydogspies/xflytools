package com.mydogspies.xflytools.gui;

import com.mydogspies.xflytools.gui.MainWindowControllerSingleton;
import com.mydogspies.xflytools.system.ExitApp;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main javafx window that opens on start
 *
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class MainWindow extends Application {

    private final Logger log = LoggerFactory.getLogger(MainWindow.class);
    public static MainWindowController controller;

    public void start(Stage primaryStage) throws Exception {

        // get the current Javafx css theme
        String appTitle = "Xflytools v.0.4.0";

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
            Parent root = loader.load();

            // instantiate the singleton that will contain the reference to our main controller
            MainWindowControllerSingleton singleton = MainWindowControllerSingleton.getInstance();
            singleton.setController(loader.getController());

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());

            primaryStage.setTitle(appTitle);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

            log.info("start(): All parts of the main scene successfully instantiated!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    // clean shutdown
    @Override
    public void stop() {

        // TODO sort this method out and queue up everything
        ExitApp.exitAll();

        // exit to OS
        log.info("exitAll(): Exiting with a clean shutdown! Bye!");
        Platform.exit();
        System.exit(0);
    }

}
