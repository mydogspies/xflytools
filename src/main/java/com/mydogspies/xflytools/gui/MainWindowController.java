package com.mydogspies.xflytools.gui;

import com.mydogspies.xflytools.data.DrefDataIO;
import com.mydogspies.xflytools.data.LayoutDataIO;
import com.mydogspies.xflytools.io.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This is the controller for the main window and is essentially the parent window for everything else.
 * The connection logic to Xplane is fired from here.
 *
 * @author Peter Mankowski
 * @since 0.4.0
 */
public class MainWindowController implements DataObserverIO {

    private static final Logger log = LoggerFactory.getLogger(MainWindowController.class);
    private DataHandler dataHandler = DataHandlerSingleton.getInstance().getHandler();

    public static String actProfile;
    public static ControllerCo radios_controller;
    public static ControllerCo apreadouts_controller;
    public static ControllerCo lightbutton_controller;
    public static ControllerCo apbutton_controller;
    public static ControllerCo misc_controller;

    /* FXML vars */

    @FXML
    private TextField ipAddress;
    @FXML
    private ToggleButton toggleConnect;
    @FXML
    private Label connectLabel;
    @FXML
    private GridPane topBaseGrid;
    @FXML
    private GridPane bottomBaseGrid;
    @FXML
    private ComboBox<String> aircraftCombo;
    @FXML
    private GridPane toggleButtonGrid;
    @FXML
    private GridPane bottomUtilGrid;

    /* Internal vars */

    private AtomicBoolean refsSubbed;
    private ToggleButton flashLight;
    private AtomicBoolean hdrStatus;

    /* INIT */
    @FXML
    void initialize() {

        dataHandler.addObserver(this);

        // Xplane IP address
        ipAddress.setText("127.0.0.1"); // default value

        // init some states
        initProfileCombobox();
        initFlashLight();
        hdrStatus = new AtomicBoolean();
        refsSubbed = new AtomicBoolean(false);
        setNoProfile();

        log.debug("initialize(): Main window has been initialized.");
    }

    /**
     * All other misc buttons and events actions that come from MainWindow itself.
     * Note that all events from the modules within are handled directly by each module respectively.
     *
     * @param event an event fired from an element that resides in MainWindow.
     */
    @FXML
    private void miscActions(ActionEvent event) {

        log.trace("miscActions(): ActionEvent called: " + event);

        Node b = (Node) event.getSource();
        String field_id = b.getId();

        if (SocketConnect.socket != null) {

            if (field_id.equals("flashlight")) {

                sendToXplane("cmd", "flashlight_toggle", "");
                log.trace("addToField(): White Flash Light has been toggled.");
            }
        } else {
            flashLight.setSelected(false);
        }
    }

    /**
     * Updates various local elements with fresh data from Xplane.
     *
     * @param command the internal xflytools command that corresponds to a given dataref
     * @param value   the current value of the dataref
     */
    public void updateData(String command, ArrayList<String> value) {

        switch (command) {

            case "hdr_status":
                String val = value.get(0);

                if (val.equals("1")) {
                    hdrStatus.set(true);
                    flashLight.setDisable(false);
                    log.trace("updateData(): [" + command + "] -> HDR status is " + val + " [ON]");
                } else {
                    hdrStatus.set(false);
                    flashLight.setDisable(true);
                    log.trace("updateData(): [" + command + "] -> HDR status is " + val + " [OFF]");
                    log.trace("updateData(): [" + command + "] -> Flashlight is DISABLED in Xplane due to the HDR option not being on.");
                }
                break;

            case "flashlight_status":
                String val2 = value.get(0);

                if (value.get(0).equals("1")) {

                    flashLight.setSelected(true);
                    log.trace("updateData(): [" + command + "] -> Flashlight is ON in Xplane.");
                } else if (value.get(0).equals("0")) {

                    flashLight.setSelected(false);
                    log.trace("updateData(): [" + command + "] -> Flashlight is OFF in Xplane.");
                }
                break;
        }
    }

    @Override
    public void update(DataObserverPacket packet) {

        // TODO implement properly this method
        receiveFromXplane(packet.getDref(), packet.getValues());
    }

    /**
     * Takes from the data handler the dataref and an array with values and passes these data
     * to the corresponding GUI elements.
     *
     * @param dataref a single dataref string
     * @param value   an array of values
     */
    public void receiveFromXplane(String dataref, ArrayList<String> value) {

        Platform.runLater(() -> {

            DrefDataIO io = new DrefDataIO();
            String command = io.getCmndByDataref(dataref);
            String type = io.getCmndTypeByDataref(dataref);

            switch (type) {

                case "lights":
                    lightbutton_controller.updateData(command, value);
                    break;

                case "radios":
                    radios_controller.updateData(command, value);
                    break;

                case "autopilot_readout":
                    apreadouts_controller.updateData(command, value);
                    break;

                case "autopilot_switch":
                    apbutton_controller.updateData(command, value);
                    break;

                case "misc":
                    misc_controller.updateData(command, value);
                    break;

                case "main":
                    updateData(command, value);
                    break;
            }
        });
    }

    /**
     * This is the main method for initiating a connection to Xplane.
     * It passes the request on to the SocketConnect class while if socket initiation,
     * it sets calls a few necessary methods and then passes on to loading the GUI modules.
     * In case of a disconnect it manages the necessary method calls for a clean shut-down/UI.
     *
     * @param event from the Connect toggle button
     */
    @FXML
    private void toggleConnect(ActionEvent event) {

        log.trace("toggleConnect(): ActionEvent called: " + event);

        ToggleButton b = (ToggleButton) event.getSource();

        if (SocketConnect.socket == null) {

            log.trace("toggleConnect(): Is attempting to connect to Xplane via TCP...");

            // instantiate a socket and attempt to connect
            SocketConnect con = new SocketConnect();
            con.connect(ipAddress.getText());

            if (SocketConnect.socket.isConnected()) {

                ipAddress.setDisable(true);
                aircraftCombo.setDisable(true);

                log.info("toggleConnect(): Using profile: " + aircraftCombo.getValue());

                // subscribe to relevant datarefs
                SubscribeDatarefs.subRefs();
                refsSubbed.set(true);

                // set toggle button state to connected
                setConnected();
                log.info("toggleConnect(): Connection to ExtPlane plugin established on: " + SocketConnect.socket.getPort());

            } else {

                // set toggle button state to error
                setErrorConnection();
                SocketConnect.socket = null; // reset the socket since it was already instantiated when trying to connect
                log.error("toggleConnect(): Connection to Xplane failed!");
            }

        } else if (SocketConnect.socket.isConnected()) {

            ipAddress.setDisable(false);
            aircraftCombo.setDisable(false);
            resetAllElements();

            // clean disconnect
            refsSubbed.set(false);
            setNotConnected();
            DisconnectAll.shutdown();
        }
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
        String dataref = io.getDatarefByCmnd(command);

        if (method.equals("set")) {
            snd.send(method + " " + dataref + " " + value);
            log.trace("sendToXplane(): " + method + " " + dataref + " " + value);
        } else if (method.equals("cmd")) {
            snd.send("cmd once " + dataref);
            log.trace("sendToXplane(): cmd once " + dataref);
        }
    }

    /**
     * This method load the 5 separate modules with respective fxml/controller class corresponding to each.
     * It also sets a global access to each controller.
     */
    @FXML
    private void loadModules(ActionEvent event) {

        LayoutDataIO lio = new LayoutDataIO();
        String path = lio.getLayout(aircraftCombo.getValue());

        this.topBaseGrid.getChildren().clear();

        // RADIOS MODULE
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("module/" + path + "/radios.fxml"));
            Pane p = loader.load();
            radios_controller = loader.getController();
            topBaseGrid.add(p, 1, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // A/P READOUTS MODULE
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("module/" + path + "/APReadouts.fxml"));
            Pane p = loader.load();
            apreadouts_controller = loader.getController();
            bottomBaseGrid.add(p, 1, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // LIGHT BUTTONS MODULE
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("module/" + path + "/lightButtons.fxml"));
            Pane p = loader.load();
            lightbutton_controller = loader.getController();
            toggleButtonGrid.add(p, 0, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // A/P BUTTONS MODULE
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("module/" + path + "/APButtons.fxml"));
            Pane p = loader.load();
            apbutton_controller = loader.getController();
            toggleButtonGrid.add(p, 0, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // MISC MODULE
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("module/" + path + "/misc.fxml"));
            Pane p = loader.load();
            misc_controller = loader.getController();
            topBaseGrid.add(p, 0, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        setNotConnected();
    }

    /**
     * Initiates the drop down menu with aircraft profiles
     */
    private void initProfileCombobox() {

        LayoutDataIO lio = new LayoutDataIO();
        List<String> nameList = lio.getAllProfileNames();

        for (String name : nameList) {
            aircraftCombo.getItems().add(name);
        }

        aircraftCombo.getSelectionModel().select("");
        actProfile = "";
        aircraftCombo.setOnAction(this::loadModules);
    }

    /**
     * Initiates the flash light function.
     * Note: This is only available if the option HDR is set in the graphics settings of Xplane.
     */
    private void initFlashLight() {

        flashLight = new ToggleButton();
        flashLight.setId("flashlight");
        flashLight.setText("Flash");
        flashLight.getStyleClass().add("flash-light");
        flashLight.setOnAction(this::miscActions);
        bottomUtilGrid.add(flashLight, 1, 0);
    }

    /* VARIOUS ELEMENT STATES */

    private void setNoProfile() {

        toggleConnect.setDisable(true);
        toggleConnect.setText("Connect");
        connectLabel.setText("Choose aircraft profile");
        connectLabel.setStyle("-fx-text-fill: #EC2F05");
    }

    private void setNotConnected() {

        toggleConnect.setDisable(false);
        toggleConnect.setText("Connect");
        connectLabel.setText("Not connected");
        connectLabel.setStyle("-fx-text-fill: #EC2F05");
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

    private void resetAllElements() {

        apreadouts_controller.onReset();
        apbutton_controller.onReset();
        radios_controller.onReset();
        lightbutton_controller.onReset();
        misc_controller.onReset();
        this.onReset();
    }

    private void onReset() {

        flashLight.setSelected(false);
    }


}