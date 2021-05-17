package com.mydogspies.xflytools.controller.module.lamcessna172;

import com.mydogspies.xflytools.controller.LightButtonController;
import com.mydogspies.xflytools.controller.inlogic.InCommandMap;
import com.mydogspies.xflytools.controller.inlogic.InCommandMapSingleton;
import com.mydogspies.xflytools.controller.outlogic.OutCommandMap;
import com.mydogspies.xflytools.controller.outlogic.OutCommandMapSingleton;
import com.mydogspies.xflytools.data.DrefDataIO;
import com.mydogspies.xflytools.controller.elements.LightToggleButton;
import com.mydogspies.xflytools.io.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the controller for the lighting buttons part of the GUI.
 *
 * @author Peter Mankowski
 * @since 0.4.0
 */
public class LightButtons implements LightButtonController, DataObserverIO {

    private static final Logger log = LoggerFactory.getLogger(LightButtons.class);
    private final DataHandler dataHandler = DataHandlerSingleton.getInstance().getHandler();
    private final InCommandMap inCommandMap = InCommandMapSingleton.getInstance().getMap();
    private final OutCommandMap outCommandMap = OutCommandMapSingleton.getInstance().getMap();

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

        dataHandler.addObserver(this);
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

            if (button_id.equals("taxi")) {
                outCommandMap.getOutCommandMap().get("taxi_lights_flip").execute();
            }

            if (button_id.equals("nav")) {
                outCommandMap.getOutCommandMap().get("nav_lights_flip").execute();
            }

            if (button_id.equals("beacon")) {
                outCommandMap.getOutCommandMap().get("beacon_lights_flip").execute();
            }

            if (button_id.equals("strobe")) {
                outCommandMap.getOutCommandMap().get("strobe_lights_flip").execute();
            }

            if (button_id.equals("landing")) {
                outCommandMap.getOutCommandMap().get("landing_lights_flip").execute();
            }

        } else {
            b.setSelected(false);
        }
    }

    /**
     * This method is what we get from the observer interface in order to receive data from Xplane.
     *
     * @param packet the data object that contains the dataref, its values and the type; i.e. the module it belongs to.
     */
    @Override
    public void updateFromXplane(DataObserverPacket packet) {

        Platform.runLater(() -> {

            DrefDataIO io = new DrefDataIO();
            String command = io.getCmndByDataref(packet.getDref());

            if (command.equals("taxi_light")) {
                inCommandMap.getInCommandMap().get("taxi_light").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("nav_light")) {
                inCommandMap.getInCommandMap().get("nav_light").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("strobe_light")) {
                inCommandMap.getInCommandMap().get("strobe_light").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("landing_light")) {
                inCommandMap.getInCommandMap().get("landing_light").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("beacon_light")) {
                inCommandMap.getInCommandMap().get("beacon_light").execute(packet.getDref(), packet.getValues());
            }

        });

    }

    @Override
    public void addToField(ActionEvent event) {
        // this method is not implemented in this class
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

    /* GETTERS and SETTERS */

    public LightToggleButton getNavLight() {
        return navLight;
    }

    public LightToggleButton getTaxiLight() {
        return taxiLight;
    }

    public LightToggleButton getBeaconLight() {
        return beaconLight;
    }

    public LightToggleButton getStrobeLight() {
        return strobeLight;
    }

    public LightToggleButton getLandingLight() {
        return landingLight;
    }
}
