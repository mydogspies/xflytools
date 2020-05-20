package com.mydogspies.xflytools.controller.module.lamcessna172;

import com.mydogspies.xflytools.data.DrefDataIO;
import com.mydogspies.xflytools.controller.ControllerCo;
import com.mydogspies.xflytools.controller.MainWindowController;
import com.mydogspies.xflytools.controller.MainWindowControllerSingleton;
import com.mydogspies.xflytools.controller.elements.LightToggleButton;
import com.mydogspies.xflytools.io.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * This is the controller for the lighting buttons part of the GUI.
 * @author Peter Mankowski
 * @since 0.4.0
 */
public class LightButtons implements ControllerCo, DataObserverIO {

    private static final Logger log = LoggerFactory.getLogger(LightButtons.class);
    private final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
    private final DataHandler dataHandler = DataHandlerSingleton.getInstance().getHandler();

    @FXML
    private GridPane buttonGrid;

    private LightToggleButton navLight;
    private LightToggleButton taxiLight;
    private LightToggleButton beaconLight;
    private LightToggleButton strobeLight;
    private LightToggleButton landingLight;


    @Override
    @FXML
    public void initialize() {

        // dataHandler.addObserver(this);
        initElements();
    }

    /**
     * This FXML method listens to all lighting buttons and calls corresponding execution code.
     *
     * @param event An event sent by the button when clicked.
     */
    @Override
    @FXML
    public void clickButton(ActionEvent event) {

        log.debug("clickButton(): ActionEvent called: " + event);

        ToggleButton b = (ToggleButton) event.getSource();
        String button_id = b.getId();

        if (SocketConnect.socket != null) {

            switch (button_id) {

                /* LIGHTING BUTTONS */

                case "taxi":
                    main_controller.sendToXplane("cmd", "taxi_lights_flip", "");
                    log.trace("clickButton(): Taxi lights toggled");
                    break;

                case "nav":
                    main_controller.sendToXplane("cmd", "nav_lights_flip", "");
                    log.trace("clickButton(): Nav lights toggled");
                    break;

                case "beacon":
                    main_controller.sendToXplane("cmd", "beacon_lights_flip", "");
                    log.trace("clickButton(): Beacon lights toggled");
                    break;

                case "strobe":
                    main_controller.sendToXplane("cmd", "strobe_lights_flip", "");
                    log.trace("clickButton(): Strobe lights toggled");
                    break;

                case "landing":
                    main_controller.sendToXplane("cmd", "landing_lights_flip", "");
                    log.trace("clickButton(): Landings lights toggled");
                    break;

            }
        } else {
            b.setSelected(false);
        }
    }

    @Override
    public void addToField(ActionEvent event) {

    }

    /**
     * This method is what we get from the observer interface in order to receive data from Xplane.
     * @param packet the data object that contains the dataref, its values and the type; i.e. the module it belongs to.
     */
    @Override
    public void updateFromXplane(DataObserverPacket packet) {

        DrefDataIO io = new DrefDataIO();
        String command = io.getCmndByDataref(packet.getDref());
        ArrayList<String> value = packet.getValues();

        switch (command) {

            case "taxi_light":
                if (taxiLight.selectedProperty().getValue().equals(true) && value.get(0).equals("0")) {
                    taxiLight.setSelected(false);
                    log.trace("updateFromXplane(): [" + command + "] -> " + value + " | taxi light turned OFF in app.");
                } else if (taxiLight.selectedProperty().getValue().equals(false) && value.get(0).equals("1")) {
                    taxiLight.setSelected(true);
                    log.trace("updateFromXplane(): [" + command + "] -> " + value + " | taxi light turned ON in app.");
                }
                break;

            case "nav_light":
                if (navLight.selectedProperty().getValue().equals(true) && value.get(0).equals("0")) {
                    navLight.setSelected(false);
                    log.trace("updateFromXplane(): [" + command + "] -> " + value + " | navigation lights turned OFF in app.");
                } else if (navLight.selectedProperty().getValue().equals(false) && value.get(0).equals("1")) {
                    navLight.setSelected(true);
                    log.trace("updateFromXplane(): [" + command + "] -> " + value + " | navigation lights turned ON in app.");
                }
                break;

            case "beacon_light":
                if (beaconLight.selectedProperty().getValue().equals(true) && value.get(0).equals("0")) {
                    beaconLight.setSelected(false);
                    log.trace("updateFromXplane(): [" + command + "] -> " + value + " | beacon light turned OFF in app.");
                } else if (beaconLight.selectedProperty().getValue().equals(false) && value.get(0).equals("1")) {
                    beaconLight.setSelected(true);
                    log.trace("updateFromXplane(): [" + command + "] -> " + value + " | beacon light turned ON in app.");
                }
                break;

            case "strobe_light":
                if (strobeLight.selectedProperty().getValue().equals(true) && value.get(0).equals("0")) {
                    strobeLight.setSelected(false);
                    log.trace("updateFromXplane(): [" + command + "] -> " + value + " | strobe lights turned OFF in app.");
                } else if (strobeLight.selectedProperty().getValue().equals(false) && value.get(0).equals("1")) {
                    strobeLight.setSelected(true);
                    log.trace("updateFromXplane(): [" + command + "] -> " + value + " | strobe lights turned ON in app.");
                }
                break;

            case "landing_light":
                if (landingLight.selectedProperty().getValue().equals(true) && value.get(0).equals("0")) {
                    landingLight.setSelected(false);
                    log.trace("updateFromXplane(): [" + command + "] -> " + value + " | landing lights turned OFF in app.");
                } else if (landingLight.selectedProperty().getValue().equals(false) && value.get(0).equals("1")) {
                    landingLight.setSelected(true);
                    log.trace("updateFromXplane(): [" + command + "] -> " + value + " | landing lights turned ON in app.");
                }
                break;
        }

    }

    @Override
    public void initElements() {

        navLight = new LightToggleButton();
        navLight.setId("nav");
        navLight.setText("Nav");
        navLight.getStyleClass().addAll("light-toggle-button");
        navLight.setOnAction(this::clickButton);
        buttonGrid.add(navLight, 1, 0);

        beaconLight = new LightToggleButton();
        beaconLight.setId("beacon");
        beaconLight.setText("Bcn");
        beaconLight.getStyleClass().addAll("light-toggle-button");
        beaconLight.setOnAction(this::clickButton);
        buttonGrid.add(beaconLight, 2, 0);

        taxiLight = new LightToggleButton();
        taxiLight.setId("taxi");
        taxiLight.setText("Taxi");
        taxiLight.getStyleClass().addAll("light-toggle-button");
        taxiLight.setOnAction(this::clickButton);
        buttonGrid.add(taxiLight, 3, 0);

        strobeLight = new LightToggleButton();
        strobeLight.setId("strobe");
        strobeLight.setText("Strb");
        strobeLight.getStyleClass().addAll("light-toggle-button");
        strobeLight.setOnAction(this::clickButton);
        buttonGrid.add(strobeLight, 4, 0);

        landingLight = new LightToggleButton();
        landingLight.setId("landing");
        landingLight.setText("Land");
        landingLight.getStyleClass().addAll("light-toggle-button");
        landingLight.setOnAction(this::clickButton);
        buttonGrid.add(landingLight, 5, 0);
    }

    @Override
    public void disableAll(boolean state) {

    }

    /**
     * Resets all elements to their initial visual state
     */
    @Override
    public void onReset() {

        navLight.setSelected(false);
        beaconLight.setSelected(false);
        taxiLight.setSelected(false);
        strobeLight.setSelected(false);
        landingLight.setSelected(false);
    }


}
