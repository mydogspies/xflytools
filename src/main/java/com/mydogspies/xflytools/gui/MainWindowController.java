package com.mydogspies.xflytools.gui;

import com.mydogspies.xflytools.data.DrefDataIO;
import com.mydogspies.xflytools.io.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This is the controller for the main window
 *
 * @author Peter Mankowski
 * @since 0.1.0
 */
public class MainWindowController {

    private static final Logger log = LoggerFactory.getLogger(MainWindowController.class);
    public static String actProfile;

    @FXML
    private AnchorPane background;

    @FXML
    private ToggleButton taxiLights;

    @FXML
    private ComboBox<String> aircraftCombo;

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

        // aircraft combo box
        aircraftCombo.getItems().addAll(
                "default");
        aircraftCombo.getSelectionModel().select("default");
        actProfile = getAircraftCombo();

        log.debug("initialize(): Main window has been initialized.");

    }

    /* UI ELEMENT METHODS */

    /**
     * This FXML method listens to all Lighting and A/P buttons and calls corresponding execution code.
     * @param event An event sent by the button when clicked.
     */
    @FXML
    private void clickButton(ActionEvent event) {

        log.debug("clickButton(): ActionEvent called: " + event);

        ToggleButton b = (ToggleButton) event.getSource();
        String button_id = b.getId();

        if (SocketConnect.socket != null) {

            SendData snd = new SendData();
            DrefDataIO io = new DrefDataIO();
            String act = aircraftCombo.getValue();

            switch (button_id) {

                case "taxi":

                    if (b.selectedProperty().getValue()) {
                        snd.send("set " + io.getDatarefByActAndCmnd("taxi_light", act).get(0) + " 1");
                        b.setStyle("-fx-base: #F0C755; -fx-text-fill: #1C1C1C;");
                        log.trace("clickButton(): taxi_light set to 1");
                    } else {
                        snd.send("set " + io.getDatarefByActAndCmnd("taxi_light", act).get(0) + " 0");
                        b.setStyle("-fx-base: #424242; -fx-text-fill: #B1B4B5;");
                        log.trace("clickButton(): taxi_light set to 0");
                    }
                    break;

                case "nav":
                    if (b.selectedProperty().getValue()) {
                        snd.send("set " + io.getDatarefByActAndCmnd("nav_light", act).get(0) + " 1");
                        b.setStyle("-fx-base: #F0C755; -fx-text-fill: #1C1C1C;");
                        log.trace("clickButton(): nav_light set to 1");
                    } else {
                        snd.send("set " + io.getDatarefByActAndCmnd("nav_light", act).get(0) + " 0");
                        b.setStyle("-fx-base: #424242; -fx-text-fill: #B1B4B5;");
                        log.trace("clickButton(): nav_light set to 0");
                    }
                    break;

                case "beacon":
                    if (b.selectedProperty().getValue()) {
                        snd.send("set " + io.getDatarefByActAndCmnd("beacon_light", act).get(0) + " 1");
                        b.setStyle("-fx-base: #F0C755; -fx-text-fill: #1C1C1C;");
                        log.trace("clickButton(): beacon_light set to 1");
                    } else {
                        snd.send("set " + io.getDatarefByActAndCmnd("beacon_light", act).get(0) + " 0");
                        b.setStyle("-fx-base: #424242; -fx-text-fill: #B1B4B5;");
                        log.trace("clickButton(): beacon_light set to 0");
                    }
                    break;

                case "strobe":
                    if (b.selectedProperty().getValue()) {
                        snd.send("set " + io.getDatarefByActAndCmnd("strobe_light", act).get(0) + " 1");
                        b.setStyle("-fx-base: #F0C755; -fx-text-fill: #1C1C1C;");
                        log.trace("clickButton(): strobe_light set to 1");
                    } else {
                        snd.send("set " + io.getDatarefByActAndCmnd("strobe_light", act).get(0) + " 0");
                        b.setStyle("-fx-base: #424242; -fx-text-fill: #B1B4B5;");
                        log.trace("clickButton(): strobe_light set to 0");
                    }
                    break;

                case "landing":
                    if (b.selectedProperty().getValue()) {
                        snd.send("set " + io.getDatarefByActAndCmnd("landing_light", act).get(0) + " 1");
                        b.setStyle("-fx-base: #F0C755; -fx-text-fill: #1C1C1C;");
                        log.trace("clickButton(): landing_light set to 1");
                    } else {
                        snd.send("set " + io.getDatarefByActAndCmnd("landing_light", act).get(0) + " 0");
                        b.setStyle("-fx-base: #424242; -fx-text-fill: #B1B4B5;");
                        log.trace("clickButton(): lading_light set to 0");
                    }
                    break;
            }
        } else {
            b.setSelected(false);
            b.setStyle("-fx-base: #424242; -fx-text-fill: #B1B4B5;");
        }
    }

    @FXML
    private void chooseAircraft() {

    }

    @FXML
    private void toggleCom() {

    }

    @FXML
    private void clickButtonAP() {

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
            refsSubbed.set(false);
            setNotConnected();
            DisconnectAll.shutdown();

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

    /* GETTERS AND SETTERS */

    // get current aircraft profile
    public String getAircraftCombo() {

        return aircraftCombo.getValue();
    }
}