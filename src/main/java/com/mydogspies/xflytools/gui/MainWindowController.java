package com.mydogspies.xflytools.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the controller for the main window
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class MainWindowController {

    private static final Logger log = LoggerFactory.getLogger(MainWindowController.class);

    @FXML
    private AnchorPane background;

    @FXML
    private ToggleButton taxiLights;

    /* INIT */
    @FXML
    void initialize() {

    }

    /* METHODS */
    @FXML
    private void clickButton(ActionEvent event) {

        log.trace("clickButton(): ActionEvent called: " + event);

        ToggleButton b = (ToggleButton) event.getSource();
        String button_id = b.getId();

        switch (button_id) {

            case "taxiLights":
                boolean buttonState = b.selectedProperty().getValue();
                System.out.println(buttonState);


        }

    }
}
