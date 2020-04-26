package com.mydogspies.xflytools.gui;

import com.mydogspies.xflytools.net.SendData;
import com.mydogspies.xflytools.net.SocketConnect;
import com.mydogspies.xflytools.net.SubscribeDatarefs;
import com.mydogspies.xflytools.net.UnsubscribeDatarefs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This is the controller for the main window
 *
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class MainWindowController {

    private static final Logger log = LoggerFactory.getLogger(MainWindowController.class);
    // private static Socket socket = null;

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

        // initial states of non-command elements
        refsSubbed = new AtomicBoolean(false);
        setNotConnected();

        log.debug("initialize(): Main window has been initialized.");

    }

    /* UI ELEMENT METHODS */

    @FXML
    private void clickButton(ActionEvent event) {

        log.trace("clickButton(): ActionEvent called: " + event);

        ToggleButton b = (ToggleButton) event.getSource();
        String button_id = b.getId();

        if (SocketConnect.socket != null) {

            SendData snd = new SendData();

            switch (button_id) {

                case "taxi":

                    if (b.selectedProperty().getValue()) {
                        snd.send("set sim/cockpit2/switches/taxi_light_on 1");
                        b.setStyle("-fx-base: #F0C755; -fx-text-fill: #1C1C1C;");
                    } else {
                        snd.send("set sim/cockpit2/switches/taxi_light_on 0");
                        b.setStyle("-fx-base: #424242; -fx-text-fill: #B1B4B5;");
                    }
                    break;

                case "nav":
                    if (b.selectedProperty().getValue()) {
                        snd.send("set sim/cockpit2/switches/navigation_lights_on 1");
                        b.setStyle("-fx-base: #F0C755; -fx-text-fill: #1C1C1C;");
                    } else {
                        snd.send("set sim/cockpit2/switches/navigation_lights_on 0");
                        b.setStyle("-fx-base: #424242; -fx-text-fill: #B1B4B5;");
                    }
                    break;

                case "beacon":
                    if (b.selectedProperty().getValue()) {
                        snd.send("set sim/cockpit2/switches/beacon_on 1");
                        b.setStyle("-fx-base: #F0C755; -fx-text-fill: #1C1C1C;");
                    } else {
                        snd.send("set sim/cockpit2/switches/beacon_on 0");
                        b.setStyle("-fx-base: #424242; -fx-text-fill: #B1B4B5;");
                    }
                    break;

                case "strobe":
                    if (b.selectedProperty().getValue()) {
                        snd.send("set sim/cockpit2/switches/strobe_lights_on 1");
                        b.setStyle("-fx-base: #F0C755; -fx-text-fill: #1C1C1C;");
                    } else {
                        snd.send("set sim/cockpit2/switches/strobe_lights_on 0");
                        b.setStyle("-fx-base: #424242; -fx-text-fill: #B1B4B5;");
                    }
                    break;

                case "landing":
                    if (b.selectedProperty().getValue()) {
                        snd.send("set sim/cockpit2/switches/landing_lights_on 1");
                        b.setStyle("-fx-base: #F0C755; -fx-text-fill: #1C1C1C;");
                    } else {
                        snd.send("set sim/cockpit2/switches/landing_lights_on 0");
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
     *
     * @param event event fired by connect/disconnect button
     */
    @FXML
    private void toggleConnect(ActionEvent event) {

        log.trace("toggleConnect(): ActionEvent called: " + event);

        setConnecting(); // TODO why does this not work here?
        ToggleButton b = (ToggleButton) event.getSource();
        boolean buttonState = b.selectedProperty().getValue();

        /* (1) First lets get a socket if none exists */

        if (SocketConnect.socket == null) {

            log.trace("toggleConnect(): Is attempting to connect to Xplane via TCP...");

            // instantiate a socket and attempt to connect
            SocketConnect con = new SocketConnect();
            con.connect();

            if (SocketConnect.socket.isConnected()) {

                // subscribe to relevant datarefs
                SubscribeDatarefs.subRefs();
                refsSubbed.set(true);

                // set toggle button state to connected
                setConnected();
                log.info("toggleConnect(): Connection to Xplane established on: " + SocketConnect.socket.getLocalAddress());

            } else {

                // set toggle button state to error
                setErrorConnection();
                SocketConnect.socket = null; // reset the socket since it was already instantiated when trying to connect
                log.error("toggleConnect(): Connection to Xplane failed!");
            }
        } else if (SocketConnect.socket.isConnected()) {

            // clean disconnect
            UnsubscribeDatarefs.unsubRefs();
            refsSubbed.set(false);
            SocketConnect.socket = null;
            setNotConnected();
            log.info("toggleConnect(): Xplane was disconnected and socket reset.");

        }
    }

    /* CONNECTION LABEL STATES */

    private void setNotConnected() {

        toggleConnect.setText("Connect");
        connectLabel.setText("Not connected");
        connectLabel.setStyle("-fx-text-fill: #f44336");
    }

    private void setConnecting() {

        connectLabel.setText("connecting...");
        connectLabel.setStyle("-fx-text-fill: #FFD600");
    }

    private void setConnected() {

        toggleConnect.setText("Disconnect");
        connectLabel.setText("Connected to Xplane");
        connectLabel.setStyle("-fx-text-fill: #45BF55");
    }

    private void setErrorConnection() {

        toggleConnect.setSelected(false);
        connectLabel.setText("Error! Could not connect!");
        connectLabel.setStyle("-fx-text-fill: #f44336");
    }
}