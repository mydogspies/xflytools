package com.mydogspies.xflytools.controller.module.lamcessna172;

import com.mydogspies.xflytools.controller.RadiosController;
import com.mydogspies.xflytools.controller.inlogic.InCommandMap;
import com.mydogspies.xflytools.controller.inlogic.InCommandMapSingleton;
import com.mydogspies.xflytools.controller.outlogic.OutCommandMap;
import com.mydogspies.xflytools.controller.outlogic.OutCommandMapSingleton;
import com.mydogspies.xflytools.data.DrefDataIO;
import com.mydogspies.xflytools.controller.elements.RadioTextField;
import com.mydogspies.xflytools.controller.elements.SwapButton;
import com.mydogspies.xflytools.io.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

/**
 * This is the controller for the radio readouts part of the GUI.
 *
 * @author Peter Mankowski
 * @since 0.4.0
 */
public class Radios implements RadiosController, DataObserverIO {

    private static final Logger log = LoggerFactory.getLogger(Radios.class);
    private final DataHandler dataHandler = DataHandlerSingleton.getInstance().getHandler();
    private final InCommandMap inCommandMap = InCommandMapSingleton.getInstance().getMap();
    private final OutCommandMap outCommandMap = OutCommandMapSingleton.getInstance().getMap();

    @FXML
    private GridPane radioGrid;

    private RadioTextField transponder;
    private RadioTextField com1Text;
    private RadioTextField com2Text;
    private RadioTextField nav1Text;
    private RadioTextField nav2Text;
    private RadioTextField adf1Text;
    private RadioTextField com1Stby;
    private RadioTextField com2Stby;
    private RadioTextField nav1Stby;
    private RadioTextField nav2Stby;
    private RadioTextField adf1Stby;
    private SwapButton com1swap;
    private SwapButton com2swap;
    private SwapButton nav1swap;
    private SwapButton nav2swap;
    private SwapButton adf1swap;

    /* INIT */

    @Override
    @FXML
    public void initialize() {

        dataHandler.addObserver(this);
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

            ArrayList<String> value = packet.getValues();

            if (command.equals("com1_freq")) {
                inCommandMap.getInCommandMap().get("com1_freq").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("com2_freq")) {
                inCommandMap.getInCommandMap().get("com2_freq").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("nav1_freq")) {
                inCommandMap.getInCommandMap().get("nav1_freq").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("nav2_freq")) {
                inCommandMap.getInCommandMap().get("nav2_freq").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("transponder_code")) {
                inCommandMap.getInCommandMap().get("transponder_code").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("com1_stdby_freq")) {
                inCommandMap.getInCommandMap().get("com1_stdby_freq").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("com2_stdby_freq")) {
                inCommandMap.getInCommandMap().get("com2_stdby_freq").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("nav1_stdby_freq")) {
                inCommandMap.getInCommandMap().get("nav1_stdby_freq").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("nav2_stdby_freq")) {
                inCommandMap.getInCommandMap().get("nav2_stdby_freq").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("adf1_freq")) {
                inCommandMap.getInCommandMap().get("adf1_freq").execute(packet.getDref(), packet.getValues());
            }

            if (command.equals("adf1_stdby_freq")) {
                inCommandMap.getInCommandMap().get("adf1_stdby_freq").execute(packet.getDref(), packet.getValues());
            }

        });

    }

    /**
     * Triggers when enter is pressed within a radio text field
     *
     * @param event the trigger from the specific tect field
     */
    @Override
    @FXML
    public void addToField(ActionEvent event) {

        log.debug("addToField(): ActionEvent called: " + event);

        if (SocketConnect.socket != null) {

            RadioTextField b = (RadioTextField) event.getSource();
            String field_id = b.getId();

            if (field_id.equals("com1text")) {
                outCommandMap.getOutCommandMap().get("com1_freq").execute();
            }

            if (field_id.equals("com1stby")) {
                outCommandMap.getOutCommandMap().get("com1_stdby_freq").execute();
            }

            if (field_id.equals("com2text")) {
                outCommandMap.getOutCommandMap().get("com2_freq").execute();
            }

            if (field_id.equals("com2stby")) {
                outCommandMap.getOutCommandMap().get("com2_stdby_freq").execute();
            }

            if (field_id.equals("nav1text")) {
                outCommandMap.getOutCommandMap().get("nav1_freq").execute();
            }

            if (field_id.equals("nav1stby")) {
                outCommandMap.getOutCommandMap().get("nav1_stdby_freq").execute();
            }

            if (field_id.equals("nav2text")) {
                outCommandMap.getOutCommandMap().get("nav2_freq").execute();
            }

            if (field_id.equals("nav2stby")) {
                outCommandMap.getOutCommandMap().get("nav2_stdby_freq").execute();
            }

            if (field_id.equals("transponderCode")) {
                outCommandMap.getOutCommandMap().get("transponder_code").execute();
            }

            if (field_id.equals("adf1text")) {
                outCommandMap.getOutCommandMap().get("adf1_freq").execute();
            }

            if (field_id.equals("adf1stby")) {
                outCommandMap.getOutCommandMap().get("adf1_stdby_freq").execute();
            }
        }
    }

    /**
     * Matches frequency input to be in 1XX. format plus 1 to 3 decimals OR 1XX without decimals.
     *
     * @param value       inputted frequency via the radio text fields
     * @param maxDecimals the amount of decimals we want to match to.
     * @return true of number of decimals match, otherwise false
     */
    @Override
    public boolean matchesFreqFormat(String value, int maxDecimals) {

        if (maxDecimals > 0) {
            if (value.matches("[1][0-9]{2}\\.[0-9]{1," + maxDecimals + "}") || value.matches("[1][0-9]{2}")) {
                log.trace("matchesFreqFormat(): The value [" + value + "] with " + maxDecimals + " max decimals match returned true.");
                return true;
            }
        } else if (maxDecimals == 0) {
            if (value.matches("[0-9]{3,4}")) {
                log.trace("matchesFreqFormat(): The value [" + value + "] with 0 decimals match returned true.");
                return true;
            }
        }

        log.trace("matchesFreqFormat(): The value [" + value + "] returned false.");
        return false;
    }

    /**
     * Takes the matched value (so either 1 to 3 decimals or none) and sees to it's always carries full 3
     * decimals by adding zeroes.
     *
     * @param value a frequency inputted via the radio fields.
     * @return frequency formatted to always having 3 decimals.
     */
    @Override
    public String formatFreqToSend(String value, int numberOfDecimals) {

        StringBuilder result = new StringBuilder(value.replace(".", ""));

        // In case we have one or more decimals
        if (value.contains(".")) {

            String[] getParts = value.split("\\.");

            if (getParts[1].length() < numberOfDecimals) {
                for (int i = 0; i < numberOfDecimals - getParts[1].length(); i++) {
                    result.append("0");
                }
            }
        }

        // in case we have no decimals
        if (!value.contains(".") && numberOfDecimals > 0) {
            for (int i = 0; i < numberOfDecimals; i++) {
                result.append("0");
            }
        } else if (!value.contains(".") && numberOfDecimals == 0) {
            if (value.length() < 4) {
                System.out.println("0" + value);
                return "0" + value;
            }
        }

        log.trace("formatFreqToSend(): returned result: " + result.toString());
        return result.toString();
    }

    /**
     * Swaps between two com or nav radios using the native swap function within Xplane
     *
     * @param event upon clicked swap button
     */
    @FXML
    @Override
    public void clickButton(ActionEvent event) {

        log.debug("toggleRadio(): ActionEvent called: " + event);

        if (SocketConnect.socket != null) {

            SwapButton b = (SwapButton) event.getSource();
            String button_id = b.getId();

            if (button_id.equals("com1swap")) {
                outCommandMap.getOutCommandMap().get("com1_flip").execute();
            }

            if (button_id.equals("com2swap")) {
                outCommandMap.getOutCommandMap().get("com2_flip").execute();
            }

            if (button_id.equals("nav1swap")) {
                outCommandMap.getOutCommandMap().get("nav1_flip").execute();
            }

            if (button_id.equals("nav2swap")) {
                outCommandMap.getOutCommandMap().get("nav2_flip").execute();
            }

            if (button_id.equals("adf1swap")) {
                outCommandMap.getOutCommandMap().get("adf1_flip").execute();
            }
        }
    }

    @Override
    public void disableAll(boolean state) {
        // not implemented in this class
    }

    @Override
    public void initElements() {

        /* SWAP BUTTONS */

        com1swap = new SwapButton();
        com1swap.setId("com1swap");
        com1swap.setText("<>");
        com1swap.getStyleClass().add("swap-button");
        com1swap.setOnAction(this::clickButton);
        radioGrid.add(com1swap, 3, 1);

        com2swap = new SwapButton();
        com2swap.setId("com2swap");
        com2swap.setText("<>");
        com2swap.getStyleClass().add("swap-button");
        com2swap.setOnAction(this::clickButton);
        radioGrid.add(com2swap, 3, 2);

        nav1swap = new SwapButton();
        nav1swap.setId("nav1swap");
        nav1swap.setText("<>");
        nav1swap.getStyleClass().add("swap-button");
        nav1swap.setOnAction(this::clickButton);
        radioGrid.add(nav1swap, 3, 3);

        nav2swap = new SwapButton();
        nav2swap.setId("nav2swap");
        nav2swap.setText("<>");
        nav2swap.getStyleClass().add("swap-button");
        nav2swap.setOnAction(this::clickButton);
        radioGrid.add(nav2swap, 3, 4);

        adf1swap = new SwapButton();
        adf1swap.setId("adf1swap");
        adf1swap.setText("<>");
        adf1swap.getStyleClass().add("swap-button");
        adf1swap.setOnAction(this::clickButton);
        radioGrid.add(adf1swap, 3, 5);

        /* RADIO FIELDS */

        com1Stby = new RadioTextField();
        com1Stby.setId("com1stby");
        com1Stby.setText("");
        com1Stby.getStyleClass().addAll("radio-field");
        com1Stby.setOnAction(this::addToField);
        radioGrid.add(com1Stby, 4, 1);

        com2Stby = new RadioTextField();
        com2Stby.setId("com2stby");
        com2Stby.setText("");
        com2Stby.getStyleClass().addAll("radio-field");
        com2Stby.setOnAction(this::addToField);
        radioGrid.add(com2Stby, 4, 2);

        nav1Stby = new RadioTextField();
        nav1Stby.setId("nav1stby");
        nav1Stby.setText("");
        nav1Stby.getStyleClass().addAll("radio-field");
        nav1Stby.setOnAction(this::addToField);
        radioGrid.add(nav1Stby, 4, 3);

        nav2Stby = new RadioTextField();
        nav2Stby.setId("nav2stby");
        nav2Stby.setText("");
        nav2Stby.getStyleClass().addAll("radio-field");
        nav2Stby.setOnAction(this::addToField);
        radioGrid.add(nav2Stby, 4, 4);

        adf1Stby = new RadioTextField();
        adf1Stby.setId("adf1stby");
        adf1Stby.setText("");
        adf1Stby.getStyleClass().addAll("radio-field");
        adf1Stby.setOnAction(this::addToField);
        radioGrid.add(adf1Stby, 4, 5);

        /* RADIO LABELS */

        com1Text = new RadioTextField();
        com1Text.setId("com1text");
        com1Text.setText("");
        com1Text.getStyleClass().addAll("radio-label");
        com1Text.setOnAction(this::addToField);
        radioGrid.add(com1Text, 2, 1);

        com2Text = new RadioTextField();
        com2Text.setId("com2text");
        com2Text.setText("");
        com2Text.getStyleClass().addAll("radio-label");
        com2Text.setOnAction(this::addToField);
        radioGrid.add(com2Text, 2, 2);

        nav1Text = new RadioTextField();
        nav1Text.setId("nav1text");
        nav1Text.setText("");
        nav1Text.getStyleClass().addAll("radio-label");
        nav1Text.setOnAction(this::addToField);
        radioGrid.add(nav1Text, 2, 3);

        nav2Text = new RadioTextField();
        nav2Text.setId("nav2text");
        nav2Text.setText("");
        nav2Text.getStyleClass().addAll("radio-label");
        nav2Text.setOnAction(this::addToField);
        radioGrid.add(nav2Text, 2, 4);

        adf1Text = new RadioTextField();
        adf1Text.setId("adf1text");
        adf1Text.setText("");
        adf1Text.getStyleClass().addAll("radio-label");
        adf1Text.setOnAction(this::addToField);
        radioGrid.add(adf1Text, 2, 5);

        transponder = new RadioTextField();
        transponder.setId("transponderCode");
        transponder.setText("");
        transponder.getStyleClass().addAll("radio-label");
        transponder.setOnAction(this::addToField);
        radioGrid.add(transponder, 2, 6);
    }

    /**
     * Resets all elements to their initial visual state
     */
    @Override
    public void onReset() {

        com1Stby.setText("");
        com2Stby.setText("");
        nav1Stby.setText("");
        nav2Stby.setText("");
        com1Text.setText("");
        com2Text.setText("");
        nav1Text.setText("");
        nav2Text.setText("");
        transponder.setText("");
        adf1Stby.setText("");
        adf1Text.setText("");
    }

    /* GETTERS and SETTERS */

    public RadioTextField getTransponder() {
        return transponder;
    }

    public RadioTextField getCom1Text() {
        return com1Text;
    }

    public RadioTextField getCom2Text() {
        return com2Text;
    }

    public RadioTextField getNav1Text() {
        return nav1Text;
    }

    public RadioTextField getNav2Text() {
        return nav2Text;
    }

    public RadioTextField getAdf1Text() {
        return adf1Text;
    }

    public RadioTextField getCom1Stby() {
        return com1Stby;
    }

    public RadioTextField getCom2Stby() {
        return com2Stby;
    }

    public RadioTextField getNav1Stby() {
        return nav1Stby;
    }

    public RadioTextField getNav2Stby() {
        return nav2Stby;
    }

    public RadioTextField getAdf1Stby() {
        return adf1Stby;
    }

    public SwapButton getCom1swap() {
        return com1swap;
    }

    public SwapButton getCom2swap() {
        return com2swap;
    }

    public SwapButton getNav1swap() {
        return nav1swap;
    }

    public SwapButton getNav2swap() {
        return nav2swap;
    }

    public SwapButton getAdf1swap() {
        return adf1swap;
    }
}
