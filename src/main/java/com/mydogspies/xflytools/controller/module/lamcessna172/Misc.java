package com.mydogspies.xflytools.controller.module.lamcessna172;

import com.mydogspies.xflytools.data.DrefDataIO;
import com.mydogspies.xflytools.controller.ControllerCo;
import com.mydogspies.xflytools.controller.MainWindowController;
import com.mydogspies.xflytools.controller.MainWindowControllerSingleton;
import com.mydogspies.xflytools.io.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

public class Misc implements ControllerCo, DataObserverIO {

    private static final Logger log = LoggerFactory.getLogger(Misc.class);
    private final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();
    private final DataHandler dataHandler = DataHandlerSingleton.getInstance().getHandler();

    @FXML
    private GridPane baroButtonGrid;
    @FXML
    private GridPane bottomGrid;

    private ToggleButton pressType;
    private Button pressStd;
    private TextField baroField;
    private boolean baroSettingInHg;

    @Override
    @FXML
    public void initialize() {

        // dataHandler.addObserver(this);
        baroSettingInHg = true;
        initElements();
    }

    @Override
    public void clickButton(ActionEvent event) {}

    @Override
    public void addToField(ActionEvent event) {}

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

            case "baro_pilot_inhg":

                if (baroSettingInHg) {
                    String val = value.get(0);
                    baroField.setText(formatToInHG(val));
                    log.trace("updateFromXplane(): [" + command + "] -> Barometer set to " + val + " inHg.");
                } else {
                    baroField.setText(convertToMillibar(value.get(0)));
                }
                break;
        }
    }

    private String formatToInHG(String raw_inhg) {

        return String.format("%.2f", Math.round(Double.parseDouble(raw_inhg)*100) / 100.00);
    }

    private String convertToMillibar(String inhg) {

        double mbar = Double.parseDouble(inhg) / 0.029530;
        return String.valueOf(Math.round(mbar));
    }

    private String convertToInchesHG(String mbar) {

        return String.valueOf(Integer.parseInt(mbar) * 0.029530);
    }

    @FXML
    private void baroToggle(ActionEvent event) {

        log.debug("addToBaroField(): ActionEvent called: " + event);

        if (SocketConnect.socket != null) {

            ToggleButton b = (ToggleButton) event.getSource();
            String field_id = b.getId();

            switch (field_id) {

                case ("barotype"):
                    if (baroSettingInHg) {
                        baroSettingInHg = false;
                        pressType.setText("mb");
                        baroField.setText(convertToMillibar(baroField.getText()));
                    } else {
                        baroSettingInHg = true;
                        pressType.setText("inHg");
                        baroField.setText(formatToInHG(convertToInchesHG(baroField.getText())));
                    }
                    break;
            }
        }
    }

    @FXML
    private void baroStandard(ActionEvent event) {

        log.debug("addToBaroField(): ActionEvent called: " + event);

        if (SocketConnect.socket != null){

            Button b = (Button) event.getSource();
            String field_id = b.getId();

            switch (field_id) {

                case ("barostd"):
                    main_controller.sendToXplane("set", "baro_pilot_inhg", "29.92");
                    if (baroSettingInHg) {
                        baroField.setText("29.92");
                        log.trace("addToField(): Baro set to STD (29.92 inHg) in Xplane.");
                    } else {
                        baroField.setText("1013");
                        log.trace("addToField(): Baro set to STD (1013 mb) in Xplane.");
                    }
                    break;
            }
        }
    }

    @FXML
    private void addToBaroField(ActionEvent event) {

        log.debug("addToBaroField(): ActionEvent called: " + event);

        if (SocketConnect.socket != null) {

            TextField b = (TextField) event.getSource();
            String field_id = b.getId();

            System.out.println("field_id = " + field_id);

            switch (field_id) {

                case "barofield":
                    String val = baroField.getText();
                    if (val.matches("[2-3][0-9]\\.[0-9]{2}")) {
                        main_controller.sendToXplane("set", "baro_pilot_inhg", val);
                        log.trace("addToField(): Baro set to " + val + " in Xplane.");
                    } else if (val.matches("[1][0][0-9]{2}")) {
                        val = convertToInchesHG(val);
                        main_controller.sendToXplane("set", "baro_pilot_inhg", val);
                        log.trace("addToField(): Baro set to " + val + " in Xplane.");
                    }
                    //
                    break;
            }
        }
    }

    @Override
    public void initElements() {

        /* BUTTONS */

        pressType = new ToggleButton();
        pressType.setId("barotype");
        pressType.setText("inHg");
        pressType.setOnAction(this::baroToggle);
        pressType.getStyleClass().add("baro-button");
        baroButtonGrid.add(pressType, 0, 1);

        pressStd = new Button();
        pressStd.setId("barostd");
        pressStd.setText("STD");
        pressStd.setOnAction(this::baroStandard);
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

    }

    /**
     * Resets all elements to their initial visual state
     */
    @Override
    public void onReset() {

        baroField.setText("");
    }


}
