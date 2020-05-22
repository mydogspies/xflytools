package com.mydogspies.xflytools.controller.module.lamcessna172;

import com.mydogspies.xflytools.controller.MiscController;
import com.mydogspies.xflytools.controller.inlogic.InCommandMap;
import com.mydogspies.xflytools.controller.inlogic.InCommandMapSingleton;
import com.mydogspies.xflytools.controller.outlogic.OutCommandMap;
import com.mydogspies.xflytools.controller.outlogic.OutCommandMapSingleton;
import com.mydogspies.xflytools.data.DrefDataIO;
import com.mydogspies.xflytools.controller.MainWindowController;
import com.mydogspies.xflytools.controller.MainWindowControllerSingleton;
import com.mydogspies.xflytools.io.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the controller for the Misc part of the GUI.
 * It's loaded into the parent window as part of the initiation process in the MainWindowController
 * <p>
 * See ControllerCo for explanation on the implemented interfaces.
 *
 * @author Peter Mankowski
 * @see MainWindowController
 * @see com.mydogspies.xflytools.controller.ControllerCo
 * @since 0.4.0
 */
public class Misc implements MiscController, DataObserverIO {

    private static final Logger log = LoggerFactory.getLogger(Misc.class);
    private final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
    private final DataHandler dataHandler = DataHandlerSingleton.getInstance().getHandler();
    private final InCommandMap inCommandMap = InCommandMapSingleton.getInstance().getMap();
    private final OutCommandMap outCommandMap = OutCommandMapSingleton.getInstance().getMap();

    @FXML
    private GridPane baroButtonGrid;
    @FXML
    private GridPane bottomGrid;

    private ToggleButton pressType;
    private Button pressStd;
    private TextField baroField;
    private boolean baroSettingInHg;

    /* INIT */

    @Override
    @FXML
    public void initialize() {

        dataHandler.addObserver(this);
        baroSettingInHg = true;
        initElements();
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

            if (command.equals("baro_pilot_inhg")) {
                inCommandMap.getInCommandMap().get("baro_pilot_inhg").execute(packet.getDref(), packet.getValues());
            }

        });
    }

    /**
     * Toggles the barometer display between millibar and inHg
     * @param event on pressing the "mb/inhg" button
     */
    @FXML
    private void baroToggleUnits(ActionEvent event) {

        log.debug("addToBaroField(): ActionEvent called: " + event);

        if (SocketConnect.socket != null) {

            ToggleButton b = (ToggleButton) event.getSource();
            String field_id = b.getId();

            if (field_id.equals("barotype")) {
                outCommandMap.getOutCommandMap().get("baro_units").execute();
            }
        }
    }

    /**
     * Sets the barometric display to standard sea level pressure; 29.92 inHg (1013 mb)
     * @param event on pressing the "STD" button
     */
    @FXML
    private void baroSetToStandard(ActionEvent event) {

        log.debug("addToBaroField(): ActionEvent called: " + event);

        if (SocketConnect.socket != null) {

            Button b = (Button) event.getSource();
            String field_id = b.getId();

            if (field_id.equals("barostd")) {
                outCommandMap.getOutCommandMap().get("baro_pilot_std").execute();
            }
        }
    }

    /**
     * Changes the value of the barometric display to value entered in the text field
     * @param event on enter in the "barofield" textfield
     */
    @FXML
    private void addToBaroField(ActionEvent event) {

        log.debug("addToBaroField(): ActionEvent called: " + event);

        if (SocketConnect.socket != null) {

            TextField b = (TextField) event.getSource();
            String field_id = b.getId();

            if (field_id.equals("barofield")) {
                outCommandMap.getOutCommandMap().get("baro_pilot_inhg").execute();
            }
        }
    }

    /**
     * Takes the raw string value that comes from Xplane and formats it into a nice readout.
     * @param raw_inhg raw value string, eg 29.92000008
     * @return formatted output into standard inHg form, eg. 29.92
     */
    @Override
    public String formatToInHG(String raw_inhg) {

        return String.format("%.2f", Math.round(Double.parseDouble(raw_inhg) * 100) / 100.00);
    }

    /**
     * Converts inHg to millibar, eg 29.92 to 1013
     * @param inhg current value in inHg
     * @return millibar
     */
    @Override
    public String convertToMillibar(String inhg) {

        double mbar = Double.parseDouble(inhg) / 0.029530;
        return String.valueOf(Math.round(mbar));
    }

    /**
     * Converts millibar to inHG, eg. 1013 to 29.92
     * @param mbar current value in millibar
     * @return inHG
     */
    @Override
    public String convertToInchesHG(String mbar) {

        return String.valueOf(Integer.parseInt(mbar) * 0.029530);
    }

    @Override
    public void clickButton(ActionEvent event) {
        // this method no implemented in this class
    }

    @Override
    public void addToField(ActionEvent event) {
        // this method no implemented in this class
    }

    @Override
    public void initElements() {

        /* BUTTONS */

        pressType = new ToggleButton();
        pressType.setId("barotype");
        pressType.setText("inHg");
        pressType.setOnAction(this::baroToggleUnits);
        pressType.getStyleClass().add("baro-button");
        baroButtonGrid.add(pressType, 0, 1);

        pressStd = new Button();
        pressStd.setId("barostd");
        pressStd.setText("STD");
        pressStd.setOnAction(this::baroSetToStandard);
        pressStd.getStyleClass().add("baro-button");
        baroButtonGrid.add(pressStd, 0, 2);

        /* FIELDS */

        baroField = new TextField();
        baroField.setId("barofield");
        baroField.setText("");
        baroField.setOnAction(this::addToBaroField);
        baroField.getStyleClass().add("baro-field");
        bottomGrid.add(baroField, 1, 0);
    }

    @Override
    public void disableAll(boolean state) {
        // this method not implemented in this class
    }

    /**
     * Resets all elements to their initial visual state
     */
    @Override
    public void onReset() {
        baroField.setText("");
    }

    /* GETTERS and SETTERS */

    @Override
    public ToggleButton getPressType() {
        return pressType;
    }

    @Override
    public Button getPressStd() {
        return pressStd;
    }

    @Override
    public TextField getBaroField() {
        return baroField;
    }

    @Override
    public boolean isBaroSettingInHg() {
        return baroSettingInHg;
    }

    @Override
    public void setBaroSettingInHg(boolean baroSettingInHg) {
        this.baroSettingInHg = baroSettingInHg;
    }
}
