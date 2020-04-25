package com.mydogspies.xflytools.gui;

import com.mydogspies.xflytools.data.DrefDataIO;
import com.mydogspies.xflytools.net.UDPSend;
import com.mydogspies.xflytools.net.UDPStringBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

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

        // TODO initialize the state of all buttons and commands based on current scene in xplane

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
                DrefDataIO io = new DrefDataIO();
                ArrayList<String> dataref;
                if (buttonState) {
                    dataref = io.getDataRefsByCommand("taxi_light_on", "LamCessna172");
                    UDPSend.sendUDPPacket(UDPStringBuilder.makeUDPString(dataref));
                } else {
                    dataref = io.getDataRefsByCommand("taxi_light_off", "LamCessna172");
                    UDPSend.sendUDPPacket(UDPStringBuilder.makeUDPString(dataref));
                }

        }

    }
}
