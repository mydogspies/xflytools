package com.mydogspies.xflytools.controller.module.lamcessna172;

import com.mydogspies.xflytools.controller.RadiosController;
import com.mydogspies.xflytools.data.DrefDataIO;
import com.mydogspies.xflytools.controller.MainWindowController;
import com.mydogspies.xflytools.controller.MainWindowControllerSingleton;
import com.mydogspies.xflytools.controller.elements.RadioTextField;
import com.mydogspies.xflytools.controller.elements.SwapButton;
import com.mydogspies.xflytools.io.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * This is the controller for the radio readouts part of the GUI.
 * @author Peter Mankowski
 * @since 0.4.0
 */
public class Radios implements RadiosController, DataObserverIO {

    private static final Logger log = LoggerFactory.getLogger(Radios.class);
    private final MainWindowController main_controller = MainWindowControllerSingleton.getInstance().getController();

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

        // dataHandler.addObserver(this);
        initElements();
    }

    @Override
    public void clickButton(ActionEvent event) {}

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

            case "com1_freq":
                String raw = value.get(0);
                String formatted = raw.substring(0, 3) + "." + raw.substring(3);
                if (!com1Text.getText().equals(formatted)) {
                    com1Text.setText(formatted);
                    log.trace("updateFromXplane(): [" + command + "] -> com1 active set to " + formatted);
                }
                break;

            case "com2_freq":
                String raw2 = value.get(0);
                String formatted2 = raw2.substring(0, 3) + "." + raw2.substring(3);
                if (!com2Text.getText().equals(formatted2)) {
                    com2Text.setText(formatted2);
                    log.trace("updateFromXplane(): [" + command + "] -> com2 active set to " + formatted2);
                }
                break;

            case "nav1_freq":
                String raw3 = value.get(0);
                String formatted3 = raw3.substring(0, 3) + "." + raw3.substring(3);
                if (!nav1Text.getText().equals(formatted3)) {
                    nav1Text.setText(formatted3);
                    log.trace("updateFromXplane(): [" + command + "] -> nav1 active set to " + formatted3);
                }
                break;

            case "nav2_freq":
                String raw4 = value.get(0);
                String formatted4 = raw4.substring(0, 3) + "." + raw4.substring(3);
                if (!nav2Text.getText().equals(formatted4)) {
                    nav2Text.setText(formatted4);
                    log.trace("updateFromXplane(): [" + command + "] -> nav2 active set to " + value);
                }
                break;

            case "transponder_code":
                if (!transponder.getText().equals(value.get(0))) {
                    transponder.setText(value.get(0));
                    log.trace("updateFromXplane(): [" + command + "] -> transponder set to " + value);
                }
                break;

            case "com1_stdby_freq":
                String raw5 = value.get(0);
                String formatted5 = raw5.substring(0, 3) + "." + raw5.substring(3);
                if (!com1Stby.getText().equals(formatted5)) {
                    com1Stby.setText(formatted5);
                    log.trace("updateFromXplane(): [" + command + "] -> com1 stand-by set to " + formatted5);
                }
                break;

            case "com2_stdby_freq":
                String raw6 = value.get(0);
                String formatted6 = raw6.substring(0, 3) + "." + raw6.substring(3);
                if (!com2Stby.getText().equals(formatted6)) {
                    com2Stby.setText(formatted6);
                    log.trace("updateFromXplane(): [" + command + "] -> com2 stand-by set to " + formatted6);
                }
                break;

            case "nav1_stdby_freq":
                String raw7 = value.get(0);
                String formatted7 = raw7.substring(0, 3) + "." + raw7.substring(3);
                if (!nav1Stby.getText().equals(formatted7)) {
                    nav1Stby.setText(formatted7);
                    log.trace("updateFromXplane(): [" + command + "] -> nav1 stand-by set to " + formatted7);
                }
                break;

            case "nav2_stdby_freq":
                String raw8 = value.get(0);
                String formatted8 = raw8.substring(0, 3) + "." + raw8.substring(3);
                if (!nav2Stby.getText().equals(formatted8)) {
                    nav2Stby.setText(formatted8);
                    log.trace("updateFromXplane(): [" + command + "] -> nav2 stand-by set to " + formatted8);
                }
                break;

            case "adf1_freq":
                String raw9 = value.get(0);
                if (!adf1Text.getText().equals(raw9)) {
                    adf1Text.setText(raw9);
                    log.trace("updateFromXplane(): [" + command + "] -> adf1 set to " + raw9);
                }
                break;

            case "adf1_stdby_freq":
                String raw10 = value.get(0);
                if (!adf1Stby.getText().equals(raw10)) {
                    adf1Stby.setText(raw10);
                    log.trace("updateFromXplane(): [" + command + "] -> adf1 stand-by set to " + raw10);
                }
                break;

        }

    }

    @Override
    public void disableAll(boolean state) {}

    /**
     * Triggers when enter is pressed within a radio text field
     * @param event the trigger from the specific tect field
     */
    @Override
    @FXML
    public void addToField(ActionEvent event) {

        log.debug("addToField(): ActionEvent called: " + event);

        if (SocketConnect.socket != null) {

            RadioTextField b = (RadioTextField) event.getSource();
            String field_id = b.getId();

            switch (field_id) {

                case "com1text":
                    String val = com1Text.getText();
                    if (matchesFreqFormat(val, 3)) {
                        val = formatFreqToSend(val, 3);
                        main_controller.sendToXplane("set", "com1_freq", val);
                        log.trace("addToField(): Com1 active set to " + val + " in Xplane.");
                    }
                    break;

                case "com1stby":
                    String val2 = com1Stby.getText();
                    if (matchesFreqFormat(val2, 3)) {
                        val2 = formatFreqToSend(val2, 3);
                        main_controller.sendToXplane("set", "com1_stdby_freq", val2);
                        log.trace("addToField(): Com1 standby set to " + val2 + " in Xplane.");
                    }
                    break;

                case "com2text":
                    String val3 = com2Text.getText();
                    if (matchesFreqFormat(val3, 3)) {
                        val3 = formatFreqToSend(val3, 3);
                        main_controller.sendToXplane("set", "com2_freq", val3);
                        log.trace("addToField(): Com2 active set to " + val3 + " in Xplane.");
                    }
                    break;

                case "com2stby":
                    String val4 = com2Stby.getText();
                    if (matchesFreqFormat(val4, 3)) {
                        val4 = formatFreqToSend(val4, 3);
                        main_controller.sendToXplane("set", "com2_stdby_freq", val4);
                        log.trace("addToField(): Com2 standby set to " + val4 + " in Xplane.");
                    }
                    break;

                case "nav1text":
                    String val5 = nav1Text.getText();
                    if (matchesFreqFormat(val5, 2)) {
                        val5 = formatFreqToSend(val5, 2);
                        main_controller.sendToXplane("set", "nav1_freq", val5);
                        log.trace("addToField(): Nav1 active set to " + val5 + " in Xplane.");
                    }
                    break;

                case "nav1stby":
                    String val6 = nav1Stby.getText();
                    if (matchesFreqFormat(val6, 2)) {
                        val6 = formatFreqToSend(val6, 2);
                        main_controller.sendToXplane("set", "nav1_stdby_freq", val6);
                        log.trace("addToField(): Nav1 standby set to " + val6 + " in Xplane.");
                    }
                    break;

                case "nav2text":
                    String val7 = nav2Text.getText();
                    if (matchesFreqFormat(val7, 2)) {
                        val7 = formatFreqToSend(val7, 2);
                        main_controller.sendToXplane("set", "nav2_freq", val7);
                        log.trace("addToField(): Nav2 active set to " + val7 + " in Xplane.");
                    }
                    break;

                case "nav2stby":
                    String val8 = nav2Stby.getText();
                    if (matchesFreqFormat(val8, 2)) {
                        val8 = formatFreqToSend(val8, 2);
                        main_controller.sendToXplane("set", "nav2_stdby_freq", val8);
                        log.trace("addToField(): Nav2 standby set to " + val8 + " in Xplane.");
                    }
                    break;

                case "transponderCode":
                    String val9 = transponder.getText();
                    if (val9.matches("[0-9]{4}")) {
                        main_controller.sendToXplane("set", "transponder_code", val9);
                        log.trace("addToField(): Transponder set to " + val9 + " in Xplane.");
                    }
                    break;

                case "adf1text":
                    String val10 = adf1Text.getText();
                    if (matchesFreqFormat(val10, 0)) {
                        val10 = formatFreqToSend(val10, 0);
                        main_controller.sendToXplane("set", "adf1_freq", val10);
                        log.trace("addToField(): Adf1 set to " + val10 + " in Xplane.");
                    }
                    break;

                case "adf1stby":
                    String val11 = adf1Stby.getText();
                    if (matchesFreqFormat(val11, 0)) {
                        val11 = formatFreqToSend(val11, 0);
                        main_controller.sendToXplane("set", "adf1_stdby_freq", val11);
                        log.trace("addToField(): Adf1 stand-by set to " + val11 + " in Xplane.");
                    }
                    break;
            }
        }
    }

    /**
     * Matches frequency input to be in 1XX. format plus 1 to 3 decimals OR 1XX without decimals.
     * @param value inputted frequency via the radio text fields
     * @param maxDecimals the amount of decimals we want to match to.
     * @return true of number of decimals match, otherwise false
     */
    private boolean matchesFreqFormat(String value, int maxDecimals) {

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
     * @param value a frequency inputted via the radio fields.
     * @return frequency formatted to always having 3 decimals.
     */
    private String formatFreqToSend(String value, int numberOfDecimals) {

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
     * @param event upon clicked swap button
     */
    @FXML
    private void toggleRadio(ActionEvent event) {

        log.debug("toggleRadio(): ActionEvent called: " + event);

        if (SocketConnect.socket != null) {

            SwapButton b = (SwapButton) event.getSource();
            String button_id = b.getId();

            switch (button_id) {

                case "com1swap":
                    main_controller.sendToXplane("cmd", "com1_flip", "");
                    log.trace("toggleRadio(): Com1 frequencies flipped.");
                    break;

                case "com2swap":
                    main_controller.sendToXplane("cmd", "com2_flip", "");
                    log.trace("toggleRadio(): Com1 frequencies flipped.");
                    break;

                case "nav1swap":
                    main_controller.sendToXplane("cmd", "nav1_flip", "");
                    log.trace("toggleRadio(): Com1 frequencies flipped.");
                    break;

                case "nav2swap":
                    main_controller.sendToXplane("cmd", "nav2_flip", "");
                    log.trace("toggleRadio(): Com1 frequencies flipped.");
                    break;

                case "adf1swap":
                    main_controller.sendToXplane("cmd", "adf1_flip", "");
                    log.trace("toggleRadio(): Adf1 frequencies flipped.");
                    break;
            }
        }
    }

    @Override
    public void initElements() {

        /* SWAP BUTTONS */

        com1swap = new SwapButton();
        com1swap.setId("com1swap");
        com1swap.setText("<>");
        com1swap.getStyleClass().add("swap-button");
        com1swap.setOnAction(this::toggleRadio);
        radioGrid.add(com1swap,3, 1);

        com2swap = new SwapButton();
        com2swap.setId("com2swap");
        com2swap.setText("<>");
        com2swap.getStyleClass().add("swap-button");
        com2swap.setOnAction(this::toggleRadio);
        radioGrid.add(com2swap, 3, 2);

        nav1swap = new SwapButton();
        nav1swap.setId("nav1swap");
        nav1swap.setText("<>");
        nav1swap.getStyleClass().add("swap-button");
        nav1swap.setOnAction(this::toggleRadio);
        radioGrid.add(nav1swap, 3, 3);

        nav2swap = new SwapButton();
        nav2swap.setId("nav2swap");
        nav2swap.setText("<>");
        nav2swap.getStyleClass().add("swap-button");
        nav2swap.setOnAction(this::toggleRadio);
        radioGrid.add(nav2swap,3, 4);

        adf1swap = new SwapButton();
        adf1swap.setId("adf1swap");
        adf1swap.setText("<>");
        adf1swap.getStyleClass().add("swap-button");
        adf1swap.setOnAction(this::toggleRadio);
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
        radioGrid.add(nav1Stby,4, 3);

        nav2Stby = new RadioTextField();
        nav2Stby.setId("nav2stby");
        nav2Stby.setText("");
        nav2Stby.getStyleClass().addAll("radio-field");
        nav2Stby.setOnAction(this::addToField);
        radioGrid.add(nav2Stby,4, 4);

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
