package com.mydogspies.xflytools.gui;

import com.mydogspies.xflytools.data.DrefDataIO;
import com.mydogspies.xflytools.gui.elements.LightToggleButton;
import com.mydogspies.xflytools.gui.elements.RadioTextField;
import com.mydogspies.xflytools.gui.elements.SwapButton;
import com.mydogspies.xflytools.io.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private LightToggleButton navLight;
    private LightToggleButton taxiLight;
    private LightToggleButton beaconLight;
    private LightToggleButton strobeLight;
    private LightToggleButton landingLight;

    @FXML
    private ComboBox<String> aircraftCombo;

    @FXML
    private GridPane radioGrid;
    private RadioTextField com1Text;
    private RadioTextField com2Text;
    private RadioTextField nav1Text;
    private RadioTextField nav2Text;
    private RadioTextField transponder;
    private RadioTextField com1Stby;
    private RadioTextField com2Stby;
    private RadioTextField nav1Stby;
    private RadioTextField nav2Stby;
    private SwapButton com1swap;
    private SwapButton com2swap;
    private SwapButton nav1swap;
    private SwapButton nav2swap;
    private String com1set;
    private String com2set;
    private String nav1set;
    private String nav2set;

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
        }
    }

    @FXML
    private void addToField(ActionEvent event) {

        log.debug("addToField(): ActionEvent called: " + event);

        RadioTextField b = (RadioTextField) event.getSource();
        String field_id = b.getId();

        switch (field_id) {

            case "com1text":
                String val = com1Text.getText();
                if (val.matches("[1][0-9]{2}\\.[0-9]{3}")) {
                    sendToXplane("set", "com1_freq", val.replace(".", ""));
                    log.trace("addToField(): Com1 active set to " + val + " in Xplane.");
                }
                //
                break;

            case "com1stby":
                String val2 = com1Stby.getText();
                if (val2.matches("[1][0-9]{2}\\.[0-9]{3}")) {
                    sendToXplane("set", "com1_stdby_freq", val2.replace(".", ""));
                    log.trace("addToField(): Com1 standby set to " + val2 + " in Xplane.");
                }
                //
                break;

            case "com2text":
                String val3 = com2Text.getText();
                if (val3.matches("[1][0-9]{2}\\.[0-9]{3}")) {
                    sendToXplane("set", "com2_freq", val3.replace(".", ""));
                    log.trace("addToField(): Com2 active set to " + val3 + " in Xplane.");
                }
                //
                break;

            case "com2stby":
                String val4 = com2Stby.getText();
                if (val4.matches("[1][0-9]{2}\\.[0-9]{3}")) {
                    sendToXplane("set", "com2_stdby_freq", val4.replace(".", ""));
                    log.trace("addToField(): Com2 standby set to " + val4 + " in Xplane.");
                }
                //
                break;

            case "nav1text":
                String val5 = nav1Text.getText();
                if (val5.matches("[1][0-9]{2}\\.[0-9]{2}")) {
                    sendToXplane("set", "nav1_freq", val5.replace(".", ""));
                    log.trace("addToField(): Nav1 active set to " + val5 + " in Xplane.");
                }
                //
                break;

            case "nav1stby":
                String val6 = nav1Stby.getText();
                if (val6.matches("[1][0-9]{2}\\.[0-9]{2}")) {
                    sendToXplane("set", "nav1_stdby_freq", val6.replace(".", ""));
                    log.trace("addToField(): Nav1 standby set to " + val6 + " in Xplane.");
                }
                //
                break;

            case "nav2text":
                String val7 = nav2Text.getText();
                if (val7.matches("[1][0-9]{2}\\.[0-9]{2}")) {
                    sendToXplane("set", "nav2_freq", val7.replace(".", ""));
                    log.trace("addToField(): Nav2 active set to " + val7 + " in Xplane.");
                }
                //
                break;

            case "nav2stby":
                String val8 = nav2Stby.getText();
                if (val8.matches("[1][0-9]{2}\\.[0-9]{2}")) {
                    sendToXplane("set", "nav2_stdby_freq", val8.replace(".", ""));
                    log.trace("addToField(): Nav2 standby set to " + val8 + " in Xplane.");
                }
                //
                break;

            case "transponderCode":
                String val9 = transponder.getText();
                if (val9.matches("[0-9]{4}")) {
                    sendToXplane("set", "transponder_code", val9);
                    log.trace("addToField(): Transponder set to " + val9 + " in Xplane.");
                }
                //
                break;
        }
    }

    @FXML
    private void chooseAircraft() {

        // TODO implement this method
        System.out.println("CHOOSE ACT PROFILE");

    }

    /**
     * Swaps between two com or nav radios using the native swap function within Xplane
     * // TODO seems to be buggy. Maybe we just swap here locally?
     * @param event upon clicked swap button
     */
    @FXML
    private void toggleRadio(ActionEvent event) {

        log.debug("toggleRadio(): ActionEvent called: " + event);

        SwapButton b = (SwapButton) event.getSource();
        String button_id = b.getId();

        switch (button_id) {

            case "com1swap":
                if (com1set.equals("0")) {
                    sendToXplane("set", "com1_selected", "1");
                    log.trace("toggleRadio(): Right Com1 is selected.");
                } else {
                    sendToXplane("set", "com1_selected", "0");
                    log.trace("toggleRadio(): Left Com1 is selected.");
                }
                break;

            case "com2swap":
                if (com2set.equals("0")) {
                    sendToXplane("set", "com2_selected", "1");
                    log.trace("toggleRadio(): Right Com1 is selected.");
                } else {
                    sendToXplane("set", "com2_selected", "0");
                    log.trace("toggleRadio(): Left Com2 is selected.");
                }
                break;

            case "nav1swap":
                if (nav1set.equals("0")) {
                    sendToXplane("set", "nav1_selected", "1");
                    log.trace("toggleRadio(): Right Nav1 is selected.");
                } else {
                    sendToXplane("set", "nav1_selected", "0");
                    log.trace("toggleRadio(): Left Nav1 is selected.");
                }
                break;

            case "nav2swap":
                if (nav2set.equals("0")) {
                    sendToXplane("set", "nav2_selected", "1");
                    log.trace("toggleRadio(): Right Nav2 is selected.");
                } else {
                    sendToXplane("set", "nav2_selected", "0");
                    log.trace("toggleRadio(): Left Nav2 is selected.");
                }
                break;


        }

        // TODO implement method
        System.out.println("TOOGLE RADIO FREQs");
    }

    @FXML
    private void clickButtonAP() {

        // TODO implement menthod
        System.out.println("AP BUTTONS CLICKED");

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

    public void receiveFromXplane(String dataref, ArrayList<String> value) {

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

                    case "com1_selected": // checks if left or right com1 is selected
                        com1set = value.get(0);

                    case "com2_selected":
                        com2set = value.get(0);

                    case "nav1_selected":
                        nav1set = value.get(0);

                    case "nav2_selected":
                        nav2set = value.get(0);
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
        com1Stby.setText("");
        com2Stby.setText("");
        nav1Stby.setText("");
        nav2Stby.setText("");
        transponder.setText("");
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

        com1Text = new RadioTextField();
        com1Text.setId("com1text");
        com1Text.setText("");
        com1Text.getStyleClass().addAll("radio-label");
        com1Text.setOnAction(this::addToField);
        radioGrid.add(com1Text, 1, 1);

        com2Text = new RadioTextField();
        com2Text.setId("com2text");
        com2Text.setText("");
        com2Text.getStyleClass().addAll("radio-label");
        com2Text.setOnAction(this::addToField);
        radioGrid.add(com2Text, 1, 2);

        nav1Text = new RadioTextField();
        nav1Text.setId("nav1text");
        nav1Text.setText("");
        nav1Text.getStyleClass().addAll("radio-label");
        nav1Text.setOnAction(this::addToField);
        radioGrid.add(nav1Text, 1, 3);

        nav2Text = new RadioTextField();
        nav2Text.setId("nav2text");
        nav2Text.setText("");
        nav2Text.getStyleClass().addAll("radio-label");
        nav2Text.setOnAction(this::addToField);
        radioGrid.add(nav2Text, 1, 4);

        transponder = new RadioTextField();
        transponder.setId("transponderCode");
        transponder.setText("");
        transponder.getStyleClass().addAll("radio-label");
        transponder.setOnAction(this::addToField);
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

        /* SWAP BUTTONS */

        com1swap = new SwapButton();
        com1swap.setId("com1swap");
        com1swap.setText("<>");
        com1swap.getStyleClass().add("swap-button");
        com1swap.setOnAction(this::toggleRadio);
        radioGrid.add(com1swap,2, 1);

        com2swap = new SwapButton();
        com2swap.setId("com2swap");
        com2swap.setText("<>");
        com2swap.getStyleClass().add("swap-button");
        com2swap.setOnAction(this::toggleRadio);
        radioGrid.add(com2swap, 2, 2);

        nav1swap = new SwapButton();
        nav1swap.setId("nav1swap");
        nav1swap.setText("<>");
        nav1swap.getStyleClass().add("swap-button");
        nav1swap.setOnAction(this::toggleRadio);
        radioGrid.add(nav1swap, 2, 3);

        nav2swap = new SwapButton();
        nav2swap.setId("nav2swap");
        nav2swap.setText("<>");
        nav2swap.getStyleClass().add("swap-button");
        nav2swap.setOnAction(this::toggleRadio);
        radioGrid.add(nav2swap,2, 4);



    }




    /* GETTERS AND SETTERS */

    // get current aircraft profile
    public String getAircraftCombo() {

        return aircraftCombo.getValue();
    }


}