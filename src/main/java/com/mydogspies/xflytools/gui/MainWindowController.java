package com.mydogspies.xflytools.gui;

import com.mydogspies.xflytools.data.DrefDataIO;
import com.mydogspies.xflytools.gui.elements.LightToggleButton;
import com.mydogspies.xflytools.gui.elements.RadioLabel;
import com.mydogspies.xflytools.gui.elements.RadioTextField;
import com.mydogspies.xflytools.io.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
    private GridPane buttonGrid;
    @FXML
    private LightToggleButton navLight;
    @FXML
    private LightToggleButton taxiLight;
    @FXML
    private LightToggleButton beaconLight;
    @FXML
    private LightToggleButton strobeLight;
    @FXML
    private LightToggleButton landingLight;

    @FXML
    private ComboBox<String> aircraftCombo;

    @FXML
    private GridPane radioGrid;
    private RadioLabel com1Text;
    private RadioLabel com2Text;
    private RadioLabel nav1Text;
    private RadioLabel nav2Text;
    private RadioLabel transponder;
    private RadioTextField com1Stby;
    private RadioTextField com2Stby;
    private RadioTextField nav1Stby;
    private RadioTextField nav2Stby;
    private RadioTextField transpStby;

    @FXML
    private ToggleButton toggleConnect;
    @FXML
    private Label connectLabel;

    private AtomicBoolean refsSubbed;

    /* INIT */
    @FXML
    void initialize() {

        initButtons();
        initFields();

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
     *
     * @param event An event sent by the button when clicked.
     */
    @FXML
    private void clickButton(ActionEvent event) {

        log.debug("clickButton(): ActionEvent called: " + event);

        ToggleButton b = (ToggleButton) event.getSource();
        String button_id = b.getId();

        if (SocketConnect.socket != null) {

            switch (button_id) {

                    /* LIGHTING BUTTONS */

                case "taxi":

                    if (b.selectedProperty().getValue()) {
                        sendToXplane("set", "taxi_light", "1");
                        log.trace("clickButton(): Taxi lights set to ON in Xplane.");
                    } else {
                        sendToXplane("set", "taxi_light", "0");
                        log.trace("clickButton(): Taxi lights set to OFF in Xplane.");
                    }
                    break;

                case "nav":
                    if (b.selectedProperty().getValue()) {
                        sendToXplane("set", "nav_light", "1");
                        log.trace("clickButton(): Navigation lights set to ON in Xplane.");
                    } else {
                        sendToXplane("set", "nav_light", "0");
                        log.trace("clickButton(): Navigation lights set to OFF in Xplane.");
                    }
                    break;

                case "beacon":
                    if (b.selectedProperty().getValue()) {
                        sendToXplane("set", "beacon_light", "1");
                        log.trace("clickButton(): Beacon light set to ON in Xplane.");
                    } else {
                        sendToXplane("set", "beacon_light", "0");
                        log.trace("clickButton(): Beacon light set to OFF in Xplane.");
                    }
                    break;

                case "strobe":
                    if (b.selectedProperty().getValue()) {
                        sendToXplane("set", "strobe_light", "1");
                        log.trace("clickButton(): Strobe lights set to ON in Xplane.");
                    } else {
                        sendToXplane("set", "strobe_light", "0");
                        log.trace("clickButton(): Strobe lights set to OFF in Xplane.");
                    }
                    break;

                case "landing":
                    if (b.selectedProperty().getValue()) {
                        sendToXplane("set", "landing_light", "1");
                        log.trace("clickButton(): Landing lights set to ON in Xplane.");
                    } else {
                        sendToXplane("set", "landing_light", "0");
                        log.trace("clickButton(): Landing lights set to OFF in Xplane.");
                    }
                    break;

                    /* RADIOS */

            }
        } else {
            b.setSelected(false);
           // b.setStyle("-fx-base: #424242; -fx-text-fill: #B1B4B5;"); // TODO delete
        }
    }

    @FXML
    private void addToField(ActionEvent actionEvent) {

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
     * Sends a string in the format METHOD COMMAND STRING to Xplane.
     * Eg. "set sim/cockpit2/switches/taxi_light_on 1" in order to turn taxi lights on
     *
     * @param method  ExtPlane methods, eg. "set", "get" etc... See ExtPlane documentation for more
     * @param command the dataref
     * @param value   the value to set for the dataref
     */
    public void sendToXplane(String method, String command, String value) {

        SendData snd = new SendData();
        DrefDataIO io = new DrefDataIO();
        String act = aircraftCombo.getValue();
        String dataref = io.getDatarefByActAndCmnd(command, act).get(0);

        // action = send
        snd.send(method + " " + dataref + " " + value);
        log.trace("sendToXplane(): " + method + " " + dataref + " " + value);
    }

    /**
     * Takes from the data handler the dataref and an array with values
     * @param dataref a single dataref string
     * @param value an array of values
     */

    public void getFromXplane(String dataref, ArrayList<String> value) {

        Platform.runLater(new Runnable() {

            @Override
            public void run() {

                DrefDataIO io = new DrefDataIO();
                String command = io.getCmndByDataref(dataref);

                switch (command) {

                    /* LIGHTING BUTTONS */

                    case "taxi_light":
                        if (taxiLight.selectedProperty().getValue().equals(true) && value.get(0).equals("0")) {
                            taxiLight.setSelected(false);
                            log.trace("getFromXplane(): [" + command + "] -> " + value + " | taxi light turned OFF in app.");
                        } else if (taxiLight.selectedProperty().getValue().equals(false) && value.get(0).equals("1")) {
                            taxiLight.setSelected(true);
                            log.trace("getFromXplane(): [" + command + "] -> " + value + " | taxi light turned ON in app.");
                        }
                        break;

                    case "nav_light":
                        if (navLight.selectedProperty().getValue().equals(true) && value.get(0).equals("0")) {
                            navLight.setSelected(false);
                            log.trace("getFromXplane(): [" + command + "] -> " + value + " | navigation lights turned OFF in app.");
                        } else if (navLight.selectedProperty().getValue().equals(false) && value.get(0).equals("1")) {
                            navLight.setSelected(true);
                            log.trace("getFromXplane(): [" + command + "] -> " + value + " | navigation lights turned ON in app.");
                        }
                        break;

                    case "beacon_light":
                        if (beaconLight.selectedProperty().getValue().equals(true) && value.get(0).equals("0")) {
                            beaconLight.setSelected(false);
                            log.trace("getFromXplane(): [" + command + "] -> " + value + " | beacon light turned OFF in app.");
                        } else if (beaconLight.selectedProperty().getValue().equals(false) && value.get(0).equals("1")) {
                            beaconLight.setSelected(true);
                            log.trace("getFromXplane(): [" + command + "] -> " + value + " | beacon light turned ON in app.");
                        }
                        break;

                    case "strobe_light":
                        if (strobeLight.selectedProperty().getValue().equals(true) && value.get(0).equals("0")) {
                            strobeLight.setSelected(false);
                            log.trace("getFromXplane(): [" + command + "] -> " + value + " | strobe lights turned OFF in app.");
                        } else if (strobeLight.selectedProperty().getValue().equals(false) && value.get(0).equals("1")) {
                            strobeLight.setSelected(true);
                            log.trace("getFromXplane(): [" + command + "] -> " + value + " | strobe lights turned ON in app.");
                        }
                        break;

                    case "landing_light":
                        if (landingLight.selectedProperty().getValue().equals(true) && value.get(0).equals("0")) {
                            landingLight.setSelected(false);
                            log.trace("getFromXplane(): [" + command + "] -> " + value + " | landing lights turned OFF in app.");
                        } else if (landingLight.selectedProperty().getValue().equals(false) && value.get(0).equals("1")) {
                            landingLight.setSelected(true);
                            log.trace("getFromXplane(): [" + command + "] -> " + value + " | landing lights turned ON in app.");
                        }
                        break;

                    /* RADIOS */

                    case "com1_freq":
                        String raw = value.get(0);
                        String formatted = raw.substring(0, 3) + "." + raw.substring(3);
                        if (!com1Text.getText().equals(formatted)) {
                            com1Text.setText(formatted);
                            log.trace("getFromXplane(): [" + command + "] -> com1 active set to " + formatted);
                        }
                        break;

                    case "com2_freq":
                        String raw2 = value.get(0);
                        String formatted2 = raw2.substring(0, 3) + "." + raw2.substring(3);
                        if (!com2Text.getText().equals(formatted2)) {
                            com2Text.setText(formatted2);
                            log.trace("getFromXplane(): [" + command + "] -> com2 active set to " + formatted2);
                        }
                        break;

                    case "nav1_freq":
                        String raw3 = value.get(0);
                        String formatted3 = raw3.substring(0, 3) + "." + raw3.substring(3);
                        if (!nav1Text.getText().equals(formatted3)) {
                            nav1Text.setText(formatted3);
                            log.trace("getFromXplane(): [" + command + "] -> nav1 active set to " + formatted3);
                        }
                        break;

                    case "nav2_freq":
                        String raw4 = value.get(0);
                        String formatted4 = raw4.substring(0, 3) + "." + raw4.substring(3);
                        if (!nav2Text.getText().equals(formatted4)) {
                            nav2Text.setText(formatted4);
                            log.trace("getFromXplane(): [" + command + "] -> nav2 active set to " + value);
                        }
                        break;

                    case "transponder_code":
                        if (!transponder.getText().equals(value.get(0))) {
                            transponder.setText(value.get(0));
                            log.trace("getFromXplane(): [" + command + "] -> transponder set to " + value);
                        }
                        break;

                    case "com1_stdby_freq":
                        String raw5 = value.get(0);
                        String formatted5 = raw5.substring(0, 3) + "." + raw5.substring(3);
                        if (!com1Stby.getText().equals(formatted5)) {
                            com1Stby.setText(formatted5);
                            log.trace("getFromXplane(): [" + command + "] -> com1 stand-by set to " + formatted5);
                        }
                        break;

                    case "com2_stdby_freq":
                        String raw6 = value.get(0);
                        String formatted6 = raw6.substring(0, 3) + "." + raw6.substring(3);
                        if (!com2Stby.getText().equals(formatted6)) {
                            com2Stby.setText(formatted6);
                            log.trace("getFromXplane(): [" + command + "] -> com2 stand-by set to " + formatted6);
                        }
                        break;

                    case "nav1_stdby_freq":
                        String raw7 = value.get(0);
                        String formatted7 = raw7.substring(0, 3) + "." + raw7.substring(3);
                        if (!nav1Stby.getText().equals(formatted7)) {
                            nav1Stby.setText(formatted7);
                            log.trace("getFromXplane(): [" + command + "] -> nav1 stand-by set to " + formatted7);
                        }
                        break;

                    case "nav2_stdby_freq":
                        String raw8 = value.get(0);
                        String formatted8 = raw8.substring(0, 3) + "." + raw8.substring(3);
                        if (!nav2Stby.getText().equals(formatted8)) {
                            nav2Stby.setText(formatted8);
                            log.trace("getFromXplane(): [" + command + "] -> nav2 stand-by set to " + formatted8);
                        }
                        break;
                }
            }
        });


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
            toggleAllToFalse();
            refsSubbed.set(false);
            setNotConnected();
            DisconnectAll.shutdown();

        }
    }


    /* GROUP UTILS */

    /**
     * Resets all toggle buttons to false/unselected
     */
    private void toggleAllToFalse() {

        /* LIGHTS */
        navLight.setSelected(false);
        taxiLight.setSelected(false);
        navLight.setSelected(false);
        beaconLight.setSelected(false);
        strobeLight.setSelected(false);
        landingLight.setSelected(false);
        /* RADIOS */
        com1Text.setText("");
        com2Text.setText("");
        nav1Text.setText("");
        nav2Text.setText("");
        transponder.setText("");
        com1Stby.setText("");
        com2Stby.setText("");
        nav1Stby.setText("");
        nav2Stby.setText("");
        transpStby.setText("");
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


    /* INITIALIZE ALL */

    /**
     * Initialize all the buttons that need to be defined dynamically.
     */
    private void initButtons() {

        /* LIGHTING BUTTONS */

        navLight = new LightToggleButton();
        navLight.setId("nav");
        navLight.setText("Nav");
        navLight.getStyleClass().addAll("light-toggle-button");
        navLight.setOnAction(this::clickButton);
        buttonGrid.add(navLight, 1, 1);

        beaconLight = new LightToggleButton();
        beaconLight.setId("beacon");
        beaconLight.setText("Bcn");
        beaconLight.getStyleClass().addAll("light-toggle-button");
        beaconLight.setOnAction(this::clickButton);
        buttonGrid.add(beaconLight, 2, 1);

        taxiLight = new LightToggleButton();
        taxiLight.setId("taxi");
        taxiLight.setText("Taxi");
        taxiLight.getStyleClass().addAll("light-toggle-button");
        taxiLight.setOnAction(this::clickButton);
        buttonGrid.add(taxiLight, 3, 1);

        strobeLight = new LightToggleButton();
        strobeLight.setId("strobe");
        strobeLight.setText("Strb");
        strobeLight.getStyleClass().addAll("light-toggle-button");
        strobeLight.setOnAction(this::clickButton);
        buttonGrid.add(strobeLight, 4, 1);

        landingLight = new LightToggleButton();
        landingLight.setId("landing");
        landingLight.setText("Land");
        landingLight.getStyleClass().addAll("light-toggle-button");
        landingLight.setOnAction(this::clickButton);
        buttonGrid.add(landingLight, 5, 1);
    }

    /**
     * Initialize all the fields that need to be defined dynamically.
     */
    private void initFields() {

        /* RADIO LABELS */

        com1Text = new RadioLabel();
        com1Text.setId("com1text");
        com1Text.setText("");
        com1Text.getStyleClass().addAll("radio-label");
        radioGrid.add(com1Text, 1, 1);

        com2Text = new RadioLabel();
        com2Text.setId("com2text");
        com2Text.setText("");
        com2Text.getStyleClass().addAll("radio-label");
        radioGrid.add(com2Text, 1, 2);

        nav1Text = new RadioLabel();
        nav1Text.setId("nav1text");
        nav1Text.setText("");
        nav1Text.getStyleClass().addAll("radio-label");
        radioGrid.add(nav1Text, 1, 3);

        nav2Text = new RadioLabel();
        nav2Text.setId("nav2text");
        nav2Text.setText("");
        nav2Text.getStyleClass().addAll("radio-label");
        radioGrid.add(nav2Text, 1, 4);

        transponder = new RadioLabel();
        transponder.setId("transponder");
        transponder.setText("");
        transponder.getStyleClass().addAll("radio-label");
        radioGrid.add(transponder, 1, 5);

        /* RADIO FIELDS */

        com1Stby = new RadioTextField();
        com1Stby.setId("com1stby");
        com1Stby.setText("");
        com1Stby.getStyleClass().addAll("radio-field");
        com1Stby.setOnAction(this::addToField);
        radioGrid.add(com1Stby, 3, 1);

        com2Stby = new RadioTextField();
        com2Stby.setId("com2stby");
        com2Stby.setText("");
        com2Stby.getStyleClass().addAll("radio-field");
        com2Stby.setOnAction(this::addToField);
        radioGrid.add(com2Stby, 3, 2);

        nav1Stby = new RadioTextField();
        nav1Stby.setId("nav1stby");
        nav1Stby.setText("");
        nav1Stby.getStyleClass().addAll("radio-field");
        nav1Stby.setOnAction(this::addToField);
        radioGrid.add(nav1Stby,3, 3);

        nav2Stby = new RadioTextField();
        nav2Stby.setId("nav2stby");
        nav1Stby.setText("");
        nav2Stby.getStyleClass().addAll("radio-field");
        nav2Stby.setOnAction(this::addToField);
        radioGrid.add(nav2Stby,3, 4);

        transpStby = new RadioTextField();
        transpStby.setId("transpStby");
        transpStby.setText("");
        transpStby.getStyleClass().add("radio-field");
        transpStby.setOnAction(this::addToField);
        radioGrid.add(transpStby,3, 5);

    }


    /* GETTERS AND SETTERS */

    // get current aircraft profile
    public String getAircraftCombo() {

        return aircraftCombo.getValue();
    }


}