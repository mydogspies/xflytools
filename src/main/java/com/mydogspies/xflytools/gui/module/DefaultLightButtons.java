package com.mydogspies.xflytools.gui.module;

import com.mydogspies.xflytools.gui.MainWindow;
import com.mydogspies.xflytools.gui.elements.LightToggleButton;
import com.mydogspies.xflytools.io.SocketConnect;
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
public class DefaultLightButtons {

    private static final Logger log = LoggerFactory.getLogger(DefaultLightButtons.class);

    @FXML
    private GridPane buttonGrid;

    private LightToggleButton navLight;
    private LightToggleButton taxiLight;
    private LightToggleButton beaconLight;
    private LightToggleButton strobeLight;
    private LightToggleButton landingLight;

    @FXML
    void initialize() {

        initElements();
    }

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
                    MainWindow.controller.sendToXplane("cmd", "taxi_lights_flip", "");
                    log.trace("clickButton(): Taxi lights toggled");
                    break;

                case "nav":
                    MainWindow.controller.sendToXplane("cmd", "nav_lights_flip", "");
                    log.trace("clickButton(): Nav lights toggled");
                    break;

                case "beacon":
                    MainWindow.controller.sendToXplane("cmd", "beacon_lights_flip", "");
                    log.trace("clickButton(): Beacon lights toggled");
                    break;

                case "strobe":
                    MainWindow.controller.sendToXplane("cmd", "strobe_lights_flip", "");
                    log.trace("clickButton(): Strobe lights toggled");
                    break;

                case "landing":
                    MainWindow.controller.sendToXplane("cmd", "landing_lights_flip", "");
                    log.trace("clickButton(): Landings lights toggled");
                    break;

            }
        } else {
            b.setSelected(false);
        }
    }

    public void updateData(String command, ArrayList<String> value) {

        switch (command) {

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
        }
    }

    private void initElements() {

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

    /**
     * Resets all elements to their initial visual state
     */
    public void onReset() {

        navLight.setSelected(false);
        beaconLight.setSelected(false);
        taxiLight.setSelected(false);
        strobeLight.setSelected(false);
        landingLight.setSelected(false);
    }
}
