package com.mydogspies.xflytools.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main javafx window that opens on start
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class MainWindow extends Application {

    private final Logger log = LoggerFactory.getLogger(MainWindow.class);

    public void start(Stage primaryStage) throws Exception {

        // get the current Javafx css theme
        String appTitle = "Xflytools v.0.1.0";

        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/mainWindow.fxml "));
            log.trace("start(): Loaded MainWindow.fxml successfully.");

            Scene scene = new Scene(root);
            log.trace("start(): New scene instantiated: " + scene);
            scene.getStylesheets().add(getClass().getResource("css/main.css").toExternalForm());
            log.trace("start(): Seems we loaded the default css file successfully.");

            primaryStage.setTitle(appTitle);
            primaryStage.setScene(scene);
            primaryStage.show();
            log.info("start(): Main window has been opened ({})", appTitle);
        } catch(Exception e) {
            log.error("start(): Main window could not be opened: {}", e.getCause().getLocalizedMessage());
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

}
