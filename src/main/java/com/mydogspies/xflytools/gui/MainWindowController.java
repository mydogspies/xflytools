package com.mydogspies.xflytools.gui;

import com.mydogspies.xflytools.net.SendData;
import com.mydogspies.xflytools.net.SocketConnect;
import com.mydogspies.xflytools.net.SubscribeDatarefs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This is the controller for the main window
 *
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class MainWindowController extends Thread {

    private static final Logger log = LoggerFactory.getLogger(MainWindowController.class);
    private static Socket socket = null;

    @FXML
    private AnchorPane background;

    @FXML
    private ToggleButton taxiLights;

    @FXML
    private Label connectLabel;

    @FXML
    private ToggleButton toggleConnect;

    private AtomicBoolean refsSubbed;

    /* INIT */
    @FXML
    void initialize() {

        refsSubbed = new AtomicBoolean(false);

        // initial states of non-command elements
        toggleConnect.setText("Connect");
        connectLabel.setText("Not connected");
        connectLabel.setStyle("-fx-text-fill: #f44336");

        log.debug("initialize(): Main window has been initialized.");

    }

    /* METHODS */

    @FXML
    private void clickButton(ActionEvent event) {

        log.trace("clickButton(): ActionEvent called: " + event);

        ToggleButton b = (ToggleButton) event.getSource();
        String button_id = b.getId();

        if(socket!=null) {

            SendData snd = new SendData();

            switch (button_id) {

                case "taxi":

                    if(b.selectedProperty().getValue()) {
                        snd.send(socket, "set sim/cockpit2/switches/taxi_light_on 1");
                        b.setStyle("-fx-base: #F0C755; -fx-text-fill: #1C1C1C;");
                    } else {
                        snd.send(socket, "set sim/cockpit2/switches/taxi_light_on 0");
                        b.setStyle("-fx-base: #424242; -fx-text-fill: #B1B4B5;");
                    }
                    break;

                case "nav":
                    if(b.selectedProperty().getValue()) {
                        snd.send(socket, "set sim/cockpit2/switches/navigation_lights_on 1");
                        b.setStyle("-fx-base: #F0C755; -fx-text-fill: #1C1C1C;");
                    } else {
                        snd.send(socket, "set sim/cockpit2/switches/navigation_lights_on 0");
                        b.setStyle("-fx-base: #424242; -fx-text-fill: #B1B4B5;");
                    }
                    break;

                case "beacon":
                    if(b.selectedProperty().getValue()) {
                        snd.send(socket, "set sim/cockpit2/switches/beacon_on 1");
                        b.setStyle("-fx-base: #F0C755; -fx-text-fill: #1C1C1C;");
                    } else {
                        snd.send(socket, "set sim/cockpit2/switches/beacon_on 0");
                        b.setStyle("-fx-base: #424242; -fx-text-fill: #B1B4B5;");
                    }
                    break;

                case "strobe":
                    if(b.selectedProperty().getValue()) {
                        snd.send(socket, "set sim/cockpit2/switches/strobe_lights_on 1");
                        b.setStyle("-fx-base: #F0C755; -fx-text-fill: #1C1C1C;");
                    } else {
                        snd.send(socket, "set sim/cockpit2/switches/strobe_lights_on 0");
                        b.setStyle("-fx-base: #424242; -fx-text-fill: #B1B4B5;");
                    }
                    break;

                case "landing":
                    if(b.selectedProperty().getValue()) {
                        snd.send(socket, "set sim/cockpit2/switches/landing_lights_on 1");
                        b.setStyle("-fx-base: #F0C755; -fx-text-fill: #1C1C1C;");
                    } else {
                        snd.send(socket, "set sim/cockpit2/switches/landing_lights_on 0");
                        b.setStyle("-fx-base: #424242; -fx-text-fill: #B1B4B5;");
                    }
                    break;

            }
        } else {
            b.setSelected(false);
            b.setStyle("-fx-base: #424242; -fx-text-fill: #B1B4B5;");
        }


    }

    /**
     * Toggles the connection over TCP between Xflytools and the ExtPlugin in Xplane.
     * It's in this method we define the static socket used throughout.
     * @param event event fired by connect/disconnect button
     */
    @FXML
    private void toggleConnect(ActionEvent event) {

        log.trace("toggleConnect(): ActionEvent called: " + event);

        ToggleButton b = (ToggleButton) event.getSource();
        boolean buttonState = b.selectedProperty().getValue();

        // while we are waiting to connect
        connectLabel.setText("connecting...");
        connectLabel.setStyle("-fx-text-fill: #1C1C1C");

        if (buttonState) {

            // connect to xplane
            SocketConnect con = new SocketConnect();
            socket = con.connect();

            if (socket != null) {

                // subscribe to relevant datarefs
                if (!refsSubbed.get()) {
                    SubscribeDatarefs.subRefs(socket);
                    refsSubbed.set(true);
                }

                // set toggle button state
                toggleConnect.setText("Disconnect");
                connectLabel.setText("Connected to Xplane");
                connectLabel.setStyle("-fx-text-fill: #45BF55");
                log.info("toggleConnect(): Connection to ExtPlane established on: " + socket.getLocalAddress());

            } else {

                toggleConnect.setSelected(false);
                connectLabel.setText("Error! Could not connect!");
                connectLabel.setStyle("-fx-text-fill: #f44336");
                log.error("toggleConnect(): Connection to Xplane failed!");
            }
        } else {
            socket = null;
            toggleConnect.setText("Connect");
            toggleConnect.setSelected(false);
            connectLabel.setText("Not connected");
            connectLabel.setStyle("-fx-text-fill: #f44336");
        }
    }
}
